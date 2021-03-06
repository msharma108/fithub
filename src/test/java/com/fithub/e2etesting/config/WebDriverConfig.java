package com.fithub.e2etesting.config;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile(value = { "cucumber_e2e_testing", "jbehave_e2e_testing" })
public class WebDriverConfig implements DisposableBean {

	@Autowired
	private WebDriver driver;

	private static final Logger LOG = LoggerFactory.getLogger(WebDriverConfig.class);

	@Bean()
	public WebDriver driver() {

		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setJavascriptEnabled(true);

		capabilities.setCapability("takesScreenshot", true);
		// Reference:-
		// http://www.andrewgorton.uk/blog/selenium-ghostdriver-phantomjs-and-self-signed-certificates/

		// PhantomJS driver settings

		// capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS,
		// new String[] { "--web-security=no", "--ignore-ssl-errors=yes",
		// "--ssl-protocol=any" });
		// capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,
		// "src/test/resources/phantomjs.exe");
		//
		// WebDriver driver = new PhantomJSDriver(capabilities);

		// Chrome specific properties
		System.setProperty(ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY, "src/test/resources/chromedriver.exe");
		WebDriver driver = new ChromeDriver(capabilities);
		driver.manage().window().maximize();
		return driver;
	}

	@Bean
	public String homeUrl() {

		return new String("https://localhost:8443/");
	}

	@Override
	public void destroy() {
		LOG.debug("Shutting down webdriver");
		driver.quit();
	}

}
