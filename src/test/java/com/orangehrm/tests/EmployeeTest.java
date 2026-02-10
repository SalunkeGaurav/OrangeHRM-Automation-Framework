package com.orangehrm.tests;

import com.orangehrm.base.LoggedInBaseTest;
import com.orangehrm.pages.PIMPage;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Objects;

/**
 * Employee CRUD test cases
 */
public class EmployeeTest extends LoggedInBaseTest {

    private String firstName;
    private String lastName;
    private String employeeId;

    @Test(groups = {"employee", "crud", "smoke"}, priority = 1, description = "Add a new employee with a unique ID and Name")
    public void testAddEmployee() {
        PIMPage pim = dashboardPage.navigateToPIMPage();

        String threadId = pim.getThreadSpecificID();
        firstName = "Test" + threadId;
        lastName = "User" + threadId;
        employeeId = threadId;

        log.info("Adding employee: {}", firstName);

        pim.addEmployee(firstName, lastName, employeeId);

        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("viewPersonalDetails"),
                "Employee not saved or redirected");

        log.info("Employee added successfully");
    }

    @Test(groups = {"employee", "crud"}, priority = 2, dependsOnMethods = "testAddEmployee", description = "Update both First and Last name")
    public void testUpdateEmployee() {
        PIMPage pim = dashboardPage.navigateToPIMPage();
        String threadId = pim.getThreadSpecificID();
        String updatedFirstName = "Aman" + threadId;
        String updatedLastName = "Kumar" + threadId;

        log.info("Updating {} to {} {}", firstName, updatedFirstName, updatedLastName);

        String currentFullName = firstName + " " + lastName;

        pim.updateEmployeeDetails(currentFullName, updatedFirstName, updatedLastName);

        String updatedFullName = updatedFirstName + " " + updatedLastName;
        pim.searchEmployee(updatedFullName);

        Assert.assertFalse(pim.getEditIcons().isEmpty(), "Update failed: Employee '" + updatedFullName + "' not found");

        firstName = updatedFirstName;
        lastName = updatedLastName;

        log.info("Employee updated successfully");
    }

    @Test(groups = {"employee", "crud"}, priority = 3, dependsOnMethods = "testUpdateEmployee", description = "Delete using full name")
    public void testDeleteEmployee() {
        PIMPage pim = dashboardPage.navigateToPIMPage();

        log.info("Deleting {} {}", firstName, lastName);

        String fullName = firstName + " " + lastName;

        boolean isDeleted = pim.deleteEmployeeByFullName(fullName);

        Assert.assertTrue(isDeleted, "Failed to delete employee: " + fullName);

        log.info("Employee deleted successfully");

    }
}