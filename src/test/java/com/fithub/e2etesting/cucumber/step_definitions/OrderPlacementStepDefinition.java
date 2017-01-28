package com.fithub.e2etesting.cucumber.step_definitions;

import static org.junit.Assert.assertEquals;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import com.fithub.e2etesting.page_driver.HomePageDriver;
import com.fithub.e2etesting.page_driver.LoginPageDriver;
import com.fithub.e2etesting.page_driver.OrderCheckoutPageDriver;
import com.fithub.e2etesting.page_driver.OrderTaskSuccessPageDriver;
import com.fithub.e2etesting.page_driver.ProductListPageDriver;
import com.fithub.e2etesting.page_driver.ShoppingCartPageDriver;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class OrderPlacementStepDefinition {

	private HomePageDriver homePageDriver;
	private ProductListPageDriver productListPageDriver;
	private ShoppingCartPageDriver shoppingCartPageDriver;
	private LoginPageDriver loginPageDriver;
	private OrderCheckoutPageDriver orderCheckoutPageDriver;
	private OrderTaskSuccessPageDriver orderTaskSuccessPageDriver;

	@Autowired
	Environment environment;

	private final WebDriver driver;

	@Autowired
	public OrderPlacementStepDefinition(WebDriver driver) {
		this.driver = driver;
		this.homePageDriver = PageFactory.initElements(driver, HomePageDriver.class);
		this.productListPageDriver = PageFactory.initElements(driver, ProductListPageDriver.class);
		this.shoppingCartPageDriver = PageFactory.initElements(driver, ShoppingCartPageDriver.class);
		this.loginPageDriver = PageFactory.initElements(driver, LoginPageDriver.class);
		this.orderCheckoutPageDriver = PageFactory.initElements(driver, OrderCheckoutPageDriver.class);
		this.orderTaskSuccessPageDriver = PageFactory.initElements(driver, OrderTaskSuccessPageDriver.class);
	}

	@Given("^I decide to add a product to cart$")
	public void i_decide_to_add_a_product_to_cart() throws Throwable {
		productListPageDriver.addToCart();
		assertEquals("Problems adding product to the cart", true, shoppingCartPageDriver.isCheckoutDisplayed());
	}

	@Given("^I decide to checkout$")
	public void i_decide_to_checkout() throws Throwable {
		shoppingCartPageDriver.checkout();

		// Assert that the registered user moves to Order Checkout Page
		if (!shoppingCartPageDriver.isGuestWelcomeDisplayed())

			assertEquals("Problems moving to the order checkout page", true,
					orderCheckoutPageDriver.isPaymentFormVisible());
	}

	@Given("^I enter shipping and valid payment details$")
	public void i_enter_shipping_and_valid_payment_details() throws Throwable {

		String validTestCardNumber = "4242424242424242";
		enterPaymentDetails(validTestCardNumber);
	}

	@When("^I hit pay now$")
	public void i_hit_pay_now() throws Throwable {
		orderCheckoutPageDriver.submitPaymentForm();
	}

	@Then("^I see order placement successful message$")
	public void i_see_order_placement_successful_message() throws Throwable {

		boolean expectedOrderSuccess = true;

		boolean actualOrderBookingSuccess = orderTaskSuccessPageDriver.handleOrderTaskSuccess();
		assertEquals("Problems booking order with valid payment details", expectedOrderSuccess,
				actualOrderBookingSuccess);
	}

	@Then("^I am redirected to Login page$")
	public void i_am_redirected_to_Login_page() throws Throwable {

		boolean expectedIsLoginFormDisplayed = true;

		assertEquals("Unregistered user not redirected to Login Page while attempting to place an order",
				expectedIsLoginFormDisplayed, loginPageDriver.isLoginFormDisplayed());
	}

	@Given("^I enter shipping and invalid payment details$")
	public void i_enter_shipping_and_invalid_payment_details() throws Throwable {
		String invalidTestCardNumber = "1234567890";
		enterPaymentDetails(invalidTestCardNumber);
	}

	@Then("^I see payment error message$")
	public void i_see_payment_error_message() throws Throwable {
		String invalidPaymentDetailsMessage = orderCheckoutPageDriver.handleInvalidPaymentDetailsAlert();
		String expectedInvalidPaymentDetailsMessage = String.format("Invalid Card Number, please correct");

		assertEquals("Problems with payment validation", expectedInvalidPaymentDetailsMessage,
				invalidPaymentDetailsMessage);
	}

	private void enterPaymentDetails(String cardNumberForPayment) {

		orderCheckoutPageDriver.enterAddress("testAddress");
		orderCheckoutPageDriver.enterCity("testCity");
		orderCheckoutPageDriver.selectCountry("Canada");
		orderCheckoutPageDriver.enterEmail(environment.getProperty("application.emailSenderAddress"));
		orderCheckoutPageDriver.enterPhone("1234567890");
		orderCheckoutPageDriver.enterFamilyName("testFamilyName");
		orderCheckoutPageDriver.enterGivenName("testGivenName");
		orderCheckoutPageDriver.enterZipcode("testZipCode");
		orderCheckoutPageDriver.enterNameOnCard("registeredUser");
		orderCheckoutPageDriver.enterMonth("4");
		orderCheckoutPageDriver.enterYear("2020");
		orderCheckoutPageDriver.enterCvc("123");
		orderCheckoutPageDriver.enterCardNumber(cardNumberForPayment);
		orderCheckoutPageDriver.enterProvince("testProvince");

	}

}
