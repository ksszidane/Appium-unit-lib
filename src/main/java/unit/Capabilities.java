package unit;

import java.io.IOException;

import java.net.MalformedURLException;

import org.openqa.selenium.Platform;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.MobileElement;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;

public class Capabilities {
	
	public static DesiredCapabilities gridSetUp (String os) throws IOException {		
		//DesiredCapabilities capability = null;	
		DesiredCapabilities capability = new DesiredCapabilities();

		//****************** Android OS Capabilities
		 if(os.equalsIgnoreCase("Android")) {			
			 capability.setCapability("deviceName","Android");
			 capability.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UIAutomator2");
		        //capabilities.setCapability("automationName", "uiautomator2"); 
		        //capabilities.setCapability("automationName", "Selendroid"); /app이 기반 테스트 프레임워크가 셀렌드로이드 기반일때만 설정(API Level 9이상 17이하일때)
			 capability.setCapability("platformName","Android");
		        //capabilities.setCapability("udid","adb devices를 통해 얻은 device ID"); //여러 디바이스가 설치된 경우, 테스트하고자 하는 디바이스를 선택 
		        
		        //capabilities.setCapability("launchActivity ", "old.SplashActivity");
		        //capabilities.setCapability("app", app.getAbsolutePath());
		        //capabilities.setCapability("appWaitActivity", "*");
		        
		        //capabilities.setCapability("appium-version", "1.15.1"); //UI Automator  사용하기 위함
		        //capabilities.setCapability("automationName", "{uiautomator2}"); //Android 8.0 이상 필수
		        //capabilities.setCapability("platformName", "{Android}"); 
		        //capabilities.setCapability("platformVersion", "9");
		        //capabilities.setCapability("deviceName", "{디바이스 이름}");
			 capability.setCapability("appPackage", "com.skt.aladdin");
			 capability.setCapability("appActivity", "com.skt.nugu.ui.LoginActivity");
		        
		        
		        //capabilities.setCapability("instrumentApp",true);
		        //capabilities.setCapability("autoWebview", true);
		        //capabilities.setCapability("noReset", true);
		        //capabilities.setCapability (MobileCapabilityType.FULL_RESET, false);
		        //capabilities.setCapability("unicodeKeyboard", true); //appium 전용 keyboard 이용하기 위함
		        
		        ChromeOptions chromeOptions = new ChromeOptions();
		        chromeOptions.setExperimentalOption("w3c", false);
		        //capabilities.merge(chromeOptions);
		        capability.setCapability("chromedriverArgs", chromeOptions); 
		        
		        capability.setCapability("chromedriverExecutable", "C:\\chromedriver_win32\\chromedriver.exe");
		        //capabilities.setCapability("showChromedriverLog", true);
		        
		        capability.setCapability("autoAcceptAlerts", true);
		        
		        
		        //capability.setCapability("instrumentApp",true);
		        //capability.setCapability("autoWebview", true);
		        //capability.setCapability("noReset", true);
		        //capability.setCapability (MobileCapabilityType.FULL_RESET, false);
		        //capability.setCapability("unicodeKeyboard", true); //appium 전용 keyboard 이용하기 위함
		        
		        //ChromeOptions chromeOptions = new ChromeOptions();
		        chromeOptions.setExperimentalOption("w3c", false);
		        //capability.merge(chromeOptions);
		        capability.setCapability("chromedriverArgs", chromeOptions); 
		        
		        capability.setCapability("chromedriverExecutable", "C:\\chromedriver_win32\\chromedriver.exe");
		        //capability.setCapability("showChromedriverLog", true);
		        
		        capability.setCapability("autoAcceptAlerts", true);
			 }
		
		
		return capability;		
	}	
}
