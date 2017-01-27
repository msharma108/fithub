package com.fithub.e2etesting.step_definition;

import static org.junit.Assert.assertEquals;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.fithub.e2etesting.page_driver.CustomErrorPagePageDriver;
import com.fithub.e2etesting.page_driver.HomePageDriver;
import com.fithub.e2etesting.page_driver.ProductListPageDriver;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class ProductSearchStepDefinition {

	private HomePageDriver homePageDriver;
	private ProductListPageDriver productListPageDriver;
	private CustomErrorPagePageDriver customErrorPagePageDriver;

	@Autowired
	public ProductSearchStepDefinition(WebDriver driver) {
		this.homePageDriver = PageFactory.initElements(driver, HomePageDriver.class);
		this.productListPageDriver = PageFactory.initElements(driver, ProductListPageDriver.class);
		this.customErrorPagePageDriver = PageFactory.initElements(driver, CustomErrorPagePageDriver.class);
	}

	@When("^I search product with name or description like \"([^\"]*)\"$")
	public void i_search_product_with_name_or_description_like(String searchString) throws Throwable {
		homePageDriver.productSearch(searchString);
	}

	@Then("^I see list of all products with name or description containing \"([^\"]*)\"$")
	public void i_see_list_of_all_products_with_name_or_description_containing(String searchString) throws Throwable {
		boolean productSearchResult = productListPageDriver.isProductMatchingSearchStringFound(searchString);
		assertEquals(String.format("Product matching search string=%s not found", searchString), true,
				productSearchResult);

	}

	@Then("^I see unsuccessful search message for search string \"([^\"]*)\"$")
	public void i_see_unsuccessful_search_message_for_search_string(String searchString) throws Throwable {
		assertEquals(String.format("Product matching the searchString=%s not found", searchString),
				customErrorPagePageDriver.getExceptionMessage());
	}

}
