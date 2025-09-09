package testcases;

import java.util.HashMap;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageobject.NavigationBarPage;
import pageobject.RegisterPage;
import testbase.BaseClass;
import utilities.DataProviders;

public class RegisterNegativeTest extends BaseClass {
    private NavigationBarPage nav;
    private RegisterPage reg;

    private void openRegistrationPage() {
        logger.debug("Navigating to registration page...");
        nav = new NavigationBarPage(driver);
        nav.clickRegisterLink();
        reg = new RegisterPage(driver);
    }

    private void submitRegistration(HashMap<String, String> data) {
        logger.debug("Submitting registration form with data: {}", data);
        reg.registerAccount(
            data.get("First Name"),
            data.get("Last Name"),
            data.get("Address"),
            data.get("City"),
            data.get("State"),
            data.get("ZipCode"),
            data.get("Phone Number"),
            data.get("SSN"),
            data.get("Username"),
            data.get("Password"),
            data.get("Confirm Password")
        );
    }

    private void assertRegistrationFails(String field) {
        Assert.assertTrue(
            reg.isOnRegistrationPage(),
            "❌ Registration should fail when mandatory field is empty: " + field
        );
        logger.info("✅ Registration correctly failed for missing field: {}", field);
    }

    @Test(dataProviderClass = DataProviders.class, dataProvider = "dp")
    public void validateMandatoryFields(HashMap<String, String> validData) throws InterruptedException {
        logger.info("===== Starting RegisterNegativeTest - Mandatory Fields Validation =====");

        String[] mandatoryFields = {
            "First Name", "Last Name", "Address", "City", "State",
            "ZipCode", "SSN", "Username", "Password", "Confirm Password"
        };

        for (String field : mandatoryFields) {
            logger.info("🧪 Testing mandatory validation for field: {}", field);

            HashMap<String, String> testData = new HashMap<>(validData);
            testData.put(field, ""); // clear the field

            openRegistrationPage();
            submitRegistration(testData);

            String errorMsg = reg.getErrorMessage(field);
            Assert.assertFalse(errorMsg.isEmpty(),
                "❌ Expected an error message for '" + field + "' but none was displayed.");
            logger.debug("Error message displayed for {}: {}", field, errorMsg);

            assertRegistrationFails(field);

            driver.get(prop.getProperty("url")); // reset for next iteration
            logger.debug("Resetting application state for next field test.");
        }

        logger.info("===== Finished RegisterNegativeTest - Mandatory Fields Validation =====");
        
    }
}
