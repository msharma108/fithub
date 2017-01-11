package com.fithub.e2etesting.page_driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Class contains locators for all the elements on the header
 *
 */
@Component
public class HeaderPageDriver {

	private final WebDriver driver;

	@FindBy(how = How.LINK_TEXT, using = "Home")
	private WebElement home;

	@FindBy(how = How.LINK_TEXT, using = "Sign up")
	private WebElement signUp;

	@Autowired
	public HeaderPageDriver(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(this.driver, HeaderPageDriver.class);
	}

	public void selectHome() {
		home.click();
	}

	public void selectSignUp() {
		signUp.click();
	}

}
