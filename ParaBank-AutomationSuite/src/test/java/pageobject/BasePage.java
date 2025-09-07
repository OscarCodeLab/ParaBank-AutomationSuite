package pageobject;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {
	WebDriverWait wait;
	WebDriver driver;
	NavigationBarPage nav;
	public BasePage(WebDriver driver) {
		this.driver = driver;
		nav = new NavigationBarPage(driver);
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		PageFactory.initElements(driver, this);
	}
}
