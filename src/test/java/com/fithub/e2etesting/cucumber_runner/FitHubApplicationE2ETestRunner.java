package com.fithub.e2etesting.cucumber_runner;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

/**
 * Class that runs the e2e tests
 *
 */
@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:feature_files", glue = "classpath:com/fithub/e2etesting/step_definition", plugin = {
		"html:target/e2etesting/e2eReports/", "json:target/e2etesting/e2eReports/cucumber.json" })

public class FitHubApplicationE2ETestRunner {

}
