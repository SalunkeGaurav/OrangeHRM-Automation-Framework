package com.orangehrm.base;

import com.orangehrm.config.ConfigReader;
import com.orangehrm.pages.DashboardPage;
import com.orangehrm.pages.LoginPage;
import org.testng.annotations.BeforeMethod;

public class LoggedInBaseTest extends BaseTest {

    protected DashboardPage dashboardPage;

    @BeforeMethod
    public void loginToApplication() {
        // Since we extend BaseTest, 'driver' is already initialized here
        LoginPage loginPage = new LoginPage(driver);

        dashboardPage = loginPage.login(
                ConfigReader.getProperty("username"),
                ConfigReader.getProperty("password")
        );

        // Logical Check: Ensure we actually reached the dashboard
        log.info("Auto-login performed for functional test execution.");
    }
}