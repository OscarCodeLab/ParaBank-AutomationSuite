package pageobject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LandingPage extends BasePage{

	public LandingPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	@FindBy(linkText = "Register")
	WebElement registerLink;
	
	@FindBy(xpath = "//input[@value = 'Log In']")
	WebElement loginBtn;
	
	@FindBy(xpath = "//input[@name = 'username']")
	WebElement usernameField;
	
	@FindBy(xpath = "//input[@name = 'password']")
	WebElement passwordField;
	
	@FindBy(partialLinkText = "Forgot login")
	WebElement forgetPasswordLink;
	
	public void clickRegisterLink() {
		registerLink.click();
	}
}
