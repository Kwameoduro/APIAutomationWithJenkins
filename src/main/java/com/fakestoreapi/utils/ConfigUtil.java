package com.fakestoreapi.utils;



import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;



// Utility class for loading configuration values from config.properties file.

public final class ConfigUtil {

    private static final String CONFIG_FILE = "/config.properties";
    private static Properties properties = new Properties();

    static {
        try (InputStream inputStream = ConfigUtil.class.getResourceAsStream(CONFIG_FILE)) {
            if (inputStream != null) {
                properties.load(inputStream);
            } else {
                throw new RuntimeException("Configuration file " + CONFIG_FILE + " not found in classpath");
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load configuration file " + CONFIG_FILE, e);
        }
    }

    private ConfigUtil() {
        // Prevent instantiation
    }


    public static String getProperty(String key) {
        return properties.getProperty(key);
    }


    public static String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }
}
