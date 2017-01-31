package com.fithub.e2etesting.jbehave.steps;

import static org.junit.Assert.assertEquals;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fithub.e2etesting.page_driver.HomePageDriver;
import com.fithub.e2etesting.page_driver.LoginPageDriver;

@Component
public class LoginStep {

	private HomePageDriver homePageDriver;
	private LoginPageDriver loginPageDriver;

	@Autowired
	public LoginStep(WebDriver driver) {
		homePageDriver = PageFactory.initElements(driver, HomePageDriver.class);
		loginPageDriver = PageFactory.initElements(driver, LoginPageDriver.class);
	}

	@Given("I am user $userName")
	public void givenIAmUser(@Named("userName") String userName) throws Throwable {

		whenILoginWithUserNamePassword(userName, "adminadmin");

	}

	@When("I login with username $username password $password")
	public void whenILoginWithUserNamePassword(String userName, String password) throws Throwable {
		homePageDriver.clickSignIn();
		loginPageDriver.enterUserName(userName);
		loginPageDriver.enterPassword(password);
		loginPageDriver.submitLoginForm();
	}

	@Then("I am authenticated successfully")
	public void thenIAmAuthenticatedSuccessfully() {
		assertEquals("Problems with authentication", false, loginPageDriver.isGuestWelcomeDisplayed());
		loginPageDriver.logout();
	}

	@Then("I see login failure")
	public void thenISeeLoginFailure() throws Throwable {
		assertEquals("Problems with authentication", true, loginPageDriver.isLoginFailureDisplayed());

	}

}
