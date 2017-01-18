package com.fithub.e2etesting.step_definition;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.fithub.e2etesting.page_driver.HomePageDriver;
import com.fithub.e2etesting.page_driver.ProductListPageDriver;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class OrderPlacementStepDefinition extends AbstractStepDefinition {

	private HomePageDriver homePageDriver;
	private ProductListPageDriver productListPageDriver;

	@Autowired
	public OrderPlacementStepDefinition(WebDriver driver) {
		this.homePageDriver = PageFactory.initElements(driver, HomePageDriver.class);
		this.productListPageDriver = PageFactory.initElements(driver, ProductListPageDriver.class);
	}

	@Given("^I decide to add a product to cart$")
	public void i_decide_to_add_a_product_to_cart() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		throw new PendingException();
	}

	@Given("^I decide to checkout$")
	public void i_decide_to_checkout() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		throw new PendingException();
	}

	@Given("^I enter shipping and payment details$")
	public void i_enter_shipping_and_payment_details() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		throw new PendingException();
	}

	@When("^I hit pay now$")
	public void i_hit_pay_now() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		throw new PendingException();
	}

	@Then("^I see order placement successful message$")
	public void i_see_order_placement_successful_message() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		throw new PendingException();
	}

	@Then("^I am redirected to Login page$")
	public void i_am_redirected_to_Login_page() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		throw new PendingException();
	}

}
