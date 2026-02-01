package com.orangehrm.base;
import com.orangehrm.config.ConfigReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

public class BaseTest {

    protected static final Logger log = LogManager.getLogger(BaseTest.class);
    protected WebDriver driver;

    @Parameters("browser")
    @BeforeMethod
    public void setUp(String browser) {
        log.info("Setting up browser: " + browser);
        DriverFactory.initDriver(browser);

        driver = DriverFactory.getDriver();
        log.info("Navigating to URL: " + ConfigReader.getProperty("baseUrl"));
        driver.get(ConfigReader.getProperty("baseUrl"));
    }

    @AfterMethod
    public void tearDown() {
        log.info("Closing browser");
        DriverFactory.quitDriver();
    }
}