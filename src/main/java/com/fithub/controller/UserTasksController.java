package com.fithub.controller;

import java.util.NoSuchElementException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fithub.domain.User;
import com.fithub.domain.UserDTO;
import com.fithub.service.user.UserService;
import com.fithub.service.user.UserTasksHelperService;
import com.fithub.validator.user.UserValidator;

@Controller
public class UserTasksController {

	private final UserService userService;
	private static final Logger LOG = LoggerFactory.getLogger(UserTasksController.class);
	@Qualifier("userEditValidator")
	private final UserValidator userEditValidator;

	private final UserTasksHelperService userTasksHelperService;

	@Autowired
	public UserTasksController(UserService userService, UserValidator userEditValidator,
			UserTasksHelperService userTasksHelperService) {
		this.userService = userService;
		this.userEditValidator = userEditValidator;
		this.userTasksHelperService = userTasksHelperService;
	}

	@InitBinder("userDTO")
	protected void initBinder(WebDataBinder binder, UserDTO userDTO, Authentication authentication) {
		userDTO.setLoggedInUserName(userTasksHelperService.getLoggedInUserName(authentication));
		userDTO.setLoggedInUserUserRole(userTasksHelperService.getLoggedInUserUserRole(authentication));
		binder.addValidators(userEditValidator);
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping(value = { "/admin/viewUsers" })
	public String getAllUsersListPage(Model model) {
		LOG.debug("Attempting to list all the users");
		model.addAttribute("allUsers", userService.getAllUsers());
		return "user/usersList";
	}

	// This method will be called after userName is entered in userSearchPage
	// ##Try the following to get the userName as part of the form's submit
	// action
	// and capture it in controller's Requestmapping and Pathvariable
	// http://stackoverflow.com/questions/786363/html-append-text-value-to-form-action
	// http://www.dynamicdrive.com/forums/showthread.php?32900-Append-form-input-to-a-URL
	// On this JSP , show the admin related operations buttons visible only for
	// admin role except for Edit User Information button
	// Or Another Solution for URL
	// Have a helper method helpConstructUrl method in the
	// UserTasksHelperService
	// the url and constructs it in the required manner for this method
	// ## Important
	// On the userProfile page, spring form model attribute should be used so
	// that on form submission the next page (RegisterPage for Editing) should
	// get the fields pre-populated with data. If that doesn't work with the
	// modelAttribute. WE CAN PUT The USER not userDTO OBJECT IN SESSION HERE &
	// retrieve it later at user edit submission handler or role change handler
	// or delete handler without having to query the database again.

	@PreAuthorize("@userTasksHelperServiceImpl.canAccessUser(principal, #userName)")
	@RequestMapping(value = { "/viewUser/{userName}", "/admin/viewUser/{userName}" })
	public String getUserProfilePage(@PathVariable("userName") String userName, Model model) {
		LOG.debug("Retreiving user page for user={}", userName);

		User user = userService.getUserByUsername(userName);
		if (user != null)
			model.addAttribute("userDTO", user);
		else
			throw new NoSuchElementException((String.format("Username=%s not found", userName)));
		return "user/userProfile";
	}

	// ##This preauthorize I feel can be omitted later as the AntMatchers would
	// ensure that
	// Url of pattern /admin cant be reached
	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(value = { "/admin/searchUser" })
	public String getUserSearchPage() {
		LOG.debug("Getting userSearchPage");
		return "user/userSearchPage";
	}

	// On the userProfile page, there should be some buttons with form actions
	// that will re-submit the JSPs model attribute to this controller method.
	// params="editUser" in the RequestMapping allows one form with multiple
	// submit buttons to be differentiated.params here would be the same as the
	// name for the input buttons name attribute
	// If this doesn't work then, I can have the request forwarded from a
	// controller method that
	// takes the username from userProfilePage as Post request with Username as
	// a request.parameter of the form. That method then takes the username and
	// modifies the url link and sends it to this method for admin/user in the
	// required form /admin/userTask/{userName}. And the helper method of
	// the controller will simply take the /admin/userTask as PostMapping.

	@PreAuthorize("@userTasksHelperServiceImpl.canAccessUser(principal, #userName)")
	@RequestMapping(value = { "/userTask/{userName}", "/admin/userTask/{userName}" }, params = "editUser", method = {
			RequestMethod.GET, RequestMethod.POST })
	public String getUserEditPage(@PathVariable("userName") String userName, @ModelAttribute("userDTO") UserDTO userDTO,
			Model model) {
		LOG.debug("Getting editUserPage for user={}", userName);
		userDTO.setEditable(true);
		model.addAttribute("userDTO", userDTO);
		// showRegisterForm is used to show/hide register modal on UI using JS
		model.addAttribute("showRegister", 1);

		// Change to registration page
		return "home";
	}

	@PostMapping(value = "/admin/userSearch")
	public String constructUrlForUserTasks(HttpServletRequest request) {
		LOG.debug("Reconstructing URL for user operations");
		String userName = request.getParameter("userName");

		return ("forward:/admin/viewUser/" + userName);

	}

	// WORK ON THIS NEXT and abstract validator and implementation
	// The register form will have a submit button with name =editUser but value
	// as "update". This will only be shown in case of edit.
	@PostMapping(value = { "/userSave", "/admin/userSave" }, params = "editUser")
	public String submitUserEditPage(@Valid @ModelAttribute("userDTO") UserDTO userDTO, BindingResult result,
			RedirectAttributes redirectAttributes, Authentication authentication) {
		LOG.debug("Attempting to register user", userDTO.getUserName());

		if (result.hasErrors()) {
			LOG.debug("Errors in the submitted form");
			// return = forward him to the registration form page
			return "canvas";
		}
		userService.createUser(userDTO);
		LOG.debug("Registration successful, heading to the jsp");

		// used to check login success on the canvas page
		redirectAttributes.addFlashAttribute("userRegisterSuccess", "enabled");
		if (authentication.isAuthenticated())
			return "redirect:/admin/userRegisterSuccess";
		else
			return "redirect:/userRegisterSuccess";

	}

}
