package testcases;

import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageobject.AccountsPage;
import pageobject.BillPayPage;
import pageobject.LoginPage;
import pageobject.NavigationBarPage;
import testbase.BaseClass;
import utilities.DataProviders;

public class BillPageTest extends BaseClass {
	
    LoginPage login;
    AccountsPage acct;
    BillPayPage bill;
    NavigationBarPage nav;

    @Test(dataProviderClass = DataProviders.class, dataProvider = "dp")
    public void billPageDataTest(HashMap<String, String> data) {
        logger.info("===== Starting billPageDataTest with data: {} =====", data);

        login = new LoginPage(driver);
        login.clicklogins(prop.getProperty("username"), prop.getProperty("pwd"));
        logger.info("Login successful with username: {}", prop.getProperty("username"));

        acct = new AccountsPage(driver);
        Double oldBal = Double.parseDouble(acct.getFirstAcctBal().replace("$", ""));
        logger.info("Fetched old balance: {}", oldBal);

        Double amount = Double.parseDouble(data.get("Amount"));
        logger.info("Bill payment amount: {}", amount);

        nav = new NavigationBarPage(driver);
        nav.clickbillPayLink();
        logger.info("Navigated to Bill Pay page.");

        bill = new BillPayPage(driver);
        bill.clickBillPage(
            data.get("Name"),
            data.get("Address"),
            data.get("City"),
            data.get("State"),
            data.get("ZipCode"), 
            data.get("Phone"), 
            data.get("Account"), 
            data.get("verify Account"), 
            String.valueOf(amount),
            (int) Double.parseDouble(data.get("From Acount")) // converting string → double → int
        );
        logger.info("Submitted Bill Pay form with payee: {}", data.get("Name"));

        Assert.assertTrue(bill.isConfirmationHeaderDisplayed(), "Confirmation header should be displayed.");
        logger.info("Verified confirmation header is displayed.");

        String actMsg = bill.getConfirmationText();
        logger.debug("Confirmation message: {}", actMsg);
        Assert.assertTrue(!actMsg.isEmpty(), "Confirmation text should be displayed.");
        logger.info("Verified confirmation text is not empty.");

        nav.clickAcctOverviewLink();
        logger.info("Navigated back to Account Overview page.");

        Double newBal = Double.parseDouble(acct.getFirstAcctBal().replace("$", ""));
        logger.info("Fetched new balance: {}", newBal);

        Assert.assertEquals(newBal, oldBal - amount, 
                "The new Balance still displays the same or an incorrect balance");
        logger.info("Verified new balance reflects the bill payment correctly. Old: {}, New: {}, Paid: {}", 
                    oldBal, newBal, amount);

        logger.info("===== Completed billPageDataTest successfully =====");
    }
}
