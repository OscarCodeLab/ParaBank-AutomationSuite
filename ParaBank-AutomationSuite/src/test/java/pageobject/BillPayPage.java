package pageobject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

public class BillPayPage extends BasePage{

	public BillPayPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	@FindBy(name = "payee.name")
	WebElement payeeName;
	
	@FindBy(name = "payee.address.street")
	WebElement payeeAddress;
	
	@FindBy(name = "payee.address.city")
	WebElement payeeCity;
	
	@FindBy(name = "payee.address.state")
	WebElement payeeState;
	
	@FindBy(name = "payee.address.zipCode")
	WebElement payZipCode;
	
	@FindBy(name = "payee.phoneNumber")
	WebElement number;
	
	@FindBy(name = "payee.accountNumber")
	WebElement acctNum;
	
	@FindBy(name = "verifyAccount")
	WebElement verifyAcctNum;
	
	@FindBy(name = "amount")
	WebElement amount;
	
	@FindBy(xpath = "//select[@name = 'fromAccountId']")
	WebElement selectAcc;
	
	@FindBy(xpath = "//input[@value = 'Send Payment']")
	WebElement sendPaymentBtn;
	
	@FindBy(xpath = "//h1[normalize-space() = 'Bill Payment Complete']")
	WebElement header;

	@FindBy(xpath = "//div[@id='billpayResult']/p[1]")
	WebElement successMsg;
	
	@FindBy(linkText = "Accounts Overview")
	WebElement AccountsOverview;

	

	public void clickBillPage(String name, String address, String city, String state, String zipCode, String pNo, String toactNo, String confirmToActNo, String amout, int fromAcc) {
		payeeName.sendKeys(name);	
		payeeAddress.sendKeys(address);
		payeeCity.sendKeys(city);
		payeeState.sendKeys(state);
		payZipCode.sendKeys(zipCode);
		number.sendKeys(pNo);
		acctNum.sendKeys(toactNo);
		verifyAcctNum.sendKeys(confirmToActNo);
		amount.sendKeys(amout);
		
		Select s = new Select(selectAcc);
		s.selectByIndex(fromAcc);
		sendPaymentBtn.click();
		
	}
	
	public Boolean isConfirmationHeaderDisplayed() {
		wait.until(ExpectedConditions.visibilityOf(header));
		return header.isDisplayed();
	}
	
	public String getConfirmationText() {
		wait.until(ExpectedConditions.visibilityOf(successMsg));
		return successMsg.getText();
	}
	
	public void gotoAcctOverViewPage() {
		AccountsOverview.click();
	}
	
}

