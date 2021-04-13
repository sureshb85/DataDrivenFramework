package com.cts.training;

import java.io.File;
import java.util.Iterator;
import java.util.Map;

import org.openqa.selenium.WebElement;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.cts.training.utils.CSVDataReader;

public class RegistrationCSVData extends TestBase {
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

	@Test(dataProvider = "csvData")
	public void registerPersonalUser(Map<String, String> testData) {
		driver.get("https://www.ebay.com");
		clickRegister();
		selectPersonalAccount();
		// Data
		String firstname = testData.get("firstname");
		String lastname = testData.get("lastname");
		String username = testData.get("username");
		String password = testData.get("password");

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

	@DataProvider(name = "csvData")
	Iterator<Object[]> getCSVData() {
		String filePath = PROJECT_DIR + File.separator + "testdata" + File.separator + "ebay_register.csv";
		CSVDataReader csvData = new CSVDataReader(filePath);
		return csvData.readCSVData();

	}

}
