package com.fithub.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.fithub.domain.UserDTO;
import com.fithub.service.user.UserService;

/**
 * Controller for handling user registrations
 *
 */
@Controller
public class UserRegisterController {

	private static final Logger LOG = LoggerFactory.getLogger(UserRegisterController.class);

	// User Service needed for interfacing with DB through DAO
	private final UserService userService;

	@Autowired
	public UserRegisterController(UserService userService) {
		this.userService = userService;
	}

	/**
	 * Method for displaying the Registration form
	 * 
	 * @param model
	 * @return registration view
	 */
	@GetMapping(value = { "/userRegister", "/admin/userRegister" })
	public String getUserRegisterPage(Model model) {
		LOG.debug("Displaying User Registration page");
		UserDTO userDTO = new UserDTO();
		model.addAttribute("userDTO", userDTO);
		// showRegisterForm is used to show/hide register modal on UI using JS
		model.addAttribute("showRegisterForm", 1);
		return "home";
	}

	/**
	 * Method handling submissions of user registration forms
	 * 
	 * @param userDTO
	 * @param result
	 * @return view based on results
	 */
	@PostMapping(value = { "/userRegister", "/admin/userRegister" })
	public String submitUserRegisterPage(@Valid @ModelAttribute("userDTO") UserDTO userDTO, BindingResult result) {
		LOG.debug("Attempting to register user", userDTO.getUserName());

		return null;

	}

}
