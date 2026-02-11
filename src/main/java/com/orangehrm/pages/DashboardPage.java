package com.orangehrm.pages;

import com.orangehrm.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * OrangeHRM Dashboard page with navigation to modules
 */

public class DashboardPage extends BasePage {

    @FindBy(xpath = "//h6[normalize-space()='Dashboard']")
    protected WebElement lblDashboardHeader;

    @FindBy(xpath = "//span[text()='PIM']")
    private WebElement linkPIM;

    public DashboardPage(WebDriver driver) {
        super(driver);
    }

    public boolean isDashboardDisplayed() {
        log.info("Checking Dashboard header...");
        return isDisplayed(lblDashboardHeader);
    }

    public PIMPage navigateToPIMPage() {
        log.info("Opening PIM module");
        click(linkPIM);
        return new PIMPage(driver);
    }
}