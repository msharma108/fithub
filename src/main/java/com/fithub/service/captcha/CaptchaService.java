package com.fithub.service.captcha;

/**
 * An interface for captcha service that ensures human access to application's registration process
 *
 */
public interface CaptchaService {
	
	 /** Method checks the response returned by the recaptcha api
	 * @param response User's response to the recaptcha field
	 * @return boolean response returned by the recaptcha api in return to user's response to captcha field
	 */
	boolean isCaptchaResponseValid(String response);

}
