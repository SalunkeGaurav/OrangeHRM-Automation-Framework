package com.orangehrm.base;

import com.orangehrm.pages.LoginPage;
import com.orangehrm.utils.ConfigReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.*;

/**
 * Base class for all test classes
 */
public class BaseTest extends BasePage {

    protected LoginPage loginPage;
    protected static final Logger log = LogManager.getLogger(BaseTest.class);

    @BeforeClass
    @Parameters("browser")
    public void setUp(@Optional("chrome") String browser) {
        log.info("Starting test setup for: {}", browser);

        // Initialize driver using factory and pass to BasePage constructor
        DriverFactory.initDriver(browser);
        String url = ConfigReader.getProperty("url");
        getDriver().get(url);
        log.info("Navigated to: {}", url);
        loginPage = new LoginPage(getDriver());
    }


    @AfterClass
    public void tearDown() {
        log.info("Cleaning up test resources");
        if (getDriver() != null) {
            DriverFactory.quitDriver();
        }
    }
}