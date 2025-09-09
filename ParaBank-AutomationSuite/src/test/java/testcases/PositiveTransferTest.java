package testcases;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageobject.LoginPage;
import pageobject.NavigationBarPage;
import pageobject.TransferFundsPage;
import testbase.BaseClass;

public class PositiveTransferTest extends BaseClass {

    private LoginPage login;
    private NavigationBarPage nav;
    private TransferFundsPage transfer;    

    @Test(priority = 1)
    public void testTransferConfirmationDetails() {
        logger.info("===== Starting Positive Transfer Test - Case 1 =====");

        login = new LoginPage(driver);
        login.clicklogins(prop.getProperty("username"), prop.getProperty("pwd"));

        nav = new NavigationBarPage(driver);

        int from = Integer.parseInt(prop.getProperty("from"));
        int to = Integer.parseInt(prop.getProperty("to"));
        logger.debug("Initiating transfer: amount={}, from={}, to={}", prop.getProperty("amount"), from, to);

        performTransfer(prop.getProperty("amount"), from, to);

        logger.info("===== Finished Positive Transfer Test - Case 1 =====");
    }

    @Test(priority = 2)
    public void testTransferConfirmationDetails2() {
        logger.info("===== Starting Positive Transfer Test - Case 2 =====");

        int from = Integer.parseInt(prop.getProperty("from"));
        int to = Integer.parseInt(prop.getProperty("to_2"));
        logger.debug("Initiating transfer: amount={}, from={}, to={}", prop.getProperty("amount_2"), from, to);

        performTransfer(prop.getProperty("amount_2"), from, to);

        logger.info("===== Finished Positive Transfer Test - Case 2 =====");
    }

    private void performTransfer(String amount, int from, int to) {
        logger.debug("Navigating to Transfer Funds page...");
        nav.clickTransferFundsLink();               

        transfer = new TransferFundsPage(driver);    
        transfer.transferFunds(amount, from, to);    

        logger.debug("Validating confirmation message...");

        // Check confirmation header
        Assert.assertTrue(transfer.isConfirmationDisplayed(),
                "❌ Confirmation header should be displayed.");
        logger.info("✅ Confirmation header displayed.");

        String actualMessage = transfer.getConfirmationText();

        // Check amount
        Assert.assertTrue(actualMessage.contains(amount),
                "❌ Confirmation should contain transfer amount.");
        logger.info("✅ Transfer amount confirmed in message: {}", amount);

        // Check account labels exist (not exact numbers since they're random)
        Assert.assertTrue(actualMessage.contains("from account"),
                "❌ Confirmation should mention source account (from account).");
        logger.info("✅ 'From account' mentioned in confirmation message.");

        Assert.assertTrue(actualMessage.contains("to account"),
                "❌ Confirmation should mention destination account (to account).");
        logger.info("✅ 'To account' mentioned in confirmation message.");
    }
}
