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
	
	public String OS_ClassName;
	public String Server;
	public String ProjectName;
	public String userID;
	public String deviceID;
	public String Place;
	public String oAuth_Token;

	
	@Parameters({"OS", "hubAddress", "Server", "Project", "userID", "deviceID", "Place", "oAuth_Token"})
	@BeforeClass
	public void setupClass (String OS, String hubAddress, String Server, String Project, String userID, String deviceID, String oAuth_Token) throws Exception {
		
		extent = ExtentManager.GetExtent();
		
		capability = Capabilities.gridSetUp(OS);		
		util = new Utilities(hubAddress, capability);
		
		OS_ClassName = OS;
		Server = "Server : "+ Server;
		ProjectName = Project;
		
		util.setFileDetector(new LocalFileDetector());
		util.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS); 
	
		
		System.out.println("\n▒▒ Start Suite : " + util.printClassName(this)+ "▒▒ Server : "+Server+"\n");
		
		
		
		
	}
	//@BeforeMethod (alwaysRun=true)
	@BeforeMethod
	public void BeforeMethod(Method method) throws Exception {
		System.out.println("method name:" + method.getName());
			test = extent.createTest(method.getName()+" "+OS_ClassName, Server).assignCategory(ProjectName+"_"+OS_ClassName);
		
			
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
	
	@Parameters({"userID", "deviceID", "Server", "Place", "oAuth_Token"})
	@AfterClass
	public void tearDownClass(String userID, String deviceID, String Server, String Place, String oAuth_Token) throws Exception {
		
		util.sendPost("그만", userID, deviceID, Server, Place, oAuth_Token);
		
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
