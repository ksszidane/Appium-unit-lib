package unit;

import java.io.IOException;

import java.net.MalformedURLException;
import java.util.HashMap;

import org.openqa.selenium.Platform;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;


public class Capabilities {

	public static DesiredCapabilities gridSetUp (String os, String Device, String AppName) throws IOException {
		//DesiredCapabilities capability = null;	
		DesiredCapabilities capability = new DesiredCapabilities();

		//****************** Android OS Capabilities
		 if(AppName.equalsIgnoreCase("NUGU")) {			
			 capability.setCapability("deviceName","Android");
			 capability.setCapability("automationName", "UIAutomator2");
		     //capabilities.setCapability("automationName", "uiautomator2"); 
		     //capabilities.setCapability("automationName", "Selendroid"); /app이 기반 테스트 프레임워크가 셀렌드로이드 기반일때만 설정(API Level 9이상 17이하일때)
			 capability.setCapability("platformName","Android");
			 capability.setCapability("udid",Device); //여러 디바이스가 설치된 경우, 테스트하고자 하는 디바이스를 선택 
		        
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
		       
			 
		     //capabilities.setCapability("instrumentApp",true); // iOS 용
		     //capabilities.setCapability("noReset", true);
		     //capabilities.setCapability (MobileCapabilityType.FULL_RESET, false);
		     //capabilities.setCapability("unicodeKeyboard", true); //appium 전용 keyboard 이용하기 위함
		        
		     ChromeOptions chromeOptions = new ChromeOptions();
		     chromeOptions.setExperimentalOption("w3c", false);
		     //capabilities.merge(chromeOptions);
		     capability.setCapability("chromedriverArgs", chromeOptions); 
		     
		      
		     //capability.setCapability("chromedriverExecutable", "C:\\chromedriver_win32\\chromedriver.exe");
		     capability.setCapability("showChromedriverLog", true);
			 capability.setCapability("appium:settings[enableMultiWindows]", true);
		     //capability.setCapability("autoWebview", true); //Webview 컨텍스트로 직접 이동하십시오. 기본false
		     //capability.setCapability("autoWebviewTimeout", "4000"); //Webview 컨텍스트가 활성화 될 때까지 기다리는 시간
		     
		     //capability.setCapability("ensureWebviewsHavePages", true);
		     //capability.setCapability("autoAcceptAlerts", true);
		        
		        
		     //capability.setCapability("instrumentApp",true);
		     //capability.setCapability("noReset", true);
		     //capability.setCapability (MobileCapabilityType.FULL_RESET, false);
		     //capability.setCapability("unicodeKeyboard", true); //appium 전용 keyboard 이용하기 위함
		        
		     //ChromeOptions chromeOptions = new ChromeOptions();
		    
		        
		     capability.setCapability("chromedriverExecutable", "C:\\chromedriver_win32\\chromedriver.exe"); 
		     //capability.setCapability("chromedriverUseSystemExecutable", true); //true 인 경우 자동 Chromedriver 구성을 무시하고 Appium과 함께 다운로드 된 버전을 사용
		     //capability.setCapability("showChromedriverLog", true);
		        
		    
			 }
		 if(AppName.equalsIgnoreCase("SDK")) {			
			 capability.setCapability("deviceName","Android");
			 capability.setCapability("automationName", "UIAutomator2");
		     capability.setCapability("platformName","Android");
			 capability.setCapability("udid",Device); //여러 디바이스가 설치된 경우, 테스트하고자 하는 디바이스를 선택 
			 
			 capability.setCapability("appPackage", "com.skt.nugu.sampleapp");
			 capability.setCapability("appActivity", "com.skt.nugu.sampleapp.activity.LoginActivity");
			 
			 capability.setCapability("noReset",true);
			 capability.setCapability("newCommandTimeout",60 * 5);
		        
		     ChromeOptions chromeOptions = new ChromeOptions();
		     chromeOptions.setExperimentalOption("w3c", true);
		     capability.setCapability("chromedriverArgs", chromeOptions); 
		     
		     capability.setCapability("recreateChromeDriverSessions", false); 
		     capability.setCapability("extractChromeAndroidPackageFromContextName", true); 
		     
		     capability.setCapability("focused", true); 
		     
		     capability.setCapability("showChromedriverLog", true);
		     capability.setCapability("appium:settings[enableMultiWindows]", true);
		     capability.setCapability("chromedriverExecutable", "C:\\chromedriver_win32\\chromedriver.exe"); 
		        
		    
		}
		 if(AppName.equalsIgnoreCase("APOLLO_PRD")) {			
			 
			 String systemOS = System.getProperty("os.name").toLowerCase();
			 
			 capability.setCapability("deviceName","Android");
			 capability.setCapability("automationName", "UIAutomator2");
		     capability.setCapability("platformName","Android");
			 capability.setCapability("udid",Device); //여러 디바이스가 설치된 경우, 테스트하고자 하는 디바이스를 선택 
			 
			 capability.setCapability("appPackage", "com.skt.nugu.apollo");
			 capability.setCapability("appActivity", "com.skt.nugu.visual.splash.SplashActivity");
			 
			 capability.setCapability("noReset",true);
			 capability.setCapability("newCommandTimeout",60 * 5);
		        
		     ChromeOptions chromeOptions = new ChromeOptions();
		     chromeOptions.setExperimentalOption("w3c", true);
		     capability.setCapability("chromedriverArgs", chromeOptions); 
		     
		     capability.setCapability("recreateChromeDriverSessions", false); 
		     capability.setCapability("extractChromeAndroidPackageFromContextName", true); 
		     
		     //noReset이 true로 처리되어 있으면 아래 명령어가 동작하지 않는다. 
		     capability.setCapability("autoGrantPermissions", "true");
		     
		     capability.setCapability("focused", true); 
		     
		     capability.setCapability("showChromedriverLog", true);
			 capability.setCapability("appium:settings[enableMultiWindows]", true);
		     
		     
		     if (systemOS.contains("win")) {
		    	 capability.setCapability("chromedriverExecutable", "C:\\chromedriver_win32\\chromedriver.exe"); //win
		     } else if (systemOS.contains("mac")) {
		    	 capability.setCapability("chromedriverExecutable", "/opt/homebrew/bin/chromedriver");
		     }

		    
		}
		 
		 if(AppName.equalsIgnoreCase("APOLLO_STG")) {			
			
			 String systemOS = System.getProperty("os.name").toLowerCase();
			 
			 capability.setCapability("deviceName","Android");
			 capability.setCapability("automationName", "UIAutomator2");
		     capability.setCapability("platformName","Android");
			 capability.setCapability("udid",Device); //여러 디바이스가 설치된 경우, 테스트하고자 하는 디바이스를 선택 
			 
			 capability.setCapability("appPackage", "com.skt.nugu.apollo.stg");
			 capability.setCapability("appActivity", "com.skt.nugu.visual.splash.SplashActivity");
			 
			 capability.setCapability("noReset",true);
			 capability.setCapability("newCommandTimeout",60 * 5);
		        
		     ChromeOptions chromeOptions = new ChromeOptions();
		     chromeOptions.setExperimentalOption("w3c", true);
		     capability.setCapability("chromedriverArgs", chromeOptions); 
		     
		     capability.setCapability("recreateChromeDriverSessions", false); 
		     capability.setCapability("extractChromeAndroidPackageFromContextName", true); 
		     
		   //noReset이 true로 처리되어 있으면 아래 명령어가 동작하지 않는다. 
		     capability.setCapability("autoGrantPermissions", "true");
		     
		     capability.setCapability("focused", true); 
		     
		     capability.setCapability("showChromedriverLog", true);
			 capability.setCapability("appium:settings[enableMultiWindows]", true);
		     
		     if (systemOS.contains("win")) {
		    	 capability.setCapability("chromedriverExecutable", "C:\\chromedriver_win32\\chromedriver.exe"); //win
		     } else if (systemOS.contains("mac")) {
		    	 //capability.setCapability("chromedriverExecutable", "/opt/homebrew/bin/chromedriver");
		    	 capability.setCapability("chromedriverExecutable", "/Users/1112049/chromedriver_mac_arm64/chromedriver");
		     }
		        
		    
		}
		 
		 if(AppName.equalsIgnoreCase("CHIPS")) {			
			 capability.setCapability("deviceName","Android");
			 capability.setCapability("automationName", "UIAutomator2");
		     capability.setCapability("platformName","Android");
			 capability.setCapability("udid",Device); //여러 디바이스가 설치된 경우, 테스트하고자 하는 디바이스를 선택 
			 
			 capability.setCapability("appPackage", "com.skt.aidev.nugufriends");
			 capability.setCapability("appActivity", "com.skt.aidev.nugufriends.ui.home.HomeActivity");

			 capability.setCapability("newCommandTimeout",60 * 5);
			 capability.setCapability("adbExecTimeout", 10000);
			 
		     ChromeOptions chromeOptions = new ChromeOptions();
		     chromeOptions.setExperimentalOption("w3c", true);
		     capability.setCapability("chromedriverArgs", chromeOptions); 
		     
		     capability.setCapability("recreateChromeDriverSessions", false); 
		     capability.setCapability("extractChromeAndroidPackageFromContextName", true); 
		     
		     capability.setCapability("focused", true); 
		     
		     capability.setCapability("showChromedriverLog", true);
			 capability.setCapability("appium:settings[enableMultiWindows]", true);
		     capability.setCapability("chromedriverExecutable", "C:\\chromedriver_win32\\chromedriver.exe"); 
		        
		    
		}

		if(AppName.equalsIgnoreCase("App_Installer")) {

			String systemOS = System.getProperty("os.name").toLowerCase();

			capability.setCapability("deviceName","Android");
			capability.setCapability("automationName", "UIAutomator2");
			capability.setCapability("platformName","Android");
			capability.setCapability("udid",Device); //여러 디바이스가 설치된 경우, 테스트하고자 하는 디바이스를 선택

			capability.setCapability("appPackage", "dev.firebase.appdistribution");
			capability.setCapability("appActivity", "dev.firebase.appdistribution.main.MainActivity");

			capability.setCapability("noReset",true);
			capability.setCapability("newCommandTimeout",60 * 5);

			ChromeOptions chromeOptions = new ChromeOptions();
			chromeOptions.setExperimentalOption("w3c", true);
			capability.setCapability("chromedriverArgs", chromeOptions);

			capability.setCapability("recreateChromeDriverSessions", false);
			capability.setCapability("extractChromeAndroidPackageFromContextName", true);

			//noReset이 true로 처리되어 있으면 아래 명령어가 동작하지 않는다.
			capability.setCapability("autoGrantPermissions", "true");

			capability.setCapability("focused", true);

			capability.setCapability("showChromedriverLog", true);
			capability.setCapability("appium:settings[enableMultiWindows]", true);
			if (systemOS.contains("win")) {
				capability.setCapability("chromedriverExecutable", "C:\\chromedriver_win32\\chromedriver.exe"); //win
			} else if (systemOS.contains("mac")) {
				//capability.setCapability("chromedriverExecutable", "/opt/homebrew/bin/chromedriver");
				capability.setCapability("chromedriverExecutable", "/Users/1112049/chromedriver_mac_arm64/chromedriver");
			}


		}
		
		
		return capability;		
	}		
}
