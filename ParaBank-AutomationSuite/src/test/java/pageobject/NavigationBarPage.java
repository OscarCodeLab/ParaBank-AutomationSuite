package pageobject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class NavigationBarPage {
	public WebDriver driver;

		
		public NavigationBarPage(WebDriver driver) {
			this.driver = driver;
			PageFactory.initElements(driver, this); 

		}
		
		@FindBy(linkText = "Open New Account")
		WebElement openAccountLink;
		
		@FindBy(linkText = "Transfer Funds")
		WebElement transferFundsLink;
		
		@FindBy(linkText = "Bill Pay")
		WebElement billPayLink;
		
		@FindBy(linkText = "Accounts Overview")
		WebElement AccountsOverview;
		
		@FindBy(linkText = "Register")
		WebElement registerLink;
		
		public void clickTransferFundsLink() {
			transferFundsLink.click();
		}
		
		public void clickbillPayLink() {
			billPayLink.click();
		}
		
		public void clickOpenAcctLink() {
	    	openAccountLink.click();
	    }
		
		public void clickAcctOverviewLink() {
			AccountsOverview.click();
		}
	    
		public void clickRegisterLink() {
			registerLink.click();
		}
		
}
