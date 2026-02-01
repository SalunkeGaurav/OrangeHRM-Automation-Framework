package com.orangehrm.base;

import com.orangehrm.config.ConfigReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage {

    protected static final Logger log = LogManager.getLogger(BasePage.class);
    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        long timeout;
        try {
            timeout = Long.parseLong(ConfigReader.getProperty("explicitWait"));
        } catch (Exception e) {
            timeout = 10;
            log.warn("Could not read explicitWait from config. Using default: {} seconds", timeout);
        }
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        PageFactory.initElements(this.driver, this);
        log.info("{} initialized successfully", this.getClass().getSimpleName());
    }

    protected void click(WebElement element) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(element)).click();
        } catch (Exception e) {
            log.error("Failed to click element: {}", e.getMessage());
            throw e;
        }
    }

    protected void type(WebElement element, String text) {
        try {
            WebElement visibleElement = wait.until(ExpectedConditions.visibilityOf(element));
            visibleElement.clear();
            visibleElement.sendKeys(text);
        } catch (Exception e) {
            log.error("Failed to type text '{}': {}", text, e.getMessage());
            throw e;
        }
    }

    protected String getText(WebElement element) {
        try {
            return wait.until(ExpectedConditions.visibilityOf(element)).getText();
        } catch (Exception e) {
            log.error("Failed to get text from element: {}", e.getMessage());
            throw e;
        }
    }

    protected boolean isDisplayed(WebElement element) {
        try {
            return wait.until(ExpectedConditions.visibilityOf(element)).isDisplayed();
        } catch (Exception e) {
            log.debug("Element not displayed: {}", e.getMessage());
            return false;
        }
    }

    protected void waitForVisibility(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    protected void waitForInvisibility(WebElement element) {
        wait.until(ExpectedConditions.invisibilityOf(element));
    }

    protected void navigateTo(String url) {
        log.info("Navigating to URL: {}", url);
        driver.get(url);
    }

    protected String getPageTitle() {
        return driver.getTitle();
    }

    protected String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}
