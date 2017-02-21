package com.fithub.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.fithub.domain.User;
import com.fithub.domain.UserDTO;
import com.fithub.domain.UserRole;
import com.fithub.service.user.UserService;
import com.fithub.service.user.UserTasksHelperService;

@Controller
@SessionAttributes("userDTO")
public class UserReadTasksController {

	private final UserService userService;
	private static final Logger LOG = LoggerFactory.getLogger(UserReadTasksController.class);

	private final UserTasksHelperService userTasksHelperService;

	@Autowired
	public UserReadTasksController(UserService userService, UserTasksHelperService userTasksHelperService,
			Environment environment) {
		this.userService = userService;
		this.userTasksHelperService = userTasksHelperService;
	}

	/**
	 * Method acts as the initial entry point for filtering various ADMIN user
	 * task requests. It retrieves the user profile and redirects request to the
	 * appropriate controller method based on the user task
	 * 
	 * @param userView
	 *            Request Parameter value passed from the user interface for
	 *            ADMIN user task type - view user profile
	 * @param userEdit
	 *            Request Parameter value passed from the user interface for
	 *            ADMIN user task type - edit user profile
	 * @param userDelete
	 *            Request Parameter value passed from the user interface for
	 *            ADMIN user task type - delete user profile
	 * @param userRoleChange
	 *            Request Parameter value passed from the user interface for
	 *            ADMIN user task type - change user role
	 * @param request
	 *            HttpServlet request object encapsulating hidden form
	 *            parameters
	 * @param authentication
	 *            Spring Security core Authentication instance that comprises of
	 *            the authenticated user's security details
	 * @param model
	 *            Spring Model object that can encompass request data
	 * @return A Forward Request URI that is handled by ADMIN user task specific
	 *         controllers
	 */
	@PostMapping(value = { "/admin/adminOperation" })
	public String constructUrlForAdminUserTasks(@RequestParam(value = "userView", required = false) String userView,
			@RequestParam(value = "userEdit", required = false) String userEdit,
			@RequestParam(value = "userDelete", required = false) String userDelete,
			@RequestParam(value = "userRoleChange", required = false) String userRoleChange,
			@RequestParam(value = "searchUser", required = false) String searchUser, HttpServletRequest request,
			Authentication authentication, Model model) {
		LOG.debug("Reconstructing URL for user operations");
		String userName = request.getParameter("userName");
		String reconstructedUrl = "";

		// check if its a user profile view request
		if (userView != null) {
			LOG.debug("routing request to userView handler");
			reconstructedUrl = "/admin/viewUser/" + userName;
		}
		// check if its a search user profile request
		else if (searchUser != null) {
			String userSearchString = request.getParameter("userSearchString");

			if (userSearchString.equals(""))
				throw new IllegalArgumentException("Please provide search values for searching the user");

			LOG.debug("routing request to search User handler");
			reconstructedUrl = "/admin/searchUser/" + userSearchString;
		} else {
			// Retrieve user by userName
			User user = userService.getUserByUsername(userName);
			if (user == null)
				throw new NoSuchElementException((String.format("Username=%s not found", userName)));
			else {
				UserDTO userDTO = userTasksHelperService.populateUserDTOFromUser(user);

				// check if its a user profile edit request
				if (userEdit != null) {
					LOG.debug("routing request to userEdit handler");
					reconstructedUrl = "/admin/userEdit/" + userName;
				}

				// check if its a user profile delete request
				if (userDelete != null) {
					LOG.debug("routing request to userDelete handler");
					reconstructedUrl = "/admin/userDelete/" + userName;
				}

				// check if its a user role change request
				if (userRoleChange != null) {
					LOG.debug("routing request to userRole change handler");
					userDTO.setRole(UserRole.valueOf(request.getParameter("userRole")));
					reconstructedUrl = "/admin/userRoleChange";
				}
				model.addAttribute("userDTO", userDTO);
			}
		}

		LOG.debug("Reconstructed URL={}", reconstructedUrl);
		return "forward:" + reconstructedUrl;
	}

	/**
	 * Method handles requests for retrieving the list of all the users in the
	 * system
	 * 
	 * @param model
	 *            Spring Model object that can encompass request data
	 * @return View that presents the list of all users
	 */
	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping(value = { "/admin/viewUsers" })
	public String getAllUsersListPage(Model model) {
		LOG.debug("Attempting to list all the users");
		model.addAttribute("allUsers", userService.getAllUsers());
		return "user/usersList";
	}

	/**
	 * Method handles requests to retrieve a user's profile page
	 * 
	 * @param userName
	 *            UserName of the user whose profile is to be retrieved
	 * @param model
	 *            Spring Model object that can encompass request data
	 * @return View that presents the profile page of the user whose UserName
	 *         was passed
	 */
	@PreAuthorize("@userTasksHelperServiceImpl.canAccessUser(principal, #userName)")
	@RequestMapping(value = { "/viewUser/{userName:.+}", "/admin/viewUser/{userName:.+}" })
	public String getUserProfilePage(@PathVariable("userName") String userName, Model model) {
		LOG.debug("Retreiving the profile of user={}", userName);

		User user = userService.getUserByUsername(userName);
		if (user != null) {
			UserDTO userDTO = userTasksHelperService.populateUserDTOFromUser(user);
			model.addAttribute("userDTO", userDTO);
		}

		else
			throw new NoSuchElementException((String.format("Username=%s not found", userName)));
		LOG.debug("Profile page to be invoked");
		return "user/profile";
	}

	/**
	 * Method handles user task success requests and comprises of logic to
	 * display success results on the UI just once thus preventing any issues
	 * that could arise due to refresh
	 * 
	 * @param request
	 *            HttpServlet request object encapsulating hidden form
	 *            parameters
	 * @return A user task success page or the home page in case of refresh on a
	 *         user task execution completion
	 */
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

	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(value = "/admin/searchUser/{userSearchString:.+}", params = "searchUser")
	public String searchUser(Model model, @PathVariable("userSearchString") String userSearchString) {
		LOG.debug("Attempting to search user based on search string ={}", userSearchString);
		List<User> userList = new ArrayList<User>();
		userList = userService.getUsersWithNameContainingUserSearchString(userSearchString);
		model.addAttribute("allUsers", userList);
		return "user/usersList";

	}

}
