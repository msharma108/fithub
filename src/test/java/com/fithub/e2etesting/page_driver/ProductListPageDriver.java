package com.fithub.e2etesting.page_driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class ProductListPageDriver extends HeaderPageDriver {

	@FindBy(how = How.ID, using = "allProductListHeadingId")
	WebElement allProductListHeading;

	@FindBy(how = How.ID, using = "topProductListHeadingId")
	WebElement topProductListHeading;

	@FindBy(how = How.NAME, name = "addToCart")
	WebElement addToCart;

	public ProductListPageDriver(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, HeaderPageDriver.class);
	}

	public boolean isAllProductListDisplayed() {
		return allProductListHeading.isDisplayed();
	}

	public boolean isTopProductListDisplayed() {
		return topProductListHeading.isDisplayed();
	}

	public boolean isProductMatchingSearchStringFound(String searchString) {
		return addToCart.isDisplayed();
	}

	public void addToCart() {
		addToCart.click();
	}

}
