package com.orangehrm.base;
import com.orangehrm.config.ConfigReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DriverFactory {

    private static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();
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

        getDriver().manage().deleteAllCookies();
        getDriver().manage().window().maximize();
        log.info("Browser launched successfully");
        return getDriver();
    }

    public static synchronized WebDriver getDriver() {
        log.info("Driver instance retrieved successfully");
        return tlDriver.get();

    }

    public static void quitDriver() {
        log.info("Quitting driver");
        if (getDriver() != null) {
            getDriver().quit();
            tlDriver.remove();
        }
    }
}