package com.orangehrm.base;

import com.orangehrm.config.ConfigReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.time.Duration;

public class DriverFactory {

    private static final ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();
    private static final Logger log = LogManager.getLogger(DriverFactory.class);

    public static WebDriver initDriver(String browserName) {

        if (browserName == null || browserName.isEmpty()) {
            browserName = ConfigReader.getProperty("browser");
            log.info("Browser not passed from XML. Using default: {}", browserName);
        } else {
            log.info("Browser received from XML: {}", browserName);
        }

        switch (browserName.toLowerCase()) {
            case "chrome":
                log.info("Launching Chrome browser");
                tlDriver.set(new ChromeDriver());
                break;
            case "firefox":
                log.info("Launching Firefox browser");
                tlDriver.set(new FirefoxDriver());
                break;
            case "edge":
                log.info("Launching Edge browser");
                tlDriver.set(new EdgeDriver());
                break;
            default:
                log.error("Unsupported browser: {}", browserName);
                throw new IllegalArgumentException("Unsupported browser: " + browserName);
        }

        WebDriver driver = getDriver();
        driver.manage().deleteAllCookies();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));

        log.info("Browser launched and configured successfully");
        return driver;
    }

    public static synchronized WebDriver getDriver() {
        return tlDriver.get();
    }

    public static void quitDriver() {
        WebDriver driver = tlDriver.get();
        if (driver != null) {
            log.info("Quitting driver for thread: {}", Thread.currentThread().getId());
            driver.quit();
            tlDriver.remove();
            log.info("Driver quit successfully");
        }
    }
}