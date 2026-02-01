package com.orangehrm.pages;

import com.orangehrm.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {

    @FindBy(name = "username")
    private WebElement txtUserName;

    @FindBy(name = "password")
    private WebElement txtUserPass;

    @FindBy(css = "button[type='submit']")
    private WebElement btnLogin;

    @FindBy(css = ".oxd-alert-content-text")
    private WebElement errorMessage;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void enterUsername(String username) {
        log.info("Entering username: {}", username);
        type(txtUserName, username);
    }

    public void enterPassword(String password) {
        log.info("Entering password");
        type(txtUserPass, password);
    }

    public DashboardPage clickLogin() {
        log.info("Clicking login button");
        click(btnLogin);
        return new DashboardPage(driver);
    }

    public DashboardPage login(String username, String password) {
        log.info("Performing login with username: {}", username);
        enterUsername(username);
        enterPassword(password);
        return clickLogin();
    }

    public boolean isLoginButtonDisplayed() {
        return isDisplayed(btnLogin);
    }

    public String getErrorMessage() {
        log.info("Getting error message");
        return getText(errorMessage);
    }
}