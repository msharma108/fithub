package com.fithub.e2etesting.page_driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
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

	public boolean handleUserRegistrationTaskSuccess() {

		// added explicit wait for user registration success modal
		WebElement userRegistrationSuccessMessagePopupWebElement = (new WebDriverWait(driver, 5))
				.until(ExpectedConditions.visibilityOf(userRegistrationSuccessMessagePopup));

		boolean userRegistrationSuccess = userRegistrationSuccessMessagePopupWebElement.isDisplayed();
		userRegistrationSuccessMessagePopupWebElement.click();
		return userRegistrationSuccess;

	}

}
