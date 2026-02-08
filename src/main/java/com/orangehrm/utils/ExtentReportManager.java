package com.orangehrm.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ExtentReports utility manager
 */
public class ExtentReportManager {

    private static final Logger log = LogManager.getLogger(ExtentReportManager.class);
    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();
    private static String reportPath;

    public static void initReports() {
        if (extent == null) {
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            reportPath = "./reports/TestReport_" + timestamp + ".html";

            File reportDir = new File("./reports");
            if (!reportDir.exists()) {
                reportDir.mkdir();
            }

            ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);

            sparkReporter.config().setDocumentTitle("OrangeHRM Automation Report");
            sparkReporter.config().setReportName("Test Execution Report");
            sparkReporter.config().setTheme(Theme.DARK);
            sparkReporter.config().setTimeStampFormat("dd-MM-yyyy HH:mm:ss");

            extent = new ExtentReports();
            extent.attachReporter(sparkReporter);

            extent.setSystemInfo("Application", "OrangeHRM");
            extent.setSystemInfo("Environment", "QA");
            extent.setSystemInfo("User", System.getProperty("user.name"));
            extent.setSystemInfo("OS", System.getProperty("os.name"));
            extent.setSystemInfo("Java Version", System.getProperty("java.version"));

            log.info("Report initialized: {}", reportPath);
        }
    }

    public static void createTest(String testName) {
        ExtentTest test = extent.createTest(testName);
        extentTest.set(test);
        log.info("Test created: {}", testName);
    }

    /**
     * Get current test
     */
    public static ExtentTest getTest() {
        return extentTest.get();
    }

    public static void logPass(String message) {
        getTest().log(Status.PASS, message);
    }

    public static void logFail(String message) {
        getTest().log(Status.FAIL, message);
    }

    public static void logInfo(String message) {
        getTest().log(Status.INFO, message);
    }

    public static void logWarning(String message) {
        getTest().log(Status.WARNING, message);
    }

    public static void logSkip(String message) {
        getTest().log(Status.SKIP, message);
    }

    public static void attachScreenshot(String screenshotPath) {
        if (screenshotPath != null && !screenshotPath.isEmpty()) {
            try {
                getTest().addScreenCaptureFromPath(screenshotPath);
                log.info("Screenshot attached: {}", screenshotPath);
            } catch (Exception e) {
                log.error("Failed to attach screenshot: {}", e.getMessage());
            }
        }
    }

    /**
     * Flush and save report
     */
    public static void flushReports() {
        if (extent != null) {
            extent.flush();
            log.info("Report saved");
            log.info("Report path: {}", reportPath);
        }
    }

    public static String getReportPath() {
        return reportPath;
    }
}