package com.fithub.e2etesting.step_definition;

import java.net.URI;

import org.openqa.selenium.WebDriver;
import org.springframework.boot.autoconfigure.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import cucumber.api.java.Before;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ContextConfiguration
@ActiveProfiles("testing")
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@Transactional
@AutoConfigureWebMvc
public class AbstractStepDefinition {

	private final WebDriver driver;
	private final URI uri;

	public AbstractStepDefinition(WebDriver driver, URI uri) {
		this.uri = uri;
		this.driver = driver;
	}

	@Before
	public void beforeScenario() {
		driver.get(uri.toString());
	}

}
