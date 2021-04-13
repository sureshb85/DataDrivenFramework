package com.cts.training;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.cts.training.utils.ExtentManager;

public class TestBase {

	protected WebDriver driver = null;
	protected WebDriverWait wait = null;

	ExtentTest test;
	ExtentReports reports;

	@BeforeMethod
	public void init(Method method) {
		String testName = method.getName();
		System.out.println(testName + "executing");
		reports = ExtentManager.getReports();
		test = reports.createTest(testName);
	}

	@AfterMethod
	public void quit() {
		reports.flush();
	}

	public void createDriver(String browser) {
		BrowserFactory browserFactory = new BrowserFactory(browser);
		driver = browserFactory.createDriver();
	}

	WebElement getElement(String locator, String locatorValue) {
		WebElement element = null;
		test.log(Status.INFO, "finding element " + locator + ":" + locatorValue);
		if (locator.equalsIgnoreCase("css")) {
			element = driver.findElement(By.cssSelector(locatorValue));
		} else if (locator.equalsIgnoreCase("xpath")) {
			element = driver.findElement(By.xpath(locatorValue));
		}
		return element;
	}

	void enterText(String locator, String locatorValue, String text) {

		WebElement element = getElement(locator, locatorValue);
		if (element.isDisplayed()) {
			System.out.println("entering text: " + text);
			element.sendKeys(text);
		} else {
			System.out.println("Element not found");
		}
	}

	void clickElement(String locator, String locatorValue) {
		WebElement element = getElement(locator, locatorValue);
		if (element.isEnabled()) {
			element.click();
		} else {
			System.out.println("Element not found");
		}
	}

	void selectDropDownValue(String locator, String locatorValue, String text) {
		WebElement element = getElement(locator, locatorValue);
		if (element.isEnabled()) {
			element.click();
		} else {
			System.out.println("Element not found");
		}
		Select selectValue = new Select(element);
		selectValue.selectByVisibleText(text);
	}

	void waitUntilElementVisible(String locator, String locatorValue) {
		WebElement element = getElement(locator, locatorValue);
		wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOf(element));

	}

	public void getScreenshot(String fileName) {
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(scrFile, new File(BrowserFactory.PROJECT_DIR + "\\screenshots\\" + fileName + ".jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Parameters("browser")
	@BeforeClass
	public void intiDriver(@Optional("chrome") String browser) {
		BrowserFactory browserFactory = new BrowserFactory(browser);
		driver = browserFactory.createDriver();
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
