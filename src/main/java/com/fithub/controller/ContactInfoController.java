/**
 * 
 */
package com.fithub.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller for the contact information page of the application
 *
 */
@Controller

public class ContactInfoController {
	private static final Logger LOG = LoggerFactory.getLogger(ContactInfoController.class);

	@RequestMapping(value = { "/contactUs" })
	public String displayContactUsPage() {
		LOG.debug("Retreive contact us page");

		return "contact";
	}

}
