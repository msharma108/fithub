package com.fithub.controller;

import java.util.Map;
import java.util.NoSuchElementException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.fithub.domain.PasswordRetrievalDTO;
import com.fithub.domain.User;
import com.fithub.domain.UserDTO;
import com.fithub.domain.UserRole;
import com.fithub.restmailclient.RestMailClient;
import com.fithub.service.user.UserService;
import com.fithub.service.user.UserTasksHelperService;

@Controller
@SessionAttributes("userDTO")
public class UserTasksController {

	private final UserService userService;
	private final RestMailClient restMailClient;

	private static final Logger LOG = LoggerFactory.getLogger(UserTasksController.class);
	private final UserTasksHelperService userTasksHelperService;

	@Autowired
	public UserTasksController(UserService userService, UserTasksHelperService userTasksHelperService,
			RestMailClient restMailClient) {
		this.userService = userService;
		this.userTasksHelperService = userTasksHelperService;
		this.restMailClient = restMailClient;
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
	@RequestMapping(value = { "/viewUser/{userName:.+}", "/admin/viewUser/{userName:.+}" })
	public String getUserProfilePage(@PathVariable("userName") String userName, Model model) {
		LOG.debug("Retreiving the profile of user={}", userName);

		User user = userService.getUserByUsername(userName);
		if (user != null) {
			UserDTO userDTO = userTasksHelperService.populateUserDTOFromUser(user);
			// setting userDTO with currently logged in user's details
			// # IF ANYTHING BREAKS UNCOMMENT FOLLOWING
			// userDTO.setLoggedInUserName(userTasksHelperService.getLoggedInUserName(authentication));
			// userDTO.setLoggedInUserUserRole(userTasksHelperService.getLoggedInUserUserRole(authentication));
			model.addAttribute("userDTO", userDTO);
		}

		else
			throw new NoSuchElementException((String.format("Username=%s not found", userName)));
		LOG.debug("Profile page to be invoked");
		return "user/profile";
	}

	// ## Not needed as there will be a user search box
	// ##This preauthorize I feel can be omitted later as the AntMatchers would
	// ensure that
	// Url of pattern /admin cant be reached
	// @PreAuthorize("hasAuthority('ADMIN')")
	// @RequestMapping(value = { "/admin/searchUser" })
	// public String getUserSearchPage() {
	// LOG.debug("Getting userSearchPage");
	// return "user/userSearchPage";
	// }

	@PostMapping(value = { "/admin/urlConstructionBasedOnOperation" })
	public String constructUrlForAdminUserTasks(@RequestParam(value = "userView", required = false) String userView,
			@RequestParam(value = "userEdit", required = false) String userEdit,
			@RequestParam(value = "userDelete", required = false) String userDelete,
			@RequestParam(value = "userRoleChange", required = false) String userRoleChange, HttpServletRequest request,
			Authentication authentication, Model model) {
		LOG.debug("Reconstructing URL for user operations");
		String userName = request.getParameter("userName");
		String reconstructedUrl = "";

		// Retrieve user by userName
		User user = userService.getUserByUsername(userName);
		// If user not found display error message
		if (user == null)
			throw new NoSuchElementException((String.format("Username=%s not found", userName)));
		else {
			UserDTO userDTO = userTasksHelperService.populateUserDTOFromUser(user);

			if (userView != null) {
				// Request routing for user profile view based on the logged in
				// user's role
				LOG.debug("routing request to userView handler");
				if (userTasksHelperService.isLoggedInUserAdmin(authentication))
					reconstructedUrl = "/admin/viewUser/" + userName;
				else
					reconstructedUrl = "/viewUser/" + userName;

			}
			if (userEdit != null) {
				LOG.debug("routing request to userEdit handler");
				reconstructedUrl = "/admin/userTask/" + userName;
			}
			if (userDelete != null) {
				LOG.debug("routing request to userDelete handler");
				reconstructedUrl = "/admin/userTask/" + userName;
			}
			if (userRoleChange != null) {
				LOG.debug("routing request to userRole change handler");
				userDTO.setRole(UserRole.valueOf(request.getParameter("userRole")));
				reconstructedUrl = "/admin/userRoleChange";
			}
			LOG.debug("Reconstructed URL={}", reconstructedUrl);
			model.addAttribute("userDTO", userDTO);

			return "forward:" + reconstructedUrl;
		}

	}

	// This method will be reached when the delete button on the user profile
	// page is clicked

	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(value = "/admin/userTask/{userName:.+}", params = "userDelete")
	public String handleUserDelete(@PathVariable("userName") String userName,
			@ModelAttribute("userDTO") UserDTO userDTO, RedirectAttributes redirectAttributes, Model model) {
		LOG.debug("Attempting to delete user={}", userDTO.getUserName());

		boolean isUserDeleted = userService.deleteUser(userDTO.getUserName());

		LOG.debug("User was delete successfuly ?={}", isUserDeleted);
		if (!isUserDeleted) {
			model.addAttribute("exception", String.format("Username=%s not found", userName));
			return "user/customErrorPage";
		}
		redirectAttributes.addFlashAttribute("userTaskTypeCompleted", 2);

		return "redirect:/admin/userTaskSuccess";
	}

	@PostMapping(value = { "/admin/userRoleChange" }, params = "userRoleChange")
	public String handleUserRoleChange(@ModelAttribute("userDTO") UserDTO userDTO,
			RedirectAttributes redirectAttributes) {
		LOG.debug("Attempting to update Role of user={}", userDTO.getUserName());

		userService.changeUserRole(userDTO);
		LOG.debug("User={} role changed successfully", userDTO.getUserName());

		// used to check update success on the canvas page
		redirectAttributes.addFlashAttribute("userTaskTypeCompleted", 3);

		return "redirect:/admin/userTaskSuccess";

	}

	@RequestMapping(value = { "/userTaskSuccess", "/admin/userTaskSuccess" })
	public String getUserTaskSuccessPage(HttpServletRequest request) {

		// Preventing problem with page refresh in case of flash attribute
		// Reference:
		// http://www.tikalk.com/redirectattributes-new-feature-spring-mvc-31/
		LOG.debug("Getting Success Page");
		Map<String, ?> checkMap = RequestContextUtils.getInputFlashMap(request);
		if (checkMap != null)

			// Success Page could be on registration itself
			// Handles RegisterSuccess and UpdateSuccess
			return "user/userTaskSuccess";
		else
			return "home";
	}

	@GetMapping(value = "/passwordRetrieval")
	public String getPasswordRetrievalPage() {
		LOG.debug("Getting password retieval page");

		return "user/passwordRetrieval";
	}

	@PostMapping(value = "/passwordRetrieval")
	public String handlePasswordRetrieval(Model model, HttpServletRequest request,
			@RequestParam(value = "performRetrieval", required = false) String performRetrieval,
			@RequestParam(value = "getSecurityChecks", required = false) String getSecurityChecks,
			RedirectAttributes redirectAttributes,
			@Valid @ModelAttribute("passwordRetrievalDTO") PasswordRetrievalDTO passwordRetrievalDTO,
			BindingResult result) {
		LOG.debug("Attempting to handle display security question");

		// Retrieve & display security question of the user
		if (getSecurityChecks != null) {
			String userName;
			userName = request.getParameter("userName");

			User user = userService.getUserByUsername(userName);
			if (user == null)
				throw new NoSuchElementException(
						(String.format("Username=%s not found", passwordRetrievalDTO.getUserName())));

			// passwordRetrievalDTO = new PasswordRetrievalDTO();
			passwordRetrievalDTO.setSecurityQuestion(user.getSecurityQuestion());
			passwordRetrievalDTO.setUserName(userName);

			model.addAttribute("passwordRetrievalDTO", passwordRetrievalDTO);
			model.addAttribute("performRetrieval", true);
			return "user/passwordRetrieval";

		}
		// Perform checks for security answer and zip to reset password
		else if (performRetrieval != null) {

			User user = userService.getUserByUsername(passwordRetrievalDTO.getUserName());

			passwordRetrievalDTO.setSecurityQuestion(user.getSecurityQuestion());

			if (result.hasErrors()) {
				LOG.debug("Password retrieval answers submitted have errors");
				model.addAttribute("showErrors", true);
				model.addAttribute("performRetrieval", true);
				model.addAttribute("passwordRetrievalDTO", passwordRetrievalDTO);
				return "user/passwordRetrieval";
			}

			LOG.debug("Checking provided security answer");
			// Check if the answers are true
			if (passwordRetrievalDTO.getSecurityAnswer().equals(user.getSecurityQuestionAnswer())
					&& passwordRetrievalDTO.getZip().equals(user.getZipcode())) {

				// Generate a random String as password & update password
				String resetPassword = RandomStringUtils.randomAlphanumeric(6);

				userService.resetPassword(user, resetPassword);

				// Email the user his new password
				restMailClient.sendPasswordResetMail(user.getGivenName(), user.getEmail(), resetPassword);

				redirectAttributes.addFlashAttribute("userTaskTypeCompleted", 5);
				return "redirect:/userTaskSuccess";
			}

		}
		return "user/home";
	}
}
