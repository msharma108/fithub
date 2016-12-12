package com.fithub.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthenticationController {

	private static final Logger LOG = LoggerFactory.getLogger(AuthenticationController.class);

	@RequestMapping(value = "/login")
	public String getUserLoginPage(@RequestParam(value = "failure", required = false) String invalidCredentials,
			Model model) {
		LOG.debug("Getting Login Page");

		if (invalidCredentials != null) {
			LOG.debug("Invalid credentials passed");
			model.addAttribute("invalidCredentials", "Invalid UserName\\Password provided");
		}

		return "login";
	}

	@RequestMapping(value = "/logoutSuccess")
	public String handleUserLogout(Model model) {

		LOG.debug("Logging out,handleUserLogout method has been called by FithubSecurityConfig logoutsuccess Url");
		model.addAttribute("logoutMessage", "You've logged out of FitHub");

		return "login";
	}

}
