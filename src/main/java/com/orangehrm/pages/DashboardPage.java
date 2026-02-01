package com.orangehrm.pages;

import com.orangehrm.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class DashboardPage extends BasePage {

    @FindBy(xpath = "//h6[normalize-space()='Dashboard']")
    private WebElement lblDashboardHeader;

    public DashboardPage(WebDriver driver) {
        super(driver);
    }

    public boolean isDashboardDisplayed() {
        log.info("Checking if dashboard is displayed");
        return isDisplayed(lblDashboardHeader);
    }

    public String getHeaderText() {
        log.info("Getting dashboard header text");
        return getText(lblDashboardHeader);
    }
}