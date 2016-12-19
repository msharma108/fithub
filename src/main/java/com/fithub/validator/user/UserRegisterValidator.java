package com.fithub.validator.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.fithub.domain.UserDTO;
import com.fithub.service.user.UserService;

/**
 * This class validates fields which are not validated by JSR-303 Annotations
 *
 */

@Component
public class UserRegisterValidator extends UserValidator {

	private final UserService userService;
	private static final Logger LOG = LoggerFactory.getLogger(UserRegisterValidator.class);

	@Autowired
	public UserRegisterValidator(UserService userService) {
		this.userService = userService;
	}

	/**
	 * Method validates the custom properties to be validated of the passed in
	 * DTO object
	 * 
	 * @param userDTOBeingValidated
	 * @param errors
	 */
	@Override
	public void validate(Object userDTOBeingValidated, Errors errors) {
		LOG.debug("Initiating validation of userDTO supplied by UserRegisterValidator");
		UserDTO userDTO = (UserDTO) userDTOBeingValidated;
		validateUserNameDoesNotExist(userDTO, errors);
		validatePasswords(userDTO, errors);
	}

	/**
	 * Method checks and populates the errors if the username entered in the
	 * registration form already exists in the database
	 * 
	 * @param userDTO
	 * @param errors
	 */
	private void validateUserNameDoesNotExist(UserDTO userDTO, Errors errors) {
		LOG.debug("Validating the entered username={} is not already in use", userDTO.getUserName());
		if ((userService.getUserByUsername(userDTO.getUserName()) != null)) {
			errors.rejectValue("userName", "userName.exists", "UserName already taken, Choose another");
		}
	}

}
