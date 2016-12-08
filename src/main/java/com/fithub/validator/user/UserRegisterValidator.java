/**
 * 
 */
package com.fithub.validator.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.fithub.domain.UserDTO;
import com.fithub.service.user.UserService;

/**
 * This class validates fields which are not validated by Hibernate & JSR-303
 * Annotations
 *
 */
public class UserRegisterValidator implements Validator {

	private final UserService userService;
	private static final Logger LOG = LoggerFactory.getLogger(UserRegisterValidator.class);

	@Autowired
	public UserRegisterValidator(UserService userService) {
		this.userService = userService;
	}

	@Override
	public boolean supports(Class<?> classToBeValidated) {
		LOG.debug("Attempting to check if {} is supported", classToBeValidated.getSimpleName());
		return classToBeValidated.equals(UserDTO.class);
	}

	@Override
	public void validate(Object userDTOBeingValidated, Errors errors) {
		LOG.debug("Initiating validation of userDTO supplied");
		UserDTO userDTO = (UserDTO) userDTOBeingValidated;
		validateUserName(userDTO, errors);
		validatePasswords(userDTO, errors);
	}

	private void validatePasswords(UserDTO userDTO, Errors errors) {
		LOG.debug("Validating if the supplied passwords match");
		if (!userDTO.getPassword().equals(userDTO.getRepeatPassword())) {
			errors.reject("The provided passwords do not match");
		}

	}

	private void validateUserName(UserDTO userDTO, Errors errors) {
		LOG.debug("Validating username");
		if ((userService.getUserByUsername(userDTO.getUserName()) != null)) {
			errors.reject("Username has already been taken,Choose another");
		}
	}

}
