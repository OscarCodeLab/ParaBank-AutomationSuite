package pageobject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

public class OpenAccountPage extends BasePage{

	public OpenAccountPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	

	
	@FindBy(xpath = "//input[@value = 'Open New Account']")
	WebElement openNewAccountBtn;
	
	@FindBy(id = "type")
	WebElement AccounttypeDropDown;
	
	@FindBy(id = "fromAccountId")
	WebElement fromAccountDropdown;
	
	
	public void selectAccountType(String type) {
		
		Select s = new Select(AccounttypeDropDown);
		s.selectByVisibleText(type);
		
	}
	
	public void selectnewAccountType(String type) {
		
		Select s = new Select(AccounttypeDropDown);
		s.selectByVisibleText(type);
		
	}
	
	public void selectFromAccount(int acct) {
		Select s = new Select(fromAccountDropdown);
		s.selectByIndex(acct);
		
	}
	

    public void clickOpenNewAccount() {
        openNewAccountBtn.click();
    }
	
	
	
}
