package com.fithub.e2etesting.jbehave_runner;

import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.spring.SpringStepsFactory;
import org.junit.AfterClass;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.ApplicationContext;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestContextManager;
import org.springframework.transaction.annotation.Transactional;

import de.codecentric.jbehave.junit.monitoring.JUnitReportingRunner;
import net.serenitybdd.jbehave.SerenityStories;

//Annotation to close application context after the execution of JBehave
//stories
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@ActiveProfiles("jbehave_e2e_testing")
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@Transactional
@RunWith(JUnitReportingRunner.class)
public class SerenityJBehaveStoryRunner extends SerenityStories {

	@Autowired
	private ApplicationContext applicationContext;

	private static TestContextManager testContextManager;

	// Method initializes the Step instances using spring application bean steps
	@Override
	public InjectableStepsFactory stepsFactory() {

		// Initialize TestContextManager that loads the application context for
		// the tests
		initializeSpringApplicationContext();
		return new SpringStepsFactory(configuration(), applicationContext);
	}

	private void initializeSpringApplicationContext() {
		try {
			SerenityJBehaveStoryRunner.testContextManager = new TestContextManager(getClass());
			SerenityJBehaveStoryRunner.testContextManager.prepareTestInstance(this);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@AfterClass
	public static void destroyApplicationContext() {
		// Call after test class hook for TestExecutionListeners specifically
		// DirtiesContextExecutionListener
		try {
			SerenityJBehaveStoryRunner.testContextManager.afterTestClass();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

}
