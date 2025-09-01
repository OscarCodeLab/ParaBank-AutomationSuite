package pageobject;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TransferFundsPage extends BasePage {

	public TransferFundsPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	@FindBy(id = "amount")
	WebElement amountField;
	
	@FindBy(xpath = "//select[@id = 'fromAccountId']")
	WebElement fromAccountField;
	
	@FindBy(xpath = "//select[@id = 'toAccountId']")
	WebElement toAccountIdField;
	
	@FindBy(xpath = "//input[@value = 'Transfer']")
	WebElement transferBtn;
	
	@FindBy(xpath = "//h1[normalize-space() = 'Transfer Complete!']")
	WebElement confirmationHeader;
	
	@FindBy(xpath = "//div[@id='showResult']//p[1]")
	WebElement confirmationMessage;
	
	@FindBy(xpath = "//h1[normalize-space() = 'Error!']")
	WebElement confirmErrorHeader;
	
	@FindBy(xpath = "//div[@id = 'showError']//p")
	WebElement confirmErrorMessage;
	
	
	public void transferFunds(String amount, int from, int to) {
	    amountField.clear();
	    amountField.sendKeys(amount);

	    Select fromAccount = new Select(fromAccountField);
	    fromAccount.selectByIndex(from);

	    Select toAccount = new Select(toAccountIdField);
	    toAccount.selectByIndex(to);
	    
	    transferBtn.click();
	}
	
	public String getConfirmationText() {
		wait.until(ExpectedConditions.visibilityOf(confirmationMessage));
		return confirmationMessage.getText();
	}
	
	public Boolean isConfirmationDisplayed() {
		wait.until(ExpectedConditions.visibilityOf(confirmationHeader));
		return confirmationHeader.isDisplayed();
	}
	
	public String getErrorText() {
		wait.until(ExpectedConditions.visibilityOf(confirmErrorMessage));
		return confirmErrorMessage.getText();
	}
	
	public Boolean isErrorDisplayed() {
	wait.until(ExpectedConditions.visibilityOf(confirmErrorHeader));	
	return confirmErrorHeader.isDisplayed();
	}

}
