package com.orangehrm.base;

import com.orangehrm.config.ConfigReader;
import com.orangehrm.utils.ScreenshotUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

public class BaseTest {

    protected static final Logger log = LogManager.getLogger(BaseTest.class);
    protected WebDriver driver;

    @Parameters("browser")
    @BeforeMethod
    public void setUp(String browser) {
        log.info("=== Test Setup Started ===");
        log.info("Setting up browser: {}", browser);

        DriverFactory.initDriver(browser);
        driver = DriverFactory.getDriver();

        String baseUrl = ConfigReader.getProperty("baseUrl");
        log.info("Navigating to URL: {}", baseUrl);
        driver.get(baseUrl);

        log.info("=== Test Setup Completed ===");
    }


    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        // Capture screenshot on failure
        if (result.getStatus() == ITestResult.FAILURE) {
            log.error("Test FAILED: {}", result.getName());
            String screenshotPath = ScreenshotUtils.captureScreenshot(driver, result.getName());
            if (screenshotPath != null) {
                log.info("Screenshot saved at: {}", screenshotPath);
            }
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            log.info("Test PASSED: {}", result.getName());
        } else if (result.getStatus() == ITestResult.SKIP) {
            log.warn("Test SKIPPED: {}", result.getName());
        }

        log.info("=== Test Teardown Started ===");
        DriverFactory.quitDriver();
        log.info("=== Test Teardown Completed ===");
    }
}
