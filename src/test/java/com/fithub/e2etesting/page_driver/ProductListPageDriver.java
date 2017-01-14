package com.fithub.e2etesting.page_driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class ProductListPageDriver extends HeaderPageDriver {

	private WebDriver driver;

	@FindBy(how = How.CLASS_NAME, using = "overlay")
	WebElement productOverlayContent;

	public ProductListPageDriver(WebDriver driver) {
		super(driver);
		this.driver = driver;
		// This can be changed later
		PageFactory.initElements(driver, HeaderPageDriver.class);
	}

	public boolean assertProductListPage() {
		return productOverlayContent.isDisplayed();
	}

}
