package com.fithub.e2etesting.jbehave.steps;

import static org.junit.Assert.assertEquals;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.fithub.e2etesting.page_driver.HomePageDriver;
import com.fithub.e2etesting.page_driver.LoginPageDriver;

public class LoginStep {

	private HomePageDriver homePageDriver;
	private LoginPageDriver loginPageDriver;

	@Autowired
	public LoginStep(WebDriver driver) {
		homePageDriver = PageFactory.initElements(driver, HomePageDriver.class);
		loginPageDriver = PageFactory.initElements(driver, LoginPageDriver.class);
	}

	@Given("I am registeredUser $registeredUser")
	public void givenIAmRegisteredUser(String username) throws Throwable {

		whenILoginWithUserNamePassword(username, "adminadmin");

	}

	@When("I login with username $username password $password")
	public void whenILoginWithUserNamePassword(String username, String password) throws Throwable {
		homePageDriver.clickSignIn();
		loginPageDriver.enterUserName(username);
		loginPageDriver.enterPassword(password);
		loginPageDriver.submitLoginForm();
	}

	@Given("a stock of symbol $symbol and a threshold of $threshold")
	public void aStock(@Named("threshold") double aThreshold, @Named("symbol") String aSymbol) {
		System.out.println(aSymbol);
		System.out.println(aThreshold);
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
