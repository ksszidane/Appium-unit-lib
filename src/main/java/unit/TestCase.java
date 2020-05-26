l dlfemt
package unit;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.LocalFileDetector;
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
	
	public static Utilities util;
	public DesiredCapabilities capability;
	
	public String browserClassName;
	public String browserName;
	public String projectName;

	
	@Parameters({"browser", "hubAddress", "browserValue", "project"})
	@BeforeClass
	public void setupClass (String browser, String hubAddress, String browserValue, String project) throws Exception {
		
		extent = ExtentManager.GetExtent();
		
		capability = Capabilities.gridSetUp(browser);		
		util = new Utilities(hubAddress, capability);
		
		browserClassName = browser;
		browserName = "브라우저정보 : "+browserValue;
		projectName = project;
		
		util.setFileDetector(new LocalFileDetector());
		util.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS); 
	
		
		System.out.println("\n▒▒ Start Suite : " + util.printClassName(this)+ "▒▒ Browser Name : "+browserValue+"\n");
		
		
		
		
	}
	//@BeforeMethod (alwaysRun=true)
	@BeforeMethod
	public void BeforeMethod(Method method) throws Exception {
		System.out.println("method name:" + method.getName());
			test = extent.createTest(method.getName()+" "+browserClassName, browserName).assignCategory(projectName+"_"+browserClassName);
		
			
	}
	//@AfterMethod (alwaysRun=true && result=failure)
	@AfterMethod
	 public void AfterMethod(ITestResult result) throws Exception {
		
		
		
		
		if (result.getStatus() == ITestResult.FAILURE) { 
			 String Time = util.getTime();
			 String screenShotPath = util.ErrorScreenshots(util, "screenShotName"+Time);
			 test.fail("테스트 실패.");
	         test.log(Status.FAIL, result.getThrowable());
	         test.log(Status.FAIL, "Snapshot below: " + test.addScreenCaptureFromPath(screenShotPath));
	         
	         System.out.println("테스트 실패.");
		} else if (result.getStatus() == ITestResult.SUCCESS) {
			test.pass("테스트 성공.");
			 //System.out.println("테스트 성공.");
		}
		
		//util.CaptureScreen(result);
	    
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
		System.out.println("\n▒▒Quit Suite : " + util.printClassName(this)+ "▒▒\n");
		
	}
}
