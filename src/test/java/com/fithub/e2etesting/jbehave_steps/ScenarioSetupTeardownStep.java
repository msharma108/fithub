package com.fithub.e2etesting.jbehave_steps;

import javax.sql.DataSource;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.fithub.e2etesting.page_driver.HomePageDriver;

import cucumber.api.Scenario;
import cucumber.api.java.en.Given;

public class ScenarioSetupTeardownStep {

	private final DataSource datasource;

	private final WebDriver driver;

	private final String homeUrl;

	private HomePageDriver homePageDriver;

	@Autowired
	public ScenarioSetupTeardownStep(WebDriver driver, DataSource datasource, String homeUrl) {
		this.driver = driver;
		this.datasource = datasource;
		this.homeUrl = homeUrl;
		homePageDriver = PageFactory.initElements(driver, HomePageDriver.class);
	}

	// @Before
	// public void beforeEachScenario() {
	// try {
	// ScriptUtils.executeSqlScript(datasource.getConnection(),
	// new
	// ClassPathResource("e2e_test_scripts/e2e_testing-test-data-creation.sql"));
	// } catch (ScriptException | SQLException e) {
	// e.printStackTrace();
	// }
	//
	// driver.get(homeUrl);
	// }

	public void captureScenarioSnapshot(Scenario scenario) {
		try {
			byte[] failureScreenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
			scenario.embed(failureScreenshot, "image/png");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Take Screenshot on test failure
	// Reference:
	// https://advancedweb.hu/2015/04/28/animated-failure-reports-with-selenium-and-cucumber/

	// @After
	// public void afterEachScenario(Scenario scenario) {
	//
	// if (scenario.isFailed())
	// captureScenarioSnapshot(scenario);
	//
	// if (homePageDriver.isLogoutDisplayed())
	// homePageDriver.logout();
	//
	// try {
	// ScriptUtils.executeSqlScript(datasource.getConnection(),
	// new
	// ClassPathResource("e2e_test_scripts/e2e_testing-test-data-deletion.sql"));
	// } catch (ScriptException | SQLException e) {
	// e.printStackTrace();
	// }
	// }

	@Given("I am on home page")
	public void i_am_on_home_page() throws Throwable {
		String homePageTitle = "FitHub.com";
		driver.get(homeUrl);
		homePageDriver.assertPageTitle(homePageTitle);
	}

}
