package com.orangehrm.base;

import com.orangehrm.utils.ConfigReader;
import com.orangehrm.utils.WaitUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Base class for all page objects
 */

public class BasePage {
    protected WebDriver driver;
    protected WaitUtils waitUtils;
    protected static final Logger log = LogManager.getLogger(BasePage.class);

    //Locator for OrangeHRM loading spinners
    private final By globalLoader = By.cssSelector(".oxd-loading-spinner, .oxd-form-loader");

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.waitUtils = new WaitUtils(driver);
        PageFactory.initElements(driver, this);
    }

    /**
     * Get unique thread ID for parallel tests
     * @return thread ID as string
     */
    public String getThreadSpecificID() {
        return String.valueOf(Thread.currentThread().getId());
    }

    /**
     * Wait for OrangeHRM spinners to disappear
     */
    public void waitForLoaderToDisappear() {
        try {
            long timeout = Long.parseLong(ConfigReader.getProperty("explicitWait", "10"));
            new WebDriverWait(driver, Duration.ofSeconds(timeout))
                    .until(ExpectedConditions.invisibilityOfElementLocated(globalLoader));
        } catch (Exception e) {
            log.debug("No spinner found - page is ready to go!");
        }
    }

    protected void click(WebElement element) {
        if (element == null) {
            log.error("Cannot click - element is null");
            return;
        }
        waitForLoaderToDisappear();
        waitUtils.waitForClickable(element);
        try {
            element.click();
        } catch (Exception e) {
            log.warn("Regular click failed, trying JavaScript click for element: {}", element.toString());
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        }
    }

    protected void type(WebElement element, String text) {
        if (element == null || text == null) {
            log.error("Cannot type - element or text is null");
            return;
        }
        waitForLoaderToDisappear();
        waitUtils.waitForVisibility(element);
        waitUtils.waitForClickable(element);
        element.clear();
        element.sendKeys(text);
    }

    protected boolean isDisplayed(WebElement element) {
        if (element == null) {
            log.error("Cannot check is displayed - element is null");
            return false;
        }

        try {
            waitForLoaderToDisappear();
            waitUtils.waitForVisibility(element);
            return element.isDisplayed();
        } catch (Exception e) {
            log.error("element not displayed: {}", element.toString());
            return false;
        }
    }

    protected String getText(WebElement element) {
        if (element == null) {
            log.error("Cannot get text - element is null");
            return null;
        }
        waitForLoaderToDisappear();
        waitUtils.waitForVisibility(element);
        return element.getText();
    }
}