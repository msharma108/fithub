package com.fithub.e2etesting.cucumber.step_definitions;

import static org.junit.Assert.assertEquals;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import com.fithub.e2etesting.page_driver.HomePageDriver;
import com.fithub.e2etesting.page_driver.RegistrationPageDriver;
import com.fithub.e2etesting.page_driver.UserTaskSuccessPageDriver;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class UserRegistrationStepDefinition {

	private HomePageDriver homePageDriver;
	private RegistrationPageDriver registrationPageDriver;
	private UserTaskSuccessPageDriver userTaskSuccessPageDriver;
	private final Environment environment;

	@Autowired
	public UserRegistrationStepDefinition(WebDriver driver, Environment environment) {

		this.environment = environment;
		userTaskSuccessPageDriver = PageFactory.initElements(driver, UserTaskSuccessPageDriver.class);
		homePageDriver = PageFactory.initElements(driver, HomePageDriver.class);
		registrationPageDriver = PageFactory.initElements(driver, RegistrationPageDriver.class);
	}

	@Given("^I decide to Sign up for an account$")
	public void i_decide_to_Sign_up_for_an_account() throws Throwable {
		homePageDriver.clickSignUp();
		boolean expectedUserRegistrationPageVisibility = true;
		assertEquals("User Registration Page not visible", expectedUserRegistrationPageVisibility,
				registrationPageDriver.isUserRegistrationPageVisible());
	}

	@When("^I enter valid details$")
	public void i_enter_valid_details() throws Throwable {
		enterUserRegistrationDetails("newUser");
		registrationPageDriver.submitUserRegistrationForm();
	}

	@Then("^I am registered successfully$")
	public void i_am_registered_successfully() throws Throwable {
		boolean expectedUserRegistrationSuccess = true;

		boolean actualUserRegistrationSuccess = userTaskSuccessPageDriver.isUserRegistrationTaskSuccess();
		assertEquals("Problems signing up user", expectedUserRegistrationSuccess, actualUserRegistrationSuccess);
	}

	@When("^I enter invalid details like existing userName \"([^\"]*)\"$")
	public void i_enter_invalid_details_like_existing_userName(String existingUserName) throws Throwable {
		enterUserRegistrationDetails(existingUserName);
		registrationPageDriver.submitUserRegistrationForm();
	}

	@Then("^I see error message$")
	public void i_see_error_message() throws Throwable {
		boolean expectedUserNameExistsErrorMessageVisible = true;
		assertEquals("Problems signing up user", expectedUserNameExistsErrorMessageVisible,
				registrationPageDriver.isUserNameExistsMessageVisible());
	}

	private void enterUserRegistrationDetails(String userName) {

		registrationPageDriver.enterAddress("testAddress");
		registrationPageDriver.enterCity("testCity");
		registrationPageDriver.selectCountry("Canada");
		registrationPageDriver.enterEmail(environment.getProperty("application.emailSenderAddress"));
		registrationPageDriver.enterPhone("1234567890");
		registrationPageDriver.enterFamilyName("testFamilyName");
		registrationPageDriver.enterGivenName("testGivenName");
		registrationPageDriver.enterZipcode("testZip");
		registrationPageDriver.selectGender("MALE");
		registrationPageDriver.selectPaymentMode("Credit");
		registrationPageDriver.enterDateOfBirth("1989-02-02");
		registrationPageDriver.enterUserName(userName);
		registrationPageDriver.enterPassword("testPassword");
		registrationPageDriver.enterRepeatPassword("testPassword");
		registrationPageDriver.enterProvince("testProvince");
		registrationPageDriver.enterSecurityQuestion("testSecurityQuestion");
		registrationPageDriver.enterSecurityQuestionAnswer("testSecurityQuestionAnswer");
		registrationPageDriver.selectRecaptcha();
	}

}
