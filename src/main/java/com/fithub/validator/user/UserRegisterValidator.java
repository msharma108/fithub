package com.fithub.validator.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.fithub.domain.UserDTO;
import com.fithub.service.user.UserService;

/**
 * This class validates fields which are not validated by JSR-303 Annotations
 *
 */
public class UserRegisterValidator implements Validator {

	private final UserService userService;
	private static final Logger LOG = LoggerFactory.getLogger(UserRegisterValidator.class);

	@Autowired
	public UserRegisterValidator(UserService userService) {
		this.userService = userService;
	}

	/**
	 * Method Returns true if the passed object can validate the given class's
	 * object
	 * 
	 * @param classToBeValidated
	 * @return boolean
	 */
	@Override
	public boolean supports(Class<?> validator) {
		LOG.debug("Attempting to check if {} is supported", validator.getSimpleName());
		return validator.isAssignableFrom(UserDTO.class);
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
		LOG.debug("Initiating validation of userDTO supplied");
		UserDTO userDTO = (UserDTO) userDTOBeingValidated;
		validateUserName(userDTO, errors);
		validatePasswords(userDTO, errors);
	}

	/**
	 * Method populates the errors if the passwords entered in the user register
	 * form don't match
	 * 
	 * @param userDTO
	 * @param errors
	 */
	private void validatePasswords(UserDTO userDTO, Errors errors) {
		LOG.debug("Validating if the supplied passwords match");
		if (!userDTO.getPassword().equals(userDTO.getRepeatPassword())) {
			errors.rejectValue("password", "password.mismatch");
		}

	}

	/**
	 * Method checks and populates the errors if the username entered in the
	 * registration form already exists in the database
	 * 
	 * @param userDTO
	 * @param errors
	 */
	private void validateUserName(UserDTO userDTO, Errors errors) {
		LOG.debug("Validating username");
		if ((userService.getUserByUsername(userDTO.getUserName()) != null)) {
			errors.rejectValue("userName", "userName.exists");
		}
	}

}
