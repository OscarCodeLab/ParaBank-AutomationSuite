package utilities;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
//import java.net.URL;
import java.net.URL;

//Extent report 5.x...//version

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.ImageHtmlEmail;
import org.apache.commons.mail.resolver.DataSourceUrlResolver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import testbase.BaseClass;


public class ExtentReportManager implements ITestListener {
	public ExtentSparkReporter sparkReporter;
	public ExtentReports extent;
	public ExtentTest test;

	String repName;

	public void onStart(ITestContext testContext) {
		
		// COMMENTED OUT CODE - Alternative approach to creating timestamp
		/*SimpleDateFormat df=new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
			Date dt=new Date();
			String currentdatetimestamp=df.format(dt);
			*/
			
		// CREATE TIMESTAMP FOR UNIQUE REPORT NAMING
		// This creates a timestamp in format: yyyy.MM.dd.HH.mm.ss (e.g., 2024.03.15.14.30.25)
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		
		// GENERATE UNIQUE REPORT FILE NAME
		// Creates report name like: "Test-Report-2024.03.15.14.30.25.html"
		repName = "Test-Report-" + timeStamp + ".html";

		
		// INITIALIZE EXTENT SPARK REPORTER
		// Creates HTML reporter and specifies the file location in the "reports" folder
		sparkReporter = new ExtentSparkReporter(".\\reports\\" + repName);
		
		// CONFIGURE REPORT APPEARANCE AND METADATA
		sparkReporter.config().setDocumentTitle("opencart Automation Report"); // Sets browser tab title
		sparkReporter.config().setReportName("opencart Functional Testing"); // Sets main report heading
		sparkReporter.config().setTheme(Theme.DARK); // Sets dark theme for the report
		
		// INITIALIZE MAIN EXTENT REPORTS OBJECT
		extent = new ExtentReports();
		extent.attachReporter(sparkReporter); // Connect the HTML reporter to ExtentReports
		
		// ADD SYSTEM INFORMATION TO REPORT
		// These details appear in the report's system info section
		extent.setSystemInfo("Application", "opencart");
		extent.setSystemInfo("Module", "Admin");
		extent.setSystemInfo("Sub Module", "Customers");
		extent.setSystemInfo("User Name", System.getProperty("user.name")); // Gets current system username
		extent.setSystemInfo("Environment", "QA"); 
		
		// GET OPERATING SYSTEM FROM TESTNG XML PARAMETER
		String os = testContext.getCurrentXmlTest().getParameter("os");
		extent.setSystemInfo("Operating System", os);
		
		// GET BROWSER FROM TESTNG XML PARAMETER
		String browser = testContext.getCurrentXmlTest().getParameter("browser");
		extent.setSystemInfo("Browser", browser);
		
		// GET TEST GROUPS FROM TESTNG XML AND ADD TO REPORT
		List<String> includedGroups = testContext.getCurrentXmlTest().getIncludedGroups();
		if(!includedGroups.isEmpty()) {
			extent.setSystemInfo("Groups", includedGroups.toString());
		}
	}

	// HANDLE SUCCESSFUL TEST EXECUTION
	public void onTestSuccess(ITestResult result) {
		// Create new test entry in report using the test class name
		test = extent.createTest(result.getTestClass().getName());
		
		// Assign test groups/categories to the test for better organization
		//test.assignCategory(result.getMethod().getGroups());
		
		// Log success message with test name
		test.log(Status.PASS, result.getName() + " got successfully executed");
	}

	// HANDLE FAILED TEST EXECUTION
	public void onTestFailure(ITestResult result) {
		// Create new test entry in report
		test = extent.createTest(result.getTestClass().getName());
		
		// Assign test groups/categories
	//	test.assignCategory(result.getMethod().getGroups());
		
		// Log failure status and error details
		test.log(Status.FAIL, result.getName() + " got failed");
		test.log(Status.INFO, result.getThrowable().getMessage()); // Log the actual error message
		
		// CAPTURE AND ATTACH SCREENSHOT FOR FAILED TESTS
		try {
			// Call method to take screenshot (assuming BaseClass has captureScreen method)
			// Creating a new BaseClass() instance here would initialize a new WebDriver,
			// which can conflict with the existing driver instance used throughout the tests.
			// To avoid multiple driver instances and maintain consistency,
			// we declare the WebDriver in BaseClass as static, so it can be accessed globally.

			String imgPath = new BaseClass().captureScreen(result.getName());
			
			// Attach screenshot to the test report
			test.addScreenCaptureFromPath(imgPath);
			
		} catch (IOException e1) {
			e1.printStackTrace(); // Print error if screenshot capture fails
		}
	}

	// HANDLE SKIPPED TEST EXECUTION
	public void onTestSkipped(ITestResult result) {
		// Create new test entry in report
		test = extent.createTest(result.getTestClass().getName());
		
		// Assign test groups/categories
		//test.assignCategory(result.getMethod().getGroups());
		
		// Log skip status and reason
		test.log(Status.SKIP, result.getName() + " got skipped");
		test.log(Status.INFO, result.getThrowable().getMessage()); // Log reason for skipping
	}

	// FINALIZE REPORT AFTER ALL TESTS COMPLETE
		public void onFinish(ITestContext testContext) {
			// Write all collected test data to the HTML report file
			extent.flush();
			
			// BUILD FULL PATH TO THE GENERATED REPORT
			String pathOfExtentReport = System.getProperty("user.dir") + "\\reports\\" + repName;
			File extentReport = new File(pathOfExtentReport);
			
			// AUTOMATICALLY OPEN REPORT IN DEFAULT BROWSER
			try {
				Desktop.getDesktop().browse(extentReport.toURI());
			} catch (IOException e) {
				e.printStackTrace(); // Print error if report opening fails
			}
		
		/* try {
			  URL url = new  URL("file:///"+System.getProperty("user.dir")+"\\reports\\"+repName);
		  
		  // Create the email message 
		  ImageHtmlEmail email = new ImageHtmlEmail();
		  email.setDataSourceResolver(new DataSourceUrlResolver(url));
		  email.setHostName("smtp.googlemail.com"); // only work for gmail 
		  email.setSmtpPort(465);
		  email.setAuthenticator(new DefaultAuthenticator("primeqalyst@gmail.com","@Oluwapomi1")); 
		  email.setSSLOnConnect(true);
		  email.setFrom("primeqalyst@gmail.com"); //Sender
		  email.setSubject("Test Results");
		  email.setMsg("Please find Attached Report....");
		  email.addTo("eviltester62@gmail.com"); //Receiver 
		  email.attach(url, "extent report", "please check report..."); 
		  email.send(); // send the email 
		  }
		  catch(Exception e) 
		  { 
			  e.printStackTrace(); 
			  }*/
		 
		 
	}

}