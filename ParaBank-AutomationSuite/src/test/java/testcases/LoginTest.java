package testcases;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import pageobject.LoginPage;
import testbase.BaseClass;
import utilities.DataProviders;

public class LoginTest extends BaseClass {

    LoginPage login;

    @Test(dataProviderClass = DataProviders.class, dataProvider = "data")
    public void loginData(String username, String pwd, String exp) throws InterruptedException {
        logger.info("===== Starting Login Test for user: " + username + " | Expected: " + exp + " =====");

        login = new LoginPage(driver);
        SoftAssert softAssert = new SoftAssert();

        logger.debug("Verifying login page UI elements...");
        Assert.assertTrue(login.verifyUsernameField(), "❌ Username field is NOT visible!");
        Assert.assertTrue(login.verifypasswordField(), "❌ Password field is NOT visible!");
        Assert.assertTrue(login.verifyloginBtn(), "❌ Login button is NOT visible!");
        Assert.assertTrue(login.verifyforgetPasswordLink(), "❌ Forgot password link is NOT visible!");

        logger.info("Entering credentials: " + username + " / " + pwd);
        login.clicklogin(username, pwd);

        boolean targetPage = login.loginTextDisplayed();  // safe method, returns true/false
        logger.debug("Login success flag returned: " + targetPage);

        if (exp.equalsIgnoreCase("valid")) {
            if (targetPage) {
                logger.info("✅ Login successful with valid credentials.");
                Assert.assertEquals(driver.getTitle(), "ParaBank | Accounts Overview",
                        "❌ Page title mismatch after valid login!");
                login.clickLogOut();
                logger.info("Logged out after successful login.");
            } else {
                logger.error("❌ Login failed with valid credentials.");
                Assert.fail("Expected login success but failed.");
            }
        } else if (exp.equalsIgnoreCase("invalid")) {
            if (targetPage) {
                logger.error("❌ Login succeeded with invalid credentials.");
                softAssert.fail("Expected login failure but succeeded.");
                login.clickLogOut();
            } else {
                String errorText = login.getErrorMsg();
                logger.debug("Error message displayed: " + errorText);
                Assert.assertEquals(errorText.trim(),
                        "The username and password could not be verified.",
                        "❌ Error message mismatch!");
                logger.info("✅ Login failed as expected with invalid credentials.");
            }
        } else {
            logger.warn("⚠️ Unrecognized expected result type: " + exp);
            Assert.fail("Test failed due to unrecognized expected result value.");
        }

        logger.info("===== Finished Login Test for user: " + username + " =====");
    }
}
