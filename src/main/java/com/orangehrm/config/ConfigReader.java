package com.orangehrm.config;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    Properties prop;
    FileInputStream fis;
    public String getBrowser() throws IOException {
        prop = new Properties();

        fis = new FileInputStream("./src/test/resources/config.properties");
        prop.load(fis);
        String browser = prop.getProperty("browser");
        return browser;
    }

    public String getBaseUrl(){
        prop = new Properties();
        String browser = prop.getProperty("baseUrl");
        return browser;
    }



}
