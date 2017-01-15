package com.fithub.e2etesting.page_driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class HomePageDriver extends HeaderPageDriver {

	public HomePageDriver(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, HeaderPageDriver.class);
	}

}
