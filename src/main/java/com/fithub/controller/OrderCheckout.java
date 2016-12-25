package com.fithub.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller for handling Order checkout
 * 
 */
@Controller
public class OrderCheckout {

	private static final Logger LOG = LoggerFactory.getLogger(OrderCheckout.class);

	@RequestMapping(value = "/orderCheckout")
	public String getOrderCheckoutPage() {

		LOG.debug("Getting Order Checkout Page");

		return "product/checkout";
	}

}
