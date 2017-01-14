package com.fithub.e2etesting.config;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile(value = "e2e_testing")
public class WebDriverConfig {

	@Bean
	public WebDriver driver() {

		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setJavascriptEnabled(true);
		capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,
				"src/test/resources/phantomjs.exe");
		capabilities.setCapability("takesScreenshot", true);
		// Reference:-
		// http://www.andrewgorton.uk/blog/selenium-ghostdriver-phantomjs-and-self-signed-certificates/

		capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS,
				new String[] { "--web-security=no", "--ignore-ssl-errors=yes" });
		WebDriver driver = new PhantomJSDriver(capabilities);
		driver.manage().window().maximize();
		return driver;
	}

	@Bean
	public String url() {

		return new String("https://localhost:8443/");
	}

}
