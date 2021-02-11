package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {
	 WebDriverWait wait;
	 WebDriver driver;

	public LoginPage(WebDriver driver) {
		wait = new WebDriverWait(driver, 45);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(css = "a.login")
	WebElement signInLink;

	@FindBy(css = "input#email")
	WebElement userName;

	@FindBy(css = "input#passwd")
	WebElement password;

	@FindBy(css = "button#SubmitLogin")
	WebElement sumitLoginButton;

	public void login(String username, String pwd) {
		wait.until(ExpectedConditions.visibilityOf(signInLink));
		signInLink.click();
		wait.until(ExpectedConditions.visibilityOf(userName));
		userName.sendKeys(username);
		wait.until(ExpectedConditions.visibilityOf(password));
		password.sendKeys(pwd);
		sumitLoginButton.click();
	}
}
