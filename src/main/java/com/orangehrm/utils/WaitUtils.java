package com.orangehrm.utils;

import com.orangehrm.base.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Duration;

/**
 * Wait utility for explicit waits
 */
public class WaitUtils {

    private final Logger log = LogManager.getLogger(WaitUtils.class);
    private final long timeout;

    public WaitUtils() {
        this.timeout = Long.parseLong(ConfigReader.getProperty("explicitWait", "10"));
        log.debug("Using {} seconds timeout", timeout);
    }

    private WebDriverWait getWait() {
        return new WebDriverWait(DriverFactory.getDriver(), Duration.ofSeconds(timeout));
    }

    public void waitForClickable(WebElement element) {
        log.debug("Waiting for element to be clickable");
        getWait().until(ExpectedConditions.elementToBeClickable(element));
    }

    public void waitForVisibility(WebElement element) {
        log.debug("Waiting for element to be visible");
        getWait().until(ExpectedConditions.visibilityOf(element));
    }

    public void waitForInvisibility(WebElement element) {
        log.debug("Waiting for element to be invisible");
        getWait().until(ExpectedConditions.invisibilityOf(element));
    }

    public void waitForPersonalDetails(WebElement element) {
        log.debug("Waiting for Personal Details element (long wait)");
        new WebDriverWait(DriverFactory.getDriver(), Duration.ofSeconds(25))
                .until(ExpectedConditions.visibilityOf(element));
    }

}