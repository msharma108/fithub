package com.fithub.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fithub.shoppingcart.ShoppingCart;

/**
 * Controller class for managing authentication requests. Login requests are
 * intercepted by Spring Security FithubSecurityConfig configuration class
 *
 */
@Controller
public class AuthenticationController {

	private static final Logger LOG = LoggerFactory.getLogger(AuthenticationController.class);

	/**
	 * Method retrieves the application's login page for user
	 * 
	 * @param invalidCredentials
	 *            acquires "failure" value from request param when
	 *            authentication requests are declared unsuccessful by Spring
	 *            Security
	 * @param model
	 *            Spring Model object that encompasses request data and invalid
	 *            credentials message
	 * @return Login page for the application
	 */
	@RequestMapping(value = "/login")
	public String getUserLoginPage(@RequestParam(value = "failure", required = false) String invalidCredentials,
			Model model) {
		LOG.debug("Getting Login Page");

		if (invalidCredentials != null) {
			LOG.debug("Invalid credentials passed");
			model.addAttribute("invalidCredentials", "Invalid UserName\\Password provided");
		}

		return "user/login";
	}

	/**
	 * Method handles User logout requests
	 * 
	 * @param model
	 *            Spring Model object that encompasses request data and logout
	 *            message
	 * @param session
	 *            Session object holding session data
	 * @return Login page for the application after logout
	 */
	@RequestMapping(value = "/logoutSuccess")
	public String handleUserLogout(Model model, HttpSession session) {

		LOG.debug("Logging out,handleUserLogout method has been called by FithubSecurityConfig logoutsuccess Url");
		model.addAttribute("logoutMessage", "You've logged out of FitHub");

		// Put an empty shopping Cart back into session
		ShoppingCart shoppingCart = new ShoppingCart();
		session.setAttribute("shoppingCart", shoppingCart);
		return "user/login";
	}

}
