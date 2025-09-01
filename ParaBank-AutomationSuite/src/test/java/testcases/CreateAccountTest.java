package testcases;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageobject.AccountOpenedPage;
import pageobject.AccountsPage;
import pageobject.LoginPage;
import pageobject.OpenAccountPage;
import testbase.BaseClass;

public class CreateAccountTest extends BaseClass {
    private LoginPage login;
    private AccountsPage acct;
    private OpenAccountPage open;
    private AccountOpenedPage acctOpen;

    @Test(priority = 1)
    public void createCheckingAccountTest() {
        // Login ONLY for first test
        initializePages();
        createAccount(prop.getProperty("account_Type"));
    }

    @Test(priority = 2)
    public void createSavingAccountTest() {
        // Already logged in; just create the new account
        createAccount(prop.getProperty("new_Account_Type"));
    }

    private void initializePages() {
        login = new LoginPage(driver);
        login.clicklogins(prop.getProperty("username"), prop.getProperty("pwd"));
        acct = new AccountsPage(driver);
    }

    private void createAccount(String accountType) {
        acct.clickOpenAcctLink();
        
        open = new OpenAccountPage(driver);
        open.selectAccountType(accountType);
        open.selectFromAccount(Integer.parseInt(prop.getProperty("account_Number")));
        open.clickOpenNewAccount();

        verifyAccountCreation();
    }

    private void verifyAccountCreation() {
        acctOpen = new AccountOpenedPage(driver);
        
        String confirm = acctOpen.getConfirmationMessage();
        System.out.println(confirm);
        Assert.assertTrue(confirm.contains("Account"), "Account Open Page not open");

        String newAccountId = acctOpen.getNewAccountId();
        System.out.println("New Account ID: " + newAccountId);
        Assert.assertNotNull(newAccountId, "Account ID should not be null.");

        acctOpen.clickAcctOverviewLink();
        boolean isFound = acct.isAccountPresent(newAccountId);
        Assert.assertTrue(isFound, "Newly created account ID " + newAccountId + " was NOT found in Accounts Overview.");
    }
}
