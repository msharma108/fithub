package com.fithub.controller;

import java.util.NoSuchElementException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fithub.domain.PasswordRetrievalDTO;
import com.fithub.domain.User;
import com.fithub.domain.UserDTO;
import com.fithub.mailclient.MailClient;
import com.fithub.service.user.UserService;
import com.fithub.service.user.UserTasksHelperService;
import com.fithub.validator.user.UserValidator;

@Controller
@SessionAttributes("userDTO")
public class UserModifyTasksController {

	private final UserService userService;

	@Qualifier("userEditValidator")
	private final UserValidator userEditValidator;
	private static final Logger LOG = LoggerFactory.getLogger(UserModifyTasksController.class);
	private final UserTasksHelperService userTasksHelperService;
	private final Environment environment;

	@Autowired
	public UserModifyTasksController(UserService userService, UserTasksHelperService userTasksHelperService,
			MailClient restMailClient, UserValidator userEditValidator, Environment environment) {
		this.userService = userService;
		this.userTasksHelperService = userTasksHelperService;
		this.userEditValidator = userEditValidator;
		this.environment = environment;
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
		LOG.debug("Attempting to handle display of security question");

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

			LOG.debug("Checking provided security answers");

			// Check if the answers are true and reset in case of matching
			// answers
			if (userService.resetPassword(user, passwordRetrievalDTO)) {
				redirectAttributes.addFlashAttribute("userTaskTypeCompleted", 5);
				return "redirect:/userTaskSuccess";
			}
		}
		return "user/home";

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
	@RequestMapping(value = { "/userEdit/{userName:.+}",
			"/admin/userEdit/{userName:.+}" }, params = "userEdit", method = { RequestMethod.GET, RequestMethod.POST })

	public String getUserEditPage(@PathVariable("userName") String userName, @ModelAttribute("userDTO") UserDTO userDTO,
			Model model, Authentication authentication, @Value("${recaptcha.publicKey}") String recaptchaPublicKey) {
		LOG.debug("Getting editUserPage for user={}", userName);

		userDTO.setLoggedInUserName(userTasksHelperService.getLoggedInUserName(authentication));
		userDTO.setLoggedInUserUserRole(userTasksHelperService.getLoggedInUserUserRole(authentication));

		// Add non-testing profile recaptcha key for user validation
		if (environment.getProperty("spring.profiles.active") != null
				&& !environment.getProperty("spring.profiles.active").contains("testing"))
			model.addAttribute("recaptchaPublicKey", recaptchaPublicKey);
		userDTO.setEditable(true);

		return "user/registration";
	}

	// WORK ON THIS NEXT and abstract validator and implementation
	// The register form will have a submit button with name =editUser but value
	// as "update". This will only be shown in case of edit.
	@PostMapping(value = { "/userSave", "/admin/userSave" }, params = "userUpdate")
	public String submitUserEditPage(@Valid @ModelAttribute("userDTO") UserDTO userDTO, BindingResult result,
			RedirectAttributes redirectAttributes, Authentication authentication, SessionStatus sessionStatus,
			Model model, @Value("${recaptcha.publicKey}") String recaptchaPublicKey) {
		LOG.debug("Attempting to update profile of user={}", userDTO.getUserName());

		// Invoking User Profile Edit in addition to JSR 303 validation
		userEditValidator.validate(userDTO, result);

		if (result.hasErrors()) {
			LOG.debug("Errors in the submitted form");
			model.addAttribute("recaptchaPublicKey", recaptchaPublicKey);
			return "user/registration";
		}
		userService.updateUserProfile(userDTO);
		LOG.debug("User={} profile update successful,profile", userDTO.getUserName());

		// used to check update success on the canvas page
		redirectAttributes.addFlashAttribute("userTaskTypeCompleted", 4);
		sessionStatus.setComplete();
		if (userTasksHelperService.isLoggedInUserAdmin(authentication))
			return "redirect:/admin/userTaskSuccess";
		else
			return "redirect:/userTaskSuccess";

	}

}
