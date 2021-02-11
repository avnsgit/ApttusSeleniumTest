package testbase;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class TestBase {
	public static String USER_DIR = System.getProperty("user.dir");
	public static String CURRENT_DATE = getCurrentDateAsString();

	public WebDriver initialization(String browser) {
		WebDriver driver = null;
		if (browser.equals("chrome")) {
			System.setProperty("webdriver.chrome.driver", USER_DIR + "\\Drivers\\chromedriver.exe");
			driver = new ChromeDriver();
			driver.manage().window().maximize();
			driver.manage().deleteAllCookies();
			driver.manage().timeouts().pageLoadTimeout(90, TimeUnit.SECONDS);
			driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		}
		return driver;
	}

	public Logger log() {
		setDateForLog4j();
		Logger log = Logger.getLogger(this.getClass());
		return log;
	}

	public static String getCurrentDateAsString() {
		SimpleDateFormat sdf = new SimpleDateFormat("_dd_MM_yyyy_HH_mm_ss_aa");
		return sdf.format(new Date());
	}

	public void setDateForLog4j() {
		System.setProperty("current_date", CURRENT_DATE);
		PropertyConfigurator.configure(USER_DIR + "\\src\\main\\java\\resources\\log4j.properties");
	}

	public Properties setPropertyFile() {
		Properties property = new Properties();
		try {
			FileInputStream fis = new FileInputStream(USER_DIR + "\\src\\main\\java\\resources\\config.properties");
			property.load(fis);
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return property;
	}
}
