package tests;

import java.util.LinkedHashMap;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pages.CartPage;
import pages.CheckoutPage;
import pages.LoginPage;
import testbase.TestBase;
import utilities.ExcelUtils;

public class ProductCheckoutTest {
	WebDriver driver;
	Properties property;
	TestBase tb;
	CartPage cart;
	CheckoutPage checkout;
	LoginPage login;
	Logger log;
	ExcelUtils exut;
	LinkedHashMap<String, String> testdata;

	@BeforeMethod
	public void browserLaunch() {
		tb = new TestBase();
		property = tb.setPropertyFile();
		log = tb.log();
		String browser = property.getProperty("browser");
		driver = tb.initialization(browser);
	}

	@Test
	public void productCheckOut() {
		cart = new CartPage(driver);
		checkout = new CheckoutPage(driver);
		login = new LoginPage(driver);
		exut = new ExcelUtils();
		testdata = exut.readExcel(TestBase.USER_DIR + "\\src\\main\\java\\testdata\\testData.xlsx", "Sheet1");
		String url = property.getProperty("ApplicationURL");
		driver.get(url);
		log.debug("Browser Launched and login to application");
		login.login(testdata.get("UserName"), testdata.get("Password"));
		log.debug("Login Done");
		cart.addToCart();
		log.debug("Product Added To Cart");
		log.debug("Checkout and validation");
		checkout.checkOut(testdata.get("ValidationMessage"), testdata.get("TshirtType"), testdata.get("ColourAndSize"),
				testdata.get("Quantity"), testdata.get("Total"));
		log.debug("test Completed");
	}

	@AfterMethod
	public void tearDown() {
		driver.quit();
		log.debug("Browser Closed");
	}
}
