package com.fithub.e2etesting.page_driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class CustomErrorPagePageDriver extends HeaderPageDriver {

	@FindBy(how = How.ID, using = "exceptionId")
	WebElement exception;

	public CustomErrorPagePageDriver(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, HeaderPageDriver.class);
	}

	public String getExceptionMessage() {
		return exception.getText();

	}

}
