package pageobject;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import java.util.ArrayList;
import java.util.List;

public class AccountsPage extends BasePage {

    public AccountsPage(WebDriver driver) {
		super(driver);
		
	}

	private WebDriver driver;

    // Main table locator
    @FindBy(id = "accountTable")
    private WebElement accountTable;
    
    //table rows
    @FindBy(xpath = "//table[@id='accountTable']//tbody/tr")
    private List<WebElement> accountRows;
    
     // all account ID links in the table
    @FindBy(xpath = "//tbody/tr/td[1]/a")
    private List<WebElement> accountLinks;
    
	@FindBy(linkText = "Open New Account")
	WebElement openAccountLink;
	
	@FindBy(linkText = "Transfer Funds")
	WebElement transferFundsLink;
	
	@FindBy(linkText = "Bill Pay")
	WebElement billPayLink;
	
	@FindBy(xpath = "//tbody/tr[1]/td[2]")
	WebElement firstAcctBal;
	
	
	
	public void clickTransferFundsLink() {
		transferFundsLink.click();
	}
	
	public void clickbillPayLink() {
		billPayLink.click();
	}
	  public boolean isAccountPresent(String expectedAccountId) {
	        for (WebElement link : accountLinks) {
	            if (link.getText().equals(expectedAccountId)) {
	                return true;
	            }
	        }
	        return false;
	    }
    
    public void clickOpenAcctLink() {
    	openAccountLink.click();
    }
    
    /**
     * Gets all table rows dynamically (tbody only)
     */
    public List<WebElement> getTableRows() {
        return accountTable.findElements(By.xpath(".//tbody/tr"));
    }

    /**
     * Extracts table data (Account, Balance, Available) for all rows except 'Total'
     */
    public List<String[]> getAccountData() {
        List<String[]> tableData = new ArrayList<>();
        List<WebElement> rows = getTableRows();

        for (WebElement row : rows) {
            List<WebElement> cols = row.findElements(By.tagName("td"));
            String firstCol = cols.get(0).getText().trim();

            // Skip total row
            if (firstCol.equalsIgnoreCase("Total")) continue;

            String account = firstCol;
            String balance = cols.get(1).getText().trim();
            String available = cols.get(2).getText().trim();

            tableData.add(new String[]{account, balance, available});
        }
        return tableData;
    }

    /**
     * Gets the displayed total balance text from the Total row.
     */
    public String getDisplayedTotalBalance() {
        List<WebElement> rows = getTableRows();
        for (WebElement row : rows) {
            List<WebElement> cols = row.findElements(By.tagName("td"));
            if (cols.get(0).getText().trim().equalsIgnoreCase("Total")) {
                return cols.get(1).getText().trim();
            }
        }
        return null;
    }

    /**
     * Converts displayed total balance to double for comparison
     */
    public double getDisplayedTotalBalanceAsDouble() {
        String total = getDisplayedTotalBalance();
        //If total has a value, strip out $ and commas, turn it into a double, and return it. If total is null, just return 0.0.”
        return total != null ? Double.parseDouble(total.replace("$", "").replace(",", "")) : 0.0;
    }

    /**
     * Calculates total balance dynamically from all rows
     */
    public double calculateTotalBalance() {
        double sum = 0.0;
        List<WebElement> rows = getTableRows();

        for (WebElement row : rows) {
            List<WebElement> cols = row.findElements(By.tagName("td"));
            String firstCol = cols.get(0).getText().trim();

            if (firstCol.equalsIgnoreCase("Total")) continue;

            String balanceText = cols.get(1).getText().replace("$", "").replace(",", "");
            sum += Double.parseDouble(balanceText);
        }
        return sum;
    }
    
    public String getFirstAcctBal() {
    	return firstAcctBal.getText();
    }
}
