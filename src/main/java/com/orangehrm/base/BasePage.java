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
            log.debug("Explicit wait timeout set from config: {} seconds", timeout);
        } catch (Exception e) {
            timeout = 10;
            log.warn("Using default explicit wait timeout: {} seconds", timeout);
        }
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        PageFactory.initElements(this.driver, this);
        log.debug("BasePage initialized with WebDriverWait");
    }

    protected void click(WebElement element) {
        log.debug("Waiting for element to be clickable and clicking");
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
        log.debug("Element clicked successfully");
    }

    protected void type(WebElement element, String text) {
        log.debug("Waiting for element visibility and typing text");
        wait.until(ExpectedConditions.visibilityOf(element)).sendKeys(text);
        log.debug("Text entered successfully");
    }

}
