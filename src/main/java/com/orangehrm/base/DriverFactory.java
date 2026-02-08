package com.orangehrm.base;

import com.orangehrm.utils.ConfigReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;


/**
 * Creates and manages WebDriver instances
 */

   public class DriverFactory {

    private static final ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();
    private static final Logger log = LogManager.getLogger(DriverFactory.class);

    public static WebDriver initDriver(String browserName) {
        boolean isHeadless = Boolean.parseBoolean(ConfigReader.getProperty("headless").toLowerCase());

        if (browserName == null || browserName.isEmpty()) {
            browserName = ConfigReader.getProperty("browser");
            log.info("No browser specified, using default: {}", browserName);
        }

        switch (browserName.toLowerCase()) {
            case "chrome":
                log.info("Starting Chrome (Headless: {})", isHeadless);
                ChromeOptions chromeOptions = new ChromeOptions();
                if (isHeadless) {
                    chromeOptions.addArguments("--headless=new");
                }
                tlDriver.set(new ChromeDriver(chromeOptions));
                break;

            case "firefox":
                log.info("Starting Firefox (Headless: {})", isHeadless);
                FirefoxOptions ffOptions = new FirefoxOptions();
                if (isHeadless) ffOptions.addArguments("-headless");
                tlDriver.set(new FirefoxDriver(ffOptions));
                break;

            case "edge":
                log.info("Starting Edge");
                EdgeOptions edgeOptions = new EdgeOptions();
                if (isHeadless) edgeOptions.addArguments("--headless=new");
                tlDriver.set(new EdgeDriver(edgeOptions));

                break;
            default:
                log.error("Unsupported browser: {}", browserName);
                throw new IllegalArgumentException("Unsupported browser: " + browserName);
        }

        getDriver().manage().deleteAllCookies();

          //Set window size if headless mode is enabled
        if (isHeadless) {
            getDriver().manage().window().setSize(new org.openqa.selenium.Dimension(1920, 1080));
            log.info("Headless mode: Window set to 1920x1080");
        } else {
            getDriver().manage().window().maximize();
        }

        return getDriver();
    }

    public static synchronized WebDriver getDriver() {
        return tlDriver.get();
    }

    public static void quitDriver() {
        if (getDriver() != null) {
            getDriver().quit();
            tlDriver.remove();
            log.info("Driver cleaned up from ThreadLocal");
        }
    }
}