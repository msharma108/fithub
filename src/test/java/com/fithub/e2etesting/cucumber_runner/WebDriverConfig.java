package com.fithub.e2etesting.cucumber_runner;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebDriverConfig {

	@Bean
	public WebDriver driver() {

		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setJavascriptEnabled(true);
		capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,
				"src/test/resources/phantomjs.exe");

		// Reference:-
		// http://www.andrewgorton.uk/blog/selenium-ghostdriver-phantomjs-and-self-signed-certificates/

		capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS,
				new String[] { "--web-security=no", "--ignore-ssl-errors=yes" });
		WebDriver driver = new PhantomJSDriver(capabilities);
		File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(screenshotFile, new File("E:\\screenshots\\screen.jpg"), true);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return driver;
	}

	@Bean
	public String url() {

		return new String("https://localhost:8443/");
	}

}
