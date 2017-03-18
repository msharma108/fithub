package com.fithub.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.fithub.domain.CustomUser;
import com.fithub.domain.OrderDTO;
import com.fithub.domain.SalesOrder;
import com.fithub.service.email.MailServiceImpl;
import com.fithub.service.salesorder.SalesOrderHelperService;
import com.fithub.service.salesorder.SalesOrderService;
import com.fithub.shoppingcart.ShoppingCart;
import com.stripe.Stripe;
import com.stripe.exception.APIConnectionException;
import com.stripe.exception.APIException;
import com.stripe.exception.AuthenticationException;
import com.stripe.exception.CardException;
import com.stripe.exception.InvalidRequestException;
import com.stripe.model.Charge;
import com.stripe.model.Refund;

/**
 * Controller for handling Order checkout
 * 
 */
@Controller
public class OrderCheckoutController {

	private static final Logger LOG = LoggerFactory.getLogger(OrderCheckoutController.class);
	private final SalesOrderService salesOrderService;
	private final SalesOrderHelperService salesOrderHelperService;
	private final MailServiceImpl restMailClient;
	private final BigDecimal dollarToCents = new BigDecimal(100);

	/**
	 * Constructor for OrderCheckoutController
	 * 
	 * @param salesOrderService
	 * @param restMailClient
	 * @param salesOrderHelperService
	 */
	public OrderCheckoutController(SalesOrderService salesOrderService, MailServiceImpl restMailClient,
			SalesOrderHelperService salesOrderHelperService) {
		this.salesOrderService = salesOrderService;
		this.restMailClient = restMailClient;
		this.salesOrderHelperService = salesOrderHelperService;
	}

	/**
	 * Method returns the order checkout page for display
	 * 
	 * @param model
	 *            Spring Model object that can encompass request data
	 * @return returns the order checkout page for display
	 */
	@RequestMapping(value = "/orderCheckout")
	public String getOrderCheckoutPage(Model model) {

		LOG.debug("Getting Order Checkout Page");
		OrderDTO orderDTO = new OrderDTO();
		model.addAttribute("orderDTO", orderDTO);

		return "product/checkout";
	}

	/**
	 * Method handles order checkout request to process the sales order in case
	 * of failure or success
	 * 
	 * @param request
	 *            HttpServlet request object encapsulating hidden form
	 *            parameters
	 * @param session
	 *            Http session object
	 * @param orderDTO
	 *            Data Transfer Object(DTO) for order that captures order
	 *            related data from the UI and also presents it on the UI.
	 * @param authentication
	 *            Spring Security core Authentication instance that comprises of
	 *            the authenticated user's security details
	 * @return redirection to success or failure view based on the result
	 * @throws AuthenticationException
	 *             Stripe authentication exception
	 * @throws InvalidRequestException
	 *             Stripe invalid request exception
	 * @throws APIConnectionException
	 *             Stripe api connection exception
	 * @throws CardException
	 *             Stripe card exception
	 * @throws APIException
	 *             Stripe api exception
	 */
	@RequestMapping(value = "/handleOrderCheckout")
	public String handleOrderCheckout(HttpServletRequest request, HttpSession session,
			@ModelAttribute("orderDTO") OrderDTO orderDTO, Authentication authentication,
			RedirectAttributes redirectAttribute) throws AuthenticationException, InvalidRequestException,
			APIConnectionException, CardException, APIException {

		LOG.debug("Handling order checkout");
		// private key for test account
		Stripe.apiKey = "sk_test_AM33dQCKgInsAIX0OcT17kYJ";

		// Stripe Charge creation params
		String paymentToken = request.getParameter("stripeToken");
		BigDecimal orderTotalCost = new BigDecimal(request.getParameter("orderTotalCost"));

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

		orderDTO = prepareOrderDTO(request, session, authentication, charge, orderDTO);

		// Create order if payment successful
		if (charge.getPaid()) {

			SalesOrder salesOrder = salesOrderService.createSalesOrder(orderDTO);

			// Shopping cart cleanup after order completion
			session.removeAttribute("shoppingCart");
			session.setAttribute("shoppingCart", new ShoppingCart());

			// send order receipt mail
			restMailClient.sendOrderReceiptMail(salesOrder);

			// flash attribute for orderBookSuccess message
			redirectAttribute.addFlashAttribute("orderTaskTypeCompleted", 1);

		} else
			throw new CardException(charge.getFailureMessage(), charge.getId(), charge.getFailureCode(), paymentToken,
					charge.getCurrency(), paymentToken, charge.getAmount().intValue(), null);

		return "redirect:/orderTaskSuccess";
	}

	/**
	 * Method prepares the order data transfer object with the supplied details
	 * 
	 * @param request
	 *            HttpServlet request object encapsulating hidden form
	 * @param session
	 *            Http session object
	 * @param authentication
	 *            Spring Security core Authentication instance that comprises of
	 *            the authenticated user's security details
	 * @param charge
	 *            Stripe charge object for requesting charging the provided card
	 *            with the order amount
	 * @param orderDTO
	 *            Data Transfer Object(DTO) for order that captures order
	 *            related data from the UI and also presents it on the UI.
	 * @return the OrderDTO object populated with the order details
	 */
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

	/**
	 * Method returns an order success modal on a order task success page or
	 * redirects the user to home in case of refresh
	 * 
	 * @param request
	 *            HttpServlet request object encapsulating hidden form
	 * @return returns an order success modal on a order task success page or
	 *         redirects the user to home in case of refresh
	 */
	@RequestMapping(value = "/orderTaskSuccess")
	public String getOrderBookingSuccessPage(HttpServletRequest request) {

		// Prevent problem with page refresh in case of flash attribute
		// Reference:
		// http://www.tikalk.com/redirectattributes-new-feature-spring-mvc-31/
		LOG.debug("Getting Order booking success Page");
		Map<String, ?> checkMap = RequestContextUtils.getInputFlashMap(request);
		if (checkMap != null)

			// Success Page could be on registration itself
			// Handles RegisterSuccess and UpdateSuccess
			return "order/orderTaskSuccess";
		else
			return "home";
	}

	/**
	 * Method returns the orderList page for display for an admin
	 * 
	 * @param model
	 *            Spring Model object that can encompass request data
	 * @return the orderList page for display
	 */
	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping(value = { "/admin/viewAllOrders" })
	public String getAllOrdersListPage(Model model) {
		LOG.debug("Attempting to list all the orders");
		model.addAttribute("orderList", salesOrderService.getAllSalesOrder());
		return "order/orderList";
	}

	/**
	 * Method returns the order details page for a specific order supplied for a
	 * user
	 * 
	 * @param userName
	 *            User Name of the user whose order is to be viewed either by
	 *            the user themselves or an admin
	 * @param model
	 *            Spring Model object that can encompass request data
	 * @param orderId
	 *            Order Id whose details are to be viewed
	 * @return the order details page for a specific order supplied for a user
	 */
	@PreAuthorize("@userTasksHelperServiceImpl.canAccessUser(principal, #userName)")
	@RequestMapping(value = { "/viewOrder/{userName}/{orderId}", "/admin/viewOrder/{userName}/{orderId}" })
	public String getOrderDetailsPage(@PathVariable("userName") String userName, Model model,
			@PathVariable("orderId") int orderId) {
		LOG.debug("Retreiving the order details of order={}", orderId);

		SalesOrder salesOrder = salesOrderService.getSalesOrderById(orderId);
		if (salesOrder != null) {
			OrderDTO orderDTO = salesOrderHelperService.populateOrderDTOFromOrder(salesOrder);

			model.addAttribute("orderDTO", orderDTO);
		}

		else
			throw new NoSuchElementException((String.format("order=%s not found", orderId)));
		return "order/orderDetails";
	}

	/**
	 * Method returns the list of order details for a specific user viewed by
	 * the user themselves or an admin
	 * 
	 * @param userName
	 *            User name of the user whose order list is of interest
	 * @param model
	 *            Spring Model object that can encompass request data
	 * @return the list of order details for a specific user viewed by the user
	 *         themselves or an admin
	 */
	@PreAuthorize("@userTasksHelperServiceImpl.canAccessUser(principal, #userName)")
	@RequestMapping(value = { "/viewUserAllOrders/{userName:.+}", "/admin/viewUserAllOrders/{userName:.+}" })
	public String getUserOrdersListPage(@PathVariable("userName") String userName, Model model) {
		LOG.debug("Retreiving the orders of user={}", userName);

		List<SalesOrder> userOrdersList = new ArrayList<SalesOrder>();
		userOrdersList = salesOrderService.getSalesOrderListByUserName(userName);
		if (!userOrdersList.isEmpty()) {

			model.addAttribute("orderList", userOrdersList);
		}

		else
			throw new NoSuchElementException("No Sales Order for the Username");
		return "order/orderList";
	}

	/**
	 * Method handles order cancellation and makes necessary refund as well as
	 * updating the stock quantity
	 * 
	 * @param salesOrderId
	 *            Sales order Id of the order which is being canceled
	 * @param authentication
	 *            Spring Security core Authentication instance that comprises of
	 *            the authenticated user's security details
	 * @param redirectAttribute
	 *            Spring MVC RedirectAttribute instance which stores flash
	 *            attribute for redirect requests. The flash attributes within
	 *            the request attribute will have life span of just one redirect
	 *            request
	 * @return order task success page for display if the order cancellation was
	 *         successful
	 * @throws AuthenticationException
	 *             Stripe authentication exception
	 * @throws InvalidRequestException
	 *             Stripe invalid request exception if the charge id for which
	 *             refund is being requested is invalid
	 * @throws APIConnectionException
	 *             Stripe API connection exception
	 * @throws CardException
	 *             Stripe invalid card exception
	 * @throws APIException
	 *             Stripe API general exception
	 */
	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(value = { "/admin/cancelOrder/{salesOrderId}" })
	public String handleOrderCancellation(@PathVariable("salesOrderId") int salesOrderId, Authentication authentication,
			RedirectAttributes redirectAttribute) throws AuthenticationException, InvalidRequestException,
			APIConnectionException, CardException, APIException {
		LOG.debug("Attempting to cancel the order with salesOrderId");
		// private key for test account
		Stripe.apiKey = "sk_test_AM33dQCKgInsAIX0OcT17kYJ";

		// Get order to be cancelled
		SalesOrder salesOrder = salesOrderService.getSalesOrderById(salesOrderId);

		if (salesOrder == null)
			throw new NoSuchElementException((String.format("order=%s not found", salesOrderId)));

		// Create a refund request for Stripe
		Map<String, Object> refundParams = new HashMap<String, Object>();
		refundParams.put("charge", salesOrder.getStripeChargeId());
		refundParams.put("amount", salesOrder.getSalesOrderTotalCost().multiply(dollarToCents).intValue());

		Refund refund = Refund.create(refundParams);

		if (refund.getStatus().equals("succeeded")) {
			// Update sales order in database for cancellation
			salesOrder.setSalesOrderEditedByUser(authentication.getName());
			salesOrder = salesOrderService.cancelSalesOrder(salesOrder, refund, authentication);
		}
		if (salesOrder.getStatus().equals("CANCELED")) {
			redirectAttribute.addFlashAttribute("orderTaskTypeCompleted", 2);
			// Send order cancellation email
			restMailClient.sendOrderCancellationMail(salesOrder);
		}
		return "redirect:/orderTaskSuccess";

	}

}
