package com.fithub.e2etesting.jbehave.steps;

import static org.junit.Assert.assertEquals;

import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.fithub.e2etesting.page_driver.HomePageDriver;
import com.fithub.e2etesting.page_driver.ProductListPageDriver;

/**
 * Step Definition class for Viewing the list of all products
 *
 */
@Component
@Profile("e2e_testing")
public class ViewProductListStep {

	private HomePageDriver homePageDriver;
	private ProductListPageDriver productListPageDriver;

	@Autowired
	public ViewProductListStep(WebDriver driver) {

		homePageDriver = PageFactory.initElements(driver, HomePageDriver.class);
		productListPageDriver = PageFactory.initElements(driver, ProductListPageDriver.class);
	}

	@When("I decide to view all products")
	public void whenIDecideToViewAllProducts() {
		homePageDriver.viewAllOrTopProductsBasedOnInput("viewAllProducts");
	}

	@Then("I see list of all the products")
	public void thenISeeListOfAllTheProducts() {
		assertEquals("Product List not displayed", true, productListPageDriver.isAllProductListDisplayed());
	}

	@When("I decide to view top products")
	public void whenIDecideToViewTopProducts() {
		homePageDriver.viewAllOrTopProductsBasedOnInput("viewTopProducts");
	}

	@Then("I see list of top products")
	public void thenISeeListOfTopProducts() {
		assertEquals("Top Product List not displayed", true, productListPageDriver.isTopProductListDisplayed());
	}

}
