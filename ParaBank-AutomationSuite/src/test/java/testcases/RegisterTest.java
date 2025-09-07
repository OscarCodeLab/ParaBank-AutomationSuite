package testcases;

import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageobject.NavigationBarPage;
import pageobject.RegisterPage;
import testbase.BaseClass;
import utilities.DataProviders;

public class RegisterTest extends BaseClass{
	public NavigationBarPage nav;
	public RegisterPage reg;

	@Test(dataProviderClass = DataProviders.class, dataProvider = "dp")
	public void registerDataTest(HashMap<String, String> data) {
		
		
		nav = new NavigationBarPage(driver);
		nav.clickRegisterLink();
		
		reg = new RegisterPage(driver);
		reg.registerAccount(
				data.get("First Name"), 
				data.get("Last Name"), 
				data.get("Address"), 
				data.get("City"), 
				data.get("State"), 
				data.get("ZipCode"), 
				data.get("Phone Number"), 
				data.get("SSN"), 
				data.get("Username"), 
				data.get("Password"), 
				data.get("Confirm Password"));
		
		boolean text = reg.verifyText();
		Assert.assertTrue(text, "The welcome message and username text are missing");
	}
	

}
