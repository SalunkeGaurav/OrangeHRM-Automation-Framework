package com.orangehrm.base;

import com.orangehrm.pages.LoginPage;
import com.orangehrm.utils.ConfigReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

/**
 * Base class for all test classes
 */
public class BaseTest {

    protected WebDriver driver;
    protected LoginPage loginPage;
    protected static final Logger log = LogManager.getLogger(BaseTest.class);

    @BeforeMethod
    @Parameters("browser")
    public void setUp(@Optional("chrome") String browser) {
        log.info("Starting test setup for: {}", browser);

        // Initialize driver using factory
        driver = DriverFactory.initDriver(browser);
        String url = ConfigReader.getProperty("url");
        driver.get(url);
        log.info("Navigated to: {}", url);
        loginPage = new LoginPage(driver);
    }

    /**
     * Helper method to get the thread-safe driver instance.
     */
        public WebDriver getDriver() {
        return DriverFactory.getDriver();
    }

    @AfterMethod
    public void tearDown() {
        log.info("Cleaning up test resources");
        if (driver != null) {
            DriverFactory.quitDriver();
        }
    }
}