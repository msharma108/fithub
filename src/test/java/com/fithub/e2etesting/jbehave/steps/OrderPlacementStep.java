package com.fithub.e2etesting.jbehave.steps;

import static org.junit.Assert.assertEquals;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.fithub.e2etesting.page_driver.HomePageDriver;
import com.fithub.e2etesting.page_driver.LoginPageDriver;
import com.fithub.e2etesting.page_driver.OrderCheckoutPageDriver;
import com.fithub.e2etesting.page_driver.OrderTaskSuccessPageDriver;
import com.fithub.e2etesting.page_driver.ProductListPageDriver;
import com.fithub.e2etesting.page_driver.ShoppingCartPageDriver;

@Component
@Profile("jbehave_e2e_testing")
public class OrderPlacementStep {

	private ProductListPageDriver productListPageDriver;
	private ShoppingCartPageDriver shoppingCartPageDriver;
	private LoginPageDriver loginPageDriver;
	private OrderCheckoutPageDriver orderCheckoutPageDriver;
	private OrderTaskSuccessPageDriver orderTaskSuccessPageDriver;
	private HomePageDriver homePageDriver;

	@Autowired
	Environment environment;

	@Autowired
	public OrderPlacementStep(WebDriver driver) {
		this.homePageDriver = PageFactory.initElements(driver, HomePageDriver.class);
		this.productListPageDriver = PageFactory.initElements(driver, ProductListPageDriver.class);
		this.shoppingCartPageDriver = PageFactory.initElements(driver, ShoppingCartPageDriver.class);
		this.loginPageDriver = PageFactory.initElements(driver, LoginPageDriver.class);
		this.orderCheckoutPageDriver = PageFactory.initElements(driver, OrderCheckoutPageDriver.class);
		this.orderTaskSuccessPageDriver = PageFactory.initElements(driver, OrderTaskSuccessPageDriver.class);
	}

	@Given("I decide to add a product to cart")
	public void givenIDecideToAddAProductToCart() throws Throwable {
		productListPageDriver.addToCart();
		assertEquals("Problems adding product to the cart", true, shoppingCartPageDriver.isCheckoutDisplayed());
	}

	@Given("I decide to checkout")
	public void givenIDecideToCheckout() throws Throwable {
		shoppingCartPageDriver.checkout();

		// Assert that the registered user moves to Order Checkout Page
		if (!shoppingCartPageDriver.isGuestWelcomeDisplayed())

			assertEquals("Problems moving to the order checkout page", true,
					orderCheckoutPageDriver.isPaymentFormVisible());
	}

	@When("I decide to checkout")
	public void whenIDecideToCheckout() throws Throwable {
		shoppingCartPageDriver.checkout();

		// Assert that the registered user moves to Order Checkout Page
		if (!shoppingCartPageDriver.isGuestWelcomeDisplayed())

			assertEquals("Problems moving to the order checkout page", true,
					orderCheckoutPageDriver.isPaymentFormVisible());
	}

	@Given("I enter shipping and valid payment details")
	public void givenIEnterShippingAndValidPaymentDetails() throws Throwable {

		String validTestCardNumber = "4242424242424242";
		enterPaymentDetails(validTestCardNumber);
	}

	@Given("I decide to view all products")
	public void whenIDecideToViewAllProducts() {
		homePageDriver.viewAllOrTopProductsBasedOnInput("viewAllProducts");
	}

	@When("I hit pay now")
	public void whenIHitPayNow() throws Throwable {
		orderCheckoutPageDriver.submitPaymentForm();
	}

	@Then("I see order placement successful message")
	public void thenISeeOrderPlacementSuccessfulMessage() throws Throwable {

		boolean expectedOrderSuccess = true;

		boolean actualOrderBookingSuccess = orderTaskSuccessPageDriver.handleOrderTaskSuccess();
		assertEquals("Problems booking order with valid payment details", expectedOrderSuccess,
				actualOrderBookingSuccess);
	}

	@Then("I am redirected to Login page")
	public void thenIAmRedirectedToLoginPage() throws Throwable {

		boolean expectedIsLoginFormDisplayed = true;

		assertEquals("Unregistered user not redirected to Login Page while attempting to place an order",
				expectedIsLoginFormDisplayed, loginPageDriver.isLoginFormDisplayed());
	}

	@Given("I enter shipping and invalid payment details")
	public void givenIEnterShippingAndInvalidPaymentDetails() throws Throwable {
		String invalidTestCardNumber = "1234567890";
		enterPaymentDetails(invalidTestCardNumber);
	}

	@Then("I see payment error message")
	public void thenISeePaymentErrorMessage() throws Throwable {
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
