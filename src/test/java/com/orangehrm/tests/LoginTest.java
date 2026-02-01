package com.orangehrm.tests;

import com.orangehrm.base.BaseTest;
import com.orangehrm.config.ConfigReader;
import com.orangehrm.pages.DashboardPage;
import com.orangehrm.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    @Test(priority = 1)
    public void verifyValidLogin() {
        log.info("Starting valid login test");

        LoginPage loginPage = new LoginPage(driver);
        String username = ConfigReader.getProperty("username");
        String password = ConfigReader.getProperty("password");

        log.info("Attempting login with username: {}", username);
        DashboardPage dashboardPage = loginPage.login(username, password);

        log.info("Verifying dashboard is displayed");
        Assert.assertTrue(dashboardPage.isDashboardDisplayed(),
                "Login failed - Dashboard not displayed");

        Assert.assertEquals(dashboardPage.getHeaderText(), "Dashboard",
                "Dashboard header text mismatch");

        log.info("Valid login test completed successfully");
    }

    @Test(priority = 2)
    public void verifyInvalidLogin() {
        log.info("Starting invalid login test");

        LoginPage loginPage = new LoginPage(driver);

        log.info("Attempting login with invalid credentials");
        loginPage.login("InvalidUser", "InvalidPass");

        log.info("Verifying user remains on login page");
        Assert.assertTrue(loginPage.isLoginButtonDisplayed(),
                "Login button should still be visible for invalid login");

        log.info("Invalid login test completed successfully");
    }

    @Test(priority = 3)
    public void verifyEmptyCredentialsLogin() {
        log.info("Starting empty credentials login test");

        LoginPage loginPage = new LoginPage(driver);

        log.info("Attempting login with empty credentials");
        loginPage.enterUsername("");
        loginPage.enterPassword("");
        loginPage.clickLogin();

        log.info("Verifying user remains on login page");
        Assert.assertTrue(loginPage.isLoginButtonDisplayed(),
                "Login button should still be visible");

        log.info("Empty credentials test completed successfully");
    }
}