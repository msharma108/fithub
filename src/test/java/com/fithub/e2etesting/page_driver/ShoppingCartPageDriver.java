package com.fithub.e2etesting.page_driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class ShoppingCartPageDriver extends HeaderPageDriver {

	@FindBy(how = How.ID, using = "checkoutId")
	WebElement checkout;

	public ShoppingCartPageDriver(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, HeaderPageDriver.class);
	}

	public void checkout() {
		checkout.click();
	}

	public boolean isCheckoutDisplayed() {
		return checkout.isDisplayed();
	}
}
