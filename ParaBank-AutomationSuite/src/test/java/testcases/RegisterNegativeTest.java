package testcases;

import java.util.HashMap;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageobject.LandingPage;
import pageobject.RegisterPage;
import testbase.BaseClass;
import utilities.DataProviders;

public class RegisterNegativeTest extends BaseClass {

    private LandingPage land;
    private RegisterPage reg;

    private void openRegistrationPage() {
        land = new LandingPage(driver);
        land.clickRegisterLink();
        reg = new RegisterPage(driver);
    }

    private void submitRegistration(HashMap<String, String> data) {
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
            "Registration should fail when mandatory field is empty: " + field
        );
    }

    @Test(dataProviderClass = DataProviders.class, dataProvider = "dp")
    public void validateMandatoryFields(HashMap<String, String> validData) throws InterruptedException {

        String[] mandatoryFields = {
            "First Name", "Last Name", "Address", "City", "State",
            "ZipCode", "SSN", "Username",
            "Password", "Confirm Password"
        };

        for (String field : mandatoryFields) {
            logger.info("Testing mandatory validation for: " + field);
            

            HashMap<String, String> testData = new HashMap<>(validData);
            testData.put(field, ""); // make the field empty
            
            openRegistrationPage();
            submitRegistration(testData);
            String errorMsg = reg.getErrorMessage(field);
            Assert.assertFalse(errorMsg.isEmpty(), 
                "Expected an error message for " + field + " but none was displayed");
            assertRegistrationFails(field);

            driver.get(prop.getProperty("url")); // reset for next iteration
        }
        
        Thread.sleep(3000);
    }
}
