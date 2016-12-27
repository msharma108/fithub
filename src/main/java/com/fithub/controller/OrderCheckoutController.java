package com.fithub.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fithub.domain.CustomUser;
import com.fithub.domain.OrderDTO;
import com.fithub.domain.SalesOrder;
import com.fithub.service.salesorder.SalesOrderService;
import com.fithub.shoppingcart.ShoppingCart;
import com.stripe.Stripe;
import com.stripe.exception.APIConnectionException;
import com.stripe.exception.APIException;
import com.stripe.exception.AuthenticationException;
import com.stripe.exception.CardException;
import com.stripe.exception.InvalidRequestException;
import com.stripe.model.Charge;

/**
 * Controller for handling Order checkout
 * 
 */
@Controller
public class OrderCheckoutController {

	private static final Logger LOG = LoggerFactory.getLogger(OrderCheckoutController.class);
	private final SalesOrderService salesOrderService;

	public OrderCheckoutController(SalesOrderService salesOrderService) {
		this.salesOrderService = salesOrderService;
	}

	@RequestMapping(value = "/orderCheckout")
	public String getOrderCheckoutPage(Model model) {

		LOG.debug("Getting Order Checkout Page");
		OrderDTO orderDTO = new OrderDTO();
		model.addAttribute("orderDTO", orderDTO);

		return "product/checkout";
	}

	@RequestMapping(value = "/handleOrderCheckout")
	public String handleOrderCheckout(HttpServletRequest request, HttpSession session,
			@ModelAttribute("orderDTO") OrderDTO orderDTO, Authentication authentication)
			throws AuthenticationException, InvalidRequestException, APIConnectionException, CardException,
			APIException {

		LOG.debug("Handling order checkout");
		// private key for test account
		Stripe.apiKey = "sk_test_AM33dQCKgInsAIX0OcT17kYJ";

		// Stripe Charge creation params
		String paymentToken = request.getParameter("stripeToken");
		BigDecimal orderTotalCost = new BigDecimal(request.getParameter("orderTotalCost"));
		BigDecimal dollarToCents = new BigDecimal(100);

		LOG.debug("payment token from stripe={}", paymentToken);

		LOG.debug("Creating a bill for the customer to be sent to Stripe PG");

		// Stripe charge creation
		Map<String, Object> bill = new HashMap<String, Object>();
		// Amount in cents
		bill.put("amount", (orderTotalCost.multiply(dollarToCents)).intValue());
		bill.put("currency", "CAD");
		bill.put("source", paymentToken);
		bill.put("description", "products sold@Test");

		Charge charge = Charge.create(bill);

		// Prepare orderDTO for Order Creation
		orderDTO = prepareOrderDTO(request, session, authentication, charge, orderDTO);

		// Create order if payment successful
		if (charge.getPaid()) {

			SalesOrder salesOrder = salesOrderService.createSalesOrder(orderDTO);
			session.removeAttribute("shoppingCart");

		}

		// ## Redirect to a Order Success Page. Dont show modal.. but show an
		// actual page with order success. Use redirect attribute to show a
		// section
		// initialize cart there.
		return "redirect:/home";
	}

	private OrderDTO prepareOrderDTO(HttpServletRequest request, HttpSession session, Authentication authentication,
			Charge charge, OrderDTO orderDTO) {

		// Get parameters from request and session
		BigDecimal shippingCost = new BigDecimal(request.getParameter("shippingCost"));
		CustomUser customer = (CustomUser) authentication.getPrincipal();
		ShoppingCart shoppingCart = (ShoppingCart) session.getAttribute("shoppingCart");

		orderDTO.setOrderProductList(shoppingCart.getCartProductList());
		orderDTO.setStripeChargeId(charge.getId());
		orderDTO.setPaymentStatus(charge.getStatus());
		orderDTO.setShippingCharge(shippingCost);
		orderDTO.setCustomerUserNameForThisOrder(customer.getUserName());
		orderDTO.setTax(shoppingCart.getCartTax());

		return orderDTO;

	}

}
