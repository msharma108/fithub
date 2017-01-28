package com.fithub.e2etesting.cucumber.step_definitions;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptException;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.fithub.e2etesting.page_driver.HomePageDriver;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@ContextConfiguration()
@ActiveProfiles("e2e_testing")
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@Transactional
public class ScenarioSetupTeardownStepDefinition {

	private final DataSource datasource;

	private final WebDriver driver;

	private final String homeUrl;

	private HomePageDriver homePageDriver;

	@Autowired
	public ScenarioSetupTeardownStepDefinition(WebDriver driver, DataSource datasource, String homeUrl) {
		this.driver = driver;
		this.datasource = datasource;
		this.homeUrl = homeUrl;
		homePageDriver = PageFactory.initElements(driver, HomePageDriver.class);
	}

	@Before
	public void beforeEachScenario() {
		try {
			ScriptUtils.executeSqlScript(datasource.getConnection(),
					new ClassPathResource("e2e_test_scripts/e2e_testing-test-data-creation.sql"));
		} catch (ScriptException | SQLException e) {
			e.printStackTrace();
		}

		driver.get(homeUrl);
	}

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

	@After
	public void afterEachScenario(Scenario scenario) {

		if (scenario.isFailed())
			captureScenarioSnapshot(scenario);

		if (homePageDriver.isLogoutDisplayed())
			homePageDriver.logout();

		try {
			ScriptUtils.executeSqlScript(datasource.getConnection(),
					new ClassPathResource("e2e_test_scripts/e2e_testing-test-data-deletion.sql"));
		} catch (ScriptException | SQLException e) {
			e.printStackTrace();
		}
	}

	@Given("^I am on home page$")
	public void i_am_on_home_page() throws Throwable {
		String homePageTitle = "FitHub.com";
		homePageDriver.assertPageTitle(homePageTitle);
	}

}
