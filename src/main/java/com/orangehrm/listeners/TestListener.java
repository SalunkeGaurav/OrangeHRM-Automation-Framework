package com.orangehrm.listeners;

import com.orangehrm.base.DriverFactory;
import com.orangehrm.utils.ExtentReportManager;
import com.orangehrm.utils.ScreenshotUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {

    private static final Logger log = LogManager.getLogger(TestListener.class);

    @Override
    public void onStart(ITestContext context) {
        log.info("========================================");
        log.info("TEST SUITE STARTED: {}", context.getName());
        log.info("========================================");

        // Initialize Extent Reports
        ExtentReportManager.initReports();
    }

    @Override
    public void onTestStart(ITestResult result) {
        log.info("========================================");
        log.info("TEST STARTED: {}", result.getName());
        log.info("========================================");

        // Create test in Extent Report
        ExtentReportManager.createTest(result.getName());
        ExtentReportManager.logInfo("Test execution started: " + result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        log.info("TEST PASSED: {}", result.getName());

        // Log to Extent Report
        ExtentReportManager.logPass("Test passed successfully: " + result.getName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        log.error("TEST FAILED: {}", result.getName());
        log.error("Failure Reason: {}", result.getThrowable().getMessage());

        // Capture screenshot
        String screenshotPath = ScreenshotUtils.captureScreenshot(
                DriverFactory.getDriver(),
                result.getName()
        );

        // Log to Extent Report
        ExtentReportManager.logFail("Test failed: " + result.getName());
        ExtentReportManager.logFail("Error: " + result.getThrowable().getMessage());

        // Attach screenshot to report
        if (screenshotPath != null) {
            ExtentReportManager.attachScreenshot(screenshotPath);
            log.info("Screenshot captured and attached to report");
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        log.warn("TEST SKIPPED: {}", result.getName());

        // Log to Extent Report
        ExtentReportManager.logSkip("Test skipped: " + result.getName());
    }

    @Override
    public void onFinish(ITestContext context) {
        log.info("========================================");
        log.info("TEST SUITE FINISHED: {}", context.getName());
        log.info("Total Tests: {}", context.getAllTestMethods().length);
        log.info("Passed: {}", context.getPassedTests().size());
        log.info("Failed: {}", context.getFailedTests().size());
        log.info("Skipped: {}", context.getSkippedTests().size());
        log.info("========================================");

        // Flush Extent Reports
        ExtentReportManager.flushReports();
        log.info("Report generated at: {}", ExtentReportManager.getReportPath());
    }
}