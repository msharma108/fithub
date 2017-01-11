package com.fithub.e2etesting.cucumber_runner;

import java.net.URI;
import java.net.URISyntaxException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConfig {

	@Bean
	public WebDriver driver() {
		return new ChromeDriver();
	}

	@Bean
	public URI uri() {
		try {
			return new URI("localhost:8443");
		} catch (URISyntaxException e) {
			e.printStackTrace();
			return null;
		}

	}

}
