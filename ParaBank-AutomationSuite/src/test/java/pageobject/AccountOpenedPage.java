package pageobject;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AccountOpenedPage extends BasePage{

	public AccountOpenedPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	@FindBy(id = "newAccountId")
	WebElement newAccountLink;
	
	@FindBy(xpath = "//h1[text() = 'Account Opened!']")
	WebElement  confirmationMessage;
	
	

    public String getConfirmationMessage() {
    	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    	wait.until(ExpectedConditions.visibilityOf(confirmationMessage));
        return confirmationMessage.getText();
    }

    public String getNewAccountId() {
        return newAccountLink.getText();
    }

	public void clickNewCheckingAcctNo() {
		newAccountLink.click();
	}
	

}
