package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CartPage {
	WebDriverWait wait;
	WebDriver driver;

	public CartPage(WebDriver driver) {
		wait = new WebDriverWait(driver, 60);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "(//a[@title='T-shirts'])[2]")
	WebElement tshirtCategory;

	@FindBy(css = "img[src='http://automationpractice.com/img/p/1/1-home_default.jpg']")
	WebElement fadedShortSleeveTshirt;

	@FindBy(css = "iframe.fancybox-iframe")
	WebElement fancyBoxIframe;

	@FindBy(css = "button.exclusive")
	WebElement addToCartButton;

	public void addToCart() {
		wait.until(ExpectedConditions.visibilityOf(tshirtCategory));
		tshirtCategory.click();
		wait.until(ExpectedConditions.visibilityOf(fadedShortSleeveTshirt));
		fadedShortSleeveTshirt.click();
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(fancyBoxIframe));
		wait.until(ExpectedConditions.visibilityOf(addToCartButton));
		addToCartButton.click();
		driver.switchTo().defaultContent();
	}
}
