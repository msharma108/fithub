package com.fithub.e2etesting.jbehave_steps;

import static org.junit.Assert.assertEquals;

import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.fithub.e2etesting.page_driver.HomePageDriver;
import com.fithub.e2etesting.page_driver.ProductListPageDriver;

/**
 * Step Definition class for Viewing the list of all products
 *
 */
public class ViewProductListStep extends AbstractStep {

	private HomePageDriver homePageDriver;
	private ProductListPageDriver productListPageDriver;

	@Autowired
	WebDriver driver;

	public ViewProductListStep() {
		homePageDriver = PageFactory.initElements(driver, HomePageDriver.class);
		productListPageDriver = PageFactory.initElements(driver, ProductListPageDriver.class);
	}

	// public ViewProductListStep(WebDriver driver) {
	// homePageDriver = PageFactory.initElements(driver, HomePageDriver.class);
	// productListPageDriver = PageFactory.initElements(driver,
	// ProductListPageDriver.class);
	// }

	@When("I decide to view all products")
	public void i_decide_to_view_all_products() throws Throwable {
		homePageDriver.viewAllOrTopProductsBasedOnInput("viewAllProducts");
	}

	@Then("I see list of all the products")
	public void i_see_list_of_all_the_products() throws Throwable {
		assertEquals("Product List not displayed", true, productListPageDriver.isAllProductListDisplayed());
	}

	@When("I decide to view top products")
	public void i_decide_to_view_top_products() throws Throwable {
		homePageDriver.viewAllOrTopProductsBasedOnInput("viewTopProducts");
	}

	@Then("I see list of top products")
	public void i_see_list_of_top_products() throws Throwable {
		assertEquals("Top Product List not displayed", true, productListPageDriver.isTopProductListDisplayed());
	}

}
