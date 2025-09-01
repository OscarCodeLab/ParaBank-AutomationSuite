package testbase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import utilities.ExcelReader;

// to rerun failed test case goto test-output and run failed.png


public class BaseClass {
	// reuseable method are kept in baseclass
	public static WebDriver driver;
	public Logger logger; //log4j
	public Properties prop;
	public  ExcelReader excel = new ExcelReader(".\\testdata\\registerdata.xlsx");
	
	@BeforeTest
	public void setup() throws IOException {
		logger = LogManager.getLogger(this.getClass()); // make sure the log xml name is "log4j2.xml"
		  // make sure the log xml name is "log4j2.xml"
		FileReader fr = new FileReader("./src//test//resources//config.properties");
		prop = new Properties();
		prop.load(fr);
		String br = prop.getProperty("browser");
		switch(br.toLowerCase()) {
		case "edge" : driver = new EdgeDriver(); break;
		case "chrome" : driver = new ChromeDriver(); break;
		case "firefox" : driver = new FirefoxDriver(); break;
		default : System.out.println("Invalid browser name..."); return;
		}
        driver.manage().window().maximize();
        driver.get(prop.getProperty("url"));           
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
   
		}
		
	
	public void teardown() {
		driver.quit();
	}
	public String randomString() {
		String generatedString = RandomStringUtils.randomAlphabetic(5);
		return generatedString;
		}
		
		
		
		public String randomNumber() {
			String generatedNumber = RandomStringUtils.randomNumeric(8);
			return generatedNumber;	
		}
		
		public String randomAlphaNumeric() {
			String generatedString = RandomStringUtils.randomAlphabetic(4);
			 String generatedNumber = RandomStringUtils.randomNumeric(4);
			 return (generatedString+generatedNumber);
			 
			 
		}
		
		public String captureScreen(String tname) throws IOException {

			String timeStamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
					
			TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
			File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
			
			String targetFilePath=System.getProperty("user.dir")+"\\screenshots\\" + tname + "_" + timeStamp + ".png";
			File targetFile=new File(targetFilePath);
			
			sourceFile.renameTo(targetFile);
				
			return targetFilePath;

		}
		
		@AfterTest
		public void tearDown() {
			if(driver != null) {
				driver.quit();
			}
		}
}
