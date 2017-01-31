package com.fithub.e2etesting.jbehave.steps;

import java.io.File;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.io.FileUtils;
import org.jbehave.core.annotations.AfterScenario;
import org.jbehave.core.annotations.BeforeScenario;
import org.jbehave.core.annotations.Given;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptException;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.stereotype.Component;

import com.fithub.e2etesting.page_driver.HomePageDriver;

@Component
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

	@BeforeScenario
	public void beforeEachScenario() {
		try {
			ScriptUtils.executeSqlScript(datasource.getConnection(),
					new ClassPathResource("e2e_test_scripts/e2e_testing-test-data-creation.sql"));
		} catch (ScriptException | SQLException e) {
			e.printStackTrace();
		}

		driver.get(homeUrl);
	}

	public void captureScenarioSnapshot() {
		try {
			File failureScreenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(failureScreenshotFile, new File("E:\\ScenarioFailureScreenShot.jpg"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@AfterScenario
	public void afterEachScenario() {

		if (homePageDriver.isLogoutDisplayed())
			homePageDriver.logout();

		try {
			ScriptUtils.executeSqlScript(datasource.getConnection(),
					new ClassPathResource("e2e_test_scripts/e2e_testing-test-data-deletion.sql"));
		} catch (ScriptException | SQLException e) {
			e.printStackTrace();
		}
	}

	// Take a screenshot for debugging purposes in case of scenario failure
	@AfterScenario(uponOutcome = AfterScenario.Outcome.FAILURE)
	public void afterFailedScenario() {
		captureScenarioSnapshot();
	}

	@Given("I am on home page")
	public void givenIAmOnHomePage() {
		String homePageTitle = "FitHub.com";
		homePageDriver.assertPageTitle(homePageTitle);
	}

}
