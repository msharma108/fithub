package com.fithub.e2etesting.page_driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class LoginPageDriver extends HeaderPageDriver {

	@FindBy(how = How.ID, using = "userNameId")
	WebElement userName;

	@FindBy(how = How.ID, using = "passwordId")
	WebElement password;

	@FindBy(how = How.ID, using = "invalidCredentialsId")
	WebElement invalidCredentials;

	@FindBy(how = How.ID, using = "loginFormId")
	WebElement loginForm;

	public LoginPageDriver(WebDriver driver) {
		super(driver);
		// This can be changed later
		PageFactory.initElements(driver, HeaderPageDriver.class);
	}

	public void enterUserName(String userName) {
		this.userName.sendKeys(userName);
	}

	public void enterPassword(String password) {
		this.password.sendKeys(password);

	}

	public void submitLoginForm() {
		this.password.submit();

	}

	public boolean isLoginFailureDisplayed() {
		return invalidCredentials.isDisplayed();
	}

	public boolean isLoginFormDisplayed() {
		return loginForm.isDisplayed();
	}
}
