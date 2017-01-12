package com.fithub.e2etesting.page_driver;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.springframework.stereotype.Component;

/**
 * Class contains locators for all the elements on the header
 *
 */
@Component
public class HeaderPageDriver {

	// private WebDriver driver;

	@FindBy(how = How.LINK_TEXT, using = "Home")
	WebElement home;

	@FindBy(how = How.LINK_TEXT, using = "Sign up")
	WebElement signUp;

	// public HeaderPageDriver(WebDriver driver) {
	// this.driver = driver;
	// }

	public void selectHome() {
		home.click();
	}

	public void selectSignUp() {
		signUp.click();
	}

}
