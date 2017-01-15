package com.fithub.e2etesting.page_driver;

import static org.junit.Assert.assertEquals;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

/**
 * Class contains locators for all the elements on the header
 *
 */
public class HeaderPageDriver {

	private WebDriver driver;

	@CacheLookup
	@FindBy(how = How.LINK_TEXT, using = "Home")
	WebElement home;

	@FindBy(how = How.LINK_TEXT, using = "Sign up")
	WebElement signUp;

	@FindBy(how = How.LINK_TEXT, using = "Sign in")
	WebElement signIn;

	@FindBy(how = How.ID, using = "logoutId")
	WebElement logout;

	@CacheLookup
	@FindBy(how = How.ID, using = "productListDropdownId")
	WebElement productListDropdown;

	@CacheLookup
	@FindBy(how = How.LINK_TEXT, using = "All Products")
	WebElement allProducts;

	@CacheLookup
	@FindBy(how = How.LINK_TEXT, using = "Top 5 Products")
	WebElement topProducts;

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
		allProducts.click();
	}

	public void clickTopProducts() {
		topProducts.click();
	}

	public void assertPageTitle(String pageTitle) {
		assertEquals("Problems loading the page", pageTitle, driver.getTitle());
	}

	public void clickSignIn() {
		signIn.click();
	}

	public boolean isGuestWelcomeDisplayed() {
		return driver.getPageSource().contains("Guest!");
	}

	public void logout() {
		logout.submit();
	}
}
