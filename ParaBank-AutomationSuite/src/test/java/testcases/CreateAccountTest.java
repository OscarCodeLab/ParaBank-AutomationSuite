package testcases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageobject.AccountOpenedPage;
import pageobject.AccountsPage;
import pageobject.LoginPage;
import pageobject.NavigationBarPage;
import pageobject.OpenAccountPage;
import testbase.BaseClass;

public class CreateAccountTest extends BaseClass {
    private LoginPage login;
    private AccountsPage acct;
    private OpenAccountPage open;
    private AccountOpenedPage acctOpen;
    private NavigationBarPage nav;

    @Test(priority = 1)
    public void createCheckingAccountTest() {
        logger.info("=== Starting Checking Account Creation Test ===");
        initializePages();
        createAccount(prop.getProperty("account_Type"));
        logger.info("=== Finished Checking Account Creation Test ===");
    }

    @Test(priority = 2)
    public void createSavingAccountTest() {
        logger.info("=== Starting Saving Account Creation Test ===");
        createAccount(prop.getProperty("new_Account_Type"));
        logger.info("=== Finished Saving Account Creation Test ===");
    }

    private void initializePages() {
        logger.info("Logging into the application");
        login = new LoginPage(driver);
        login.clicklogins(prop.getProperty("username"), prop.getProperty("pwd"));

        nav = new NavigationBarPage(driver);
        acct = new AccountsPage(driver);
        logger.debug("Login successful, navigation and accounts pages initialized");
    }

    private void createAccount(String accountType) {
        logger.info("Navigating to Open Account page for account type: " + accountType);
        nav.clickOpenAcctLink();

        open = new OpenAccountPage(driver);
        open.selectAccountType(accountType);
        open.selectFromAccount(Integer.parseInt(prop.getProperty("account_Number")));
        open.clickOpenNewAccount();
        logger.info("Submitted account creation form for type: " + accountType);

        verifyAccountCreation();
    }

    private void verifyAccountCreation() {
        acctOpen = new AccountOpenedPage(driver);

        String confirm = acctOpen.getConfirmationMessage();
        logger.info("Confirmation Message: " + confirm);
        Assert.assertTrue(confirm.contains("Account"), "Account Open Page not open");

        String newAccountId = acctOpen.getNewAccountId();
        logger.info("New Account ID: " + newAccountId);
        Assert.assertNotNull(newAccountId, "Account ID should not be null.");

        logger.info("Navigating back to Account Overview to verify new account");
        nav.clickAcctOverviewLink();
        boolean isFound = acct.isAccountPresent(newAccountId);
        logger.info("Account found in overview: " + isFound);
        Assert.assertTrue(isFound, "Newly created account ID " + newAccountId + " was NOT found in Accounts Overview.");
    }
}
