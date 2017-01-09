package com.fithub.e2etesting.cucumber_runner;

import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

/**
 * Class that runs the e2e tests
 *
 */
@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:com/fithub/e2etesting/feature", glue = "classpath:com/fithub/e2etesting/step_definition")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("testing")
@AutoConfigureTestDatabase(replace = Replace.AUTO_CONFIGURED)
public class FitHubApplicationE2ETestRunner {

}
