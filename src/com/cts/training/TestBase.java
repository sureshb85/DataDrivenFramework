package com.cts.training;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class TestBase {

	static WebDriver driver = null;
	static WebDriverWait wait = null;
	public static final String PROJECT_DIR = System.getProperty("user.dir");

	public static void createDriver(String browser) {
		if (browser.equalsIgnoreCase("edge")) {
			System.setProperty("webdriver.edge.driver", PROJECT_DIR + "\\drivers\\msedgedriver.exe");
			driver = new EdgeDriver();
		} else if (browser.equalsIgnoreCase("firefox")) {
			System.setProperty("webdriver.gecko.driver", PROJECT_DIR + "\\drivers\\geckodriver.exe");
			driver = new FirefoxDriver();
		} else {
			System.setProperty("webdriver.chrome.driver", PROJECT_DIR + "\\drivers\\chromedriver.exe");
			driver = new ChromeDriver();
		}
	}

	static WebElement getElement(String locator, String locatorValue) {
		WebElement element = null;
		if (locator.equalsIgnoreCase("css")) {
			element = driver.findElement(By.cssSelector(locatorValue));
		} else if (locator.equalsIgnoreCase("xpath")) {
			element = driver.findElement(By.xpath(locatorValue));
		}
		return element;
	}

	static void enterText(String locator, String locatorValue, String text) {
		WebElement element = getElement(locator, locatorValue);
		if (element.isDisplayed()) {
			System.out.println("entering text: " + text);
			element.sendKeys(text);
		} else {
			System.out.println("Element not found");
		}
	}

	static void clickElement(String locator, String locatorValue) {
		WebElement element = getElement(locator, locatorValue);
		if (element.isEnabled()) {
			element.click();
		} else {
			System.out.println("Element not found");
		}
	}

	static void selectDropDownValue(String locator, String locatorValue, String text) {
		WebElement element = getElement(locator, locatorValue);
		if (element.isEnabled()) {
			element.click();
		} else {
			System.out.println("Element not found");
		}
		Select selectValue = new Select(element);
		selectValue.selectByVisibleText(text);
	}

	static void waitUntilElementVisible(String locator, String locatorValue) {
		WebElement element = getElement(locator, locatorValue);
		wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOf(element));

	}

	public static void getScreenshot(String fileName) {
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(scrFile, new File(PROJECT_DIR + "\\screenshots\\" + fileName + ".jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@BeforeClass
	public void intiDriver() {
		createDriver("chrome");
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);

	}

	@AfterClass
	public void quitDriver() {
		if (driver != null) {
			driver.quit();
			driver = null;
		}
	}
}
