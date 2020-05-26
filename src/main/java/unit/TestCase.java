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
	
	public String os_ClassName;
	public String os_Version;
	public String projectName;

	
	@Parameters({"os", "hubAddress", "osVersion", "project"})
	@BeforeClass
	public void setupClass (String os, String hubAddress, String osVersion, String project) throws Exception {
		
		extent = ExtentManager.GetExtent();
		
		capability = Capabilities.gridSetUp(os);		
		util = new Utilities(hubAddress, capability);
		
		os_ClassName = os;
		os_Version = "OS_Version : "+osVersion;
		projectName = project;
		
		util.setFileDetector(new LocalFileDetector());
		util.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS); 
	
		
		System.out.println("\n▒▒ Start Suite : " + util.printClassName(this)+ "▒▒ OS Version : "+os_Version+"\n");
		
		
		
		
	}
	//@BeforeMethod (alwaysRun=true)
	@BeforeMethod
	public void BeforeMethod(Method method) throws Exception {
		System.out.println("method name:" + method.getName());
			test = extent.createTest(method.getName()+" "+os_ClassName, os_Version).assignCategory(projectName+"_"+os_ClassName);
		
			
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
