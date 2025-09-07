package testcases;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import pageobject.LoginPage;
import testbase.BaseClass;
import utilities.DataProviders;

public class LoginTest extends BaseClass {

    LoginPage login;

    @Test(dataProviderClass = DataProviders.class, dataProvider = "dp")
    public void loginData(String username, String pwd, String exp) throws InterruptedException {
        login = new LoginPage(driver);

        SoftAssert softAssert = new SoftAssert();
        // Verify login page fields
        Assert.assertTrue(login.verifyUsernameField(), "❌ Username field is NOT visible!");
        Assert.assertTrue(login.verifypasswordField(), "❌ Password field is NOT visible!");
        Assert.assertTrue(login.verifyloginBtn(), "❌ Login button is NOT visible!");
        Assert.assertTrue(login.verifyforgetPasswordLink(), "❌ Forgot password link is NOT visible!");

        // Perform login
        login.clicklogin(username, pwd);

        boolean targetPage = login.loginTextDisplayed();  // safe method, returns true/false

        if (exp.equalsIgnoreCase("valid")) {
            if (targetPage) {
                logger.info("✅ Login successful with valid credentials: " + username + " / " + pwd);
                Assert.assertEquals(driver.getTitle(), "ParaBank | Accounts Overview", 
                                    "❌ Page title mismatch after valid login!");
                login.clickLogOut();
            } else {
                logger.error("❌ Login failed with valid credentials: " + username + " / " + pwd);
                Assert.fail("Expected login success but failed.");
            }
        } 
        else if (exp.equalsIgnoreCase("invalid")) {
            if (targetPage) {
                logger.error("❌ Login succeeded with invalid credentials: " + username + " / " + pwd);
                softAssert.fail("Expected login failure but succeeded.");
                login.clickLogOut();
            } else {
                String errorText = login.getErrorMsg();
                Assert.assertEquals(errorText.trim(), 
                    "The username and password could not be verified.",
                    "❌ Error message mismatch!");
                logger.info("✅ Login failed as expected with invalid credentials: " + username + " / " + pwd);
            }
        } 
        else {
            logger.warn("⚠️ Invalid expected result type: " + exp);
            Assert.fail("Test failed due to unrecognized expected result value.");
        }

        logger.info("***** Finished TC_003_LoginDDT *****");
    }
        
        
      
}
