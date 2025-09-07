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

public class BillPageTest extends BaseClass{
	
	LoginPage login;
	AccountsPage acct;
	BillPayPage bill;
	NavigationBarPage nav;
	
	@Test(dataProviderClass = DataProviders.class, dataProvider = "dp")
	public void billPageDataTest(HashMap<String, String> data) {
	login = new LoginPage(driver);
	login.clicklogins(prop.getProperty("username"), prop.getProperty("pwd"));
	
	acct = new AccountsPage(driver);
	Double oldBal = Double.parseDouble(acct.getFirstAcctBal().replace("$", ""));
	Double amount = Double.parseDouble(data.get("Amount"));
	
	nav = new NavigationBarPage(driver);
	nav.clickbillPayLink();
	
	
	bill = new BillPayPage(driver);
	bill.clickBillPage(data.get("Name"),
			data.get("Address"),
			data.get("City"),
			data.get("State"),
			data.get("ZipCode"), 
			data.get("Phone"), 
			data.get("Account"), 
			data.get("verify Account"), 
			String.valueOf(amount),
			(int) Double.parseDouble(data.get("From Acount")) //converting the data from string to double and casting it to integer
			);
	
	Assert.assertTrue(bill.isConfirmationHeaderDisplayed(), "Confirmation header should be displayed.");
	String actMsg = bill.getConfirmationText();
	System.out.println(actMsg);
	Assert.assertTrue(!actMsg.isEmpty(), "Confirmation text should be displayed." );
	
	
	nav.clickAcctOverviewLink();
	Double newBal = Double.parseDouble(acct.getFirstAcctBal().replace("$", ""));
	Assert.assertEquals(newBal, oldBal - amount, "The new Balance still display thesame or wrong balance ");
	}
}
