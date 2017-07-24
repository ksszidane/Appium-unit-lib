package unit;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.ExtentReporter;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import unit.Capabilities;
import unit.Utilities;


public class TestCase {
	
	public ExtentReports extent;
	public ExtentTest test;
	
	public Utilities util;
	public DesiredCapabilities capability;
	
	public String browserClassName;
	public String browserName;

	
	@Parameters({"browser", "hubAddress", "browserValue"})
	@BeforeClass
	public void setupClass (String browser, String hubAddress, String browserValue) throws Exception {
		
		extent = ExtentManager.GetExtent();
		
		capability = Capabilities.gridSetUp(browser);		
		util = new Utilities(hubAddress, capability);
		
		browserClassName = browser;
		browserName = "브라우저정보 : "+browserValue;
		
		
		//util.closeAllOpenedBrowser();
		//util.windowMaximize();
		util.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS); 
		//util.waitForPageToLoad();
		//util.AddCookie("notSupportBrowserAlert", "true"); //chrome 51 부터 지원 안함.
		
		System.out.println("\n▒▒▒▒ Start Suite : " + util.printClassName(this)+ "▒▒▒▒\n");
	
	}
	
	//@AfterMethod (alwaysRun=true && result=failure)
	@AfterMethod
	 public void afterScreenShot(ITestResult result) throws Exception {
		
		if (result.getStatus() == ITestResult.FAILURE) { 
			 String screenShotPath = util.ErrorScreenshots(util, "screenShotName");
	            test.log(Status.FAIL, result.getThrowable());
	            test.log(Status.FAIL, "Snapshot below: " + test.addScreenCaptureFromPath(screenShotPath));
		}
		
		//util.CaptureScreen(result);
	    
	} 
	
	//@BeforeMethod (alwaysRun=true)
	//@BeforeMethod
	 public void beforeScreenShot() throws Exception {
		//util.StartCaptureScreen();
	}
	
	@AfterClass
	public void tearDownClass(){
		
		try {
			extent.flush();
			util.quit();
		}
		catch (WebDriverException we) {
			util.printLog(" ** tearDownClass catch WebDriverException");
		}
		System.out.println("\n▒▒▒▒Quit Suite : " + util.printClassName(this)+ "▒▒▒▒\n");
		//util.close();
	}
}
