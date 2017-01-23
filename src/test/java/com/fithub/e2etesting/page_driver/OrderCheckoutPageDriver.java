package com.fithub.e2etesting.page_driver;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class OrderCheckoutPageDriver extends HeaderPageDriver {

	@FindBy(how = How.ID, using = "givenNameId")
	WebElement givenName;

	@FindBy(how = How.ID, using = "familyNameId")
	WebElement familyName;

	@FindBy(how = How.ID, using = "addressId")
	WebElement address;

	@FindBy(how = How.ID, using = "cityId")
	WebElement city;

	@FindBy(how = How.ID, using = "provinceId")
	WebElement province;

	@FindBy(how = How.ID, using = "countryId")
	WebElement country;

	@FindBy(how = How.ID, using = "nameOnCardId")
	WebElement nameOnCard;

	@FindBy(how = How.ID, using = "cardNumberId")
	WebElement cardNumber;

	@FindBy(how = How.ID, using = "cvcId")
	WebElement cvc;

	@FindBy(how = How.ID, using = "monthId")
	WebElement month;

	@FindBy(how = How.ID, using = "yearId")
	WebElement year;

	@FindBy(how = How.ID, using = "zipcodeId")
	WebElement zipcode;

	@FindBy(how = How.ID, using = "phoneId")
	WebElement phone;

	@FindBy(how = How.ID, using = "emailId")
	WebElement email;

	private final WebDriver driver;

	public OrderCheckoutPageDriver(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, HeaderPageDriver.class);
	}

	public void enterYear(String year) {
		this.year.sendKeys(year);
	}

	public void enterMonth(String month) {
		this.month.sendKeys(month);
	}

	public void enterCvc(String cvc) {
		this.cvc.sendKeys(cvc);
	}

	public void enterCardNumber(String cardNumber) {
		this.cardNumber.sendKeys(cardNumber);
	}

	public void enterNameOnCard(String nameOnCard) {
		this.nameOnCard.sendKeys(nameOnCard);
	}

	public void enterProvince(String province) {
		this.province.sendKeys(province);
	}

	public void enterZipcode(String zipcode) {
		this.zipcode.sendKeys(zipcode);
	}

	public void enterPhone(String phone) {
		this.phone.sendKeys(phone);
	}

	public void enterEmail(String email) {
		this.email.sendKeys(email);
	}

	public void enterCity(String city) {
		this.city.sendKeys(city);
	}

	public void enterAddress(String address) {
		this.address.sendKeys(address);
	}

	public void enterGivenName(String givenName) {
		this.givenName.sendKeys(givenName);
	}

	public void enterFamilyName(String familyName) {
		this.familyName.sendKeys(familyName);
	}

	public void submitPaymentForm() {
		this.cvc.submit();
	}

	public void selectCountry(String country) {
		Select countrySelect = new Select(this.country);
		countrySelect.selectByVisibleText(country);
	}

	public boolean isPaymentFormVisible() {
		return cardNumber.isDisplayed();
	}

	public String handleInvalidPaymentDetailsAlert() {
		Alert invalidPaymentAlert = driver.switchTo().alert();
		String alertText = invalidPaymentAlert.getText();
		invalidPaymentAlert.accept();
		return alertText;
	}
}
