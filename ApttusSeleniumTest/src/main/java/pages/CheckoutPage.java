package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class CheckoutPage {
	 WebDriverWait wait;
	 WebDriver driver;

	public CheckoutPage(WebDriver driver) {	
		wait = new WebDriverWait(driver, 60);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(css="a[title='Proceed to checkout']")
	WebElement proceedToCheckOutButton;
	
	@FindBy(css="div.layer_cart_product  h2")
	WebElement validationMessage;
	
	@FindBy(css="span.product-name")
	WebElement productName;
	
	@FindBy(css="span#layer_cart_product_attributes")
	WebElement productColourAndSize;
	
	@FindBy(css="span#layer_cart_product_quantity")
	WebElement productQuantity;
	
	@FindBy(css="span#layer_cart_product_price")
	WebElement productPrice;
	
	public void checkOut(String validationMsg ,String productname, String productAttb, String quantity, String price) {
		wait.until(ExpectedConditions.visibilityOf(proceedToCheckOutButton));
		Assert.assertEquals(validationMessage.getText().trim(),validationMsg);
		Assert.assertEquals(productName.getText(),productname);
		Assert.assertEquals(productColourAndSize.getText(),productAttb);
		Assert.assertEquals(productQuantity.getText(),quantity);
		Assert.assertEquals(productPrice.getText(),price);
		wait.until(ExpectedConditions.visibilityOf(proceedToCheckOutButton));
		proceedToCheckOutButton.click();
	}
}
