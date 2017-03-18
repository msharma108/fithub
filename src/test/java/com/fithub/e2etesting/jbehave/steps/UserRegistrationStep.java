package com.fithub.e2etesting.jbehave.steps;

import static org.junit.Assert.assertEquals;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.fithub.e2etesting.page_driver.HomePageDriver;
import com.fithub.e2etesting.page_driver.RegistrationPageDriver;
import com.fithub.e2etesting.page_driver.UserTaskSuccessPageDriver;

/**
 * Step definition class for user registration testing
 *
 */
@Component
@Profile("jbehave_e2e_testing")
public class UserRegistrationStep {

	private HomePageDriver homePageDriver;
	private UserTaskSuccessPageDriver userTaskSuccessPageDriver;
	private RegistrationPageDriver registrationPageDriver;
	private final Environment environment;

	@Autowired
	public UserRegistrationStep(WebDriver driver, Environment environment) {
		this.environment = environment;
		homePageDriver = PageFactory.initElements(driver, HomePageDriver.class);
		registrationPageDriver = PageFactory.initElements(driver, RegistrationPageDriver.class);
		userTaskSuccessPageDriver = PageFactory.initElements(driver, UserTaskSuccessPageDriver.class);
	}

	@Given("I decide to Sign up for an account")
	public void givenIDecideToSignUpForAnAccount() {
		homePageDriver.clickSignUp();
		boolean expectedUserRegistrationPageVisibility = true;
		assertEquals("User Registration Page not visible", expectedUserRegistrationPageVisibility,
				registrationPageDriver.isUserRegistrationPageVisible());
	}

	@When("I enter invalid details like existing userName $registeredUser")
	public void whenIEnterInvalidDetailsLikeExistingUserName(@Named("registeredUser") String existingUserName) {
		enterUserRegistrationDetails(existingUserName);
		registrationPageDriver.submitUserRegistrationForm();
	}

	@Then("I see error message")
	public void thenISeeErrorMessage() {
		boolean expectedUserNameExistsErrorMessageVisible = true;
		assertEquals("Problems signing up user", expectedUserNameExistsErrorMessageVisible,
				registrationPageDriver.isUserNameExistsMessageVisible());
	}

	@When("I enter valid details")
	public void whenIEnterValidDetails() {
		enterUserRegistrationDetails("newUser");
		registrationPageDriver.submitUserRegistrationForm();
	}

	@Then("I am registered successfully")
	public void thenIAmRegisteredSuccessfully() {
		boolean expectedUserRegistrationSuccess = true;

		boolean actualUserRegistrationSuccess = userTaskSuccessPageDriver.isUserRegistrationTaskSuccess();
		assertEquals("Problems signing up user", expectedUserRegistrationSuccess, actualUserRegistrationSuccess);
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
