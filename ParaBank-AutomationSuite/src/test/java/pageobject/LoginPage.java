package pageobject;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage extends BasePage {

	public LoginPage(WebDriver driver) {
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
	
	@FindBy(xpath = "//h1[normalize-space() = 'Accounts Overview']")
	WebElement verifyLoginText;
	
	@FindBy(linkText = "Log Out")
	WebElement logOutLink;
	
	@FindBy(xpath = "//p[@class = 'error']")
	WebElement erroMsg;
	
	public void clicklogins(String username, String pwd) {
		usernameField.sendKeys(username);
		passwordField.sendKeys(pwd);
		loginBtn.click();
	}
	
	public void clicklogin(String username, String pwd) {
		usernameField.sendKeys(username);
		passwordField.sendKeys(pwd);
		loginBtn.click();
	}
	
	public boolean verifyUsernameField() {
		return usernameField.isDisplayed();
	}
	

	public boolean verifypasswordField() {
		return passwordField.isDisplayed();
	}
	

	public boolean verifyloginBtn() {
		return loginBtn.isDisplayed();
	}
	

	public boolean verifyforgetPasswordLink() {
		return forgetPasswordLink.isDisplayed();
	}

	public boolean loginTextDisplayed() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOf(verifyLoginText));
		return verifyLoginText.isDisplayed();
	}
	
	public String getErrorMsg() {
	    
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
	        WebElement error = wait.until(ExpectedConditions.visibilityOf(erroMsg) );
	        return error.getText();
	    }

	public void clickLogOut() {
		logOutLink.click();
	}

	
}
