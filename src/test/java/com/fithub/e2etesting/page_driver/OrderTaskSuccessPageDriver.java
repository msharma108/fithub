package com.fithub.e2etesting.page_driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class OrderTaskSuccessPageDriver extends HeaderPageDriver {

	@FindBy(how = How.ID, using = "orderBookingSuccessModalId")
	WebElement orderBookingSuccessMessagePopup;

	public OrderTaskSuccessPageDriver(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, HeaderPageDriver.class);
	}

	public boolean handleOrderTaskSuccess() {

		// wait half a second for the success modal to show up on HTML
		try {
			Thread.sleep(700);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		boolean orderSuccess = this.orderBookingSuccessMessagePopup.isDisplayed();
		this.orderBookingSuccessMessagePopup.click();
		return orderSuccess;

	}

}
