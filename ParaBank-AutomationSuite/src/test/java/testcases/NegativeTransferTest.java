package testcases;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import pageobject.AccountsPage;
import pageobject.LoginPage;
import pageobject.TransferFundsPage;
import testbase.BaseClass;
import utilities.DataProviders;

public class NegativeTransferTest extends BaseClass {
    LoginPage login;
    AccountsPage acct;
    TransferFundsPage transfer;
    SoftAssert softAssert;

    @BeforeTest
    public void setUpLogin() {
        login = new LoginPage(driver);
        login.clicklogins(prop.getProperty("username"), prop.getProperty("pwd"));
        acct = new AccountsPage(driver);
        softAssert = new SoftAssert(); // Initialize SoftAssert
        System.out.println("✅ Logged in successfully!");
    }

    @Test(dataProviderClass = DataProviders.class, dataProvider = "dp")
    public void negativeTransferTest(String amount, String from, String to) {
        // Convert from and to strings to integers (handle decimals)
        int fromAccount = (int) Double.parseDouble(from);
        int toAccount = (int) Double.parseDouble(to);
        
        // Perform transfer with invalid data
        acct.clickTransferFundsLink();
        transfer = new TransferFundsPage(driver);
        
        System.out.println("🧪 Testing: amount=" + amount + ", from=" + fromAccount + ", to=" + toAccount);
        transfer.transferFunds(amount, fromAccount, toAccount);
        
        

        // Check if error is displayed (which is expected for invalid data)
        if (transfer.isErrorDisplayed()) {
            System.out.println("✅ PASS - Invalid input rejected: " + transfer.getErrorText());
        } else if (transfer.isConfirmationDisplayed()) {
            System.out.println("🐛 BUG FOUND - Invalid data was accepted!");
            System.out.println("   Input: amount='" + amount + "', from=" + fromAccount + ", to=" + toAccount);
            String successMsg = transfer.getConfirmationText();
            System.out.println("   Success message: " + successMsg);
            softAssert.fail("APPLICATION BUG - Invalid data should be rejected but was accepted: " + successMsg);
        } else {
            System.out.println("🔍 DEBUG - No error or success message found");
            //System.out.println("   Current page: " + driver.getTitle());
            softAssert.fail("No response from system for input: " + amount);
        }
        
        // Assert all collected failures at the end
        softAssert.assertAll();
    }
}