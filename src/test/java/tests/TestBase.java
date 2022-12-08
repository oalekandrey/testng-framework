package tests;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;
import utilities.Driver;
import utilities.PropertyReader;
import utilities.SeleniumUtils;

import java.io.File;
import java.lang.reflect.Method;
import java.time.Duration;

public class TestBase {

    protected WebDriver driver;
    protected static ExtentReports report;
    protected static ExtentSparkReporter htmlReport;
    protected static ExtentTest logger;

    @BeforeSuite (alwaysRun = true)
    public void setupReport(){
        report = new ExtentReports();
        String path = System.getProperty("user.dir") + "/target/extentReports/extentReport.html";
        htmlReport = new ExtentSparkReporter(path);
        report.attachReporter(htmlReport);
        report.setSystemInfo("Name","Test Automation results");
        report.setSystemInfo("Automation Engineer","Andrey Oreshenkov");
        report.setSystemInfo("OS", System.getProperty("os.name"));
        report.setSystemInfo("Browser", PropertyReader.readProperty("browser"));
    }

    @AfterSuite (alwaysRun = true)
    public void tearDownReport(){
        report.flush();
    }

    @BeforeMethod(alwaysRun = true)
    public void setupMethod(Method method){
        driver = Driver.getDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        logger = report.createTest(method.getName());
    }

    @AfterMethod(alwaysRun = true)
    public void tearDownMethod(ITestResult testResult){
        if(testResult.getStatus() == ITestResult.SUCCESS){
            logger.pass("Test PASSED: " + testResult.getName());
        }else if(testResult.getStatus() == ITestResult.SKIP){
            logger.skip("Test SKIPPED: " + testResult.getName());
        }else if(testResult.getStatus() == ITestResult.FAILURE){
            logger.fail("Test FAILED: " + testResult.getName());
            logger.fail("The exception message: " + testResult.getThrowable());
            logger.addScreenCaptureFromPath(SeleniumUtils.getScreenshotOnFailure());
        }
        Driver.closeDriver();
    }

}
