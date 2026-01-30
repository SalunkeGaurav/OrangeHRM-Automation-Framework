package com.orangehrm.base;
import com.orangehrm.config.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.edge.EdgeDriver;

import java.io.IOException;

public class DriverFactory {
    private WebDriver driver;
    private ConfigReader configReader;

    public DriverFactory() {
        configReader = new ConfigReader();
    }

    public WebDriver initDriver(String BrowserName) throws IOException {
        if (BrowserName == null || BrowserName.isEmpty()){
            BrowserName = configReader.getBrowser();
        }

        switch (BrowserName.toLowerCase()){
            case "chrome":
                driver = new ChromeDriver();
                break;
            case "firefox":
                driver = new FirefoxDriver();
                break;
            case "edge":
                driver = new EdgeDriver();
                break;
            default:
                throw new IllegalArgumentException("Unsupported browser: " + BrowserName);
        }
        return driver;
    }
}
