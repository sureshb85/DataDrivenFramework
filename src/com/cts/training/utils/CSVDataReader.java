package com.cts.training.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.cts.training.TestBase;
import com.opencsv.CSVReader;

public class CSVDataReader extends TestBase {
	String filePath;
	CSVReader reader;

	public CSVDataReader(String filePath) {
		this.filePath = filePath;

	}

	public Iterator<Object[]> readCSVData() {
		List<Object[]> list = new ArrayList<Object[]>();
		File csvFile = new File(filePath);
		try {
			reader = new CSVReader(new FileReader(csvFile));
			String[] keys = reader.readNext();
			if (keys != null) {
				String[] dataParts;
				while ((dataParts = reader.readNext()) != null) {
					Map<String, String> testData = new HashMap<String, String>();
					for (int i = 0; i < keys.length; i++) {
						testData.put(keys[i], dataParts[i]);
						// System.out.println(keys[i] + dataParts[i]);
					}

					list.add(new Object[] { testData });
				}
			}
			reader.close();
		} catch (FileNotFoundException e) {
			throw new RuntimeException("file not found " + filePath + e.getStackTrace().toString());
		} catch (IOException e) {
			throw new RuntimeException("Invalid file " + filePath + e.getStackTrace().toString());
		}
		return list.iterator();
	}
}
