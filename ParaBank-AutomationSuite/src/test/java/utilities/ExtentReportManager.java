package utilities;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import testbase.BaseClass;

/**
 * ExtentReportManager class
 * Implements TestNG ITestListener to generate ExtentReports for the test execution.
 * Uses Log4j logger for tracking important events.
 */
public class ExtentReportManager implements ITestListener {
    
    // Create a dedicated logger for this class
    private static final Logger logger = LogManager.getLogger(ExtentReportManager.class);

    public ExtentSparkReporter sparkReporter;  // UI (HTML) reporter
    public ExtentReports extent;              // Main ExtentReports instance
    public ExtentTest test;                   // Represents each individual test

    String repName; // Unique report name with timestamp

    /**
     * Called when the test suite starts execution
     */
    @Override
    public void onStart(ITestContext testContext) {
        // COMMENTED OUT CODE - Alternative way of generating timestamp
        /*SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
        Date dt = new Date();
        String currentdatetimestamp = df.format(dt);*/

        // CREATE TIMESTAMP FOR UNIQUE REPORT NAMING
        // Example: "2025.09.08.17.45.30"
        logger.info("===== Test Suite Started: {} =====", testContext.getName());

        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        repName = "Test-Report-" + timeStamp + ".html";

        // INITIALIZE EXTENT SPARK REPORTER
        // Generates HTML file under /reports folder
        sparkReporter = new ExtentSparkReporter(".\\reports\\" + repName);
        sparkReporter.config().setDocumentTitle("Opencart Automation Report"); // Browser tab title
        sparkReporter.config().setReportName("Opencart Functional Testing");  // Main report heading
        sparkReporter.config().setTheme(Theme.DARK);                          // Dark mode report

        // INITIALIZE MAIN EXTENT REPORTS OBJECT
        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);

        // ADD SYSTEM INFORMATION TO REPORT
        extent.setSystemInfo("Application", "opencart");
        extent.setSystemInfo("Module", "Admin");
        extent.setSystemInfo("Sub Module", "Customers");
        extent.setSystemInfo("User Name", System.getProperty("user.name"));
        extent.setSystemInfo("Environment", "QA");

        // CAPTURE OS & Browser info from testng.xml parameters
        String os = testContext.getCurrentXmlTest().getParameter("os");
        extent.setSystemInfo("Operating System", os);

        String browser = testContext.getCurrentXmlTest().getParameter("browser");
        extent.setSystemInfo("Browser", browser);

        // If groups are included in testng.xml, log them too
        List<String> includedGroups = testContext.getCurrentXmlTest().getIncludedGroups();
        if (!includedGroups.isEmpty()) {
            extent.setSystemInfo("Groups", includedGroups.toString());
        }

        logger.info("Extent report initialized: {}", repName);
    }

    /**
     * Called when a test case passes
     */
    @Override
    public void onTestSuccess(ITestResult result) {
        // Create a new test entry in report
        test = extent.createTest(result.getTestClass().getName());
        test.log(Status.PASS, result.getName() + " got successfully executed");

        logger.info("✅ Test Passed: {}", result.getName());
    }

    /**
     * Called when a test case fails
     */
    @Override
    public void onTestFailure(ITestResult result) {
        // Create a new test entry in report
        test = extent.createTest(result.getTestClass().getName());
        test.log(Status.FAIL, result.getName() + " got failed");
        test.log(Status.INFO, result.getThrowable().getMessage());
        test.fail(result.getThrowable());
        
        logger.error("❌ Test Failed: {} - {}", result.getName(), result.getThrowable().getMessage());

        // CAPTURE AND ATTACH SCREENSHOT
        try {
            String imgPath = new BaseClass().captureScreen(result.getName());
            test.addScreenCaptureFromPath(imgPath);

            logger.info("Screenshot captured for failed test: {}", imgPath);
        } catch (IOException e1) {
            logger.error("Failed to capture screenshot for {}", result.getName(), e1);
        }
    }

    /**
     * Called when a test case is skipped
     */
    @Override
    public void onTestSkipped(ITestResult result) {
        // Create a new test entry in report
        test = extent.createTest(result.getTestClass().getName());
        test.log(Status.SKIP, result.getName() + " got skipped");
        test.log(Status.INFO, result.getThrowable().getMessage());

        logger.warn("⚠️ Test Skipped: {} - {}", result.getName(), result.getThrowable().getMessage());
    }

    /**
     * Called when the test suite finishes execution
     */
    @Override
    public void onFinish(ITestContext testContext) {
        // Flush all collected test data into the HTML file
        extent.flush();
        logger.info("===== Test Suite Finished: {} =====", testContext.getName());

        // Build full report path
        String pathOfExtentReport = System.getProperty("user.dir") + "\\reports\\" + repName;
        File extentReport = new File(pathOfExtentReport);

        // Automatically open report in default browser
        try {
            Desktop.getDesktop().browse(extentReport.toURI());
            logger.info("Extent report opened automatically: {}", pathOfExtentReport);
        } catch (IOException e) {
            logger.error("Failed to open extent report automatically.", e);
        }
    }
}
