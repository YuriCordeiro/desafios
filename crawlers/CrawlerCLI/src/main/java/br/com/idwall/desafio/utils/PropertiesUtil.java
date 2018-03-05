package br.com.idwall.desafio.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.Properties;

public class PropertiesUtil {
	/**
	 * Searches for the properties file
	 * 
	 * @return props
	 */
	public static Properties getProp() {
		Properties props = new Properties();
		FileInputStream file;
		try {
			file = new FileInputStream("./src/main/resources/config.properties");
			props.load(file);
		} catch (FileNotFoundException e) {
			System.err.println("Config properties file not found");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Config properties file not found");
			e.printStackTrace();
		}
		return props;
	}

	/**
	 * A shortcut method to the <code>getProperty()</code>
	 * 
	 * @param key
	 *            a key contained on .properties file
	 * @return the value contained on the key
	 */
	public static String getBundleMessage(String key) {
		try {
			return getProp().getProperty(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}

	public static String getBundleMessage(String key, Object... params) {
		try {
			return MessageFormat.format(getProp().getProperty(key), params);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}
