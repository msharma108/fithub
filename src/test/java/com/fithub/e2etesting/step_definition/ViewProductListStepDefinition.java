package com.fithub.e2etesting.step_definition;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.fithub.e2etesting.page_driver.HeaderPageDriver;

import cucumber.api.PendingException;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * Step Definition class for Viewing the list of all products
 *
 */

public class ViewProductListStepDefinition extends AbstractStepDefinition {

	// @Autowired
	// HeaderPageDriver headerPageDriver;

	private HeaderPageDriver headerPageDriver;

	private final WebDriver driver;

	@Autowired
	String url;

	@Autowired
	public ViewProductListStepDefinition(WebDriver driver) {
		this.driver = driver;
		headerPageDriver = PageFactory.initElements(driver, HeaderPageDriver.class);
	}

	@Before
	public void beforeScenarios() {
		System.out.println(url);
		driver.get(url);
	}

	@Before("@viewProducts")
	@Sql(scripts = "classpath:/integration_test_scripts/product_service-test-data-creation.sql")
	public void beforeScenario() {
		System.out.println("before");
	}

	@After("@viewProducts")
	@Sql(scripts = "classpath:/integration_test_scripts/product_service-test-data-deletion.sql")
	public void afterScenario() {
		System.out.println("after");
	}

	@Given("^I am on the home page$")
	public void i_am_on_the_home_page() throws Throwable {

		headerPageDriver.selectSignUp();
	}

	@When("^I decide to view all products$")
	public void i_decide_to_view_all_products() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		throw new PendingException();
	}

	@Then("^I see list of all the products$")
	public void i_see_list_of_all_the_products() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		throw new PendingException();
	}

}
