package com.cts.training;

import java.io.IOException;

import org.openqa.selenium.WebElement;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.cts.training.utils.XLSReader;

public class Registration extends TestBase {

	void clickRegister() {
		clickElement("xpath", "//a[text()='register']");
	}

	void selectPersonalAccount() {
		boolean isPersonalAccount = getElement("css", "#personalaccount-radio").isSelected();
		if (isPersonalAccount) {
			System.out.println("Personal Account Registration");
		} else {
			clickElement("css", "#personalaccount-radio");

		}
	}

	void selectBusinessAccount() {
		boolean isBusinessAccount = getElement("css", "#businessaccount-radio").isSelected();
		if (isBusinessAccount) {
			System.out.println("Personal Account Registration");
		} else {
			clickElement("css", "#businessaccount-radio");

		}
	}

	@Test(dataProvider = "xlsRegisterData")
	public void registerPersonalUser(String firstname, String lastname, String username, String password) {
		driver.get("https://www.ebay.com");
		clickRegister();
		selectPersonalAccount();
		System.out.println(firstname + lastname + username + password);
		WebElement fName = getElement("css", "#firstname");
		fName.sendKeys(firstname);
		WebElement lName = getElement("css", "#lastname");
		lName.sendKeys(lastname);
		WebElement uName = getElement("css", "#Email");
		uName.sendKeys(username);
		WebElement uPassword = getElement("css", "#password");
		uPassword.sendKeys(password);
	}

	@Test(dataProvider = "businessData", enabled = true)
	public void registerBusinessUser(String name, String email, String password, String location)
			throws InterruptedException {
		driver.get("https://www.ebay.com");
		clickRegister();
		selectBusinessAccount();
		System.out.println(name + email + password + location);
		enterText("css", "#businessName", name);
		enterText("css", "#businessEmail", email);
		enterText("css", "#bizPassword", password);
		selectDropDownValue("css", "#businessCountry", location);
		Thread.sleep(3000);
	}

	@DataProvider(name = "personalData")
	String[][] getPersonalData() {
		String[][] userData = { { "John", "Peter", "john@peter.com", "peter123" },
				{ "John", "William", "john@william.com", "william123" }, { "John", "Ahn", "john@ahn.com", "ahn123" } };
		return userData;
	}

	@DataProvider(name = "businessData")
	String[][] getBusinessData() {
		String[][] userData = { { "Gracy", "gracy@gmail.com", "gracy123", "Australia" },
				{ "Emma", "emma@gmail.com", "Emma123", "Canada" }, { "Merry", "merry@gmail.com", "ahn123", "Aruba" } };
		return userData;
	}

	@DataProvider(name = "xlsRegisterData")
	public String[][] getXlsData() throws IOException {
		String path = BrowserFactory.PROJECT_DIR + "\\testdata\\ebay_login.xls";
		XLSReader xlsReader = new XLSReader(path);
		int totalRows = xlsReader.getRowCount("personalUser");
		int totalCols = xlsReader.getCellCount("personalUser", 0);
		String testData[][] = new String[totalRows][totalCols];
		for (int i = 1; i <= totalRows; i++) {
			for (int j = 0; j < totalCols; j++) {
				testData[i - 1][j] = xlsReader.getCellData("personalUser", i, j);
			}
		}
		return testData;
	}
}

//@Test
//public void registerUser() {
//	driver.findElement(By.xpath("//a[text()='register']")).click();
//	boolean isHeader = driver.findElement(By.xpath("//h1[text()='Create an account']")).isDisplayed();
//	if (isHeader) {
//		System.out.println("Header is displayed");
//	} else {
//		System.out.println("header not displayed");
//
//	}
//
//	driver.findElement(By.cssSelector("#firstname")).sendKeys("John");
//	driver.findElement(By.cssSelector("#lastname")).sendKeys("Peter");
//	driver.findElement(By.cssSelector("#Email")).sendKeys("john@peter.com");
//	driver.findElement(By.cssSelector("#password")).sendKeys("John123");
//
//}
