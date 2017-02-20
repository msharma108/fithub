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
import com.fithub.mailclient.MailClient;
import com.fithub.service.user.UserService;
import com.fithub.service.user.UserTasksHelperService;

@Controller
@SessionAttributes("userDTO")
public class UserTasksController {

	private final UserService userService;
	private final MailClient restMailClient;

	private static final Logger LOG = LoggerFactory.getLogger(UserTasksController.class);
	private final UserTasksHelperService userTasksHelperService;

	@Autowired
	public UserTasksController(UserService userService, UserTasksHelperService userTasksHelperService,
			MailClient restMailClient) {
		this.userService = userService;
		this.userTasksHelperService = userTasksHelperService;
		this.restMailClient = restMailClient;
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
	@PostMapping(value = { "/admin/urlConstructionBasedOnOperation" })
	public String constructUrlForAdminUserTasks(@RequestParam(value = "userView", required = false) String userView,
			@RequestParam(value = "userEdit", required = false) String userEdit,
			@RequestParam(value = "userDelete", required = false) String userDelete,
			@RequestParam(value = "userRoleChange", required = false) String userRoleChange, HttpServletRequest request,
			Authentication authentication, Model model) {
		LOG.debug("Reconstructing URL for user operations");
		String userName = request.getParameter("userName");
		String reconstructedUrl = "";

		if (userView != null) {
			// Request routing for user profile view based on the logged in
			// user's role
			LOG.debug("routing request to userView handler");
			if (userTasksHelperService.isLoggedInUserAdmin(authentication))
				reconstructedUrl = "/admin/viewUser/" + userName;
			else
				reconstructedUrl = "/viewUser/" + userName;

		} else {
			// Retrieve user by userName
			User user = userService.getUserByUsername(userName);
			if (user == null)
				throw new NoSuchElementException((String.format("Username=%s not found", userName)));
			else {
				UserDTO userDTO = userTasksHelperService.populateUserDTOFromUser(user);
				if (userEdit != null) {
					LOG.debug("routing request to userEdit handler");
					reconstructedUrl = "/admin/userEdit/" + userName;
				}
				if (userDelete != null) {
					LOG.debug("routing request to userDelete handler");
					reconstructedUrl = "/admin/userDelete/" + userName;
				}
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
	 * Method handles request for ADMIN user delete tasks for deleting a user
	 * profile
	 * 
	 * @param userName
	 *            UserName of the user whose profile is to be retrieved
	 * @param userDTO
	 *            Data Transfer Object(DTO) for user that captures user related
	 *            data from the UI and also presents it on the UI.
	 * @param redirectAttributes
	 *            Spring MVC RedirectAttribute instance which stores flash
	 *            attribute for redirect requests. The flash attributes within
	 *            the request attribute will have life span of just one redirect
	 *            request
	 * @param model
	 *            Spring Model object that can encompass request data
	 * @param request
	 *            HttpServlet request object encapsulating hidden form
	 *            parameters
	 * @return Redirected request URI that is handled by a dedicated controller
	 *         handling various task success
	 */
	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(value = "/admin/userDelete/{userName:.+}", params = "userDelete")
	public String handleUserDelete(@PathVariable("userName") String userName,
			@ModelAttribute("userDTO") UserDTO userDTO, RedirectAttributes redirectAttributes, Model model,
			HttpServletRequest request) {
		LOG.debug("Attempting to delete user={}", userDTO.getUserName());

		boolean isUserDeleted = userService.deleteUser(userDTO.getUserName());

		LOG.debug("User was delete successfuly ?={}", isUserDeleted);
		if (!isUserDeleted) {
			model.addAttribute("exception", String.format("Username=%s not found", userName));
			model.addAttribute("errorUrl", request.getRequestURL());
			return "customErrorPage";
		}
		redirectAttributes.addFlashAttribute("userTaskTypeCompleted", 2);

		return "redirect:/admin/userTaskSuccess";
	}

	/**
	 * Method handles request for ADMIN user change user role task for switching
	 * a user's role in the application
	 * 
	 * @param userDTO
	 *            Data Transfer Object(DTO) for user that captures user related
	 *            data from the UI and also presents it on the UI.
	 * @param redirectAttributes
	 *            Spring MVC RedirectAttribute instance which stores flash
	 *            attribute for redirect requests. The flash attributes within
	 *            the request attribute will have life span of just one redirect
	 *            request
	 * 
	 * @return Redirected request URI that is handled by a dedicated controller
	 *         handling various task success
	 */
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

	/**
	 * Method displays password retrieval page for the user
	 * 
	 * @return
	 */
	@GetMapping(value = "/passwordRetrieval")
	public String getPasswordRetrievalPage() {
		LOG.debug("Getting password retieval page");

		return "user/passwordRetrieval";
	}

	/**
	 * Method processes password retrieval information submitted on the password
	 * retrieval page. It validates and confirms user's authenticity and in case
	 * of successful validation, sends a randomly generated string token to the
	 * user's registered email address for resetting password
	 * 
	 * @param model
	 *            Spring Model object that can encompass request data
	 * @param request
	 *            HttpServlet request object encapsulating hidden form
	 *            parameters
	 * @param performRetrieval
	 *            Request Parameter that separates requests for retrieving a
	 *            user's security questions based on his stored security
	 *            questions from requests that validate user's response to
	 *            security questions
	 * @param getSecurityChecks
	 *            Request Parameter that separates requests for validating a
	 *            user's response to security questions from requests for
	 *            retrieving a user's security questions based on his stored
	 *            security questions
	 * @param redirectAttributes
	 *            Spring MVC RedirectAttribute instance which stores flash
	 *            attribute for redirect requests. The flash attributes within
	 *            the request attribute will have life span of just one redirect
	 *            request
	 * @param passwordRetrievalDTO
	 *            Data Transfer Object for capturing password retrieval related
	 *            answers from the UI and also display the security questions on
	 *            the UI
	 * @param result
	 *            Spring's BindingResult object that validates the user input on
	 *            password retrieval page for client side validation i.e.
	 *            checking Nulls,formats
	 * @return Returns a UI page based on the results of the password retrieval
	 *         process. Displays the password retrieval page in case of any
	 *         failures or else in case of successful validation of user
	 *         security questions, redirects to home page
	 */
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
