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
    }

    public void waitForClickable(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public void waitForVisibility(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public void waitForInvisibility(WebElement element) {
        wait.until(ExpectedConditions.invisibilityOf(element));
    }


}