package TestNG_Set;

import java.io.IOException;
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

import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import NUGU_data.data;
import unit.Capabilities;
import unit.ExtentManager;
import unit.Utilities;
import unit.ADB;


public class NUGU_TestCase {
	
	public ExtentReports extent;
	public ExtentTest test;
	
	public static Utilities util;
	public DesiredCapabilities capability;
	public ADB adb;
	
	public static final data data = new data();
	
	public String OS_ClassName;
	public String OS;
	public String hubAddress;
	public String ProjectName;
	public String ServerName;
	public String Project;
	public String Place;
	public String udid;
	public String App;
	
	public String NU100_4228C8_did;
	public String NU100_4228C8_token;
	public String NU200_3F2B99_did;
	public String NU200_3F2B99_token;
	public String NU200_95C146_did;
	public String NU200_95C146_token;
	public String smart3_did;
	public String smart3_token;
	public String AI2_did;
	public String AI2_token;
	
	public String ksszidane;
	public String ksszidane10;
	

	@Parameters({"OS", "AppName", "hubAddress", "Server", "Project", "TestPlace", "MobileDevice", "userID_ksszidane", "userID_ksszidane10",
		"deviceID_NU100_4228C8", "oAuth_Token_NU100_4228C8", 
		"deviceID_NU200_3F2B99", "oAuth_Token_NU200_3F2B99",
		"deviceID_NU200_95C146", "oAuth_Token_NU200_95C146",
		"deviceID_smart3","oAuth_Token_smart3",
		"deviceID_AI2", "oAuth_Token_AI2"})
	@BeforeClass
	public void setupClass (String OS, String AppName, String hubAddress, String Server, 
			String Project, String TestPlace, String MobileDevice,
			String userID_ksszidane, String userID_ksszidane10, 
			String deviceID_NU100_4228C8, String oAuth_Token_NU100_4228C8,
			String deviceID_NU200_3F2B99, String oAuth_Token_NU200_3F2B99,
			String deviceID_NU200_95C146, String oAuth_Token_NU200_95C146, 
			String deviceID_smart3, String oAuth_Token_smart3, 
			String deviceID_AI2, String oAuth_Token_AI2) throws Exception {
		
		OS_ClassName = OS;
		//Server = "Server : "+ Server;
		ProjectName = Project;
		ServerName = Server;
		udid = MobileDevice;
		Place = TestPlace;
		App = AppName;
		
		NU100_4228C8_did = deviceID_NU100_4228C8;
		NU100_4228C8_token = oAuth_Token_NU100_4228C8;
		NU200_3F2B99_did = deviceID_NU200_3F2B99;
		NU200_3F2B99_token = oAuth_Token_NU200_3F2B99;
		NU200_95C146_did = deviceID_NU200_95C146;
		NU200_95C146_token = oAuth_Token_NU200_95C146;
		smart3_did = deviceID_smart3;
		smart3_token = oAuth_Token_smart3;
		AI2_did = deviceID_AI2;
		AI2_token = oAuth_Token_AI2;
		
		ksszidane = userID_ksszidane;
		ksszidane10 = userID_ksszidane10;
		
		adb = new ADB();
		
		adb.ADB_SetCommand(udid);
		
		extent = ExtentManager.getExtentReports(OS, AppName, Server, Project, TestPlace);
		
		capability = Capabilities.gridSetUp(OS, MobileDevice, AppName);		
		util = new Utilities(hubAddress, capability);

		util.unlockDevice();
		
		util.setFileDetector(new LocalFileDetector());
		util.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS); 
	
		
		System.out.println("\n▒▒ Start Suite : " + util.printClassName(this)+ " ▒▒ ("+ OS + " | "+ Server + " | "+ App +")\n");
			
	}
	
	//@BeforeMethod (alwaysRun=true)
	@BeforeMethod
	public void BeforeMethod(Method method) throws Exception {
		System.out.println("\n - method name :" + method.getName() + " 시작 \n");
		test = extent.createTest(method.getName()+" | "+OS_ClassName, ServerName).assignCategory(ProjectName+" | "+OS_ClassName+" | "+ServerName);
		
	}
	
	//@AfterMethod (alwaysRun=true && result=failure)
	@AfterMethod
	 public void AfterMethod(ITestResult result) throws Exception {
		
		if (result.getStatus() == ITestResult.FAILURE) { 
			util.context("NATIVE_APP"); 
			String Time = util.getTime();
			String screenShotPath = util.ErrorScreenshots(util, "screenShotName"+Time);
			test.fail("[Test Result] : [Fail] - 테스트 실패");
	        test.log(Status.FAIL, "Snapshot below: " + test.addScreenCaptureFromPath(screenShotPath));
	         
	        System.out.println("[Test Result] : [Fail] - 테스트 실패");
	      
		} else if (result.getStatus() == ITestResult.SUCCESS) {
			test.pass("[Test Result] : [Pass] - 테스트 성공");
			 System.out.println("[Test Result] : [Pass] - 테스트 성공");
		} else if (result.getStatus() == ITestResult.SKIP) {
			test.skip("[Test Result] : [Skip] - 테스트 스킵");
			 System.out.println("[Test Result] : [Skip] - 테스트 스킵");
		} else if (result.getStatus() == ITestResult.SUCCESS_PERCENTAGE_FAILURE) {
			test.skip("[Test Result] : [OK-Pass] - 테스트 성공");
			System.out.println("[Test Result] : [OK-Pass] - 테스트 성공");
		} else { 
			test.skip("[Test Result] : [N/I] - Not Included");
			System.out.println("[Test Result] : [N/I] - Not Included");
		}
		System.out.println("\n");
		//util.CaptureScreen(result);
	    
	} 
	
	@Parameters({"Server", "TestPlace", "MobileDevice", "userID_ksszidane", "userID_ksszidane10",
		"deviceID_NU100_4228C8", "oAuth_Token_NU100_4228C8", 
		"deviceID_NU200_3F2B99", "oAuth_Token_NU200_3F2B99",
		"deviceID_NU200_95C146", "oAuth_Token_NU200_95C146",
		"deviceID_smart3","oAuth_Token_smart3",
		"deviceID_AI2", "oAuth_Token_AI2"})
	@AfterClass
	public void tearDownClass(String Server, String AppName, String TestPlace, String MobileDevice,
			String userID_ksszidane, String userID_ksszidane10, 
			String deviceID_NU100_4228C8, String oAuth_Token_NU100_4228C8,
			String deviceID_NU200_3F2B99, String oAuth_Token_NU200_3F2B99,
			String deviceID_NU200_95C146, String oAuth_Token_NU200_95C146, 
			String deviceID_smart3, String oAuth_Token_smart3, 
			String deviceID_AI2, String oAuth_Token_AI2) throws Exception {
		
		util.sendPost("그만", userID_ksszidane, deviceID_NU100_4228C8, Server, Place, oAuth_Token_NU100_4228C8);
		util.sendPost("그만", userID_ksszidane10, deviceID_NU200_3F2B99, Server, Place, oAuth_Token_NU200_3F2B99);
		util.sendPost("그만", userID_ksszidane, deviceID_NU200_95C146, Server, Place, oAuth_Token_NU200_95C146);
		util.sendPost("그만", userID_ksszidane10, deviceID_smart3, Server, Place, oAuth_Token_smart3);
		util.sendPost("그만", userID_ksszidane, deviceID_AI2, Server, Place, oAuth_Token_AI2);
		System.out.println("\n");
		
		
		try {
			extent.flush();
			util.quit();
		}
		catch (WebDriverException we) {
			util.printLog(" ** tearDownClass catch WebDriverException");
		}
		System.out.println("\n▒▒ Quit Suite : " + util.printClassName(this)+ " ▒▒\n");
		
		adb.ADB_cellular_On(udid);
		adb.ADB_WiFi_On(udid);
		adb.NUGUAPP_permission_LOCATION_On(udid);
		adb.ADB_GPS_On(udid);
		adb.ADB_ScreenLock(udid);
		
	}
}
