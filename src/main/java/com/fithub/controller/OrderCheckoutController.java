package com.fithub.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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

	@RequestMapping(value = "/orderCheckout")
	public String getOrderCheckoutPage() {

		LOG.debug("Getting Order Checkout Page");

		return "product/checkout";
	}

	@RequestMapping(value = "/handleOrderCheckout")
	public String handleOrderCheckout(HttpServletRequest request, HttpSession session) throws AuthenticationException,
			InvalidRequestException, APIConnectionException, CardException, APIException {

		LOG.debug("Handling order checkout");
		// private key for test account
		Stripe.apiKey = "sk_test_AM33dQCKgInsAIX0OcT17kYJ";
		String paymentToken = request.getParameter("stripeToken");
		LOG.debug("payment token from stripe={}", paymentToken);

		BigDecimal dollarToCents = new BigDecimal(1000);

		ShoppingCart shoppingCart = (ShoppingCart) session.getAttribute("shoppingCart");
		// Create a bill for customer

		LOG.debug("Creating a bill for the customer to be sent to Stripe PG");

		Map<String, Object> bill = new HashMap<String, Object>();
		// Amount in cents
		bill.put("amount", (shoppingCart.getCartTotalCost().multiply(dollarToCents)).intValue());
		bill.put("currency", "CAD");
		bill.put("source", paymentToken);
		bill.put("description", bill.get("amount"));

		Charge charge = Charge.create(bill);

		return "home";
	}

}
