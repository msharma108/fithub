package com.fithub.e2etesting.step_definition;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptException;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import com.fithub.e2etesting.page_driver.HomePageDriver;
import com.fithub.e2etesting.page_driver.ProductListPageDriver;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * Step Definition class for Viewing the list of all products
 *
 */

public class ViewAllProductListStepDefinition extends AbstractStepDefinition {

	// @Autowired
	// HeaderPageDriver headerPageDriver;

	private HomePageDriver homePageDriver;
	private ProductListPageDriver productListPageDriver;

	@Autowired
	DataSource datasource;

	private final WebDriver driver;

	@Autowired
	public ViewAllProductListStepDefinition(WebDriver driver) {
		this.driver = driver;
		homePageDriver = PageFactory.initElements(driver, HomePageDriver.class);
		productListPageDriver = PageFactory.initElements(driver, ProductListPageDriver.class);
	}

	// @Before
	// public void beforeScenarios() {
	// System.out.println(url);
	// driver.get(url);
	// }

	@Before
	public void beforeScenario() {
		System.out.println("before");
		try {
			ScriptUtils.executeSqlScript(datasource.getConnection(),
					new ClassPathResource("e2e_test_scripts/e2e_testing-test-data-creation.sql"));
		} catch (ScriptException | SQLException e) {

			e.printStackTrace();
		}
	}

	@After
	public void afterScenario() {
		System.out.println("after");
		try {
			ScriptUtils.executeSqlScript(datasource.getConnection(),
					new ClassPathResource("e2e_test_scripts/e2e_testing-test-data-deletion.sql"));
		} catch (ScriptException | SQLException e) {

			e.printStackTrace();
		}
	}

	// Take Screenshot on test failure
	// Reference:
	// https://advancedweb.hu/2015/04/28/animated-failure-reports-with-selenium-and-cucumber/

	@After
	public void captureScenarioFailureScreenshot(Scenario scenario) {
		if (scenario.isFailed()) {
			try {
				byte[] failureScreenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
				scenario.embed(failureScreenshot, "image/png");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Given("^I am on the home page$")
	public void i_am_on_the_home_page() throws Throwable {

		String homePageTitle = "FitHub.com";

		homePageDriver.navigateToHomePage();
		homePageDriver.assertPageTitle(homePageTitle);
	}

	@When("^I decide to view all products$")
	public void i_decide_to_view_all_products() throws Throwable {
		homePageDriver.viewAllOrTopProductsBasedOnInput("viewAllProducts");
	}

	@Then("^I see list of all the products$")
	public void i_see_list_of_all_the_products() throws Throwable {
		assertEquals("Product List not displayed", true, productListPageDriver.isAllProductListDisplayed());
	}

}
