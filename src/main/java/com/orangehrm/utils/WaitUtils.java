package com.orangehrm.utils;

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
    private final WebDriverWait wait;

    public WaitUtils(WebDriver driver) {
        long timeout = Long.parseLong(ConfigReader.getProperty("explicitWait", "10"));
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        log.debug("Using {} seconds timeout", timeout);
    }

    public void waitForClickable(WebElement element) {
        log.debug("Waiting for element to be clickable");
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public void waitForVisibility(WebElement element) {
        log.debug("Waiting for element to be visible");
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public void waitForInvisibility(WebElement element) {
        log.debug("Waiting for element to be invisible");
        wait.until(ExpectedConditions.invisibilityOf(element));
    }

    public void waitForPersonalDetails(WebElement element, WebDriver driver) {
        log.debug("Waiting for Personal Details element (long wait)");
        WebDriverWait longWait = new WebDriverWait(driver, Duration.ofSeconds(25));
        longWait.until(ExpectedConditions.visibilityOf(element));
    }

}