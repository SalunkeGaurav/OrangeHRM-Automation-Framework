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
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class DriverFactory {

    private static final ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();
    private static final Logger log = LogManager.getLogger(DriverFactory.class);

    public static WebDriver initDriver(String browserName) {
        String executionMode = ConfigReader.getProperty("execution_mode").toLowerCase();
        boolean isHeadless = Boolean.parseBoolean(ConfigReader.getProperty("headless"));

        if (browserName == null || browserName.isEmpty()) {
            browserName = ConfigReader.getProperty("browser");
        }

        log.info("Initializing driver: Browser={}, Mode={}, Headless={}", browserName, executionMode, isHeadless);

        WebDriver driver;

        if (executionMode.equals("remote")) {
            driver = createRemoteDriver(browserName, isHeadless);
        } else {
            driver = createLocalDriver(browserName, isHeadless);
        }

        tlDriver.set(driver);

        getDriver().manage().deleteAllCookies();
        if (isHeadless) {
            getDriver().manage().window().setSize(new org.openqa.selenium.Dimension(1920, 1080));
        } else {
            getDriver().manage().window().maximize();
        }

        return getDriver();
    }

    private static WebDriver createLocalDriver(String browser, boolean headless) {
        switch (browser.toLowerCase()) {
            case "chrome":
                return new ChromeDriver(getChromeOptions(headless));
            case "firefox":
                return new FirefoxDriver(getFirefoxOptions(headless));
            case "edge":
                return new EdgeDriver(getEdgeOptions(headless));
            default:
                throw new IllegalArgumentException("Invalid local browser: " + browser);
        }
    }

    private static WebDriver createRemoteDriver(String browser, boolean headless) {
        String hubUrl = ConfigReader.getProperty("selenium_grid_url");
        try {
            switch (browser.toLowerCase()) {
                case "chrome":
                    return new RemoteWebDriver(new URL(hubUrl), getChromeOptions(headless));
                case "firefox":
                    return new RemoteWebDriver(new URL(hubUrl), getFirefoxOptions(headless));
                case "edge":
                    return new RemoteWebDriver(new URL(hubUrl), getEdgeOptions(headless));
                default:
                    throw new IllegalArgumentException("Invalid remote browser: " + browser);
            }
        } catch (MalformedURLException e) {
            log.error("Grid URL is malformed: {}", hubUrl);
            throw new RuntimeException("Check your selenium_grid_url in config properties", e);
        }
    }

    // Helper methods to keep code DRY
    private static ChromeOptions getChromeOptions(boolean headless) {
        ChromeOptions options = new ChromeOptions();
        if (headless) options.addArguments("--headless=new");
        return options;
    }

    private static FirefoxOptions getFirefoxOptions(boolean headless) {
        FirefoxOptions options = new FirefoxOptions();
        if (headless) options.addArguments("-headless");
        return options;
    }

    private static EdgeOptions getEdgeOptions(boolean headless) {
        EdgeOptions options = new EdgeOptions();
        if (headless) options.addArguments("--headless=new");
        return options;
    }

    public static synchronized WebDriver getDriver() {
        return tlDriver.get();
    }

    public static void quitDriver() {
        if (getDriver() != null) {
            getDriver().quit();
            tlDriver.remove();
            log.info("Driver instance removed.");
        }
    }
}