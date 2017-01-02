package com.fithub.domain;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

public class PasswordRetrievalDTO {

	String securityQuestion;

	String userName;

	@NotEmpty
	String securityAnswer = "";

	@Size(min = 6, max = 7)
	@NotEmpty
	String zip = "";

	public String getSecurityQuestion() {
		return securityQuestion;
	}

	public String getSecurityAnswer() {
		return securityAnswer;
	}

	public String getZip() {
		return zip;
	}

	public void setSecurityQuestion(String securityQuestion) {
		this.securityQuestion = securityQuestion;
	}

	public void setSecurityAnswer(String securityAnswer) {
		this.securityAnswer = securityAnswer;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
