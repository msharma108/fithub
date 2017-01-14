package com.fithub.e2etesting.cucumber_runner;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

/**
 * Class that runs the e2e tests
 *
 */
@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:com/fithub/e2etesting/feature", glue = "classpath:com/fithub/e2etesting/step_definition", plugin = {
		"pretty", "html:src/test/java/com/fithub/e2etesting/e2eReports" })

public class FitHubApplicationE2ETestRunner {

}
