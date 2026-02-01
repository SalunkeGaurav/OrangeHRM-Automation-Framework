package com.orangehrm.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConfigReader {
    private static final Properties prop = new Properties();
    private static final Logger logger = LogManager.getLogger(ConfigReader.class);

    static {
        loadProperties();
    }

    private static void loadProperties() {
        String configPath = System.getProperty("user.dir") + "/src/test/resources/config.properties";
        logger.info("Loading configuration from: {}", configPath);

        try (FileInputStream fis = new FileInputStream(configPath)) {
            prop.load(fis);
            logger.info("Configuration loaded successfully with {} properties", prop.size());
        } catch (IOException e) {
            logger.error("Failed to load configuration file from: {}", configPath, e);
            throw new RuntimeException("Configuration file not found at: " + configPath, e);
        }
    }

    public static String getProperty(String key) {
        String value = prop.getProperty(key);
        if (value == null || value.trim().isEmpty()) {
            logger.error("Property not found or empty for key: {}", key);
            throw new RuntimeException("Property [" + key + "] not found in config.properties");
        }
        return value;
    }

    // Optional: Method to get property with default value
    public static String getProperty(String key, String defaultValue) {
        String value = prop.getProperty(key);
        if (value == null || value.trim().isEmpty()) {
            logger.warn("Property [{}] not found, using default value", key);
            return defaultValue;
        }
        return value;
    }
}