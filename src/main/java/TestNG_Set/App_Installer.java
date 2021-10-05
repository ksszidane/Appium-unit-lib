package TestNG_Set;

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

import com.aventstack.extentreports.Status;

import unit.ADB;
import unit.Capabilities;
import unit.ExtentManager;
import unit.Utilities;

public class App_Installer {
	
	public static Utilities util;
	public DesiredCapabilities capability;
	public ADB adb;
	
	public String udid;
	
	@Parameters({"MobileDevice"})
	@BeforeClass
	public void setupClass (String MobileDevice) throws Exception {
		
		udid = MobileDevice;

		
		adb = new ADB();
		
		adb.ADB_SetCommand(udid);

		
	}
	
	//@BeforeMethod (alwaysRun=true)
	@BeforeMethod
	public void BeforeMethod(Method method) throws Exception {

		
	}
	
	//@AfterMethod (alwaysRun=true && result=failure)
	@AfterMethod
	 public void AfterMethod(ITestResult result) throws Exception {
		
	    
	} 
	
	@Parameters({"MobileDevice"})
	@AfterClass
	public void tearDownClass(String MobileDevice) throws Exception {
		
		adb.ADB_ScreenLock(udid); //빌드할때와 시나리오 테스트 진핸 완료 후에는 주석 풀기
		
	}

}
