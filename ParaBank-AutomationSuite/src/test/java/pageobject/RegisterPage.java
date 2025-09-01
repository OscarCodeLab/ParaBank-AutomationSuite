package pageobject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class RegisterPage extends BasePage{

	public RegisterPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	@FindBy(id = "customer.firstName")
	WebElement fnameField;
	
	@FindBy(id = "customer.lastName")
	WebElement lnameField;
	
	@FindBy(id = "customer.address.street")
	WebElement addressField;
	
	@FindBy(id = "customer.address.city")
	WebElement cityField;
	
	@FindBy(id = "customer.address.state")
	WebElement stateField;
	
	@FindBy(id = "customer.address.zipCode")
	WebElement zipCodeField;
	
	@FindBy(id = "customer.phoneNumber")
	WebElement pNumberField;
	
	@FindBy(id = "customer.ssn")
	WebElement ssnField;
	
	@FindBy(id = "customer.username")
	WebElement usernameField;
	
	@FindBy(id = "customer.password")
	WebElement passwordField;
	
	@FindBy(id = "repeatedPassword")
	WebElement confirmPasswordField;
	
	@FindBy(xpath = "//input[@value = 'Register']")
	WebElement registerBtn;
	
	@FindBy(xpath = "//h1[@class = 'title']")
	WebElement confirmtext;
	
	public void registerAccount(String fName, String lName, String address, String city, String state, String zipCode, String pNumber, String ssn, String username, String pwd, String confirmpwd) {
		fnameField.sendKeys(fName);
		lnameField.sendKeys(lName);
		addressField.sendKeys(address);
		cityField.sendKeys(city);
		stateField.sendKeys(state);
		zipCodeField.sendKeys(zipCode);
		pNumberField.sendKeys(pNumber);
		ssnField.sendKeys(ssn);
		usernameField.sendKeys(username);
		passwordField.sendKeys(pwd);
		confirmPasswordField.sendKeys(confirmpwd);
		registerBtn.click();
			
	}
	
	public boolean verifyText() {
		
		return confirmtext.isDisplayed();
	}
	
	public boolean isOnRegistrationPage() {
	    try {
	        return registerBtn.isDisplayed();
	    } catch (Exception e) {
	        return false;
	    }
	}
	
	@FindBy(id = "customer.firstName.errors")
	WebElement fNameError;
	
	@FindBy(id = "customer.lastName.errors")
	WebElement lNameError;
	
	@FindBy(id = "customer.address.street.errors")
	WebElement addressError;
	
	@FindBy(id = "customer.address.city.errors")
	WebElement cityError;
	
	@FindBy(id = "customer.address.state.errors")
	WebElement stateError;
	
	@FindBy(id = "customer.address.zipCode.errors")
	WebElement zipCodeError;
	
	@FindBy(id = "customer.ssn.errors")
	WebElement ssnError;
	
	@FindBy(id = "customer.username.errors")
	WebElement usernameError;
	
	@FindBy(id = "customer.password.errors")
	WebElement passwordError;
	
	public String getErrorMessage(String fieldName) {
	    try {
	        switch (fieldName) {
	            case "First Name":
	                return fNameError.getText();
	            case "Last Name":
	                return lNameError.getText();
	            case "Address":
	                return addressError.getText();
	            case "City":
	                return cityError.getText();
	            case "State":
	                return stateError.getText();
	            case "ZipCode":
	                return zipCodeError.getText();
	            case "SSN":
	                return ssnError.getText();
	            case "Username":
	                return usernameError.getText();
	            case "Password":
	                return passwordError.getText();
	            default:
	                return "";
	        }
	    } catch (Exception e) {
	        return ""; // if no error is displayed for that field
	    }
	    
	    
	}


}
