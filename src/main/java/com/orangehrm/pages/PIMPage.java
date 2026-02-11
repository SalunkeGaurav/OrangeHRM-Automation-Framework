package com.orangehrm.pages;

import com.orangehrm.base.BasePage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/**
 * Employee management page
 */
public class PIMPage extends BasePage {

    @FindBy(xpath = "//span[text()='PIM']")
    private WebElement menuPIM;

    @FindBy(xpath = "//a[text()='Add Employee']")
    private WebElement linkAddEmployee;

    @FindBy(xpath = "//a[text()='Employee List']")
    private WebElement linkEmployeeList;

    @FindBy(name = "firstName")
    private WebElement txtFirstName;

    @FindBy(name = "lastName")
    private WebElement txtLastName;

    @FindBy(xpath = "(//input[@class='oxd-input oxd-input--active'])[2]")
    private WebElement txtID;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement btnSave;

    @FindBy(xpath = "(//input[@placeholder='Type for hints...'])[1]")
    private WebElement txtSearchName;

    @FindBy(css = "button[type='submit']")
    private WebElement btnSearch;

    @FindBy(css = ".bi-trash")
    private List<WebElement> btnDeleteIcons;

    @FindBy(css = ".bi-pencil-fill")
    private List<WebElement> btnEditIcons;

    @FindBy(xpath = "//button[text()=' Yes, Delete ']")
    private WebElement btnConfirmDelete;

    @FindBy(xpath = "//h6[normalize-space()='Personal Details']")
    private WebElement lblPersonalDetails;

    public List<WebElement> getEditIcons() {
        return btnEditIcons;
    }


    public PIMPage(WebDriver driver) {
        super(driver);
    }


    public void addEmployee(String firstName, String lastName, String empId) {
        log.info("Adding employee: {}", empId);

        click(linkAddEmployee);

        type(txtFirstName, firstName);
        type(txtLastName, lastName);

        log.info("Setting employee ID: {}", empId);
        type(txtID, empId);

        click(btnSave);
        waitForLoaderToDisappear();

        waitUtils.waitForPersonalDetails(lblPersonalDetails, driver);
        log.info("Employee saved - redirected to details");
    }

    public void searchEmployee(String fullName) {
        log.info("Searching for: {}", fullName);

        click(linkEmployeeList);
        waitForLoaderToDisappear();

        waitUtils.waitForVisibility(txtSearchName);
        type(txtSearchName, fullName);

        click(btnSearch);

        waitForLoaderToDisappear();
        log.info("Search completed");
    }


    public void updateEmployeeDetails(String currentFullName, String newFirstName, String newLastName) {
        log.info("Starting Update Process for: {} ", currentFullName);

        searchEmployee(currentFullName);

        // Check if search returned results
        if (btnEditIcons.isEmpty()) {
            log.error("Update failed: No employee found with the name '{}'", currentFullName);
            return;
        }

        log.info("Employee found. Navigating to the Edit page.");
        click(btnEditIcons.get(0));

        waitUtils.waitForPersonalDetails(lblPersonalDetails, driver);
        log.info("Updating to: {} {}", newFirstName, newLastName);

        waitForLoaderToDisappear();

        txtFirstName.click();
        txtFirstName.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        txtFirstName.sendKeys(Keys.BACK_SPACE);
        txtFirstName.sendKeys(newFirstName);

        txtLastName.click();
        txtLastName.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        txtLastName.sendKeys(Keys.BACK_SPACE);
        txtLastName.sendKeys(newLastName);

        click(btnSave);
        waitForLoaderToDisappear();

        log.info("Update successful: {} {}", newFirstName, newLastName);
    }


    public boolean deleteEmployeeByFullName(String fullName) {
        log.info("Deleting: {}", fullName);

        for (int i = 0; i < 3; i++) {
            try {
                searchEmployee(fullName);

                if (btnDeleteIcons.isEmpty()) {
                    log.error("Delete test failed: Employee '{}' not found - cannot test deletion", fullName);
                    return false;
                }

                log.info("Clicking delete icon...");
                click(btnDeleteIcons.get(0));
                click(btnConfirmDelete);
                waitForLoaderToDisappear();
                waitUtils.waitForInvisibility(btnConfirmDelete);
                waitUtils.waitForVisibility(txtSearchName);
                
                searchEmployee(fullName);
                
                // Verify employee is no longer in list
                if (btnDeleteIcons.isEmpty()) {
                    log.info("Deletion verified - employee no longer in list");
                    return true;
                } else {
                    log.warn("Deletion failed - employee still in list, retrying...");
                }

            } catch (Exception e) {
                log.warn("Retrying delete... (Attempt {}) - Error: {}", i + 1, e.getMessage());
            }
        }
        log.error("Delete failed after 3 attempts for: {}", fullName);
        return false;
    }
}