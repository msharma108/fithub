package com.fithub.e2etesting.page_driver;

import static org.junit.Assert.assertEquals;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

/**
 * Class contains locators for all the elements on the header
 *
 */
public class HeaderPageDriver {

	private WebDriver driver;

	@FindBy(how = How.LINK_TEXT, using = "Home")
	WebElement home;

	@FindBy(how = How.LINK_TEXT, using = "Sign up")
	WebElement signUp;

	@FindBy(how = How.ID, using = "productListDropdownId")
	WebElement productListDropdown;

	@FindBy(how = How.LINK_TEXT, using = "All Products")
	WebElement allProductsLink;

	@FindBy(how = How.LINK_TEXT, using = "Top 5 Products")
	WebElement topProductsLink;

	String viewAllProducts = "viewAllProducts";

	String viewTopProducts = "viewTopProducts";

	public HeaderPageDriver(WebDriver driver) {
		this.driver = driver;
	}

	public void selectHome() {
		home.click();
	}

	public void clickSignUp() {
		signUp.click();
	}

	public void viewAllOrTopProductsBasedOnInput(String input) {
		productListDropdown.click();

		if (input.equals(viewAllProducts))
			clickAllProducts();
		else
			clickTopProducts();
	}

	public void clickAllProducts() {
		allProductsLink.click();
	}

	public void clickTopProducts() {
		topProductsLink.click();
	}

	public void assertPageTitle(String pageTitle) {
		assertEquals("Problems loading the page", pageTitle, driver.getTitle());
	}

}
