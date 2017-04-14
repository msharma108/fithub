package com.fithub.e2etesting.page_driver;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.springframework.beans.factory.annotation.Autowired;

public class RegistrationPageDriver extends HeaderPageDriver {

	@FindBy(how = How.ID, using = "userRegistrationId")
	WebElement userRegistrationPage;

	@FindBy(how = How.ID, using = "givenNameId")
	WebElement givenName;

	@FindBy(how = How.ID, using = "familyNameId")
	WebElement familyName;

	@FindBy(how = How.ID, using = "sexId")
	WebElement sex;

	@FindBy(how = How.ID, using = "addressId")
	WebElement address;

	@FindBy(how = How.ID, using = "dateOfBirthId")
	WebElement dateOfBirth;

	@FindBy(how = How.ID, using = "cityId")
	WebElement city;

	@FindBy(how = How.ID, using = "provinceId")
	WebElement province;

	@FindBy(how = How.ID, using = "countryId")
	WebElement country;

	@FindBy(how = How.ID, using = "zipcodeId")
	WebElement zipcode;

	@FindBy(how = How.ID, using = "phoneId")
	WebElement phone;

	@FindBy(how = How.ID, using = "emailId")
	WebElement email;

	@FindBy(how = How.ID, using = "userNameId")
	WebElement userName;

	@FindBy(how = How.ID, using = "passwordId")
	WebElement password;

	@FindBy(how = How.ID, using = "repeatPasswordId")
	WebElement repeatPassword;

	@FindBy(how = How.ID, using = "securityQuestionId")
	WebElement securityQuestion;

	@FindBy(how = How.ID, using = "securityQuestionAnswerId")
	WebElement securityQuestionAnswer;

	@FindBy(how = How.ID, using = "paymentModeId")
	WebElement paymentMode;

	@FindBy(how = How.ID, using = "g-recaptcha")
	WebElement recaptchaField;

	@FindBy(how = How.ID, using = "userRegisterId")
	WebElement userRegisterForm;

	@FindBy(how = How.ID, using = "userNameExistsErrorId")
	WebElement userNameExistsErrorMessage;

	private WebDriver driver;

	@Autowired
	public RegistrationPageDriver(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, HeaderPageDriver.class);
	}

	public boolean isUserRegistrationPageVisible() {
		return userRegistrationPage.isDisplayed();
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

	public void enterUserName(String userName) {
		this.userName.sendKeys(userName);
	}

	public void enterDateOfBirth(String dateOfBirth) {
		this.dateOfBirth.sendKeys(dateOfBirth);
	}

	public void selectPaymentMode(String paymentMode) {
		Select paymentModeSelect = new Select(this.paymentMode);
		paymentModeSelect.selectByVisibleText(paymentMode);
	}

	public void enterPassword(String password) {
		this.password.sendKeys(password);
	}

	public void enterRepeatPassword(String repeatPassword) {
		this.repeatPassword.sendKeys(repeatPassword);
	}

	public void selectCountry(String country) {
		Select countrySelect = new Select(this.country);
		countrySelect.selectByVisibleText(country);
	}

	public void selectGender(String sex) {
		Select genderSelect = new Select(this.sex);
		genderSelect.selectByVisibleText(sex);
	}

	public void enterSecurityQuestion(String securityQuestion) {
		this.securityQuestion.sendKeys(securityQuestion);
	}

	public void enterSecurityQuestionAnswer(String securityQuestionAnswer) {
		this.securityQuestionAnswer.sendKeys(securityQuestionAnswer);
	}

	public void selectRecaptcha() {

		// Get the element in view
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].scrollIntoView()", recaptchaField);

		recaptchaField.click();
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void submitUserRegistrationForm() {
		userRegisterForm.click();
	}

	public boolean isUserNameExistsMessageVisible() {
		return userNameExistsErrorMessage.isDisplayed();
	}

}
