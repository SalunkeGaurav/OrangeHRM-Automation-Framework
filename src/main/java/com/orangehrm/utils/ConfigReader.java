package com.orangehrm.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Configuration file reader
 */
public class ConfigReader {
    private static final Properties prop = new Properties();
    private static final Logger logger = LogManager.getLogger(ConfigReader.class);

    static {
        loadProperties();
    }

    /**
     * Load configuration file
     */
    private static void loadProperties() {
        String configPath = System.getProperty("user.dir") + "/src/test/resources/config.properties";
        logger.info("Loading config: {}", configPath);

        try (FileInputStream fis = new FileInputStream(configPath)) {
            prop.load(fis);
            logger.info("Config loaded: {} properties", prop.size());
        } catch (IOException e) {
            logger.error("Config file not found: {}", configPath, e);
            throw new RuntimeException("Config file missing: " + configPath, e);
        }
    }

    /**
     * Get property value by key
     */
    public static String getProperty(String key) {
        String value = prop.getProperty(key);
        if (value == null || value.trim().isEmpty()) {
            logger.error("Property missing: {}", key);
            throw new RuntimeException("Property missing: " + key);
        }
        return value;
    }

    /**
     * Get property with default value
     */
    public static String getProperty(String key, String defaultValue) {
        String value = prop.getProperty(key);
        if (value == null || value.trim().isEmpty()) {
            logger.warn("Using default for [{}]", key);
            return defaultValue;
        }
        return value;
    }
}