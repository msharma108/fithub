package com.fithub.e2etesting.jbehave_runner;

import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.spring.SpringStepsFactory;
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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import net.serenitybdd.jbehave.SerenityStories;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
// Annotation to close application context after the execution of JBehave
// stories
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)

@ContextConfiguration()
@ActiveProfiles("jbehave_e2e_testing")
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@Transactional
public class SerenityJBehaveStoryRunner extends SerenityStories {

	@Autowired
	ApplicationContext applicationContext;

	// Method initializes the Step instances using spring application bean steps
	@Override
	public InjectableStepsFactory stepsFactory() {
		return new SpringStepsFactory(configuration(), applicationContext);
	}

}
