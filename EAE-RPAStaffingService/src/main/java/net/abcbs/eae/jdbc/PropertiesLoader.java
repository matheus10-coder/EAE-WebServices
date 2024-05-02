package net.abcbs.eae.jdbc;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import net.abcbs.rpa.javabeans.StaffWorkloadJavaBean;

public class PropertiesLoader {
private String jdbcProperties;
	
	//Add Default Constructor to indicate the correct path to JDBC properties (that way it doesn't get stuck on a infite loop)
	public PropertiesLoader() {
		jdbcProperties = "jdbc/jdbc.properties";
	}
	
	//New Constructor will require a new parameter to receive the proper file location
	/*
	 * Code goes here
	 *
	 */
	
	
	public String loadJDBCProperties(String propertyName) {
		String jdbcProp = null;
		
		try {
			//Load the property file from the class path
			InputStream inputStream = PropertiesLoader.class.getClassLoader().getResourceAsStream(jdbcProperties);
			Properties properties = new Properties ();
			properties.load(inputStream);
			inputStream.close();
			jdbcProp = properties.getProperty(propertyName);
			
			
		} catch (IOException e) {
			//have a message created 
			e.printStackTrace();
		}
		return jdbcProp;
	}
	
	
	public String getJdbcProperties() {
		return jdbcProperties;
	}

	public void setJdbcProperties(String jdbcProperties) {
		this.jdbcProperties = jdbcProperties;
	}
	

}
