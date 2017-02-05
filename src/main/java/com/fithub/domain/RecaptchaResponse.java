package com.fithub.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class to model the google recaptcha service's response to user validation
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RecaptchaResponse {
	
    @JsonProperty("success")
    private boolean isSuccess;
    
    @JsonProperty("error-codes")
    private List<String> errorCodes;

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public List<String> getErrorCodes() {
		return errorCodes;
	}

	public void setErrorCodes(List<String> errorCodes) {
		this.errorCodes = errorCodes;
	}

}
