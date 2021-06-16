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

import NUGU_data.data;
import unit.Capabilities;
import unit.Utilities;
import unit.ADB;

public class SDK_TestCase {
	
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
	
	public String SampleApp_did;
	
	public String nuguqa01;

	

	@Parameters({"OS", "AppName", "hubAddress", "Server", "Project", "TestPlace", "MobileDevice", 
		"userID_nuguqa01", "deviceID_SampleApp"})
	@BeforeClass
	public void setupClass (String OS, String AppName, String hubAddress, String Server, 
			String Project, String TestPlace, String MobileDevice, String userID_nuguqa01, 
			String deviceID_SampleApp) throws Exception {
		
		OS_ClassName = OS;
		//Server = "Server : "+ Server;
		ProjectName = Project;
		ServerName = Server;
		udid = MobileDevice;
		Place = TestPlace;
		App = AppName;
		
		SampleApp_did = deviceID_SampleApp;
		
		nuguqa01 = userID_nuguqa01;
		
		adb = new ADB();
		
		adb.ADB_SetCommand(udid);
		
		extent = ExtentManager.GetExtent();
		
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
			 String Time = util.getTime();
			 String screenShotPath = util.ErrorScreenshots(util, "screenShotName"+Time);
			 test.fail("테스트 실패.");
	         test.log(Status.FAIL, result.getThrowable());
	         test.log(Status.FAIL, "Snapshot below: " + test.addScreenCaptureFromPath(screenShotPath));
	         
	         System.out.println("테스트 실패.");
		} else if (result.getStatus() == ITestResult.SUCCESS) {
			test.pass("테스트 성공.");
			 //System.out.println("테스트 성공.");
		} else if (result.getStatus() == ITestResult.SKIP) {
			test.skip("테스트 스킵.");
			 //System.out.println("테스트 성공.");
		}
		System.out.println("\n");
		//util.CaptureScreen(result);
	    
	} 
	
	@Parameters({"Server", "AppName", "TestPlace", "MobileDevice", "userID_ksszidane", "userID_ksszidane10",
		"deviceID_Chips"})
	@AfterClass
	public void tearDownClass(String Server, String AppName, String TestPlace, String MobileDevice,
			String userID_ksszidane, String userID_ksszidane10, 
			String deviceID_Chips) throws Exception {
		
		//util.sendPost("그만", userID_ksszidane, deviceID_Chips, Server, Place, oAuth_Token_Chips);
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
		adb.ADB_GPS_On(udid);
		adb.ADB_ScreenLock(udid); //시나리오 테스트 진핸 완료 후에는 주석 풀기
		
	}

}
