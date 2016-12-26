/**
 * 
 */
package com.fithub.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fithub.shoppingcart.ShoppingCart;

/**
 * Controller for the Home page of the application
 *
 */
@Controller

public class HomeController {
	private static final Logger LOG = LoggerFactory.getLogger(HomeController.class);

	@RequestMapping(value = { "/", "/home" })
	public String displayHome(HttpSession session) {
		LOG.debug("Retreive home page");

		if (session.getAttribute("shoppingCart") == null) {

			ShoppingCart shoppingCart = new ShoppingCart();
			session.setAttribute("shoppingCart", shoppingCart);
		}

		return "home";
	}

}
