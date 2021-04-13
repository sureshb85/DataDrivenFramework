package com.cts.training.utils;

import java.io.File;
import java.util.Date;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentManager {

	static ExtentReports reports;

	public static ExtentReports getReports() {
		if (reports == null) {
			reports = new ExtentReports();
			Date d = new Date();
			String reportsFolder = d.toString().replaceAll(":", "-") + "//screenshots";

			String screenshotFolderPath = System.getProperty("user.dir") + "//reports//" + reportsFolder;
			String reportFolderPath = System.getProperty("user.dir") + "//reports//"
					+ d.toString().replaceAll(":", "-");
			System.out.println(screenshotFolderPath);
			File f = new File(screenshotFolderPath);
			f.mkdirs();
			ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportFolderPath);
			sparkReporter.config().setReportName("AUTOMATION TESTING");
			sparkReporter.config().setDocumentTitle("SELENIUM Reports");
			sparkReporter.config().setTheme(Theme.STANDARD);
			sparkReporter.config().setEncoding("utf-8");

			reports.attachReporter(sparkReporter);
		}

		return reports;

	}

}
