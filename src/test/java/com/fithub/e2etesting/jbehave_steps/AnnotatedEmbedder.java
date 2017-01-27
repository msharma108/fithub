package com.fithub.e2etesting.jbehave_steps;

import java.util.ArrayList;
import java.util.List;

import org.jbehave.core.InjectableEmbedder;
import org.jbehave.core.annotations.Configure;
import org.jbehave.core.annotations.UsingEmbedder;
import org.jbehave.core.annotations.UsingSteps;
import org.jbehave.core.embedder.Embedder;
import org.jbehave.core.embedder.StoryControls;
import org.jbehave.core.io.LoadFromClasspath;
import org.jbehave.core.io.StoryFinder;
import org.jbehave.core.junit.AnnotatedEmbedderRunner;
import org.jbehave.core.parsers.RegexPrefixCapturingPatternParser;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;

import com.fithub.e2etesting.jbehave_steps.AnnotatedEmbedder.CustomReportBuilder;
import com.fithub.e2etesting.jbehave_steps.AnnotatedEmbedder.CustomStoryControls;
import com.fithub.e2etesting.jbehave_steps.AnnotatedEmbedder.CustomStoryLoader;

//Reference:
//http://jbehave.org/reference/stable/configuration.html

@RunWith(AnnotatedEmbedderRunner.class)
@Configure(storyControls = CustomStoryControls.class, storyLoader = CustomStoryLoader.class, storyReporterBuilder = CustomReportBuilder.class)
@UsingEmbedder(embedder = Embedder.class, generateViewAfterStories = true, ignoreFailureInStories = true, ignoreFailureInView = true, verboseFailures = true, storyTimeoutInSecs = 100, threads = 2, metaFilters = "-skip")
// @UsingSteps(packages = "com.fithub.e2etesting.jbehave_steps", matchingNames =
// "*Step.java")
@UsingSteps(instances = { ViewProductListStep.class })

public class AnnotatedEmbedder extends InjectableEmbedder {

	@Autowired
	WebDriver driver;

	protected List<String> getStoryPaths() {
		List<String> testList = new ArrayList<String>();
		testList = new StoryFinder().findPaths(
				org.jbehave.core.io.CodeLocations.codeLocationFromClass(this.getClass()).getFile(), "**/*.story", "");
		return testList;
	}

	public static class CustomReportBuilder extends StoryReporterBuilder {
		@SuppressWarnings("deprecation")
		public CustomReportBuilder() {
			this.withFormats(Format.HTML, Format.XML, Format.STATS);
		}
	}

	public static class CustomStoryLoader extends LoadFromClasspath {
		public CustomStoryLoader() {
			super(AnnotatedEmbedder.class.getClassLoader());
		}
	}

	public static class CustomStoryControls extends StoryControls {
		public CustomStoryControls() {
			doDryRun(false);
			doSkipScenariosAfterFailure(false);
		}
	}

	@Test
	public void run() throws Throwable {
		// injectedEmbedder().runStoriesAsPaths(getStoryPaths());
		injectedEmbedder().runStoriesAsPaths(getStoryPaths());
	}

	public static class MyRegexPrefixCapturingPatternParser extends RegexPrefixCapturingPatternParser {
		public MyRegexPrefixCapturingPatternParser() {
			super("%");
		}
	}

}
