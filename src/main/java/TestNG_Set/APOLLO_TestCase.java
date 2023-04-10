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

import unit.Capabilities;
import unit.ExtentManager;
import unit.Utilities;
import unit.ADB;

public class APOLLO_TestCase {
	
	public ExtentReports extent;
	public ExtentTest test;
	
	public static Utilities util;
	public DesiredCapabilities capability;
	public ADB adb;
	
	
	public String OS_ClassName;
	public String OS;
	public String hubAddress;
	public String ProjectName;
	public String ServerName;
	public String Project;
	public String Place;
	public String udid;
	public String App;
	public String Service;
	
	public String dID;
	public String uID;
	

	@Parameters({"OS", "AppName", "hubAddress", "Server", "Project", "TestPlace", "MobileDevice", "ServiceName",
		"userID", "deviceID" })
	@BeforeClass
	public void setupClass (String OS, String AppName, String hubAddress, String Server, String Project, String TestPlace, String MobileDevice, 
			String ServiceName, String userID, String deviceID) throws Exception {
		
		OS_ClassName = OS;
		//Server = "Server : "+ Server;
		ProjectName = Project;
		ServerName = Server;
		udid = MobileDevice;
		Place = TestPlace;
		App = AppName;
		Service = ServiceName;
		
		dID = deviceID;
		uID = userID;
		
		adb = new ADB();
		
		adb.ADB_SetCommand(udid); 
		
		extent = ExtentManager.getExtentReports(OS, AppName, Server, Project, TestPlace);
		
		capability = Capabilities.gridSetUp(OS, MobileDevice, AppName);		
		util = new Utilities(hubAddress, capability);

		util.unlockDevice();
		
		util.setFileDetector(new LocalFileDetector());
		util.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS); 
	
		
		System.out.println("\n▒▒ Start Suite : " + util.printClassName(this)+ " ▒▒ ("+ OS + " | "+ Server + " | "+ App +")\n");
		
		if(Server.contains("STG")) {
			adb.permission_BATTERY_OPTIMIZATIONS_On(udid, "com.skt.nugu.apollo.stg");
		} else {
			adb.permission_BATTERY_OPTIMIZATIONS_On(udid, "com.skt.nugu.apollo");
		}
	
			
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
	        test.log(Status.FAIL, result.getThrowable());
	        System.out.println("[Test Result] : [Fail] - 테스트 실패");
	      
		} else if (result.getStatus() == ITestResult.SUCCESS) {
			test.pass("[Test Result] : [Pass] - 테스트 성공");
			 System.out.println("[Test Result] : [Pass] - 테스트 성공");
		} else if (result.getStatus() == ITestResult.SKIP) {
			test.skip("[Test Result] : [Skip] - 테스트 스킵");
			 System.out.println("[Test Result] : [Skip] - 테스트 스킵");
		} 
		System.out.println("\n");
		//util.CaptureScreen(result);
	    
	} 
	
	@Parameters({"Server", "AppName", "TestPlace", "MobileDevice", "ServiceName", "userID", "deviceID"})
	@AfterClass
	public void tearDownClass(String Server, String AppName, String TestPlace, String MobileDevice, String ServiceName, String userID, String deviceID_SampleApp) throws Exception {
		
		//util.sendPost("그만", userID_nuguqa001, deviceID_SampleApp, Server, Place, Auth_Token_SampleApp);
		System.out.println("\n");
		
		
		try {
			extent.flush();
			util.quit();
		}
		catch (WebDriverException we) {
			util.printLog(" ** tearDownClass catch WebDriverException");
		}
		System.out.println("\n▒▒ Quit Suite : " + util.printClassName(this)+ " ▒▒\n");
		
		//adb.ADB_ScreenLock(udid); //빌드할때와 시나리오 테스트 진핸 완료 후에는 주석 풀기
		
	}

}
