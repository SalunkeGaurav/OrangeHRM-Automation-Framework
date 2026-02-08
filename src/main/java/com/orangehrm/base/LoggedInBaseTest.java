package com.orangehrm.base;

import com.orangehrm.pages.DashboardPage;
import com.orangehrm.pages.LoginPage;
import com.orangehrm.utils.ConfigReader;
import org.testng.annotations.BeforeMethod;
/**
 * Tests extending this class start with user logged in to Dashboard
  */
public class LoggedInBaseTest extends BaseTest {

    protected DashboardPage dashboardPage;

    @BeforeMethod
    public void setupLogin() {
        log.info("Auto-logging in before test");

        // Initialize login page
        LoginPage loginPage = new LoginPage(getDriver());
                dashboardPage = loginPage.login(
                ConfigReader.getProperty("username"),
                ConfigReader.getProperty("password")
        );

        log.info("Login complete - Dashboard ready");
    }
}