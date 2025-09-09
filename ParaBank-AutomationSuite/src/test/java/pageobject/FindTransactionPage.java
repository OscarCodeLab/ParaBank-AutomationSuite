package pageobject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

public class FindTransactionPage extends BasePage{

	public FindTransactionPage(WebDriver driver) {
		super(driver);
		 //TODO Auto-generated constructor stub
	}
	
	@FindBy(xpath = "select[@id = 'accountId']")
	WebElement acctDropDown;

	@FindBy(xpath = "input[@id = 'transactionId']")
	WebElement transactionIdField;
	
	@FindBy(xpath = "button[@id = 'findById']")
	WebElement findByIdBtn;
	
	@FindBy(xpath = "input[@id = 'transactionDate']")
	WebElement transactionDateField;
	
	@FindBy(xpath = "button[@id = 'findByDate']")
	WebElement findByDateBtn;	

	@FindBy(xpath = "input[@id = 'fromDate']")
	WebElement fromDateField;
	
	@FindBy(xpath = "input[@id = 'toDate']")
	WebElement toDateField;
	
	@FindBy(xpath = "button[@id = 'findByDateRange']")
	WebElement findByDateRangeBtn;
	
	@FindBy(xpath = "input[@id = 'amount']")
	WebElement amountField;
	
	@FindBy(xpath = "button[@id = 'findByAmount']")
	WebElement findByAmountBtn;
	
	
	public void findBytransactionId(int acct, String transId) {
		Select s = new Select(acctDropDown);
		s.selectByIndex(acct);
		transactionIdField.sendKeys(transId);
		findByIdBtn.click();
	}
	
	public void findBytransactionDate(int acct, String transDate) {
		Select s = new Select(acctDropDown);
		s.selectByIndex(acct);
		transactionDateField.sendKeys(transDate);
		findByDateBtn.click();
	}
	
	public void findByDateRange(int acct, String fromDate, String toDate) {
		Select s = new Select(acctDropDown);
		s.selectByIndex(acct);
		fromDateField.sendKeys(fromDate);
		toDateField.sendKeys(toDate);
		findByDateRangeBtn.click();
	}
	
	public void findByAmount(int acct, String amount) {
		Select s = new Select(acctDropDown);
		s.selectByIndex(acct);
		amountField.sendKeys(amount);
		findByAmountBtn.click();
	}

}
