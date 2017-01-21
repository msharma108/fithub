package com.fithub.e2etesting.page_driver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
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

		// wait half a second for the success modal to show up on HTML
		// try {
		// Thread.sleep(900);
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// }

		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		boolean orderSuccess = this.orderBookingSuccessMessagePopup.isDisplayed();
		this.orderBookingSuccessMessagePopup.click();
		return orderSuccess;

	}

}
