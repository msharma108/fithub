package com.fithub.e2etesting.page_driver;

import static org.junit.Assert.assertEquals;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.springframework.stereotype.Component;

/**
 * Class contains locators for all the elements on the header
 *
 */
@Component
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
	WebElement top5ProductsLink;

	String viewAllProducts = "viewAllProducts";

	String viewTop5Products = "viewTop5Products";

	public HeaderPageDriver(WebDriver driver) {
		this.driver = driver;
	}

	public void selectHome() {
		home.click();
	}

	public void clickSignUp() {
		signUp.click();
	}

	public void viewAllOrTop5ProductsBasedOnInput(String input) {
		productListDropdown.click();

		if (input.equals(viewAllProducts))
			clickAllProducts();
		else
			clickTop5Products();
	}

	public void clickAllProducts() {
		allProductsLink.click();
	}

	public void clickTop5Products() {
		top5ProductsLink.click();
	}

	public void assertPageTitle(String pageTitle) {
		assertEquals("Problems loading the page", pageTitle, driver.getTitle());
	}

}
