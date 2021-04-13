package com.cts.training;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

public class Login extends TestBase {
	void clickSignIn() {
		test.log(Status.INFO, "clicking SignIn link");
		clickElement("xpath", "//li/span//a[text()='Sign in']");
	}

	@Test(dataProvider = "LoginData")
	public void userLogin(String username, String password) {
		test.log(Status.INFO, "navigating to application url");
		driver.get("https://www.ebay.com");
		clickSignIn();
		enterText("css", "#userid", username);
		clickElement("css", "#signin-continue-btn");
		waitUntilElementVisible("css", "#pass");
		enterText("css", "#pass", username);
	}

	@DataProvider(name = "LoginData")
	String[][] getPersonalData() {
		String[][] loginData = { { "Suresh", "Kumar" }, { "Chaitu", "Kumar" } };
		return loginData;
	}
}
