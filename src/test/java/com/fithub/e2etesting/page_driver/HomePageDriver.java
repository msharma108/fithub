package com.fithub.e2etesting.page_driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class HomePageDriver extends HeaderPageDriver {

	private WebDriver driver;

	private static String HOME_URL = "https://localhost:8443/";

	public HomePageDriver(WebDriver driver) {
		// super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, HeaderPageDriver.class);
	}

	public void navigateToHomePage() {
		driver.get(HOME_URL);
	}

}
