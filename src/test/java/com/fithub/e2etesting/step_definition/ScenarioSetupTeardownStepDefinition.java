package com.fithub.e2etesting.step_definition;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptException;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import cucumber.api.java.After;
import cucumber.api.java.Before;

public class ScenarioSetupTeardownStepDefinition extends AbstractStepDefinition {

	private final DataSource datasource;

	private final WebDriver driver;

	@Autowired
	public ScenarioSetupTeardownStepDefinition(WebDriver driver, DataSource datasource) {
		this.driver = driver;
		this.datasource = datasource;
	}

	@Before
	public void beforeEachScenario() {
		try {
			ScriptUtils.executeSqlScript(datasource.getConnection(),
					new ClassPathResource("e2e_test_scripts/e2e_testing-test-data-creation.sql"));
		} catch (ScriptException | SQLException e) {
			e.printStackTrace();
		}

		String homeURL = "https://localhost:8443/";
		driver.get(homeURL);
	}

	@After
	public void afterEachScenario() {
		try {
			ScriptUtils.executeSqlScript(datasource.getConnection(),
					new ClassPathResource("e2e_test_scripts/e2e_testing-test-data-deletion.sql"));
		} catch (ScriptException | SQLException e) {
			e.printStackTrace();
		}
	}
}
