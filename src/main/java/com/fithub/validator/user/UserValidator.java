package com.fithub.validator.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.fithub.domain.UserDTO;
import com.fithub.service.captcha.CaptchaService;

@Component
public abstract class UserValidator implements Validator {

	private static final Logger LOG = LoggerFactory.getLogger(UserValidator.class);
	
	@Autowired
	private CaptchaService recaptchaService;
	
	


	/**
	 * Method returns true if the passed object can be validated by the given
	 * validator
	 * 
	 * @param classToBeValidated
	 *            Class that this validator is being asked if it can validate
	 *
	 * @return boolean
	 */
	@Override
	public boolean supports(Class<?> validator) {
		LOG.debug("Attempting to check if {}.class is supported", validator.getSimpleName());
		return validator.isAssignableFrom(UserDTO.class);
	}

	/**
	 * Method populates the errors if the passwords entered in the user register
	 * form don't match
	 * 
	 * @param userDTO
	 * @param errors
	 */
	protected void validatePasswords(UserDTO userDTO, Errors errors) {
		LOG.debug("Validating if the supplied passwords match");
		if (!userDTO.getPassword().equals(userDTO.getRepeatPassword())) {
			errors.rejectValue("password", "password.mismatch", "The entered passwords do not match");
		}
	}
	
	/** Method checks recaptcha response from user, validating that the user is not a robot
	 * @param userDTO
	 * @param errors
	 */
	protected void validateRecaptchaResponse(UserDTO userDTO, Errors errors) {
		LOG.debug("Validating if the user is a human");
	
            if (!userDTO.getRecaptchaResponse().isEmpty() && userDTO.getRecaptchaResponse() != null
                    && !recaptchaService.isCaptchaResponseValid(userDTO.getRecaptchaResponse())) 
            	
                errors.rejectValue("captcha", "captcha.invalid","Invalid captcha provided");
           
	}

	@Override
	public abstract void validate(Object target, Errors errors);

}
