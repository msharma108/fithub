package com.fithub.e2etesting.step_definition;

import static org.junit.Assert.assertEquals;

import javax.sql.DataSource;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.fithub.e2etesting.page_driver.HomePageDriver;
import com.fithub.e2etesting.page_driver.LoginPageDriver;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class LoginStepDefinition extends AbstractStepDefinition {

	private HomePageDriver homePageDriver;
	private LoginPageDriver loginPageDriver;

	private final DataSource datasource;

	private final WebDriver driver;

	@Autowired
	public LoginStepDefinition(WebDriver driver, DataSource datasource) {
		this.driver = driver;
		this.datasource = datasource;
		homePageDriver = PageFactory.initElements(driver, HomePageDriver.class);
		loginPageDriver = PageFactory.initElements(driver, LoginPageDriver.class);
	}

	@Given("^I am on the home page$")
	public void i_am_on_the_home_page() throws Throwable {

		String homePageTitle = "FitHub.com";
		String homeURL = "https://localhost:8443/";

		driver.get(homeURL);
		homePageDriver.assertPageTitle(homePageTitle);
	}

	@Given("^I am \"([^\"]*)\"$")
	public void i_am(String userName) throws Throwable {
		i_login_with_username_password(userName, "adminadmin");
	}

	@When("^I login with username \"([^\"]*)\" password \"([^\"]*)\"$")
	public void i_login_with_username_password(String userName, String password) throws Throwable {
		homePageDriver.clickSignIn();
		loginPageDriver.enterUserName(userName);
		loginPageDriver.enterPassword(password);
		loginPageDriver.submitLoginForm();
	}

	@Then("^I am authenticated successfully$")
	public void i_am_authenticated_successfully() throws Throwable {
		assertEquals("Problems with authentication", false, loginPageDriver.isGuestWelcomeDisplayed());
		loginPageDriver.logout();
	}

	@Then("^I see login failure$")
	public void i_see_login_failure() throws Throwable {
		assertEquals("Problems with authentication", true, loginPageDriver.isLoginFailureDisplayed());

	}

}
