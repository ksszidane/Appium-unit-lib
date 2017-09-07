package unit;

import java.io.IOException;
import java.net.MalformedURLException;

import org.openqa.selenium.Platform;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

public class Capabilities {
	
	public static DesiredCapabilities gridSetUp (String browser) throws IOException {		
		DesiredCapabilities capability = null;	

		//****************** firefox Capabilities
		 if(browser.equalsIgnoreCase("firefox")) {			
			 FirefoxProfile profile = new FirefoxProfile();
			 profile.setEnableNativeEvents(false);
			 capability = DesiredCapabilities.firefox();			 
			 capability.setCapability(FirefoxDriver.PROFILE, profile);
			 capability.setBrowserName("firefox");
			 capability.setPlatform(Platform.ANY);
			 capability.setCapability(CapabilityType.TAKES_SCREENSHOT, true);
			 }
		
		 //****************** IE6 Capabilities
		 if(browser.equalsIgnoreCase("IE6")) {
			 capability = DesiredCapabilities.internetExplorer();
			 capability.setBrowserName("internet explorer");
			 capability.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			 capability.setPlatform(Platform.ANY);
			 capability.setCapability(CapabilityType.TAKES_SCREENSHOT, true);
			 capability.setVersion("6");
			 }

		 //****************** IE7 Capabilities
		 if(browser.equalsIgnoreCase("IE7")) {
			 capability = DesiredCapabilities.internetExplorer();
			 capability.setBrowserName("internet explorer");
			 capability.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			 capability.setPlatform(Platform.ANY);			 
			 capability.setVersion("7");			
			 capability.setCapability(CapabilityType.TAKES_SCREENSHOT, true);
			 }
		
		 //****************** IE8 Capabilities
		if(browser.equalsIgnoreCase("IE8")) {
			capability = DesiredCapabilities.internetExplorer();
			capability.setBrowserName("internet explorer");
			capability.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true); 
			capability.setPlatform(Platform.ANY);			
			capability.setVersion("8");
			capability.setCapability(CapabilityType.TAKES_SCREENSHOT, true);
			}
		
		//****************** IE9 Capabilities
		if(browser.equalsIgnoreCase("IE9")) {
			capability = DesiredCapabilities.internetExplorer();
			capability.setBrowserName("internet explorer");
			capability.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			capability.setPlatform(Platform.ANY);			
			capability.setVersion("9");
			capability.setCapability(CapabilityType.TAKES_SCREENSHOT, true);
			}
		
		//****************** IE10 Capabilities
		if(browser.equalsIgnoreCase("IE10")) {
			capability = DesiredCapabilities.internetExplorer();
			capability.setBrowserName("internet explorer");
			capability.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			capability.setPlatform(Platform.ANY);			
			capability.setVersion("10");
			capability.setCapability(CapabilityType.TAKES_SCREENSHOT, true);
			}
		
		//****************** IE11 Capabilities
		if(browser.equalsIgnoreCase("IE11")) {
			capability = DesiredCapabilities.internetExplorer();
			capability.setBrowserName("internet explorer");
			capability.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			capability.setCapability("ie.ensureCleanSession", true); // Remote:
			
			//capability.setCapability(InternetExplorerDriver.NATIVE_EVENTS, false);
			
			capability.setJavascriptEnabled(true);
			capability.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
			capability.setCapability(InternetExplorerDriver.ENABLE_ELEMENT_CACHE_CLEANUP, true);
			capability.setPlatform(Platform.ANY);			
			capability.setVersion("11");
			capability.setCapability(CapabilityType.TAKES_SCREENSHOT, true);
			}
				
		//****************** Chrome Capabilities
		if(browser.equalsIgnoreCase("Chrome")) {
			capability = DesiredCapabilities.chrome();
			capability.setJavascriptEnabled(true);
			capability.setBrowserName("chrome");
			capability.setPlatform(Platform.ANY);
			capability.setCapability(CapabilityType.TAKES_SCREENSHOT, true);
			}
		
		if(browser.equalsIgnoreCase("Safari")) {
			capability = DesiredCapabilities.chrome();
			capability.setBrowserName("safari");
			capability.setPlatform(Platform.ANY);
			capability.setCapability(CapabilityType.TAKES_SCREENSHOT, true);
			}
		
		if(browser.equalsIgnoreCase("Edge")) {
			capability = DesiredCapabilities.chrome();
			capability.setBrowserName("edge");
			capability.setPlatform(Platform.ANY);
			capability.setCapability(CapabilityType.TAKES_SCREENSHOT, true);
			}
		
		// ****************** Chrome User Agent Capabilities
		if (browser.equalsIgnoreCase("Chrome Android UA")) {

			ChromeOptions options = new ChromeOptions();
			options.addArguments("--user-agent=Mozilla/5.0 (Linux; Android 4.0.4; Galaxy Nexus Build/IMM76B) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.133 Mobile Safari/535.19");

			capability = DesiredCapabilities.chrome();
			capability.setBrowserName("chrome");
			capability.setPlatform(Platform.ANY);
			capability.setVersion("android");
			capability.setCapability(ChromeOptions.CAPABILITY, options);
			capability.setCapability(CapabilityType.TAKES_SCREENSHOT, true);
		}
				
		if (browser.equalsIgnoreCase("Chrome iPhone UA")) {
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--user-agent=Mozilla/5.0 (iPhone; CPU iPhone OS 6_1_4 like Mac OS X) AppleWebKit/536.26 (KHTML, like Gecko) CriOS/27.0.1453.10 Mobile/10B350 Safari/8536.25");

			capability = DesiredCapabilities.chrome();
			capability.setBrowserName("chrome");
			capability.setPlatform(Platform.ANY);
			capability.setVersion("iPhone");
			capability.setCapability(ChromeOptions.CAPABILITY, options);
			capability.setCapability(CapabilityType.TAKES_SCREENSHOT, true);
		}
		
		return capability;		
	}	
}
