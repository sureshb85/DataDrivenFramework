package com.cts.training;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class BrowserFactory {

	private ThreadLocal<WebDriver> driver = new ThreadLocal<WebDriver>();
	private String browser;
	public static final String PROJECT_DIR = System.getProperty("user.dir");

	public BrowserFactory(String browser) {
		this.browser = browser.toLowerCase();
	}

	public WebDriver createDriver() {
		System.out.println("initializing driver: " + browser);
		if (browser.equalsIgnoreCase("edge")) {
			System.setProperty("webdriver.edge.driver", PROJECT_DIR + "\\drivers\\msedgedriver.exe");
			driver.set(new EdgeDriver());
		} else if (browser.equalsIgnoreCase("firefox")) {
			System.setProperty("webdriver.gecko.driver", PROJECT_DIR + "\\drivers\\geckodriver.exe");
			driver.set(new FirefoxDriver());
		} else {
			System.setProperty("webdriver.chrome.driver", PROJECT_DIR + "\\drivers\\chromedriver.exe");
			driver.set(new ChromeDriver());
		}
		return driver.get();
	}

}
