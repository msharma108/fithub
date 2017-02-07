package com.fithub.controller;

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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fithub.domain.UserDTO;
import com.fithub.service.user.UserService;
import com.fithub.service.user.UserTasksHelperService;
import com.fithub.validator.user.UserValidator;

@Controller
@SessionAttributes("userDTO")
public class UserEditController {

	private final UserService userService;
	private static final Logger LOG = LoggerFactory.getLogger(UserEditController.class);
	@Qualifier("userEditValidator")
	private final UserValidator userEditValidator;

	private final UserTasksHelperService userTasksHelperService;
	private final Environment environment;

	@Autowired
	public UserEditController(UserService userService, UserValidator userEditValidator,
			UserTasksHelperService userTasksHelperService, Environment environment) {
		this.userService = userService;
		this.userEditValidator = userEditValidator;
		this.userTasksHelperService = userTasksHelperService;
		this.environment= environment;
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
	@RequestMapping(value = { "/userTask/{userName:.+}",
			"/admin/userTask/{userName:.+}" }, params = "userEdit", method = { RequestMethod.GET, RequestMethod.POST })
	
	public String getUserEditPage(@PathVariable("userName") String userName, @ModelAttribute("userDTO") UserDTO userDTO,
			Model model, Authentication authentication,@Value("${recaptcha.publicKey}") String recaptchaPublicKey) {
		LOG.debug("Getting editUserPage for user={}", userName);

		userDTO.setLoggedInUserName(userTasksHelperService.getLoggedInUserName(authentication));
		userDTO.setLoggedInUserUserRole(userTasksHelperService.getLoggedInUserUserRole(authentication));
		
		// Add non-testing profile recaptcha key for user validation
		if(environment.getProperty("spring.profiles.active") !=null && !environment.getProperty("spring.profiles.active").contains("testing"))
		model.addAttribute("recaptchaPublicKey",recaptchaPublicKey);
		userDTO.setEditable(true);

		return "user/registration";
	}

	// WORK ON THIS NEXT and abstract validator and implementation
	// The register form will have a submit button with name =editUser but value
	// as "update". This will only be shown in case of edit.
	@PostMapping(value = { "/userSave", "/admin/userSave" }, params = "userUpdate")
	public String submitUserEditPage(@Valid @ModelAttribute("userDTO") UserDTO userDTO, BindingResult result,
			RedirectAttributes redirectAttributes, Authentication authentication, SessionStatus sessionStatus) {
		LOG.debug("Attempting to update profile of user={}", userDTO.getUserName());

		// Invoking User Profile Edit in addition to JSR 303 validation
		userEditValidator.validate(userDTO, result);

		if (result.hasErrors()) {
			LOG.debug("Errors in the submitted form");
			// return = forward him to the registration form page
			// This can happen only in case of userUpdate as in userRoleChange,
			// user's input is not involved
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
