package TestNG_Set;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.NoSuchSessionException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.LocalFileDetector;
import org.testng.ITestResult;
import org.testng.annotations.*;
import unit.ADB;
import unit.Capabilities;
import unit.ExtentManager;
import unit.Utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.util.concurrent.TimeUnit;

public class A_LLM_TestCase {
	
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
	public String AppPackage;

	public String MobileDevice;
	public String AppName;
	public int row;
	

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

		row = 0;

		System.out.println("BeforeClass " + row);

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
			AppPackage = "com.skt.nugu.apollo.stg";
		} else {
			AppPackage = "com.skt.nugu.apollo";
		}
		
		adb.permission_BATTERY_OPTIMIZATIONS_On(udid, AppPackage);
	
			
	}
	
	//@BeforeMethod (alwaysRun=true)
	@BeforeMethod
	public void BeforeMethod(Method method) throws Exception {
		System.out.println("\n - method name :" + method.getName() + " 시작 \n");
		test = extent.createTest(method.getName()+" | "+OS_ClassName, ServerName).assignCategory(ProjectName+" | "+OS_ClassName+" | "+ServerName);
		System.out.println("BeforeMethod " + row);
	}
	
	//@AfterMethod (alwaysRun=true && result=failure)
	@Parameters({"OS", "AppName", "hubAddress", "Server", "Project", "TestPlace", "MobileDevice", "ServiceName",
			"userID", "deviceID" })
	@AfterMethod
	 public void AfterMethod(ITestResult result, String OS, String AppName, String hubAddress, String Server, String Project, String TestPlace, String MobileDevice,
							 String ServiceName, String userID, String deviceID) throws Exception {

		String excelFilePath = "/Users/1112049/Downloads/vux.xlsx";
		System.out.println("AfterMethod " + row);
		if (result.getStatus() == ITestResult.FAILURE) {
			test.fail("[Test Result] : [Fail] - 테스트 실패");
	        System.out.println("[Test Result] : [Fail] - 테스트 실패");
			util.excelWrite(excelFilePath, "0", "0", "테스트실패", "테스트실패", row);
			row = row+1;
		} else if (result.getStatus() == ITestResult.SUCCESS) {
			test.pass("[Test Result] : [Pass] - 테스트 성공");
			 System.out.println("[Test Result] : [Pass] - 테스트 성공");
			row = row+1;
		} else if (result.getStatus() == ITestResult.SKIP) {
			test.skip("[Test Result] : [Skip] - 테스트 스킵");
			 System.out.println("[Test Result] : [Skip] - 테스트 스킵");
			row = row+1;
		}
		System.out.println("\n");
		//util.CaptureScreen(result);


		if (!isAppRunning(AppPackage)) {
			// 앱이 종료되었으므로 다시 실행
			System.out.println("앱이 종료되었습니다.");
			Thread.sleep(1500);

			while (true) {
				try {
					capability = Capabilities.gridSetUp(OS, MobileDevice, AppName);
					util = new Utilities(hubAddress, capability);

					util.unlockDevice();

					util.setFileDetector(new LocalFileDetector());
					util.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

					// 성공적으로 세션 시작했으면 반복문 종료
					break;
				} catch (NoSuchSessionException e) {
					// NoSuchSessionException이 발생했을 때 세션 재시작
					System.out.println("Session not found. Restarting session...");
					e.printStackTrace();

					// 이전 세션 종료
					if (util != null) {
						util.quit();
					}


				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
			}

			String url = "skt.adot://plugin/chat?agent_id=music";
			util.get(url);
			Thread.sleep(1500);

			System.out.println("앱을 다시 실행하였습니다.");

		} else {
			System.out.println("앱이 실행 중입니다.");
		}
	    
	} 
	
	@Parameters({"Server", "AppName", "TestPlace", "MobileDevice", "ServiceName", "userID", "deviceID"})
	@AfterClass
	public void tearDownClass(String Server, String AppName, String TestPlace, String MobileDevice, String ServiceName, String userID, String deviceID_SampleApp) throws Exception {
		
		//util.sendPost("그만", userID_nuguqa001, deviceID_SampleApp, Server, Place, Auth_Token_SampleApp);
		System.out.println("\n");
		System.out.println("AfterClass " + row);
		
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

	// 앱 실행 여부를 확인하는 메서드
	private static boolean isAppRunning(String packageName) throws IOException {
		Process process = Runtime.getRuntime().exec("adb shell ps");
		BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
		String line;
		while ((line = reader.readLine()) != null) {
			if (line.contains(packageName)) {
				return true;
			}
		}
		return false;
	}
}
