package com.orangehrm.base;

import com.orangehrm.pages.DashboardPage;
import com.orangehrm.pages.LoginPage;
import com.orangehrm.utils.ConfigReader;
import org.testng.annotations.*;

/**
 * Tests extending this class start with user logged in to Dashboard
 */
public class LoggedInBaseTest extends BaseTest {

    protected DashboardPage dashboardPage;

    @BeforeClass
    @Parameters("browser")
    @Override
    public void setUp(@Optional("chrome") String browser) {
        super.setUp(browser);
        log.info("Auto-logging in before test");

        // Initialize login page
        dashboardPage = this.loginPage.login(
                ConfigReader.getProperty("username"),
                ConfigReader.getProperty("password")
        );

        log.info("Login complete - Dashboard ready");
    }
}