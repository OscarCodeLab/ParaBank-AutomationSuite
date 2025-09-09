package testcases;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;


import pageobject.AccountsPage;
import pageobject.LoginPage;
import testbase.BaseClass;

public class AccountPageTest extends BaseClass {

    public LoginPage login;
    public AccountsPage accountsPage;

    @Test(priority = 1)
    public void verifyAccountRowsData() {
        logger.info("===== Starting Test: verifyAccountRowsData =====");

        login = new LoginPage(driver);
        logger.info("Logging in with username: {}", prop.getProperty("username"));
        login.clicklogins(prop.getProperty("username"), prop.getProperty("pwd"));

        accountsPage = new AccountsPage(driver);
        logger.info("Fetching account data from Accounts page...");
        List<String[]> tableData = accountsPage.getAccountData();

        for (String[] row : tableData) {
            String account = row[0];
            String balance = row[1];
            String available = row[2];

            logger.info("Row Data -> Account: {} | Balance: {} | Available: {}", account, balance, available);

            // Assertions with logging
            Assert.assertFalse(account.isEmpty(), "Account should not be empty");
            logger.debug("Validated account is not empty.");

            Assert.assertTrue(balance.contains("$"), "Balance should contain '$'");
            logger.debug("Validated balance contains $ symbol.");

            Assert.assertTrue(available.contains("$") || available.isEmpty(),
                    "Available should have '$' or be empty");
            logger.debug("Validated available balance field.");
        }

        logger.info("===== Completed Test: verifyAccountRowsData =====");
    }

    @Test(priority = 2)
    public void verifyTotalBalance() {
        logger.info("===== Starting Test: verifyTotalBalance =====");

        double calculatedTotal = accountsPage.calculateTotalBalance();
        double displayedTotal = accountsPage.getDisplayedTotalBalanceAsDouble();

        logger.info("Calculated Total: {}", calculatedTotal);
        logger.info("Displayed Total: {}", displayedTotal);

        Assert.assertEquals(calculatedTotal, displayedTotal,
                "Displayed total should match calculated total");

        logger.info("Verified total balance successfully.");
        logger.info("===== Completed Test: verifyTotalBalance =====");
    }
}
