package com.fithub.e2etesting.jbehave_runner;

import java.util.List;

import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.embedder.Embedder;
import org.jbehave.core.io.StoryFinder;
import org.jbehave.core.steps.spring.SpringStepsFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@ContextConfiguration()
@ActiveProfiles("e2e_testing")
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@Transactional
public class JBehaveStoryRunner {

	@Autowired
	ApplicationContext applicationContext;

	@Test
	public void runStoriesAsPaths() {
		embedder().runStoriesAsPaths(storyPaths());
	}

	private Embedder embedder() {
		Embedder embedder = new Embedder();
		embedder.useStepsFactory(new SpringStepsFactory(new MostUsefulConfiguration(), applicationContext));

		return embedder;
	}

	protected List<String> storyPaths() {
		return new StoryFinder().findPaths(
				org.jbehave.core.io.CodeLocations.codeLocationFromClass(this.getClass()).getFile(), "**/*.story", "");
	}

	// public Configuration configuration() {
	// Configuration configuration = new MostUsefulConfiguration()
	// .useStoryControls(new
	// StoryControls().doDryRun(false).doSkipScenariosAfterFailure(false))
	// .useStoryReporterBuilder(new
	// StoryReporterBuilder().withDefaultFormats());
	// return configuration;
	//
	// }

}