package com.fithub.e2etesting.jbehave.steps;

import static org.junit.Assert.assertEquals;

import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.fithub.e2etesting.page_driver.CustomErrorPagePageDriver;
import com.fithub.e2etesting.page_driver.HomePageDriver;
import com.fithub.e2etesting.page_driver.ProductListPageDriver;

@Component
@Profile("jbehave_e2e_testing")
public class ProductSearchStep {

	private HomePageDriver homePageDriver;
	private ProductListPageDriver productListPageDriver;
	private CustomErrorPagePageDriver customErrorPagePageDriver;

	@Autowired
	public ProductSearchStep(WebDriver driver) {
		this.homePageDriver = PageFactory.initElements(driver, HomePageDriver.class);
		this.productListPageDriver = PageFactory.initElements(driver, ProductListPageDriver.class);
		this.customErrorPagePageDriver = PageFactory.initElements(driver, CustomErrorPagePageDriver.class);
	}

	@When("I search product with name or description like $searchString")
	public void whenISearchProductWithNameOrDescriptionLikeSearchString(String searchString) throws Throwable {
		homePageDriver.productSearch(searchString);
	}

	@Then("I see list of all products with name or description containing $searchString")
	public void thenISeeListOfAllProductsWithNameOrDescriptionContainingSearchString(String searchString)
			throws Throwable {
		boolean productSearchResult = productListPageDriver.isProductMatchingSearchStringFound(searchString);
		assertEquals(String.format("Product matching search string=%s not found", searchString), true,
				productSearchResult);

	}

	@Then("I see unsuccessful search message for search string $searchString")
	public void thenISeeUnsuccessfulSearchMessageForSearchString(String searchString) throws Throwable {
		assertEquals(String.format("Product matching the searchString=%s not found", searchString),
				customErrorPagePageDriver.getExceptionMessage());
	}

}
