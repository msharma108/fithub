package com.fithub.validator.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.Errors;

import com.fithub.domain.UserDTO;
import com.fithub.domain.UserRole;

public class UserEditValidator extends UserValidator {

	private static final Logger LOG = LoggerFactory.getLogger(UserEditValidator.class);

	/**
	 * Method validates the custom properties to be validated of the passed in
	 * DTO object
	 * 
	 * @param userDTOBeingValidated
	 * @param errors
	 */
	@Override
	public void validate(Object userDTOBeingValidated, Errors errors) {
		LOG.debug("Initiating validation of userDTO supplied by UserEditValidator");
		UserDTO userDTO = (UserDTO) userDTOBeingValidated;
		validatePasswords(userDTO, errors);
		validateUserNameNotChangedByNonAdmin(userDTO, errors);
	}

	/**
	 * Method checks and populates the errors if the userName entered in the
	 * user update form is changed by a Non-admin
	 * 
	 * @param userDTO
	 * @param errors
	 */
	private void validateUserNameNotChangedByNonAdmin(UserDTO userDTO, Errors errors) {
		LOG.debug("Validating the entered username={} is not being changed by a Non-Admin", userDTO.getUserName());
		// If the Logged in User is not admin and is attempting to submit a
		// different userName during profile update, give validation error
		if ((userDTO.getLoggedInUserUserRole().equals(UserRole.CUSTOMER.getRoleAsString()))
				&& !(userDTO.getUserName().equals(userDTO.getLoggedInUserName()))) {
			errors.rejectValue("userName", "userName.changeByNonAdmin");
		}

	}

}
