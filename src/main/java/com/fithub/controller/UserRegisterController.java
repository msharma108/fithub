package com.fithub.controller;

import java.util.Map;

import javax.validation.Valid;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.fithub.domain.UserDTO;
import com.fithub.service.user.UserService;
import com.fithub.validator.user.UserRegisterValidator;
import com.fithub.validator.user.UserValidator;

/**
 * Controller for handling user registrations
 *
 */
@Controller
public class UserRegisterController {

	private static final Logger LOG = LoggerFactory.getLogger(UserRegisterController.class);

	// User Service needed for interfacing with DB through DAO
	private final UserService userService;
	@Qualifier("userRegisterValidator")
	private final UserValidator userRegisterValidator;

	@Autowired

	public UserRegisterController(UserRegisterValidator userRegisterValidator, UserService userService) {
		this.userRegisterValidator = userRegisterValidator;
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
		model.addAttribute("showRegister", 1);
		// #Change this to registration page
		return "registration";
	}

	/**
	 * Method handling submissions of user registration forms In case of errors,
	 * it displays the errors to the users on the view In case of no errors in
	 * the form validation , it saves the user to the database. Redirects to
	 * registration success page based on the logged in user's role
	 * 
	 * @param userDTO
	 * @param result
	 * @return view based on results
	 */
	@PostMapping(value = { "/userSave", "/admin/userSave" }, params = "userRegister")
	public String submitUserRegisterPage(@Valid @ModelAttribute("userDTO") UserDTO userDTO, BindingResult result,
			RedirectAttributes redirectAttributes, Authentication authentication) {
		LOG.debug("Attempting to register user", userDTO.getUserName());

		// Invoking User Registration in addition to JSR 303 validation
		userRegisterValidator.validate(userDTO, result);

		if (result.hasErrors()) {
			LOG.debug("Errors in the submitted form");
			// return = forward him to the registration form page
			return "registration";
		}
		userService.createUser(userDTO);
		LOG.debug("Registration successful, heading to the jsp");

		// used to check login success on the canvas page
		redirectAttributes.addFlashAttribute("userRegisterSuccess", "enabled");
		if (authentication.isAuthenticated())
			return "redirect:/admin/userSaveSuccess";
		else
			return "redirect:/userSaveSuccess";

	}

	@RequestMapping(value = { "/userSaveSuccess", "/admin/userSaveSuccess" })
	public String getUserRegisterSuccessPage(HttpServletRequest request) {

		// Preventing problem with page refresh in case of flash attribute
		// Reference:
		// http://www.tikalk.com/redirectattributes-new-feature-spring-mvc-31/
		Map<String, ?> checkMap = RequestContextUtils.getInputFlashMap(request);
		if (checkMap != null)

			// Success Page could be on registration itself
			// Handles RegisterSuccess and UpdateSuccess
			return "user/userRegisterSuccess";
		else
			return "home";
	}
}
