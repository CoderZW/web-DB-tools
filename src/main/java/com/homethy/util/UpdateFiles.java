package com.homethy.util;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class UpdateFiles {

	public static void updateFile(String key,String value,String filePath){
    Properties props = loadThirdDomainProperties(filePath);
		props.setProperty(key, value);
		try {
			props.store(new FileWriter(new File(filePath)), null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	private static Properties loadThirdDomainProperties(String filePath){
		Properties props = new Properties();
		try {
			props.load(new FileReader(new File(filePath)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return props;
	}
}
