package com.orangehrm.pages;

import com.orangehrm.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * OrangeHRM login page for user authentication
 */

public class LoginPage extends BasePage {

    @FindBy(name = "username")
    private WebElement txtUserName;

    @FindBy(name = "password")
    private WebElement txtUserPass;

    @FindBy(css = "button[type='submit']")
    private WebElement btnLogin;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public DashboardPage login(String username, String password) {
        if (username == null || username.trim().isEmpty()) {
            log.error("Username cannot be null or empty");
            return null;
        }
        if (password == null || password.trim().isEmpty()) {
            log.error("Password cannot be null or empty");
            return null;
        }
        log.info("Logging in with user: {}", username);
        type(txtUserName, username);
        type(txtUserPass, password);
        click(btnLogin);
        return new DashboardPage(driver);
    }

    public boolean isLoginButtonDisplayed() {
        log.info("Checking login button...");
        return isDisplayed(btnLogin);
    }


}