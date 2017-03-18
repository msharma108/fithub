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

	/**
	 * Method returns the home view for display and sets a shopping cart in the
	 * session if it doesn't already exists
	 * 
	 * @param session
	 *            Http session object
	 * @return the home view for display
	 */
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
