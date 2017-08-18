package unit;

//import static org.junit.Assert.*;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.image.BufferedImage;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;



import java.util.concurrent.TimeUnit;

import static org.testng.Assert.fail;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.Rotatable;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.DriverCommand;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;



//import ru.yandex.qatools.ashot.AShot;
//import ru.yandex.qatools.ashot.Screenshot;
//import ru.yandex.qatools.ashot.screentaker.ViewportPastingStrategy;

import com.google.common.collect.ImmutableMap;


public class Utilities extends RemoteWebDriver implements TakesScreenshot, Rotatable { 
	
	// RemoteWebDriver Hub Address
	public static String hubAddress;
	
	public String mainWindowHandle = null;
	public String lastWindowHandle = null;
	public String newWindowHandle = null;
	
	public String beforeFilePath = null;
	public String beforeFilePath2 = null;
	
	

	public String type = null;
	
	public String deviceName = null;
	public String modelName = null;
	
	public String osVersion = null;
	public String udid = null;
	
	public int winX, winY;
	public int centerX, centerY;
	public int avoidTop, avoidBottom;
	
	//protected final double WAIT_TICK = 0.5; // by second
	protected final int TIME_OUT_SEC = 10; // by second
	protected final int WAIT_SEC = 1;
	protected final int MIN_WAIT_TIME = 1;
	protected final int MAX_WAIT_TIME = 20;
	
	//protected final String WAIT_TIME_OUT = "15000";
	private final int PAGE_LOAD_TIME_OUT = 30;
	private final int MAX_TRY_COUNT = 10;
	
	
	
	private final String MAXIMIZE_BROWSER_WINDOW = 
		"if (window.screen) {window.moveTo(0, 0);window.resizeTo(window.screen.availWidth,window.screen.availHeight);};";

	
	public Utilities (DesiredCapabilities capability) throws MalformedURLException {
		this(hubAddress, capability);
	}
	
	public Utilities (String url, DesiredCapabilities capability) throws MalformedURLException { 
		super (new URL (url), capability);
	}
	
	public <X> X getScreenshotAs(OutputType<X> target) throws WebDriverException {

		X targetShot = null;
		targetShot = target.convertFromBase64Png(execute(DriverCommand.SCREENSHOT).getValue().toString());
		
		return targetShot;
		/*
		if ((Boolean) getCapabilities().getCapability(CapabilityType.TAKES_SCREENSHOT)) { 
			  return target.convertFromBase64Png(execute(DriverCommand.SCREENSHOT).getValue().toString());
		} 
		return null;
		*/
	}
	
	public <X> X getScreenshotAsCapa(OutputType<X> target) throws WebDriverException {

		  if ((Boolean) getCapabilities().getCapability(CapabilityType.TAKES_SCREENSHOT)) { 
			  
			  return target.convertFromBase64Png(execute(DriverCommand.SCREENSHOT).getValue().toString());
		} 
		  return null;
	}
	
	public void CaptureScreen (ITestResult result) throws Exception {
		
		Date date = new Date();
		File directory = new File (".");
		DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd__HH_mm_ss");
		
		File screenshot = getScreenshotAs(OutputType.FILE);
		String FileNamePath = "/ScreenShots/"+ getCapabilities().getBrowserName() + getCapabilities().getVersion() + "/" + dateFormat.format(date)+"_"+ "finish.png";
		String FileNamePath2 = directory.getCanonicalPath()+ FileNamePath;
						
		try {  			
			FileUtils.copyFile(screenshot, new File(FileNamePath2));
			} catch (IOException e1) {
				printLog("After ScreenShot Error : " + FileNamePath2);      
				e1.printStackTrace();
			} catch (Exception e) {
				printLog ("** After ScreenShot catch Excetion");
				//e.printStackTrace();
			}
		//filename is the name of file where screenshot is copied 
		Reporter.setCurrentTestResult(result);
		

		//Reporter.log("<a href=\"" + beforeFilePath + "\">Start</a>&nbsp; ");
		//Reporter.log("<a href=\"" + FileNamePath + "\">Finish</a>");
	}
	
	/**
	 * Fail시 스크린 캡쳐를 동작하는 메소드
	 * 
	 */
	public void FailCaptureScreen (ITestResult result) throws Exception {
		
		Date date = new Date();
		File directory = new File (".");
		DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd__HH_mm_ss");
		
		File screenshot = getScreenshotAs(OutputType.FILE);
		String FileNamePath = "/ScreenShots/"+ getCapabilities().getBrowserName() + getCapabilities().getVersion() + "/" + dateFormat.format(date)+"_"+ "Fail Screenshot.png";
		String FileNamePath2 = directory.getCanonicalPath()+ FileNamePath;
						
		try {  			
			FileUtils.copyFile(screenshot, new File(FileNamePath2));
			} catch (IOException e1) {
				printLog("After ScreenShot Error : " + FileNamePath2);      
				e1.printStackTrace();
			} catch (Exception e) {
				printLog ("** After ScreenShot catch Excetion");
				//e.printStackTrace();
			}
		//filename is the name of file where screenshot is copied 
		Reporter.setCurrentTestResult(result);
		//Reporter.log("<a href=\"" + "..\\ws\\target" + beforeFilePath + "\" target=\"_blank\">Start</a>&nbsp;");  
		Reporter.log("<a href=\"" + "..\\ws\\target" + FileNamePath + "\" target=\"_blank\">Fail Screenshot</a>");  

	}
	
	/**
	 * 테스트 시작시 스크린 캡쳐를 동작하는 메소드
	 * 
	 */
	public void StartCaptureScreen () throws Exception {
		
		Date date = new Date();
		File directory = new File (".");
		DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd__HH_mm_ss");
				
		File screenshot = getScreenshotAs(OutputType.FILE);
		//String FileNamePath = directory.getCanonicalPath()+ "\\ScreenShots\\"+ getCapabilities().getBrowserName() + getCapabilities().getVersion() + "\\" + dateFormat.format(date)+"_"+ ".png";
		//beforeFilePath = directory.getCanonicalPath()+ "\\ScreenShots\\"+ getCapabilities().getBrowserName() + getCapabilities().getVersion() + "\\" + dateFormat.format(date)+"_"+ ".png";
		beforeFilePath = "/ScreenShots/"+ getCapabilities().getBrowserName() + getCapabilities().getVersion() + "/" + dateFormat.format(date)+"_"+ "start.png";
		beforeFilePath2 = directory.getCanonicalPath()+ beforeFilePath;
					 			
		try {  			
			FileUtils.copyFile(screenshot, new File(beforeFilePath2));
			} catch (IOException e1) {
				printLog("Before ScreenShot Error : " + beforeFilePath2);
				e1.printStackTrace();
			} catch (Exception e) {
				printLog ("** Before ScreenShot catch Excetion");
				//e.printStackTrace();
			}

		//filename is the name of file where screenshot is copied 
		//Reporter.log("<a href=\"" + FileNamePath + "\">Start Screenshot </a>");
	} 
	
	/**
	 * 단말기별 화면 해상도 저장 scroll & swipe 동작 시 활용
	 */
	private void getWindowSize() {
		Dimension winSize = manage().window().getSize();

		winX = winSize.getWidth();
		winY = winSize.getHeight();

		centerX = (int) (winX * 0.5);
		centerY = (int) (winY * 0.5);
	}


	
	
	/**
	 * @param int
	 *            리턴받을 스트링의 길이
	 * @param boolean
	 *            특수문자 포함 여부
	 * @return String 랜덤문자들로 생성된
	 */
	public String getRandomString(int length) {

		final String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJLMNOPQRSTUVWXYZ1234567890!@#$%^&*()_+";

		StringBuilder result = new StringBuilder();

		while (length > 0) {
			Random rand = new Random();
			result.append(characters.charAt(rand.nextInt(characters.length())));
			length--;
		}

		return result.toString();
	}
	
	/** 
	 * 무작위 숫자 생성
	 */
	public int getRandomNum() {

		return (int)(Math.random() * 1000 + 1);
	}
	
	public int getRandomNum(int min, int max) {

		//return (int)(Math.random() * max + min);
		return (int)(Math.random() * (max - min)) + min;
	}	
	
	public int getRandomPath (By locator) throws Exception {
	
		int max = this.getXpathCount(locator);
		return (int)(Math.random() * (max - 1)) + 1;
	}
	
	/**
	 * locator가 노출될 때까지 기다렸다 xpath count를 읽어들이는 메소드
	 * @param locator 링크
	 * @return 읽어들인 xpath count (int)
	 * @throws Exception - Selenium Exception
	 */
	public int getXpathCount(By locator) throws Exception {
	
		List<WebElement> elements = waitForIsAllElementsPresent(locator);
		return elements.size();
	}
	
	public String waitGetCurrentUrl() {
		
		waitForPageToLoad();
		return getCurrentUrl();
	}
	
	/** 
	 * url 링크 접속 후 상태 확인
	 * @param url
	 * @throws Exception 
	 */
	public String isLinkBroken(URL url) throws Exception {
		
		this.getCurrentUrl();
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();

		try {
			connection.connect();
			String response = connection.getResponseMessage();
			connection.disconnect();

			return response;
		}
		catch (Exception exp) {
			return exp.getMessage();
		}
	}
	
	/** 
	 * 모든 링크 검색
	 * @throws Exception 
	 */
	public List<WebElement> findAllLinks() throws Exception {

		List<WebElement> elementList = new ArrayList<WebElement>();
		List<WebElement> finalList = new ArrayList<WebElement>();
		
		elementList = findElements(By.tagName("a"));
		elementList.addAll(findElements(By.tagName("img")));
		for (WebElement element : elementList) {

			String href = element.getAttribute("href");
			if ((href != null) && href.contains("http://")) {
	
				finalList.add(element);
			}
		}
		return finalList;
	}
	
	/** 
	 * 모든 링크 검색
	 * @param 링크를 검색 할 상위 element
	 * @throws Exception 
	 * getAttribute가 href안에 http:// or https://로 시작되는 경우만 검색됨
	 */
	public List<WebElement> findAllLinks(By locator) throws Exception {

		List<WebElement> elementList = new ArrayList<WebElement>();
		List<WebElement> finalList = new ArrayList<WebElement>();
		
		elementList = findElements (locator);
		for (WebElement element : elementList) {			
			
			String href = element.getAttribute("href");
			if ((href != null) && href.contains("http://")) {

				finalList.add(element);
			}
			else if ((href != null) && href.contains("https://")) {

				finalList.add(element);
			}
		}
		return finalList;
	}
	
	/**
	 * 페이지 전체의 url 링크의 정상 여부 확인
	 * locator에 href가 있는 경우 isChechedLink 동작
	 * @throws Exception
	 */
	public boolean isBrokenLinkExist() {
		boolean result = true;

		List<WebElement> links = getAllLinks();
		printLog("Total Links Count : " + links.size());

		for (WebElement element : links) {
			try {		
				if (!isCheckedLink(new URL(element.getAttribute("href"))))
					result = false;
			
			} catch (Exception e) {
				printLog("At " + element.getAttribute("innerHTML") + " Exception occured ; " + e.getMessage());
				
				result = false;
			}
		}
		return result;
	}

	/**
	 * 선택한 엘리먼트에 url 링크의 정상 여부 확인
	 * href가 있는 경우 isChechedLink 동작
	 * @throws Exception
	 */
	public boolean isBrokenLinkExist(By locator) {
		boolean result = true;
		
		List<WebElement> links = getAllLinks(locator);
		printLog("Total Links Count : " + links.size());

		for (WebElement element : links) {
			try {		
				if(!isCheckedLink(new URL(element.getAttribute("href"))))
					result= false;
						
			} catch (Exception e) {
				printLog("At " + element.getAttribute("innerHTML") + " Exception occured ; " + e.getMessage());
				
				result = false;
			}
		}

		return result;
	}

	
	/**
	 * url 링크 접속 후 상태 확인
	 * 
	 * @param url
	 * @return boolean HTTPResponseCode가 200인 경우만 true
	 * @throws Exception
	 */
	public boolean isCheckedLink(URL url) {
		
		
		boolean result = true;
		

		try {
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.connect();
			
			printLog("URL: " + url);
			printLog("-> " + connection.getResponseCode() + "-" + connection.getResponseMessage());
			
			connection.disconnect();

			if (connection.getResponseCode() == 404)
				result = false;

		} catch (IOException ioe) {
			printLog("LinkTest Fail");
		}

		return result;
	}
	
	/**
	 * url 링크 접속 후 Code를 리턴 
	 * 
	 * @param url
	 * @return boolean HTTPResponseCode값을 리턴해 준다. 200.400.404.500
	 * @throws Exception
	 */
	public int getStatusCode(String urlAddress) throws Exception {
		
		URL url = new URL(urlAddress);
		
	    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.connect();
		connection.disconnect();
	    
		int code = connection.getResponseCode();
	    
	    return code;
	}
	
	
	
	/** 
	 * x box 노출 확인
	 * @param By.TagName ("img")
	 * @throws Exception 
	 */
	public Boolean isImagePresent(By locator) throws Exception {
	
		Boolean result = true;
		List<WebElement> elementList = new ArrayList<WebElement>();
		
		elementList = findElements (locator);
		printLog ("All images count : " + elementList.size());
		
		for( WebElement element : elementList) {
			
			if (element.isDisplayed()) {
		    
				try {
		    		Boolean ImagePresent = (Boolean) ((JavascriptExecutor) this).executeScript
					("return arguments[0].complete && typeof arguments[0].naturalWidth != \"undefined\" && arguments[0].naturalWidth > 0",element);
		    		
		    		if (!ImagePresent) {
		    			printLog (element.getAttribute("src") + " --> [Not displayed]");
		    			result = false;	    		
		    		} else {
		    			printLog (element.getAttribute("src") + " --> [Displayed]");
		    		}
		    	}

		    	catch(Exception e) {
		    		printLog (e.getMessage());
		    		result = false;
		    	}
			}
		}		
		return result;
	}
	
	/** 
	 * x box 노출 확인
	 * @param By.TagName ("img")
	 * @throws Exception 
	 */
	public boolean isCheckedImage(WebElement element) {
		Boolean ImagePresent = (Boolean) ((JavascriptExecutor) this).executeScript(
				"return arguments[0].complete && typeof arguments[0].naturalWidth != \"undefined\" && arguments[0].naturalWidth > 0",
				element);

		if (!ImagePresent)
			printLog(element.getAttribute("src") + " --> [Not displayed]");
		else {
			printLog(element.getAttribute("src") + " --> [Displayed]");
			
			return true;
		}
		
		return false;
	}
	
	
	
	/** 
	 * 파라미터로 받은 URL로 오픈 후 wait
	 * @param url 주소
	 * @return void
	 */
	public void goTo(String url) {
		navigate().to(url);
		waitForPageToLoad();
	}
	
	
	/** 
	 * "Back" 실행 후 wait
	 * @param null
	 * @return void
	 */
	public void goBackAndWait() {
		navigate().back();
		waitForPageToLoad();
	}
	
	/** 
	 * "Forward" 실행 후 wait
	 * @param null
	 * @return void
	 */
	public void goForwardAndWait() {
		navigate().forward();
		waitForPageToLoad();
	}
	
	/**
	 * 특정 URL로 이동할 때 까지 기다리는 메소드
	 * @param pattern 대기할 특정주소를 지정
	 * @return Element 노출 유무
	 * @throws Exception - Selenium Exception
	 */
	public boolean waitForLocation(String pattern) throws Exception {
		
		for (int second = 0; second <= TIME_OUT_SEC; second += WAIT_SEC) {
		
			if (getCurrentUrl().contains(pattern)) {
				return true;
			}
			sleep(WAIT_SEC);
		}

		fail (pattern + " : 해당 URL을 포함하는 페이지가 " + TIME_OUT_SEC + "초내에 노출되지 않음");
		return false;
	}
	
	/**
	 * 특정 Title을 포함한 페이지로 이동할 때 까지 기다리는 메소드
	 * @param pattern 대기할 특정주소를 지정
	 * @return Element 노출 유무
	 * @throws Exception - Selenium Exception
	 */
	public boolean waitForTitle(String Title) throws Exception {
		
		WebDriverWait wait = new WebDriverWait(this, PAGE_LOAD_TIME_OUT);
		
		if (wait.until(ExpectedConditions.titleContains(Title))) {
			return true;
		}
		fail (Title + " : 해당 Title을 포함하는 페이지가 " + TIME_OUT_SEC + "초내에 노출되지 않음");
		return false;
	}
	
	/**
	 * 특정 새창이 뜰때까지 기다리는 메소드
	 * @throws Exception
	 */
	public boolean waitForNewWindow() throws Exception {

		mainWindowHandle = getWindowHandle();

		for (int second = 0; second <= TIME_OUT_SEC; second += WAIT_SEC) {
		
			Set<String> winHandles = getWindowHandles();
			int winSize = winHandles.size();
			printLog ("오픈 된 윈도우 # : " + winSize);
			
			if (winSize >= 2) {

				for (String Handle : winHandles) {

					switchTo().window(Handle);

					if (!getWindowHandle().equals(mainWindowHandle)) {

						lastWindowHandle = getWindowHandle();
						
						waitForPageToLoad();
						Thread.sleep(1000);
						
						printLog ("새창 URL : " + getCurrentUrl());
						printLog ("새창 Title : " + getTitle());
						return true;
					}
				}
			}
			sleep(WAIT_SEC);
		}
		fail("새창이  " + TIME_OUT_SEC + "초내에 로드되지 않음");
		return false;
	}
	
	/**
	 * 마지막에 열린 창이 뜰때까지 기다리는 메소드
	 * @throws Exception
	 */
	public boolean waitForLastWindow() throws Exception {

		String tempHandle = null;

		for (int second = 0; second <= TIME_OUT_SEC; second += WAIT_SEC) {
			
			Set<String> getHandles = getWindowHandles();
			Iterator<String> winHandles = getHandles.iterator();
			
			int winSize = getHandles.size();
			// System.out.println ("Size : " + winSize);
			
			if (winSize > 2) {
				while (winHandles.hasNext()) {
					tempHandle = winHandles.next();
					switchTo().window(tempHandle);
				}
				
				printLog("마지막 창 : " + getTitle());
				printLog("마지막 창 : " + getCurrentUrl());
				
				return true;
			}
		}
		fail("마지막 창이  " + TIME_OUT_SEC + "초내에 로드되지 않음");
		return false;
	}
	
	/**
	 * 메인윈도우로 포커스 전환 메소드
	 * @throws Exception
	 */
	public void selectMainWindow() throws Exception {
		
		Set<String> getHandles = getWindowHandles();
		Iterator<String> winHandles = getHandles.iterator();
		
		String mainWindow = winHandles.next();
		switchTo().window(mainWindow);
		
		printLog ("메인창 URL : " + getCurrentUrl());
		printLog ("메인창 Title : " + getTitle());
	}
	
	/**
	 * 특정 윈도우로 포커스 전환 메소드
	 * @param 사전 정의해둔 윈도우 핸들어
	 * @throws Exception
	 */
	public void selectWindow(String winHandle) throws Exception {
		
		switchTo().window(winHandle);
	}
	
	/**
	 * 특정 윈도우 close 메소드
	 * @param 사전 정의해둔 윈도우 핸들어
	 * @throws Exception
	 */
	public void closeWindow(String winHandle) throws Exception {
		
		switchTo().window(winHandle).close();
	}
	
	
	/**
	 * 메인 윈도우 외의 모든 오픈 윈도우 close 메소드
	 * @throws Exception
	 */
	public void closeNewWindows() throws Exception {

		String tempHandle = null;
			
		Set<String> getHandles = getWindowHandles();
		Iterator<String> winHandles = getHandles.iterator();
			
		int winSize = getHandles.size();
			
		if (winSize > 2) {
			while (winHandles.hasNext()) {
				
				tempHandle = winHandles.next();
				if (!tempHandle.equals(mainWindowHandle)) {
					switchTo().window(tempHandle).close();	
				}
			}
		}
		switchTo().window(mainWindowHandle); 
	}
	
	/**
	 * 모든 브라우져 종료
	 */
	public void closeAllOpenedBrowser () {
		
		String command = null;
		String browser = getCapabilities().getBrowserName();
		Runtime rt = Runtime.getRuntime();

		if (browser.equals("firefox"))
			command = "taskkill /im firefox.exe /f";
		
		else if (browser.equals("internet explorer"))
			command = "taskkill /im iexplore.exe /f";
		
		else if (browser.equals("chrome"))
			command = "taskkill /im chrome.exe /f";
		
		else
			printLog ("unknown browser type");		
	
		try {
			printLog ("** exec " + command);
			rt.exec(command);
		}
		catch (Exception e) {
			printLog ("** catch Exception");
			e.printStackTrace();
		}
	}
	
	/**
	  * 브라우저 사이즈를 최대화 시키는 메소드
	  * @return void
	  */
	/*public void windowMaximize() {
		executeJavascript (this, MAXIMIZE_BROWSER_WINDOW);
	}*/
	
	/**
	  * 브라우저 사이즈를 최대화 시키는 메소드
	  * @return void
	  */
	public void windowMaximize() {
		manage().window().maximize();
	}
	
	/**
	 * 현재 열려있는 창의 인덱스 수집 후 그 번호에 맞는 창전환
	 * @param 윈도우 인덱스 번호
	 * @throws Exception
	 */
	public void switchToWindwosIndex(int windowIndex) throws Exception {
		
		ArrayList<String> AllWindows = new ArrayList<String>(getWindowHandles());
        switchTo().window(AllWindows.get(windowIndex));
	}
	
	
	
	
	
	public Object executeJavascript(Utilities util, String script) {
		JavascriptExecutor js = (JavascriptExecutor)util;
		return js.executeScript(script);
	}
	
	
	/**
	  * 내용을 입력하는 메소드
	  * @param locator	내용을 입력받을 element 정보
	  * @param inputText locator에 입력할 내용
	  * @return void
	  */
	public void type (By locator, String inputText) throws Exception {
		
		WebElement element = waitForIsElementPresent(locator);
		element.sendKeys(inputText);
	}

	/**
	  * 키보드 key action을 입력하는 메소드
	  * @param locator	내용을 입력받을 element 정보
	  * @param inputText locator에 입력할 key action
	  * @return void
	  */
	public void type (By locator, Keys keyValue) throws Exception {
		
		WebElement element = waitForIsElementPresent(locator);
		element.sendKeys(keyValue);
		
		waitForPageToLoad();
	}
	
	
	/**
	  * 로그를 출력하는 메소드
	  * 메소드를 실행하는 system의 정보를 출력 (platform, browser info)
	  * @param 출력할 메시지
	  * @return void
	  */
	public void printLog(String logs) {
		String browserName = getCapabilities().getBrowserName();
		Platform platForm = getCapabilities().getPlatform();
		String verSion = getCapabilities().getVersion();
				
		System.out.println ("[" + platForm + "/" + browserName + "" +  verSion + "]" + " " + logs);
		//Reporter.log("[" + platForm + "/" + browserName + "" +  verSion + "]" + " " + logs + "<br>");
		//System.out.println (logs);
		Reporter.log(logs + "<br>");
	}
	
	/**
	  * 물리적인 keyboard 입력을 처리하는 메소드
	  * @param locator	물리적 처리를 기다리는 element
	  * @param value	Press할 키보드 값
	  * @return void
	  */
	public void pressKeys(By locator, Keys value) throws Exception {
		
		WebElement element = null;
		element = waitForIsElementPresent(locator);
		//WebElement element = locator.findElement((SearchContext) this);
		
		element.sendKeys(value);
	}
	
	
	
	
	 /**
	  * DragAndDrop을 위한 메소드
	  * @param sourceLocate 드래그 실행할 element
	  * @param tagetLocate 드랍 실행할 element
	  * @return void
	  */
	public void dragAndDrop (By sourceLocate, By tagetLocate) throws Exception {
		
		WebElement source_element = null;
		WebElement target_element = null;
	    Actions action = new Actions (this);
	    
	    source_element = waitForIsElementPresent (sourceLocate);
	    target_element = waitForIsElementPresent (tagetLocate);
	    
	    action.dragAndDrop(source_element, target_element).perform();  
	 }
	
	/**
	  * MouseOver을 위한 메소드
	  * @param locator 링크
	  * @throws Exception - Selenium Exception
	  */
	public void waitAndMouseOver (By locator) throws Exception {
		
		WebElement target_element = null;
		int target_width = 0;
		int target_height = 0;
		 
	    Actions action = new Actions (this);
	    
	    target_element = waitForIsElementPresent(locator);
	    
	    //target_width = (target_element.getSize().width)/2;
	    //target_height = (target_element.getSize().height)/2;
	    
	   //action.moveToElement(target_element, target_width, target_height).perform();
	    action.moveToElement(target_element).build().perform();
	    //waitForPageToLoad();
	 }
	
	/**
	  * MouseOver을 위한 메소드
	  * @param locator 링크
	  * @throws Exception - Selenium Exception
	  */
	public void mouseOver (By locator) throws Exception {
		
		int target_width = 0;
		int target_height = 0;
		 
	    Actions action = new Actions (this);	    
		WebElement element = waitForIsElementPresent(locator);
	    
	    target_width = (element.getSize().width)/2;
	    target_height = (element.getSize().height)/2;
	    
	    action.moveToElement(element, target_width, target_height).perform();	    
	    waitForPageToLoad();
	 }
	
	/**
	  * MouseOver을 위한 메소드
	  * @param locator 링크
	  * @param locator의 off set 정보 (가로.세로)
	  * @throws Exception - Selenium Exception
	  */
	public void mouseOver (By locator, int offset_X, int offset_Y) throws Exception {
		
	    Actions action = new Actions (this);	    
		WebElement element = waitForIsElementPresent(locator);
	
	    action.moveToElement(element, offset_X, offset_Y).perform();	    
	    waitForPageToLoad();
	 }
	/**
	  * 파일을 선택하는 메소드 
	  * @param filePath 파일경로
	  */
	public void selectFile (String filePath) throws Exception {
		
	    StringSelection path = new StringSelection(filePath);
	    Toolkit.getDefaultToolkit().getSystemClipboard().setContents(path, null);

	    Thread.sleep(4000);//Sleep time to detect the window dialog box

	    //Pasting the path in the File name field
	    Robot robot = new Robot();
	    robot.keyPress(KeyEvent.VK_CONTROL);
	    robot.keyPress(KeyEvent.VK_V);
	    robot.keyRelease(KeyEvent.VK_V);
	    robot.keyRelease(KeyEvent.VK_CONTROL);

	    //To click the Open button so as to upload file
	    robot.keyPress(KeyEvent.VK_ENTER);
	    robot.keyRelease(KeyEvent.VK_ENTER);
	}
	
	
	/** 
	 * locator 클릭 후 기다리는 메소드
	 * @param locator 링크
	 * @throws Exception 
	 */
	
	public boolean clickAndWait(By locator) throws Exception {
	    
		int tryCount = 0;
		WebElement element = null;
		WebDriverWait wait = (WebDriverWait) new WebDriverWait(this, PAGE_LOAD_TIME_OUT);
		
		while (tryCount < MAX_TRY_COUNT) {
			try {
				element = wait.until(ExpectedConditions.elementToBeClickable(locator));
				if (element != null && element.isDisplayed() && element.isEnabled()) {
					element.click();
					waitForPageToLoad();
					return true;
				}
			}
			catch (StaleElementReferenceException e){
				printLog ("** Catch StaleElement Exception : #" + tryCount );
				Thread.sleep(1000);
				tryCount = tryCount + 1;
			}
		}
		fail(locator + " : 해당 엘리먼트가 " + TIME_OUT_SEC + "초내에 노출되지 않음");
		return false;
	}
	
	/** 
	 * locator 클릭 후 기다리지 않은 메소드 (wait alert)
	 * @param locator 링크
	 * @throws Exception 
	 */
	public boolean waitAndClick(By locator) throws Exception {
	    
		int tryCount = 0;
		WebElement element = null;
		WebDriverWait wait = (WebDriverWait) new WebDriverWait(this, PAGE_LOAD_TIME_OUT);
		
		while (tryCount < MAX_TRY_COUNT) {
			try {
				element = wait.until(ExpectedConditions.elementToBeClickable(locator));
				if (element != null && element.isDisplayed() && element.isEnabled()) {
					element.click();
					return true;
				}
			}
			catch (StaleElementReferenceException e){
				printLog ("** Catch StaleElement Exception : #" + tryCount );
				Thread.sleep(1000);
				tryCount = tryCount + 1;
			}
		}
		fail(locator + " : 해당 엘리먼트가 " + TIME_OUT_SEC + "초내에 노출되지 않음");
		return false;
	}

	
	
	/**
	 * Element가 존재할때까지 대기하는 메소드
	 * @param locator 존재 확인 할 Element를 지정
	 * @return locator에 존재하는 WebElement List(element, null)
	 * @throws Exception - Selenium Exception
	 */
	public List<WebElement> waitForIsAllElementsPresent (By locator) throws InterruptedException {
		
		int tryCnt = 0;
		WebDriverWait wait = (WebDriverWait) new WebDriverWait(this, PAGE_LOAD_TIME_OUT);

		while (tryCnt < MAX_TRY_COUNT) {
			try {
				List<WebElement> elements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
				return elements;

			} catch (StaleElementReferenceException e) {
				printLog("** Catch StaleElement Exception : #" + tryCnt);
				Thread.sleep(1000);
				tryCnt = tryCnt + 1;
				
			} catch (Exception e) {
				printLog (e.getMessage());
				return null;
			}
		}
		fail (locator + " : 해당 엘리먼트가 " + TIME_OUT_SEC + "초내에 노출되지 않음");
		return null;
	}
	
	
	/**
	 * Element가 존재할때까지 대기하는 메소드
	 * @param locator 존재 확인 할 Element를 지정
	 * @return locator에 존재하는 WebElement List(element, null)
	 * @throws Exception - Selenium Exception
	 */
	public WebElement waitForIsElementPresent (By locator) throws InterruptedException {
		
		int tryCount = 0;
		WebElement element = null;
		WebDriverWait wait = (WebDriverWait) new WebDriverWait(this, PAGE_LOAD_TIME_OUT);
		
		while (tryCount < MAX_TRY_COUNT) {
			try {
				element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
				if (element != null && element.isEnabled() && element.isDisplayed()){ 
					return element;
				}
			}
			catch (StaleElementReferenceException e){
				printLog ("** Catch StaleElement Exception : #" + tryCount );
				Thread.sleep(1000);
				tryCount = tryCount + 1;
			}
		}
		fail(locator + " : 해당 엘리먼트가 " + TIME_OUT_SEC + "초내에 노출되지 않음");
		return null;
	}
	
	
	
	


	/**
	 * Element가 존재하는지 확인하는 메소드
	 * @param locator 존재 확인 할 Element를 지정
	 * @return locator에 존재하는 WebElement (element, null)
	 * @throws Exception - Selenium Exception
	 */
	public WebElement isElementPresent(By locator) {
		
		WebElement result = null;
		
		try {
			
			WebElement element = locator.findElement((SearchContext) this);
			if (element.isDisplayed()) {
				result = element;
			}
			
			/*
			if (locator.findElement((SearchContext) this).isDisplayed()) {
				result = locator.findElement((SearchContext) this);
			}*/
			
		}
		catch (NoSuchElementException e) { 
			return null;
		}
		return result;
	}
	
	
	
	
		
	
	/**
	 * Element가 visible 될 때까지 기다리는 메소드
	 * @param locator 대기할 Element를 지정
	 * @return Element visible 유무
	 * @throws Exception - Selenium Exception
	 */
	/*
	public boolean waitForIsVisible(By locator) throws Exception {
		
		WebElement element = null;
		WebDriverWait wait = (WebDriverWait) new WebDriverWait(this, PAGE_LOAD_TIME_OUT).ignoring(StaleElementReferenceException.class);
		element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		
		for (int second = 0; second <= TIME_OUT_SEC; second += WAIT_SEC) {
			
			if (element.isDisplayed()) 
				return true;

			sleep(WAIT_SEC);
		}

		fail(locator + " : 해당 엘리먼트가 " + TIME_OUT_SEC + "초내에 visible 되지 않음");
		return false;
	}*/
	
	/**
	 * Element가 visible 될 때까지 기다리는 메소드
	 * @param locator 대기할 Element를 지정
	 * @return Element visible 유무
	 * @throws Exception - Selenium Exception
	 */
	public boolean waitForIsVisible(By locator) throws Exception {
		
		WebDriverWait wait = (WebDriverWait) new WebDriverWait(this, PAGE_LOAD_TIME_OUT);
		//WebDriverWait wait = (WebDriverWait) new WebDriverWait(this, PAGE_LOAD_TIME_OUT).ignoring(StaleElementReferenceException.class);

		try {
			WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
			element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
			
			if (element.isDisplayed()) {
				return true;
			}
		}catch (Exception e){
			printLog (e.getMessage());
			return false;
		}
		
		fail (locator + " : 해당 엘리먼트가 " + TIME_OUT_SEC + "초내에 visible 되지 않음");
		return false;
	}
	
	/*
	public boolean waitForIsNotVisible(By locator) throws Exception {
		
		WebElement element = null;
		
		for (int second = 0; second <= TIME_OUT_SEC; second += WAIT_SEC) {
			
			element = isElementPresent(locator);
			
			if (element == null || !element.isDisplayed())
				return true;
						
			sleep(WAIT_SEC);
		}

		fail(locator + " : 해당 엘리먼트가 " + TIME_OUT_SEC + "초내에 사라지지 않음");
		//System.out.println(locator + " : 해당 엘리먼트가 " + TIME_OUT_SEC + "초내에 사라지지 않음");
		return false;
	} */
	
	
	/**
	 * Element가 invisible 될 때까지 기다리는 메소드
	 * @param locator 대기할 Element를 지정
	 * @return Element invisible 유무
	 * @throws Exception - Selenium Exception
	 */
	public boolean waitForIsNotVisible(By locator) throws Exception {
		
		WebDriverWait wait = (WebDriverWait) new WebDriverWait(this, PAGE_LOAD_TIME_OUT);
		//WebDriverWait wait = (WebDriverWait) new WebDriverWait(this, PAGE_LOAD_TIME_OUT).ignoring(StaleElementReferenceException.class);

		try {
			//wait.until(ExpectedConditions.presenceOfElementLocated(locator));
			if (wait.until(ExpectedConditions.invisibilityOfElementLocated(locator))) {
				return true;
			}
		} catch (Exception e){
			printLog (e.getMessage());
			return false;
		}
		
		fail (locator + " : 해당 엘리먼트가 " + TIME_OUT_SEC + "초내에 invisible 되지 않음");
		return false;
	}
	
	/**
	 * searchText 나타날 때까지 기다리는 메소드
	 * @param searchText 나타날때까지 기다릴 Text
	 * @return Text Present 유무
	 * @throws Exception - Selenium Exception
	 */
	public boolean waitForIsTextPresent (String searchText) throws Exception {
		
		//if (waitForIsElementPresent(By.xpath("//*[contains(.,'"+searchText+"')]")) != null) {
		if (waitForIsVisible(By.xpath("//*[contains(.,'"+searchText+"')]"))) {
			return true;
		}

		fail (searchText + " : 해당 Text가 " + TIME_OUT_SEC + "초내에 나타나지 않음");
		return false;
	}
	
	
	
	
	/** 
	 * locator 클릭 후 기다리는 메소드
	 * @param locator 링크
	 * @throws Exception 
	 */
	public boolean click(By locator) throws Exception {
	    
		int tryCnt = 0;
		WebElement element = null;
		WebDriverWait wait = (WebDriverWait) new WebDriverWait(this, PAGE_LOAD_TIME_OUT);
		
		while (tryCnt < MAX_TRY_COUNT) {
			try {
				element = wait.until(ExpectedConditions.elementToBeClickable(locator));
				if (element != null && element.isDisplayed() && element.isEnabled()) {
					
					//highlightElement (element);
					element.click();
					waitForPageToLoad();
					
					return true;
				}
			}
			catch (StaleElementReferenceException e){
				printLog ("** Catch StaleElement Exception : #" + tryCnt );
				Thread.sleep(1000);
				tryCnt = tryCnt + 1;
			}
			catch (Exception e) {
				printLog (e.getMessage());
				fail (locator + " : 해당 엘리먼트를 찾지 못함");
				return false;
			}
		}
		fail (locator + " : 해당 엘리먼트가 " + TIME_OUT_SEC + "초내에 노출되지 않음");
		return false;
	}
	
	
	/** 
	 * locator 클릭 후 기다리는 메소드
	 * @param locator 링크
	 * @throws Exception 
	 */
	public boolean clickAndNoWait(By locator) throws Exception {
	    
		int tryCnt = 0;
		WebElement element = null;
		WebDriverWait wait = (WebDriverWait) new WebDriverWait(this, PAGE_LOAD_TIME_OUT);
		
		while (tryCnt < MAX_TRY_COUNT) {
			try {
				element = wait.until(ExpectedConditions.elementToBeClickable(locator));
				if (element != null && element.isDisplayed() && element.isEnabled()) {
					
					//highlightElement (element);
					element.click();					
					return true;
				}
			}
			catch (StaleElementReferenceException e){
				printLog ("** Catch StaleElement Exception : #" + tryCnt );
				Thread.sleep(1000);
				tryCnt = tryCnt + 1;
			}
			catch (Exception e) {
				printLog (e.getMessage());
				fail (locator + " : 해당 엘리먼트를 찾지 못함");
				return false;
			}
		}
		fail (locator + " : 해당 엘리먼트가 " + TIME_OUT_SEC + "초내에 노출되지 않음");
		return false;
	}
	
	
	/**
	 * sleepSec 만큼 sleep
	 * @param sleepSec	초 단위 대기할 시간 (int, double)
	 * @throws Exception - Exception
	 */
	
	public void sleep(int sleepSec) throws Exception {
		try {
			Thread.sleep(sleepSec*1000);
		} catch (InterruptedException ie) {
			printLog("InterruptedException");
		}
	}
	
	
	/**
	 * 현재 날짜 및 시간 정보 [yyyy.MM.dd_HH:mm:ss]를 가져오는 메소드
	 * 
	 * @return date [yyyy.MM.dd_HH:mm:ss]
	 * @throws Exception
	 */
	public String getDate() {
		
		Calendar cal = Calendar.getInstance();
		//SimpleDateFormat form = new SimpleDateFormat("yyyy.MM.dd-");
		SimpleDateFormat form = new SimpleDateFormat("[yyyy.MM.dd_HH:mm:ss] ");
		return form.format(cal.getTime());	
	}
	
	public String getTime() {
		
		Calendar cal = Calendar.getInstance();
		//SimpleDateFormat form = new SimpleDateFormat("yyyy.MM.dd-");
		SimpleDateFormat form = new SimpleDateFormat("yyyyMMddHHmmss");
		
		return form.format(cal.getTime());	
	}

	/**
	 * 현재 날짜 및 시간 정보 [yyMMdd]를 가져오는 메소드
	 * 
	 * @return date [yyMMdd]
	 * @throws Exception
	 */
	public String getSimpleDate() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat form = new SimpleDateFormat("yyMMdd");
		return form.format(cal.getTime());
	}
	
	/**
	 * locator가 노출될 때까지 기다렸다 text를 읽어들이는 메소드
	 * @param locator 링크
	 * @return 읽어들인 text (string)
	 * @throws Exception - Selenium Exception
	 */
	public String getText(By locator) throws Exception {
		
		WebElement element = waitForIsElementPresent(locator);
		return element.getText();
		
	}
	/**
	 * locator가 노출될 때까지 기다렸다 Attribute를 읽어들이는 메소드
	 * @param locator 링크
	 * @return 읽어들인 Attribute의 Vaule
	 * @throws Exception - Selenium Exception
	 */
	public String getAttribute(By locator, String Attribute) throws Exception {
		
		WebElement element = waitForIsElementPresent(locator);
		return element.getAttribute(Attribute);
		
	}
	
	
	/*
	public String getText(By locator) throws Exception {
	    
		int tryCnt = 0;
		String text = null;
		WebElement element = null;
		WebDriverWait wait = (WebDriverWait) new WebDriverWait(this, PAGE_LOAD_TIME_OUT);
		
		while (tryCnt < MAX_TRY_COUNT) {
			try {
				element = wait.until(ExpectedConditions.elementToBeClickable(locator));
				if (element != null && element.isDisplayed() && element.isEnabled()) {
					
					//highlightElement (element);
					text = element.getText();
					
					return text;
				}
			}
			catch (StaleElementReferenceException e){
				printLog ("** Catch StaleElement Exception : #" + tryCnt );
				Thread.sleep(1000);
				tryCnt = tryCnt + 1;
			}
			catch (Exception e) {
				printLog (e.getMessage());
				fail (locator + " : 해당 엘리먼트를 찾지 못함");
				return null;
			}
		}
		fail (locator + " : 해당 엘리먼트가 " + TIME_OUT_SEC + "초내에 노출되지 않음");
		return null;
	}
	*/
	

	
	
	/**
	 * 페이지의 load complete 대기
	 * @throws Exception - Exception
	 */
	public void waitForPageToLoad() {
		try {
			//Thread.sleep(500);
			waitForPageLoaded();
			waitForAjaxLoaded(this);
		} catch (Exception e) {
			printLog (e.getMessage());
		}
	}
		
	/**
	 * 페이지의 load complete 대기
	 * @throws Exception - Exception
	 */
	public void waitForPageLoaded() { 

		ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() { 
        	public Boolean apply(WebDriver driver) { 
        		boolean readyState =  ((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete");
        		
        		return readyState;
        	} 
        };
         
        Wait<WebDriver> wait = new WebDriverWait(this, PAGE_LOAD_TIME_OUT).ignoring(StaleElementReferenceException.class);
        //Wait<WebDriver> wait = new WebDriverWait(this, PAGE_LOAD_TIME_OUT).ignoring(StaleElementReferenceException.class).ignoring(UnhandledAlertException.class);
        wait.until(expectation);
	} 
		
	/**
	 * 페이지 내의 ajax load complete 대기
	 * @throws Exception - Exception
	 */
	public void waitForAjaxLoaded (WebDriver driver){
	
		ElementLocatorFactory element = new AjaxElementLocatorFactory(driver, PAGE_LOAD_TIME_OUT);
		PageFactory.initElements(element, this);
	}
		
	/*
	public void waitForPageToLoad(int loopCount) {
		for (int count = 0; count < loopCount; count ++) {
			waitForPageToLoad();
		}
	}
	*/
	
	
	/*
	public void waitForPageLoaded(WebDriver driver) { 
		int time = 30;    
		WebDriverWait wait = new WebDriverWait(driver, time);
		ExpectedCondition<Boolean> pageLoadCondition = new ExpectedCondition<Boolean>() {
		   public Boolean apply(WebDriver driver) {
		      return ((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete");
		    }
		};
		wait.until(pageLoadCondition);
		
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		driver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);
	}
	*/	
	
	public void waitForPageLoaded(WebDriver driver) {
	     int i = 1;
	     for(i=1;i<=100;i++) {
	  
	    	 String Title = driver.getTitle();
	          if (driver.getTitle().equals(Title)) {
	        	  	//driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
	        	  	//driver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS); 
	        	  	//driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);   
	        	  	break;
	          }
	          try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	     }
	}
	
	
	
	
	public ExpectedCondition<WebElement> visibilityOfElementLocated(final By locator) {
		
		return new ExpectedCondition<WebElement>() {
		    public WebElement apply(WebDriver driver) {
		    	WebElement toReturn = driver.findElement(locator);
		    	if (toReturn.isDisplayed()) {
		    		return toReturn;
		    	}
		    	return null;
		    }
		};
	}
	
	/**
	 * 얼럿창의 확인 or 다음 버튼 클릭
	 * @throws Exception - Exception
	 */
	public void chooseOkOnNextConfirmation() throws Exception {
	    executeJavascript(this, "window.confirm = function(msg) {return true;}");
	   }
	
	/**
	 * 얼럿창의 취소버튼 클릭
	 * @throws Exception - Exception
	 */
	public void chooseCancelOnNextConfirmation() throws Exception {
	   executeJavascript(this, "window.confirm = function(msg) {return false;}");
	   }
	
	
	/**
	 * 페이지 스크롤 값을 받아 스크롤 위치 변경
	 * @throws Exception - Exception
	 */
	public void PageScrollControl(int scroll_value) throws Exception {
		
		executeScript("window.scrollBy(0,"+scroll_value+");");
	  }
	/**
	 * 얼럿창의 메세지를 읽어오는 메소드
	 */
	 public String getAlert(By locator) throws Exception {   
	   executeJavascript(this, "window.alert = function(msg) {window.lastAlertMessage = msg;}");
	   findElementWait(locator).click();  
	   return (String) executeJavascript(this, "return window.lastAlertMessage");     
	  }
	  
	 /**
	 * 얼럿창의 메세지를 읽어오는 메소드
	 */
	  public String getConfirm(By locator) throws Exception {
	   executeJavascript(this, "window.confirm = function(msg) {window.lastConfirmMessage = msg;}");
	   findElementWait(locator).click();   
	   return (String) executeJavascript(this, "return window.lastConfirmMessage");     
	  }

	  /**
	 * Click 후 Alert이 나타날 때까지 대기하는 메쏘드
	 * @return Alert 노출 유무
	 * @throws Exception - Selenium Exception
	 */	
	public Alert ClickAndAlert(By locator) throws Exception {
		
		WebDriverWait wait = (WebDriverWait) new WebDriverWait(this, PAGE_LOAD_TIME_OUT);
			WebElement element = waitForIsElementPresent (locator);
		element.click();
		
		Alert alert = wait.until(ExpectedConditions.alertIsPresent());
		printLog ("Alert : " + alert.getText());
		
		return alert;
	}
	
	
	/**
	 * Element가 select 될 때까지 기다리는 메소드
	 * @param locator 대기할 Element를 지정
	 * @return Element select 유무
	 * @throws Exception - Selenium Exception
	 */
	public boolean waitForIsSelected (By locator) throws Exception {
		
		WebDriverWait wait = (WebDriverWait) new WebDriverWait(this, PAGE_LOAD_TIME_OUT);
		//WebDriverWait wait = (WebDriverWait) new WebDriverWait(this, PAGE_LOAD_TIME_OUT).ignoring(StaleElementReferenceException.class);

		try {
			wait.until(ExpectedConditions.presenceOfElementLocated(locator));
			if (wait.until(ExpectedConditions.elementToBeSelected(locator))) { 
				return true;
			}
		}catch (Exception e){
			printLog (e.getMessage());
			return false;
		}
		
		fail (locator + " : 해당 엘리먼트가 " + TIME_OUT_SEC + "초내에 select 되지 않음");
		return false;
	}

	/*
	 public ExpectedCondition<WebElement> visibilityOfElementLocated(final By by) {
	        return new ExpectedCondition<WebElement>() {
	          public WebElement apply(WebDriver driver) {
	            WebElement element = driver.findElement(by);
	            return element.isDisplayed() ? element : null;
	          }
	        };
	      }
	      
	      public void performSomeAction() {
	        Wait<WebDriver> wait = new WebDriverWait(this, 20);
	        WebElement element = wait.until(visibilityOfElementLocated(By.tagName("a")));
        
	      }
	      */
	
	/*
	public void waitForAjaxLoaded() { 				// ...update
		ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() { 
        	public Boolean apply(WebDriver driver) { 
        		boolean activeRequestCount = ((JavascriptExecutor)driver).executeScript("return Ajax.activeRequestCount").equals("0");
        		boolean jQueryActive = ((JavascriptExecutor)driver).executeScript("return jQuery.active").equals("0");
        		return (activeRequestCount && jQueryActive);
        		//return activeRequestCount;
        	} 
        };
	        
        Wait<WebDriver> wait = new WebDriverWait(this, PAGE_LOAD_TIME_OUT); 
    	wait.until(expectation);
    	//System.out.println("load wait for ajax ... ");
	} 
	*/
	
	

	/**
	 * 화면 뷰 전환시 사용되는 메소드, 모바일에서 사용
	 */
	public void rotate(ScreenOrientation orientation) {
	    execute(DriverCommand.SET_SCREEN_ORIENTATION, ImmutableMap.of("orientation", orientation));
	  }

	  public ScreenOrientation getOrientation() {
	    return ScreenOrientation.valueOf(
	        (String) execute(DriverCommand.GET_SCREEN_ORIENTATION).getValue());
	  }
	  
	  /**
		 * 엘리먼트를 지정하여, 해당 Tag갯수를 구함
		 * @return int 갯수 
		 * @throws Exception - Exception
	 */
	  public int ElementSize(By locator, String Tag) throws Exception {
		  
		  int tryCount = 0;
		  WebElement element = null;
		  WebDriverWait wait = (WebDriverWait) new WebDriverWait(this, PAGE_LOAD_TIME_OUT);
		  
		  while (tryCount < MAX_TRY_COUNT) {
				try {
					element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
					if (element.isEnabled() && element.isDisplayed()){ 
						List<WebElement> TagList = element.findElements(By.tagName(Tag));
						return TagList.size();
					}
				}
				catch (StaleElementReferenceException e){
					printLog ("** Catch StaleElement Exception : #" + tryCount );
					Thread.sleep(1000);
					tryCount = tryCount + 1;
				}
			}
			fail(locator + " : 해당 엘리먼트가 " + TIME_OUT_SEC + "초내에 노출되지 않음");
			return (Integer) null;
			
		}
	  /**
		 * 엘리먼트를 지정하여, 있으면 클릭(없으면 Skip)
		 * @return True면 클릭
		 * @throws Exception - Exception
	 */
	  
	  
	  /********************** IsMethod ****************************/
		/**
		 * Element가 selected or checked 되었는지 확인하는 메소드
		 * 
		 * @param locator존재
		 *            확인 할 Element를 지정
		 * @return boolean
		 * @throws Selenium
		 *             Exception
		 */
		public boolean isSelected(By locator) {
			manage().timeouts().implicitlyWait(MIN_WAIT_TIME, TimeUnit.SECONDS);

			try {
				WebElement element = locator.findElement((SearchContext) this);
				if (element.getAttribute("checked").equals("true") || element.getAttribute("selected").equals("true")) {
					
					return true;
				}
			} catch (NoSuchElementException e) {
				
				return false;
			}
			
			return false;
		}
	  
		/**
		 * Element가 enabled(사용) 되었는지 확인하는 메소드
		 * 
		 * @param locator존재
		 *            확인 할 Element를 지정
		 * @return boolean
		 */     
		public boolean isEnabled(By locator) {
			manage().timeouts().implicitlyWait(MIN_WAIT_TIME, TimeUnit.SECONDS);

			try {
				WebElement element = locator.findElement((SearchContext) this);
				if (element.getAttribute("enabled").equals("true")) {
					
					return true;
				}
			} catch (NoSuchElementException e) {
				
				return false;
			}
			
			return false;
		}
		
	  
		
		/**
		 * Element가 있는지 확인하고 클릭하는 메소드
		 * 
		 * @param locator존재
		 *            확인 할 Element를 지정
		 * @return boolean
		 */ 
	  public boolean isElementPresentClick(By locator) throws Exception {
	        
		  WebElement result = null;
		  
		  
			//WebElement element = findElement(locator);
		  
		  try {
			  	WebElement element = locator.findElement((SearchContext) this);
			  	if (element.isDisplayed()) {
					result = element;
					result.click();
				}
	            return true;

	        } catch (NoSuchElementException e) {
	            return false;
	        }
	    }
	  
	  /**
		 * Element가 존재하는지 확인하는 메소드
		 * 
		 * @param locator존재
		 *            확인 할 Element를 지정
		 * @return boolean
		 */ 
	  public boolean isElementPresentCheck(By locator) throws Exception {
			
			WebElement result = null;
			
			
			try {
				
				WebElement element = locator.findElement((SearchContext) this);
				if (element.isDisplayed()) {
					result = element;
				}
				return true;

				
			}
			catch (NoSuchElementException e) { 
				return false;
			}
		}
	  
	  /**
		 * Element가 있는지 확인하고 클릭하는 메소드(대기모드)
		 * 
		 * @param locator존재
		 *            확인 할 Element를 지정
		 * @return boolean
		 */ 
	  public WebElement waitForIsElementCheck (By locator) throws InterruptedException {
			
			int tryCount = 0;
			WebElement element = null;
			WebDriverWait wait = (WebDriverWait) new WebDriverWait(this, TIME_OUT_SEC);
			
				try {
					element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
					if (element != null && element.isEnabled() && element.isDisplayed()){ 
						return element;
					}
				}
				catch (StaleElementReferenceException e){
					printLog ("** Catch StaleElement Exception : #" + tryCount );
					Thread.sleep(1000);
					tryCount = tryCount + 1;
				}
			fail(locator + " : 해당 엘리먼트가 " + TIME_OUT_SEC + "초내에 노출되지 않음");
			return null;
		}
	  
	  /**
		 * URL를 문자열로 받아와서 로드
		 * 페이지 연결호 로딩완료될때까지 wait
	 */
	  public void getGo(String URL) throws InterruptedException {
		   get(URL);
		   //waitForPageLoaded(this);
		   //waitForPageToLoad();
	  }
	  
	
	  
	  /**
		 * 엘리먼트를 지정하여, 20초동안 찾음
		 * @return superElement로 전환 
	 */
	  public WebElement findElementWait(By locator) throws Exception {
		   
		  	WebDriverWait wait = (WebDriverWait) new WebDriverWait(this, TIME_OUT_SEC);
			//wait.until(ExpectedConditions.elementToBeClickable(locator));    
			//WebElement superElement = findElement(locator);
		  	WebElement superElement = wait.until(ExpectedConditions.elementToBeClickable(locator));    
			return superElement;
	  }
	  
	  /**
		 * 쿠기 생성
	 */
	  public void AddCookie (String Cookiename, String Cookievalue) throws Exception {
		  Cookie ck = new Cookie(Cookiename, Cookievalue);
		  manage().addCookie(ck);
	  }
	  
	  /**
		 * test
	 */
	  protected String id;
	  public void click() {
		 execute(DriverCommand.CLICK_ELEMENT, ImmutableMap.of("id", id));
	  }
	  
	  /**
		 * 셀렉트박스 지정하여 텍스트값으로 클릭
		 * @locator xPath
		 * @byText 체크박스의 Text값
	 */
	  public WebElement SelectClickByText(By locator, String byText) throws Exception {
		  
		  
		  Select Select = new Select(findElement(locator));
		  Select.selectByVisibleText(byText);
		  waitForPageToLoad();
		  return null;
		  
	  }
	  
	  /** 
	 * locator select 후 기다리는 메소드
	 * @param selectLocator 링크
	 * @paran byType 값을 선택할 방법 (byValue, byText, byIndex)
	 * @param optionLocator 선택할 값의 링크
	 * @throws Exception 
	 */
	public boolean select(By selectLocator, String byType, String optionLocator) throws Exception {
			
		boolean result = false;
		WebElement element = waitForIsElementPresent(selectLocator);
		Select select = new Select(element);
		
		try {
			if (byType.equals("text"))
				select.selectByVisibleText(optionLocator);
			
			else if (byType.equals("index"))
				select.selectByIndex(Integer.parseInt(optionLocator));
			
			else if (byType.equals("value"))
				select.selectByValue(optionLocator);
			
			else {
					printLog("unknown option type");
			return false;
			}
			waitForPageToLoad();
			result = true;
		} 
		catch (NoSuchElementException e) {
			result = false;
			//printLog("--- received an exception: " + t);
		}
		return result;
	}
	  
		/** 
		 * select element를 기다리는 메소드
		 * @param locator 링크
		 * @throws Exception 
		 */
	public Select select (By locator) throws Exception {
			
		WebElement element = waitForIsElementPresent(locator);	
		Select select = new Select(element);
			
		return select;
	}
		
	  
	  /**
		 * 셀렉트박스 지정하여 Value값으로 클릭
		 * @locator xPath
		 * @byText 체크박스의 Value값
	 */
	  public WebElement SelectClickByValue(By locator, String ByValue) throws Exception {
		   
		  Select Select = new Select(findElement(locator));
		  Select.selectByValue(ByValue);
		  waitForPageToLoad();
		  return null;
	  }
	  
	  /**
		 * 셀렉트박스 지정하여 Index순서로 클릭
		 * @locator xPath
		 * @byText 체크박스의 Index 순서 ('0'부터 시작)
	 */
	  public WebElement SelectClickByIndex(By locator, int Index) throws Exception {
		   
		  Select Select = new Select(findElement(locator));
		  Select.selectByIndex(Index);
		  waitForPageToLoad();
		  return null;
	  }
	  
	  /** 
	 * locator submit 후 기다리는 메소드
	 * @param locator 링크
	 * @throws Exception 
	 * @throws Exception - Selenium Exception
	 */
	public void submit(By formLocator) throws Exception {
				
		WebElement element = waitForIsElementPresent(formLocator);
		element.submit();
		waitForPageToLoad();
	}
	  
	  
	  /**
	     * 현재날짜에 일자를 더한 값
	     * 
	     * @param DateTime
	     *            YYMMDDHHMMSS
	     * @param iDay
	     *            더할 일자
	     * @return 특정날짜에 일자를 더한 값
	     */
	  
	  public String getDate ( int iDay ) 
	  {
		  Calendar temp=Calendar.getInstance ();
		  StringBuffer sbDate=new StringBuffer ();

		  temp.add ( Calendar.DAY_OF_MONTH, iDay );

		  int nYear = temp.get ( Calendar.YEAR );
		  int nMonth = temp.get ( Calendar.MONTH ) + 1;
		  int nDay = temp.get ( Calendar.DAY_OF_MONTH );

		  sbDate.append ( nYear );
		  if ( nMonth < 10 ) 
			  sbDate.append ( "0" );
		  sbDate.append ( nMonth );
		  if ( nDay < 10 ) 
			  sbDate.append ( "0" );
		  sbDate.append ( nDay );

		  return sbDate.toString ( );
	  }
	  
	  
	  
	  /**
	     * 특정날짜에 일자를 더한 값
	     * 
	     * @param DateTime
	     *            YYMMDDHHMMSS
	     * @param plusDay
	     *            더할 일자
	     * @return 특정날짜에 일자를 더한 값
	     */
	  public String getAddDay(String DateTime, int plusDay) {
		  
	        if (DateTime == null)
	            return "";
	 
	        if (DateTime.length() == 8)
	            DateTime += "000000";
	 
	        if (DateTime.equals("99991231")) {
	            return "99991231000000";
	        }
	 
	        if (DateTime.equals("99991231235959")) {
	            return "99991231235959";
	        }
	 
	        int y = Integer.parseInt(DateTime.substring(0, 4));
	        int m = Integer.parseInt(DateTime.substring(4, 6));
	        int d = Integer.parseInt(DateTime.substring(6, 8));
	 
	        java.util.GregorianCalendar sToday = new java.util.GregorianCalendar();
	        sToday.set(y, m - 1, d);
	        sToday.add(GregorianCalendar.DAY_OF_MONTH, plusDay);
	 
	        int day = sToday.get(GregorianCalendar.DAY_OF_MONTH);
	        int month = sToday.get(GregorianCalendar.MONTH) + 1;
	        int year = sToday.get(GregorianCalendar.YEAR);
	 
	        String sNowyear = String.valueOf(year);
	        String sNowmonth = "";
	        String sNowday = "";
	 
	        if (month < 10)
	            sNowmonth = "0" + String.valueOf(month);
	        else
	            sNowmonth = String.valueOf(month);
	 
	        if (day < 10)
	            sNowday = "0" + String.valueOf(day);
	        else
	            sNowday = String.valueOf(day);
	 
	        return sNowyear + sNowmonth + sNowday + DateTime.substring(8, 14);
	 
	    }
	  
	  /**
		 * class name return 메소드
		 */
		public String printClassName(Object obj) {
	        return (obj.getClass().getName());
	    }

		/**
		 * method name return 메소드
		 */
		public Method[] printMethodName(Object obj) {
			return (obj.getClass().getMethods());
		}
		
		public String ErrorScreenshots(WebDriver driver,String screenShotName) throws IOException
	    {
	        TakesScreenshot ts = (TakesScreenshot)driver;
	        File source = ts.getScreenshotAs(OutputType.FILE);
	        String dest = ".\\ErrorScreenshots\\"+screenShotName+".png";
	        File destination = new File(dest);
	        FileUtils.copyFile(source, destination);        
	                     
	        return dest;
	    }
		

		/**
		 * 해당 locator의 element값을 가져오는 메소드
		 * 
		 * @param locator
		 * @return WebElement
		 */
		public WebElement getElement(By locator) {
			WebElement element = null;
			manage().timeouts().implicitlyWait(MIN_WAIT_TIME, TimeUnit.SECONDS);

			try {
				element = locator.findElement((SearchContext) this);
				if (element.isDisplayed()) {
					return element;
				}
			} catch (NoSuchElementException e) {
				return null;
			}
			return null;
		}
		
		/**
		 * 해당 locator의 element값을 가져오는 메소드
		 * 
		 * @param locator
		 * @return List<WebElement>
		 */
		public List<WebElement> getElements(By locator) {
			List<WebElement> elemList = null;
			manage().timeouts().implicitlyWait(MIN_WAIT_TIME, TimeUnit.SECONDS);
			try {
				elemList = locator.findElements((SearchContext) this);
				return elemList;
			} catch (NoSuchElementException e) {
				return null;
			}
		}
		
		/**
		 * 모든 링크 검색
		 * 
		 * @throws Exception
		 */
		public List<WebElement> getAllLinks() {
			List<WebElement> elementList = new ArrayList<WebElement>();
			List<WebElement> finalList = new ArrayList<WebElement>();

			elementList = findElements(By.tagName("a"));
			elementList.addAll(findElements(By.tagName("img")));
			
			for (WebElement element : elementList) {
				String href = element.getAttribute("href");
				
				if ((href != null) && href.contains("http://")) {
					finalList.add(element);
				}
				
				if ((href != null) && href.contains("https://")) {
					finalList.add(element);
				}
			}
			
			return finalList;
		}
		
		/**
		 * 모든 링크 검색
		 * 
		 * @param 링크를
		 *            검색 할 상위 element
		 * @throws Exception
		 */
		public List<WebElement> getAllLinks(By locator) {
			List<WebElement> elementList = new ArrayList<WebElement>();
			List<WebElement> finalList = new ArrayList<WebElement>();

			elementList = findElement(locator).findElements(By.tagName("a"));
			elementList.addAll(findElement(locator).findElements(By.tagName("img")));
			
			for (WebElement element : elementList) {
				String href = element.getAttribute("href");
				
				if ((href != null) && href.contains("http://")) {
					finalList.add(element);
				}
				
				if ((href != null) && href.contains("https://")) {
					finalList.add(element);
				}
			}
			
			return finalList;
		}
		
}
