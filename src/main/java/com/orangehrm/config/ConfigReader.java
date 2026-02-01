    package com.orangehrm.config;

    import java.io.FileInputStream;
    import java.io.IOException;
    import java.util.Properties;
    import org.apache.logging.log4j.LogManager;
    import org.apache.logging.log4j.Logger;

    public class ConfigReader {
        private static Properties prop;
        private static final Logger logger = LogManager.getLogger(ConfigReader.class);

        static {
            try {
                logger.info("Initializing ConfigReader - loading configuration properties");
                String path = System.getProperty("user.dir") + "/src/test/resources/config.properties";
                logger.info("Loading config file from: " + path);
                FileInputStream fis = new FileInputStream(path);
                prop = new Properties();
                prop.load(fis);
                logger.info("Configuration properties loaded successfully");
            } catch (IOException e) {
                logger.error("Failed to load configuration file: " + e.getMessage());
                e.printStackTrace();
                throw new RuntimeException("File Not Found");
            }
        }

        public static String getProperty(String key) {
            logger.info("Retrieving property for key: " + key);
            String value = prop.getProperty(key);
            if (value == null) {
                logger.error("Property not found for key: " + key);
                throw new RuntimeException("Key [" + key + "] not found in config.properties file.");
            }
            logger.info("Property retrieved successfully for key: " + key + " with value: " + value);
            return value;
        }
    }
