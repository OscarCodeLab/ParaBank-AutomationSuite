package testcases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageobject.AccountsPage;
import pageobject.LoginPage;
import pageobject.TransferFundsPage;
import testbase.BaseClass;

public class PositiveTransferTest extends BaseClass{

	LoginPage login;
	AccountsPage acct;
	TransferFundsPage transfer;	
	
	
	@Test(priority = 1)
    public void testTransferConfirmationDetails() {
        login = new LoginPage(driver);
        login.clicklogins(prop.getProperty("username"), prop.getProperty("pwd"));

        acct = new AccountsPage(driver);

        int from = Integer.parseInt(prop.getProperty("from"));
        int to = Integer.parseInt(prop.getProperty("to"));
        performTransfer(prop.getProperty("amount"), from, to);
    }

    @Test(priority = 2)
    public void testTransferConfirmationDetails2() {
        int from = Integer.parseInt(prop.getProperty("from"));
        int to = Integer.parseInt(prop.getProperty("to_2"));
        performTransfer(prop.getProperty("amount_2"), from, to);
    }

    
    private void performTransfer(String amount, int from, int to) {
        acct.clickTransferFundsLink();               
        transfer = new TransferFundsPage(driver);    
        transfer.transferFunds(amount, from, to);    

        // 
        Assert.assertTrue(transfer.isConfirmationDisplayed(),
                "Confirmation header should be displayed.");

        String actualMessage = transfer.getConfirmationText();

        // check amount
        Assert.assertTrue(actualMessage.contains(amount),
                "Confirmation should contain transfer amount.");

        // check account labels exist (not exact numbers since they're random)
        Assert.assertTrue(actualMessage.contains("from account"),
                "Confirmation should mention source account (from account).");

        Assert.assertTrue(actualMessage.contains("to account"),
                "Confirmation should mention destination account (to account).");
    }
}
