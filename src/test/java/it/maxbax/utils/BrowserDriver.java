package it.maxbax.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class BrowserDriver {

	private WebDriver driver;

	public BrowserDriver() throws Exception {
		String browserName = System.getProperty("browser");
		String driverName = System.getProperty("driver");
		System.out.println("\n[INFO] Browser: " + browserName + " - Driver: " + driverName + "\n");
		switch (browserName.toLowerCase()) {
		case "firefox":
			System.setProperty("webdriver.gecko.driver", driverName);
			driver = new FirefoxDriver();
			break;
		case "chrome":
			System.setProperty("webdriver.chrome.driver", driverName);
			driver = new ChromeDriver();
			break;
		case "ie":
			System.setProperty("webdriver.ie.driver", driverName);
			driver = new InternetExplorerDriver();
			break;
		default:
			throw new Exception("Browser is not correct");
		}
	}

	public WebDriver getDriver() {
		return driver;
	}
}
