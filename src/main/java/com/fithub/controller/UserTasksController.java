package com.fithub.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.fithub.domain.UserDTO;
import com.fithub.service.user.UserService;

/**
 * Controller for registration of users either by an admin or an anonymous user
 *
 */
@Controller
public class UserTasksController {

	private static final Logger LOG = LoggerFactory.getLogger(UserTasksController.class);

	// User Service needed for interfacing with DB through DAO
	private final UserService userService;

	@Autowired
	public UserTasksController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping(value = { "/registerUser", "/admin/links" })
	public String getRegisterPage(Model model) {
		LOG.debug("Displaying User Registration page");
		UserDTO userDTO = new UserDTO();
		model.addAttribute("userDTO", userDTO);
		model.addAttribute("showRegister", 1);
		return "home";
	}

}
