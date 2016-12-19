/**
 * 
 */
package com.fithub.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller for the Home page of the application
 *
 */
@Controller

public class HomeController {
	private static final Logger LOG = LoggerFactory.getLogger(HomeController.class);

	@RequestMapping(value = { "/", "/home" })
	public String displayHome() {
		LOG.debug("Retreive home page");
		return "home";
	}

}
