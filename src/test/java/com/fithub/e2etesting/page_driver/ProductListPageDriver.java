package com.fithub.e2etesting.page_driver;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class ProductListPageDriver extends HeaderPageDriver {

	private WebDriver driver;

	@FindBy(how = How.ID, using = "allProductListHeadingId")
	WebElement allProductListHeading;

	@FindBy(how = How.ID, using = "topProductListHeadingId")
	WebElement topProductListHeading;

	@FindBy(how = How.NAME, name = "addToCart")
	WebElement addToCart;

	public ProductListPageDriver(WebDriver driver) {
		super(driver);
		this.driver = driver;
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

		// Get the element in view
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].scrollIntoView()", addToCart);

		addToCart.click();
	}

}
