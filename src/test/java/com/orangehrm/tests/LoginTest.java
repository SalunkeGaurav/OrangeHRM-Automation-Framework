package com.orangehrm.tests;

import com.orangehrm.base.BaseTest;
import com.orangehrm.pages.LoginPage;
import com.orangehrm.pages.DashboardPage;
import com.orangehrm.testdata.TestDataProvider;
import com.orangehrm.utils.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Login functionality tests
 */
public class LoginTest extends BaseTest {

    @Test(groups = {"login", "smoke"}, priority = 0, description = "Verify valid login and dashboard display")
    public void testValidLogin() {
        log.info("Starting valid login test");
        LoginPage loginPage = new LoginPage(driver);

        Assert.assertTrue(loginPage.isLoginButtonDisplayed(), "Login button not visible");

        DashboardPage dashboard = loginPage.login(
                ConfigReader.getProperty("username"),
                ConfigReader.getProperty("password")
        );

        Assert.assertTrue(dashboard.isDashboardDisplayed(), "Dashboard not displayed");
        log.info("Valid login test passed");
    }

    @Test(groups = {"login", "negative"}, priority = 2, dataProvider = "invalidLoginData",
            dataProviderClass = TestDataProvider.class,
            description = "Verify negative login scenarios")
    public void testInvalidLogin(String user, String pass) {
        log.info("Testing invalid login for: {}", user);
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.login(user, pass);

        Assert.assertTrue(loginPage.isLoginButtonDisplayed(),
                "Login button missing after failed login");

        log.info("Invalid login test passed for: {}", user);
    }

}