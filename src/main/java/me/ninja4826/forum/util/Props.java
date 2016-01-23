package me.ninja4826.forum.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import spark.resource.ClassPathResource;

public class Props {
	
	private static Props instance = new Props();
	
	private Properties p;
	
	private Props() {
		ClassPathResource resource = new ClassPathResource("config.properties");
		p = new Properties();
		InputStream inputStream = null;
		try {
			inputStream = resource.getInputStream();
			p.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static Props getInstance() {
		return instance;
	}
	
	public String getProperty(String prop) {
		return p.getProperty(prop);
	}
	
	public static String getProp(String prop) {
		if (System.getProperty(prop) != null) {
			return System.getProperty(prop);
		} else {
			return instance.getProperty(prop);
		}
	}
}
