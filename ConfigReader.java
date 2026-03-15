/*This utility class reads values from config.properties at runtime. This way your tests can call ConfigReader.get("browser") instead of hardcoding values. It loads the file once using a static block so it's efficient.
*/

package java.com.automation.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
	private static Properties properties = new Properties();

//	static block runs once when class is loaded
	static {
		try {
			FileInputStream fis = new FileInputStream("/formy-automation/src/main/resources/config.properties");

			properties.load(fis);
			fis.close();
		} catch (IOException e) {
			throw new RuntimeException("Could not load config.properties: " + e.getMessage());
		}
	}

	/**
	 * Retrieves a property value by key
	 * 
	 * @param key - the property name
	 * @return the property value as String after trimming
	 */

	public static String get(String key) {
		String value = properties.getProperty(key);
		if (value == null) {
			throw new RuntimeException("Property '" + key + "'not found in config.properties");
		}
		return value.trim();
	}

}
