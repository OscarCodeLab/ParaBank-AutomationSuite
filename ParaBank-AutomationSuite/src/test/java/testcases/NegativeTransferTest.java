package testcases;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import pageobject.LoginPage;
import pageobject.NavigationBarPage;
import pageobject.TransferFundsPage;
import testbase.BaseClass;
import utilities.DataProviders;

public class NegativeTransferTest extends BaseClass {
    LoginPage login;
    NavigationBarPage nav;
    TransferFundsPage transfer;
    SoftAssert softAssert;

    @BeforeTest
    public void setUpLogin() {
        login = new LoginPage(driver);
        login.clicklogins(prop.getProperty("username"), prop.getProperty("pwd"));        
        softAssert = new SoftAssert(); // Initialize SoftAssert
        logger.info("✅ Logged in successfully!");
    }

    @Test(dataProviderClass = DataProviders.class, dataProvider = "data")
    public void negativeTransferTest(String amount, String from, String to) {
        logger.info("===== Starting Negative Transfer Test =====");
        
        // Convert from and to strings to integers (handle decimals)
        int fromAccount = (int) Double.parseDouble(from);
        int toAccount = (int) Double.parseDouble(to);

        // Perform transfer with invalid data
        nav = new NavigationBarPage(driver);
        nav.clickTransferFundsLink();
        transfer = new TransferFundsPage(driver);

        logger.debug("🧪 Testing: amount={}, from={}, to={}", amount, fromAccount, toAccount);
        transfer.transferFunds(amount, fromAccount, toAccount);

        // Check if error is displayed (which is expected for invalid data)
        if (transfer.isErrorDisplayed()) {
            logger.info("✅ PASS - Invalid input rejected: {}", transfer.getErrorText());
        } else if (transfer.isConfirmationDisplayed()) {
            logger.error("🐛 BUG FOUND - Invalid data was accepted!");
            logger.error("   Input: amount='{}', from={}, to={}", amount, fromAccount, toAccount);
            String successMsg = transfer.getConfirmationText();
            logger.error("   Success message: {}", successMsg);
            softAssert.fail("APPLICATION BUG - Invalid data should be rejected but was accepted: " + successMsg);
        } else {
            logger.warn("🔍 DEBUG - No error or success message found");
            // logger.debug("   Current page: {}", driver.getTitle());
            softAssert.fail("No response from system for input: " + amount);
        }

        // Assert all collected failures at the end
        softAssert.assertAll();
        logger.info("===== Finished Negative Transfer Test =====");
    }
}
