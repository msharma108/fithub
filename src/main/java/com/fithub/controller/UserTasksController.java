package com.fithub.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fithub.service.user.UserService;

@Controller
public class UserTasksController {

	private final UserService userService;
	private static final Logger LOG = LoggerFactory.getLogger(UserTasksController.class);

	@Autowired
	public UserTasksController(UserService userService) {
		this.userService = userService;
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping(value = { "/admin/viewUsers" })
	public String getAllUsersListPage(Model model) {
		LOG.debug("Attempting to list all the users");
		model.addAttribute("allUsers", userService.getAllUsers());
		return "user/usersList";
	}

	// ##Try the following to get the userName as part of the form's submit
	// action
	// and capture it in controller's Requestmapping and Pathvariable
	// http://stackoverflow.com/questions/786363/html-append-text-value-to-form-action
	// On this JSP , show the admin related operations buttons visible only for
	// admin role except for Edit User Information button

	@PreAuthorize("@userTasksHelperServiceImpl.canAccessUser(principal, #userName)")
	@RequestMapping(value = { "/viewUser/{userName}", "/admin/viewUser/{userName}" })
	public String getUserProfilePage(@PathVariable("userName") String userName, Model model) {
		LOG.debug("Retreiving user page for user={}", userName);

		model.addAttribute("userDTO", userService.getUserByUsername(userName));
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

}
