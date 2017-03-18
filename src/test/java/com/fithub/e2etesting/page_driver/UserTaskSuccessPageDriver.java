package com.fithub.e2etesting.page_driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class UserTaskSuccessPageDriver extends HeaderPageDriver {

	WebDriver driver;

	@FindBy(how = How.ID, using = "userRegistrationSuccessModalId")
	WebElement userRegistrationSuccessMessagePopup;

	@Autowired
	public UserTaskSuccessPageDriver(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, HeaderPageDriver.class);
	}

	public boolean isUserRegistrationTaskSuccess() {

		String userRegisterSuccesPageTitle = "UserTaskSuccess";
		return driver.getTitle().equalsIgnoreCase(userRegisterSuccesPageTitle);

	}

}
