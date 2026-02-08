package com.orangehrm.testdata;

import org.testng.annotations.DataProvider;

/**
 * Test data provider for data-driven tests
 */
public class TestDataProvider {

    /**
     * Provides login credentials for invalid login tests
     */
    @DataProvider(name = "invalidLoginData")
    public static Object[][] getInvalidLoginData() {
        return new Object[][]{
                {"admin", "Admin123"},
                {"", "admin123"},
                {"Admin", ""},
                {"", ""}
        };
    }


}