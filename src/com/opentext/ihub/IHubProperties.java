package com.opentext.ihub;

import java.io.InputStream;
import java.util.Properties;

public class IHubProperties {
	private static final String PROPERTIES_FILE = "iHub.properties";
	private Properties properties = new Properties();
	
	public IHubProperties() {
		reloadProperties();
	}
	
	public void reloadProperties() {
		try {
			InputStream inputStream = getClass().getClassLoader().getResourceAsStream(PROPERTIES_FILE);
			
			if(inputStream != null) {
				properties.load(inputStream);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}		
	}
	
	public String getProperty(String propertyName) {
		return properties.getProperty(propertyName);
	}
	
	public String getRestURL() {
		return "http://" + properties.getProperty("hostname")
				         + ":" + properties.getProperty("port")
				         + "/ihub/" + properties.getProperty("version");
	}
}
