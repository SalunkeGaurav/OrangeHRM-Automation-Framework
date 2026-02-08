package com.orangehrm.listeners;

import com.orangehrm.base.DriverFactory;
import com.orangehrm.utils.ExtentReportManager;
import com.orangehrm.utils.ScreenshotUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

/**
 * TestNG listener for logging and reporting
 */
public class TestListener implements ITestListener {

    private static final Logger log = LogManager.getLogger(TestListener.class);

    @Override
    public void onStart(ITestContext context) {
        log.info("Starting test suite: {}", context.getName());

        // Initialize Extent Reports
        ExtentReportManager.initReports();
    }

    @Override
    public void onTestStart(ITestResult result) {
        log.info("Starting test: {}", result.getName());

        // Create test in Extent Report
        ExtentReportManager.createTest(result.getName());
        ExtentReportManager.logInfo("Test execution started: " + result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        log.info("Test passed: {}", result.getName());

        // Log to Extent Report
        ExtentReportManager.logPass("Test passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        log.error("Test failed: {}", result.getName());
        if (result.getThrowable() != null) {
            log.error("Reason: {}", result.getThrowable().getMessage());
        }

        // Capture screenshot
        String screenshotPath = ScreenshotUtils.captureScreenshot(
                DriverFactory.getDriver(),
                result.getName()
        );

        // Log to Extent Report
        ExtentReportManager.logFail("Test failed");
        if (result.getThrowable() != null) {
            ExtentReportManager.logFail("Error: " + result.getThrowable().getMessage());
        }

        // Attach screenshot to report
        if (screenshotPath != null) {
            ExtentReportManager.attachScreenshot(screenshotPath);
            log.info("Screenshot attached to report");
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        log.warn("Test skipped: {}", result.getName());

        // Log to Extent Report
        ExtentReportManager.logSkip("Test skipped");
    }

    @Override
    public void onFinish(ITestContext context) {
        log.info("Test suite finished: {}", context.getName());
        log.info("Total: {}, Passed: {}, Failed: {}, Skipped: {}", 
            context.getAllTestMethods().length,
            context.getPassedTests().size(),
            context.getFailedTests().size(),
            context.getSkippedTests().size());

        // Flush Extent Reports
        ExtentReportManager.flushReports();
        log.info("Report saved to: {}", ExtentReportManager.getReportPath());
    }
}