package com.fithub.e2etesting.jbehave_runner;

import java.util.ArrayList;
import java.util.List;

import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.io.LoadFromClasspath;
import org.jbehave.core.io.StoryFinder;
import org.jbehave.core.junit.JUnitStories;
import org.jbehave.core.reporters.Format;
import org.jbehave.core.reporters.StoryReporterBuilder;
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

// Annotation to close application context after the execution of JBehave
// stories
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@ActiveProfiles("jbehave_e2e_testing")
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@Transactional
@RunWith(JUnitReportingRunner.class)
public class JBehaveStoryRunner extends JUnitStories {

	@Autowired
	private ApplicationContext applicationContext;

	private static TestContextManager testContextManager;

	public JBehaveStoryRunner() {
		JUnitReportingRunner.recommendedControls(configuredEmbedder());
	}

	// Method overrides JBehave's Embedder configuration by specifying
	// application specific configuration
	@Override
	public Configuration configuration() {

		return new MostUsefulConfiguration().useStoryLoader(new LoadFromClasspath()).doDryRun(false)
				.useStoryReporterBuilder(new StoryReporterBuilder().withDefaultFormats().withFormats(Format.CONSOLE,
						Format.TXT, Format.HTML, Format.STATS));
	}

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
			JBehaveStoryRunner.testContextManager = new TestContextManager(getClass());
			JBehaveStoryRunner.testContextManager.prepareTestInstance(this);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	// Method returns the list of paths to the stories in the project
	protected List<String> storyPaths() {
		List<String> testList = new ArrayList<String>();
		testList = new StoryFinder().findPaths(
				org.jbehave.core.io.CodeLocations.codeLocationFromClass(this.getClass()).getFile(), "**/*.story", "");
		return testList;
	}

	@AfterClass
	public static void destroyApplicationContext() {
		// Call after test class hook for TestExecutionListeners specifically
		// DirtiesContextExecutionListener
		try {
			testContextManager.afterTestClass();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

}