package com.fithub.e2etesting.cucumber.step_definitions;

import static org.junit.Assert.assertEquals;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.fithub.e2etesting.page_driver.HomePageDriver;
import com.fithub.e2etesting.page_driver.ProductListPageDriver;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * Step Definition class for Viewing the list of all products
 *
 */

public class ViewProductListStepDefinition {

	private HomePageDriver homePageDriver;
	private ProductListPageDriver productListPageDriver;

	@Autowired
	public ViewProductListStepDefinition(WebDriver driver) {
		homePageDriver = PageFactory.initElements(driver, HomePageDriver.class);
		productListPageDriver = PageFactory.initElements(driver, ProductListPageDriver.class);
	}

	@When("^I decide to view all products$")
	public void i_decide_to_view_all_products() throws Throwable {
		homePageDriver.viewAllOrTopProductsBasedOnInput("viewAllProducts");
	}

	@Then("^I see list of all the products$")
	public void i_see_list_of_all_the_products() throws Throwable {
		assertEquals("Product List not displayed", true, productListPageDriver.isAllProductListDisplayed());
	}

	@When("^I decide to view top products$")
	public void i_decide_to_view_top_products() throws Throwable {
		homePageDriver.viewAllOrTopProductsBasedOnInput("viewTopProducts");
	}

	@Then("^I see list of top products$")
	public void i_see_list_of_top_products() throws Throwable {
		assertEquals("Top Product List not displayed", true, productListPageDriver.isTopProductListDisplayed());
	}

}
