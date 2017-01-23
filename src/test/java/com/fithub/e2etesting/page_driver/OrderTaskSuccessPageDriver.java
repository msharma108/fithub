package com.fithub.e2etesting.page_driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;

public class OrderTaskSuccessPageDriver extends HeaderPageDriver {

	WebDriver driver;

	@FindBy(how = How.ID, using = "orderBookingSuccessModalId")
	WebElement orderBookingSuccessMessagePopup;

	@Autowired
	public OrderTaskSuccessPageDriver(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, HeaderPageDriver.class);
	}

	public boolean handleOrderTaskSuccess() {

		// added explicit wait for booking success modal
		WebElement orderBookingPopupWebElement = (new WebDriverWait(driver, 2))
				.until(ExpectedConditions.visibilityOf(orderBookingSuccessMessagePopup));

		boolean orderSuccess = orderBookingPopupWebElement.isDisplayed();
		orderBookingPopupWebElement.click();
		return orderSuccess;

	}

}
