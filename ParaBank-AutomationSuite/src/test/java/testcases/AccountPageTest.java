package testcases;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageobject.AccountsPage;
import pageobject.LoginPage;
import testbase.BaseClass;

public class AccountPageTest extends BaseClass{
	public LoginPage login;
	public AccountsPage accountsPage;
	
	@Test(priority = 1)
    public void verifyAccountRowsData() {
		login = new LoginPage(driver);
		login.clicklogins(prop.getProperty("username"), prop.getProperty("pwd"));
		
		
		accountsPage = new AccountsPage(driver);
        List<String[]> tableData = accountsPage.getAccountData();

        for (String[] row : tableData) {
            String account = row[0];
            String balance = row[1];
            String available = row[2];

            System.out.println("Account: " + account + 
                               " | Balance: " + balance + 
                               " | Available: " + available);

            //  Basic checks
            Assert.assertFalse(account.isEmpty(), "Account should not be empty");
            Assert.assertTrue(balance.contains("$"), "Balance should contain '$'");
            Assert.assertTrue(available.contains("$") || available.isEmpty(),
                    "Available should have '$' or be empty");
        }
    }

    @Test(priority = 2)
    public void verifyTotalBalance() {
        double calculatedTotal = accountsPage.calculateTotalBalance();
        double displayedTotal = accountsPage.getDisplayedTotalBalanceAsDouble();

        System.out.println("Calculated Total: " + calculatedTotal);
        System.out.println("Displayed Total: " + displayedTotal);

        Assert.assertEquals(calculatedTotal, displayedTotal, 
                "Displayed total should match calculated total");
    }

}
