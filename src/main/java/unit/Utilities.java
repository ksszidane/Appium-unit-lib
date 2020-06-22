package unit;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;


import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidTouchAction;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.touch.LongPressOptions;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import junit.framework.Assert;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Rotatable;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.HasTouchScreen;
import org.openqa.selenium.interactions.TouchScreen;
import org.openqa.selenium.interactions.touch.TouchActions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.ExecuteMethod;
import org.openqa.selenium.remote.RemoteTouchScreen;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.Parameters;


import static org.testng.Assert.fail;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.relevantcodes.extentreports.LogStatus;

import unit.TestCase;

public class Utilities extends AndroidDriver<WebElement> implements HasTouchScreen, TakesScreenshot { 
	
	
	public RemoteTouchScreen Screentouch;
	public TouchActions actions;
	public TouchAction action;
	
	
    public WebDriverWait Wait;
	
	protected final int TIME_OUT_SEC = 10; // by second
	protected final int WAIT_SEC = 1;
	protected final int PAGE_LOAD_TIME_OUT = 10;
	protected final int MAX_TRY_COUNT = 10;
	protected final int MIN_WAIT_TIME = 1;
	protected final int MAX_WAIT_TIME = 20;
	  
	public int winX, winY;
	public int centerX, centerY;
	
	public String modelName = null;
	public String osVersion = null;
	public String udid = null;
	
	public String beforeFilePath = null;
	public String beforeFilePath2 = null;
	

	
	private final OkHttpClient httpClient = new OkHttpClient();
	
	public static String hubAddress;

	public Utilities (DesiredCapabilities capability) throws MalformedURLException {
		
		this(hubAddress, capability);
		Wait = (WebDriverWait) new WebDriverWait(this, PAGE_LOAD_TIME_OUT);
	}
	
	public Utilities (String url, DesiredCapabilities capability) throws MalformedURLException { 
		
		super (new URL(url), capability);
		Wait = (WebDriverWait) new WebDriverWait(this, PAGE_LOAD_TIME_OUT);
	}
	
	
	
	
	
	public WebElement WaitfindElement(By locator) throws Exception {
		   
	  	WebDriverWait wait = (WebDriverWait) new WebDriverWait(this, TIME_OUT_SEC);
		//wait.until(ExpectedConditions.elementToBeClickable(locator));    
		//WebElement superElement = findElement(locator);
	  	WebElement superElement = wait.until(ExpectedConditions.elementToBeClickable(locator));    
		return superElement;
  }
		public WebElement findElementLocated(By locator) throws Exception {
		   
	  	WebDriverWait wait = (WebDriverWait) new WebDriverWait(this, TIME_OUT_SEC);
		//wait.until(ExpectedConditions.elementToBeClickable(locator));    
		//WebElement superElement = findElement(locator);
	  	WebElement superElement = wait.until(ExpectedConditions.presenceOfElementLocated(locator));    
		return superElement;
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
	

	private WebElement findElementByAccessibilityId(By locator) {
		// TODO Auto-generated method stub
		return null;
	}

	public String takeScreenShot(String callerName, String deviceName) {
		
		String destDir;
		DateFormat dateFormat;
		destDir = "screenshots";
		
		File scrFile = ((TakesScreenshot) this).getScreenshotAs(OutputType.FILE);
		//dateFormat = new SimpleDateFormat("dd-MM-yyyy_hh_mm_ssaa");
		dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");

		new File(destDir).mkdirs();
		String destFile = dateFormat.format(new Date()) + "_" + deviceName + "_" + callerName + ".png";
		String imgLink = destDir + "\\" + destFile;
		
		try {
			FileUtils.copyFile(scrFile, new File(destDir + "/" + destFile));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return imgLink;
	}
	
	/**
	 * 페이지의 load complete 대기
	 * @throws Exception - Exception
	 */
	public void waitForPageToLoad() {
		try {
			Thread.sleep(300);
			//waitForPageLoaded();
			waitForAjaxLoaded(this);
		} catch (Exception e) {
			printLog (e.getMessage());
		}
	}
	
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
	
	/**
	 * 현재 날짜 및 시간 정보 [yyyy.MM.dd_HH:mm:ss]
	 * @throws Exception - Exception
	 */
	public String getDate() {
		
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat form = new SimpleDateFormat("[yyyy.MM.dd_HH:mm:ss]");
		return form.format(cal.getTime());	
	}
	
	public String getSimpleDate() {
		
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat form = new SimpleDateFormat("yyMMdd");
		return form.format(cal.getTime());	
	}
	
	/** 
	 * 무작위 숫자 생성
	 * @throws Exception 
	 */
	public int getRandomNum() {

		return (int)(Math.random() * 1000 + 1);
	}
	
	/** 
	 * 무작위 숫자 생성 (범위 지정)
	 * @throws Exception 
	 */
	public int getRandomNum(int min, int max) {

		//return (int)(Math.random() * max + min);
		return (int)(Math.random() * (max - min)) + min;
	}	
	
	/** 
	 * length 크기의 무작위 문자열 생성 (특수 기호 포함)
	 * @param 문자열 size 
	 */
	public String getRandomString(int length) {

		String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJLMNOPQRSTUVWXYZ1234567890!@#$%^&*()_+`~=[]{};:',./<>?";
		StringBuilder result = new StringBuilder();

		while (length > 0) {
			Random rand = new Random();
			result.append(characters.charAt(rand.nextInt(characters.length())));
			length--;
		}
		return result.toString();
	}
	
	/** 
	 * length 크기의 무작위 문자열 생성 (영문 & 숫자 만..)
	 * @param 문자열 size 
	 */
	public String getRandomText(int length) {

		String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJLMNOPQRSTUVWXYZ1234567890";
		StringBuilder result = new StringBuilder();

		while (length > 0) {
			Random rand = new Random();
			result.append(characters.charAt(rand.nextInt(characters.length())));
			length--;
		}
		return result.toString();
	}
	
	public WebElement getRandumElement(By locator) throws Exception{
		
		this.waitForIsElementPresent(locator);
		List<WebElement> elements = this.findElements(locator);
		
		return elements.get(this.getRandomNum(0, elements.size() - 1));
	}
	
	
	/** 
	 * @param int startx - the starting x position
	 * @param int starty - the starting y position
	 * @param int endx - the ending x position
	 * @param int endy - the ending y position
	 * @throws InterruptedException 
	 */
	@SuppressWarnings("rawtypes")
	public void swipe(int startx, int starty, int endx, int endy) throws InterruptedException {
		
		Thread.sleep(2000);
	    TouchAction touchAction = new TouchAction(this);
	    

	    touchAction.press(PointOption.point(startx, starty))
	    		   //.waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1)))
	    		   .waitAction()
	               .moveTo(PointOption.point(endx, endy))
	               .release()
	               .perform();
	    
	    Thread.sleep(2000);

	}
	
	@SuppressWarnings("rawtypes")
	public void touchTab(int x, int y) throws InterruptedException {
		
		Thread.sleep(2000);
	    TouchAction touchAction = new TouchAction(this);
	    
	    touchAction.tap(PointOption.point(x, y)).release().perform();
	    Thread.sleep(2000);

	}
	
	/**
	@SuppressWarnings("rawtypes")
	public void scrollDown() throws Exception  {
		
	    int pressX = manage().window().getSize().width / 2;
	    int bottomY = manage().window().getSize().height * 4/5;
	    int topY = manage().window().getSize().height / 8;
	    Thread.sleep(2000);
	    scroll(pressX, bottomY, pressX, topY);
	    Thread.sleep(2000);

	}
	
	public void scrollDown (int count) throws Exception {
		for (int i = 0 ; i < count ; i ++) this.scrollDown();
	}
	*/
	
	/**
	 * Vertical scroll
	 * @param direction : "up" or "down" / 1회 수행
	 * @throws Exception - Exception
	 */
	public void scrollUp () throws Exception {
		scroll(0.2, 1);
	}
	
	public void scrollUp (int count) throws Exception {
		Thread.sleep(1200);
		for (int i = 0 ; i < count ; i ++) this.scrollUp();
	}
	public void scrollDown () throws Exception {
		scroll(0.8, 1);
	}
	public void scrollDown (int count) throws Exception {
		Thread.sleep(1200);
		for (int i = 0 ; i < count ; i ++) this.scrollDown();
	}
	/**
	@SuppressWarnings("rawtypes")
	private void scroll(int fromX, int fromY, int toX, int toY) {
	    TouchAction touchAction = new TouchAction(this);
	    touchAction.longPress(PointOption.point(fromX, fromY)).moveTo(PointOption.point(toX, toY)).release().perform();
	}*/
	
	@SuppressWarnings("rawtypes")
	public void scroll(double direction, int count) throws Exception {
		
		TouchAction touchAction = new TouchAction (this);
		
		if (direction > 1.0 || direction < 0.1)
			fail ("scoll direction : 0.1 ~ 1.0");
		/**
        int startY = (int) (winY * direction);
        int startX = winX / 2;
        int endY = (int) (winY * (1 - direction));
        int endX = startX;
        */
		
        int pressX = manage().window().getSize().width / 2;
	    double d_bottomY = manage().window().getSize().height * direction;
	    double d_topY = manage().window().getSize().height / (7 * direction);
        
	    int i_bottomY = (int)d_bottomY;
	    int i_topY = (int)d_topY;
	    
        for (int i = 0 ; i < count ; i ++) {
    
        	// printLog ("scroll : [" +  startX + ", " + startY  + ", " + endX  + ", " + endY + "]");
        	//touchAction.longPress(PointOption.point(startX, startY)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1))).moveTo(PointOption.point(endX, endY)).release().perform();
        	touchAction.longPress(PointOption.point(pressX, i_bottomY))
        				.moveTo(PointOption.point(pressX, i_topY))
        				.release()
        				.perform();
        	//Wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.FrameLayout"))));
       
        }
	}
	
	/**
	 * Vertical scroll
	 * @param direction : "up" or "down" / count : 수행 횟수
	 * @throws Exception - Exception
	 */
	public void scroll (String direction, int count) throws Exception {
		
		if (direction.equalsIgnoreCase("down")) this.scroll(0.8, count);
		else if (direction.equalsIgnoreCase("up")) this.scroll(0.2, count);
	}

	public void getWindowSize() {
		
		Dimension winSize = manage().window().getSize();
		
		winX = winSize.width;
		winY = winSize.height;
		
		centerX = (int) (winX * 0.5);
		centerY = (int) (winY * 0.5);
	}
	
	public void getDeviceInfo() {
		
		this.getWindowSize();
		
		modelName = (String) this.getCapabilities().getCapability("modelName");
		osVersion = (String) this.getCapabilities().getCapability("osVersion");
		udid = (String) this.getCapabilities().getCapability("udid");
	}
	
	/**
	  * NATIVE -> WEBVIWE 전환
	  */
	public void switchToWeb() {
		
		Set<String> contextNames = getContextHandles();
        
		for (String contextName : contextNames) {
			if (contextName.contains("WEBVIEW"))
				context(contextName);
		}
	}
	
	/**
	  * WEBVIWE -> NATIVE 전환
	  */
	public void switchToNative() {
		
		Set<String> contextNames = getContextHandles();
        
		for (String contextName : contextNames) {
			if (contextName.contains("NATIVE"))
				context(contextName);
		}
	}
	
	/**
	  * 로그를 출력하는 메소드
	  * 메소드를 실행하는 system의 정보를 출력 (platform, browser info)
	  * @param 출력할 메시지
	  * @return void
	  */
	public void printLog(String logs) {
		
		//String modelName = getCapabilities().getCapability("modelName").toString();
		//String deviceName = getCapabilities().getCapability("deviceName").toString();

		//Reporter.log("[" + platForm + "/" + browserName + "" +  verSion + "]" + " " + logs + "<br>");
		//Reporter.log(logs + "<br>");
		
		System.out.println ("[" + modelName + "] " + logs);
	}
	
	/**
	 * Element가 존재하는지 확인하는 메소드
	 * @param locator 존재 확인 할 Element를 지정
	 * @return boolean
	 * @throws Exception - Selenium Exception
	 */
	public boolean isElementPresent(By locator) throws Exception{
		
		boolean result = false;
		manage().timeouts().implicitlyWait(MIN_WAIT_TIME, TimeUnit.SECONDS);
		WebDriverWait wait = (WebDriverWait) new WebDriverWait(this, TIME_OUT_SEC);
		
		try {		
			WebElement element = locator.findElement((SearchContext) this);
			//WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));  
			
			if (element.isDisplayed()) {
				manage().timeouts().implicitlyWait(MAX_WAIT_TIME, TimeUnit.SECONDS);
				result = true;
			}
		} catch (NoSuchElementException e) {
			manage().timeouts().implicitlyWait(MAX_WAIT_TIME, TimeUnit.SECONDS);
			return false;
		}
		manage().timeouts().implicitlyWait(MAX_WAIT_TIME, TimeUnit.SECONDS);
		return result;
		
	}
	
	public void connectingDevice() throws Exception {
		
		Thread.sleep(1500);
		
		boolean view = this.isElementPresent(By.xpath("//*[@text='연결을 기다리는 디바이스가 있어요.']"));
		if(view == true) {
			System.out.println("연결을 기다리는 디바이스 상단 알림창 [있음]");
			this.swipe(560, 180, 560, 78);
			Thread.sleep(1000);
		
		} if(view == false) {
			System.out.println("연결을 기다리는 디바이스 상단 알림창 [없음]");
			Thread.sleep(1000);
		}
		
		
	}
	
	/**
	 * Element가 selected or checked 되었는지 확인하는 메소드
	 * @param locator 존재 확인 할 Element를 지정
	 * @return boolean
	 * @throws Exception - Selenium Exception
	 */
	public boolean isSelected(By locator) throws Exception{
		
		boolean result = false;
		manage().timeouts().implicitlyWait(MIN_WAIT_TIME, TimeUnit.SECONDS);
		
		try {		
			WebElement element = locator.findElement((SearchContext) this);
			if (element.getAttribute("checked").equals("true") 
					|| element.getAttribute("selected").equals("true")) {
				manage().timeouts().implicitlyWait(MAX_WAIT_TIME, TimeUnit.SECONDS);
				result = true;
			}
		} catch (NoSuchElementException e) {
			manage().timeouts().implicitlyWait(MAX_WAIT_TIME, TimeUnit.SECONDS);
			return false;
		}
		manage().timeouts().implicitlyWait(MAX_WAIT_TIME, TimeUnit.SECONDS);
		return result;
	}
	
	/*public boolean isElementSelected(By locator) throws Exception{
		
		boolean result = false;
		manage().timeouts().implicitlyWait(MIN_WAIT_TIME, TimeUnit.SECONDS);
		
		try {		
			WebElement element = locator.findElement((SearchContext) this);
			if (element.isDisplayed() && element.isSelected()) {
				result = true;
			}
		} catch (NoSuchElementException e) {
			manage().timeouts().implicitlyWait(MAX_WAIT_TIME, TimeUnit.SECONDS);
			return false;
		}
		manage().timeouts().implicitlyWait(MAX_WAIT_TIME, TimeUnit.SECONDS);
		return result;
	}*/
	
	/**
	 * Element가 존재할때까지 대기하는 메소드
	 * @param locator 존재 확인 할 Element를 지정
	 * @return locator에 존재하는 WebElement (element, null)
	 * @throws Exception - Selenium Exception
	 */
	public WebElement waitForIsElementPresent(By locator) throws Exception {

		WebElement element = Wait.until(ExpectedConditions.presenceOfElementLocated(locator));

		if (element != null) {
			return element;	
		}
		else {
			//printLog ("해당 element가 없습니다. : " + locator.toString());
			return null;
		}
	} 
	
	
	/**
	 * 아래 커맨트가 원본임 
	public WebElement waitForIsElementPresent(By locator) throws Exception {

		WebElement element = Wait.until(ExpectedConditions.presenceOfElementLocated(locator));

		if (element != null) {
			return element;	
		}
		else {
			//printLog ("해당 element가 없습니다. : " + locator.toString());
			return null;
		}
	} 
	*/
	
	/**
	 * Element가 selected or checked 될때까지 대기하는 메소드
	 * @param locator 존재 확인 할 Element를 지정
	 * @return boolean
	 * @throws Exception - Selenium Exception
	 */
	public Boolean waitForIsElementSelected(By locator) throws Exception {
		
		for (int i = 0 ; i < MAX_TRY_COUNT ; i ++) {
			
			//if (waitForIsElementPresent(By.xpath("//*[contains(.,'"+searchText+"')]")) != null) {
			if (isSelected(locator)) {
				return true;
			}
			Thread.sleep(2000);
		}
		//printLog (locator + " : 해당 Text가 " + TIME_OUT_SEC + "초내에 check되지 않음");
		return false;
	}
	/*public Boolean waitForIsElementSelected(By locator) throws Exception {

		Boolean result = false;
		result = Wait.until(ExpectedConditions.elementToBeSelected(locator));
		
		return result;
	}*/
	
	/**
	 * searchText 나타날 때까지 기다리는 메소드
	 * @param searchText 나타날때까지 기다릴 Text
	 * @return Text Present 유무
	 * @throws Exception - Selenium Exception
	 */
	public boolean waitForIsTextPresent (String searchText) throws Exception {
		
		for (int i = 0 ; i < MAX_TRY_COUNT ; i ++) {
		
			//if (waitForIsElementPresent(By.xpath("//*[contains(.,'"+searchText+"')]")) != null) {
			if (isTextPresent(searchText)) {
				return true;
			}
			Thread.sleep(1000);
		}
		//printLog (searchText + " : 해당 Text가 " + TIME_OUT_SEC + "초내에 나타나지 않음");
		return false;
	}
	
	/**
	 * 프로그레스바가 사라질 때까지 기다리는 메소드
	 * try 10회 X 회당 대기 4 sec X implict wait 3 sec : 최대 120 sec 대기
	 * @param null
	 * @return whether progress bar disappeared or not
	 * @throws Exception - Exception
	 */
	public boolean waitProgressCompleted () throws Exception {
		
		for (int i = 0 ; i < MAX_TRY_COUNT ; i ++) {
			
			if (!isElementPresent(By.className("android.widget.ProgressBar"))) { 	
				return true;
			}
			Thread.sleep(4000);
		}
		fail ("Progress Bar가 사라지지 않음");
		return false;
	}
	
	/**
	 * Activity가 전환될때까지 대기하는 메소드
	 * @param 전환을 대기하는 Activity
	 * @return 전환 확인에 따른 true vs false
	 * @throws Exception - Selenium Exception
	 */
	
	public boolean waitForActivity (String expectedActivity) throws Exception {
		
		for (int second = 0; second <= TIME_OUT_SEC; second += WAIT_SEC) {
			
			String currentActivity = currentActivity();
			
			if (currentActivity.contains(expectedActivity)) {
				//printLog (expectedActivity + " : 해당 Activity 전환");
				return true;
			}
			
			Thread.sleep(1000);
		}
		//printLog (expectedActivity + " : " + TIME_OUT_SEC + "초내에 Activity 전환 되지 않음");
		return false;
	} 
	
	/**
	 * text 존재 확인 메소드
	 * @param locator 존재 확인 할 text 지정
	 * @return boolean
	 * @throws Exception - Selenium Exception
	 */
	/*=
	public boolean isTextPresent (String searchText) throws Exception {
		
		List<WebElement> TextViewList = findElementsByClassName("android.widget.TextView");
		int TextViewSize = TextViewList.size();
		manage().timeouts().implicitlyWait(MIN_WAIT_TIME, TimeUnit.SECONDS);
		
		// no textview in page
		if (TextViewSize < 1)
			return false;
		
		for (int i = 0 ; i < TextViewSize ; i ++) {
			String temp = TextViewList.get(i).getText();
		
			if (temp.contains(searchText)) {	
				manage().timeouts().implicitlyWait(MAX_WAIT_TIME, TimeUnit.SECONDS);
				return true;
			}
		}

		printLog (searchText + " : 해당 Text가 존재하지 않습니다");
		manage().timeouts().implicitlyWait(MAX_WAIT_TIME, TimeUnit.SECONDS);

		return false;
	}
	*/
	
	public boolean isTextPresent(String searchText) throws Exception{
		
		boolean result = false;
		manage().timeouts().implicitlyWait(MIN_WAIT_TIME, TimeUnit.SECONDS);
		
		try {		
			if (findElement(By.xpath("//*[contains(@text,'"+searchText+"')]")) != null) {
				manage().timeouts().implicitlyWait(MAX_WAIT_TIME, TimeUnit.SECONDS);
				return true;
			}
		} catch (NoSuchElementException e) {
			manage().timeouts().implicitlyWait(MAX_WAIT_TIME, TimeUnit.SECONDS);
			return false;
		}
		manage().timeouts().implicitlyWait(MAX_WAIT_TIME, TimeUnit.SECONDS);
		return result;
	}
	
	/**
	 * Element click
	 * @param locator 존재 확인 및 click 후 프로그레스바 completed 대기
	 * @return boolean
	 * @throws Exception - Selenium Exception
	 */
	public void click(By locator) throws Exception {
	
		waitForPageToLoad();
		
		WebElement element = null;
		//waitForIsElementPresent (locator);
		
		element = WaitfindElement(locator);
		element.click();
		
		//waitProgressCompleted();
	}
	
	// to do : 앞 머리에 공백인 경우 전체 선택 안됨 -> 무한루프
	@SuppressWarnings("rawtypes")
	public void clear(By locator) throws Exception {

		Boolean status = false;
		WebElement element = null;
		element = waitForIsElementPresent(locator);

		while (!status) {

			String text = element.getText();
			if (text.isEmpty() || text.contains("주세요") || text.contains("새 폴더") || text.contains("입력하세요")) {
				status = true;
			}
			
			TouchAction touchAction = new TouchAction(this);
			touchAction.longPress((LongPressOptions) element);
			this.getKeyboard().sendKeys(Keys.DELETE);
		}
	}
	
	/**
	 * Element click / toggle & switch on
	 * @param locator swtich off 상태이면 클릭 (set on)
	 * @throws Exception - Selenium Exception
	 */
	public void switchSetOn(By locator) throws Exception {
		
		WebElement element = waitForIsElementPresent (locator);
		String isChecked = element.getAttribute("checked");
		
		if (isChecked.equals("false")) { 
			element.click();
			waitProgressCompleted();
		}
	}
	
	/**
	 * Element click / toggle & switch off
	 * @param locator swtich on 상태이면 클릭 (set off)
	 * @throws Exception - Selenium Exception
	 */
	public void switchSetOff(By locator) throws Exception {
		
		WebElement element = waitForIsElementPresent (locator);
		String isChecked = element.getAttribute("checked");
		
		if (isChecked.equals("true")) {
			element.click();
			waitProgressCompleted();
		}
	}
	
	/**
	 * 문자열 입력
	 * @param locator 정보 및 입력 text
	 * @return boolean
	 * @throws Exception - Selenium Exception
	 */
	public void type(By locator, String text) throws Exception {
		
		WebElement element = null;
		element = this.waitForIsElementPresent(locator);
		
		//element.clear();
		element.sendKeys(text);
	}
	
	public void sendImeKeys(int keyValue) throws Exception {
		
		HashMap<String, Integer> keycode = new HashMap<String, Integer>();
		keycode.put("keycode", keyValue);
		
		((JavascriptExecutor) this).executeScript("mobile: keyevent", keycode);
	}
	
	/**
	 * element 의 text 값 가져오기
	 * @param target element
	 * @return boolean
	 * @throws Exception - Selenium Exception
	 */
	public String getText(By locator) throws Exception {
		
		waitForPageToLoad();
		
		WebElement element = null;
		element = WaitfindElement (locator);
		
		String text = element.getText();

		return text;
	}
	
	public int findListViewContent(String elementId, By by) {
	    //driver.findElements(by.xpath(elementId));
	    //driver.findElements(By.xpath(elementId)).
	    List weekObjectList = findElements(By.xpath(elementId));
	    System.out.println(weekObjectList.size());
	    return weekObjectList.size();
	}
	
	public int getSize(By locator) throws Exception {

		List<WebElement> element = null;
		element = findElements(locator);
		
		int count = element.size();

		return count;
	}
	
	
	
	public String getTextWait(By locator) throws Exception {
		
		Thread.sleep(1500);
		
		WebElement element = null;
		element = findElementLocated(locator);
		
		String text = element.getText();

		return text;
	}
	

	/**
	 * element 속성 가져오기
	 * @param target element / 속성 (ex. id값 : resourceId & content-desc : name)
	 * @return boolean
	 * @throws Exception - Selenium Exception
	 */
	public String getAttribute(By locator, String attr) throws Exception {
		
		WebElement element = null;
		element = waitForIsElementPresent (locator);
		
		String attribute = element.getAttribute(attr);
		return attribute;
	}
	
	public int getElementCount(By locator) throws Exception {
		
		int count = 0;
		WebElement element = null;
		element = waitForIsElementPresent (locator);
		
		if (element != null) {
			count = this.findElements(locator).size();
			return count;	
		}
		return count;
	}
	
	public void tap(By locator) throws Exception {
		WebDriverWait wait = new WebDriverWait(this, PAGE_LOAD_TIME_OUT);
		wait.until(ExpectedConditions.elementToBeClickable(locator));    
		WebElement superElement = findElement(locator);
		
		if (wait instanceof HasTouchScreen) {
			TouchActions tap = new TouchActions((WebDriver) wait).singleTap(superElement);
			tap.perform();
		} else {
			superElement.click();
		}
	}
	
	
	
	
	
	public String printClassName(Object obj) {
        return (obj.getClass().getName());
    }
	
    public String getTime() {
		
		Calendar cal = Calendar.getInstance();
		//SimpleDateFormat form = new SimpleDateFormat("yyyy.MM.dd-");
		SimpleDateFormat form = new SimpleDateFormat("yyyyMMddHHmmss");
		
		return form.format(cal.getTime());	
	}
    
    public String ErrorScreenshots(WebElement webElement,String screenShotName) throws IOException
    {
        TakesScreenshot ts = (TakesScreenshot)webElement;
        File source = ts.getScreenshotAs(OutputType.FILE);
        String dest = ".\\ErrorScreenshots\\"+screenShotName+".png";
        File destination = new File(dest);
        FileUtils.copyFile(source, destination);        
                     
        return dest;
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
    
    public void switchContext (String context) throws Exception {
    	
    	Thread.sleep(4000);
    	
    	Set<String> contextNames = getContextHandles(); 
        for (String contextName : contextNames) {
            System.out.println("context 목록  : " + contextName);
            if(contextName.contains(context)) {
            	System.out.println("적용 될 context 이름은 : " +contextName);
            	context(contextName);
            	break;
            }
        }
        System.out.println("스위칭된 context : " + getContext() + "\n");
 	
    }
   
    
    public void  sendGet() throws Exception {

        Request request = new Request.Builder()
                .url("https://www.google.com/search?q=mkyong")
                .addHeader("custom-key", "mkyong")  // add request headers
                .addHeader("User-Agent", "OkHttp Bot")
                .build();

        try (Response response = httpClient.newCall(request).execute()) {

            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            // Get response body
            System.out.println(response.body().string());
        }

    }
    
    @SuppressWarnings("unchecked")
	public void sendPost(String command, String userID, String deviceID, String Server, String Place, String oAuth_Token) throws Exception {
    	
    	System.out.println("sendPost 뭐라고 나오는지 찍어보자 : " + userID +" "+ deviceID +" "+ Server +" "+ Place +" "+ oAuth_Token);
    	
    	String CommandText = command;
    	
    	JSONObject Main_jsonObject = new JSONObject();
    	
    	JSONArray directivesArray = new JSONArray();
    	
    	JSONObject payload_data = new JSONObject();
    	payload_data.put("text", CommandText);
    	
    	
    	JSONObject header_data = new JSONObject();
    	header_data.put("messageId", "09b809b0e50160f1250e4e4868a78b21");
    	header_data.put("dialogRequestId", "09b809b0e50160f1250e4e483e5bb9a4");
    	header_data.put("namespace", "Text");
    	header_data.put("referrerDialogRequestId", "");
    	header_data.put("name", "TextSource");
    	header_data.put("version", "1.0");
    	
 
    	JSONObject data_jsonObject = new JSONObject();
    	data_jsonObject.put("payload", payload_data);
    	data_jsonObject.put("header", header_data);
    
    	directivesArray.add(data_jsonObject);
    	
    	
    	@SuppressWarnings("unused")
		JSONObject directives_jsonObject = new JSONObject();
    	//directives_jsonObject.put("directives", directivesArray.toString());
    	
    	Main_jsonObject.put("directives", directivesArray);
    	Main_jsonObject.put("deviceId", deviceID);  

    	
    	System.out.println(Main_jsonObject);
    	
    	MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    	
    	// form parameters
    	@SuppressWarnings("deprecation")
		RequestBody body = RequestBody.create(JSON, Main_jsonObject.toString());
    	

    	if (Server.equals("PRD") && Place.equals("in")) {
    		System.out.println("PRD + in");
    		Request request = new Request.Builder()
                    .url("http://10.40.92.200:8080/v1/setting/deviceGateway/directive") //PRD directive URL
                    .addHeader("User-Id", userID)
                    .addHeader("Target-Device-Id", deviceID)
                    .addHeader("Content-Type", "application/json")
                    .post(body)
                    .build();
    		
    		try (Response response = httpClient.newCall(request).execute()) {

                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                // Get response body
                System.out.println(response.body().string());
            }
    	} else if (Server.equals("PRD") && Place.equals("out")) {
    		System.out.println("PRD + out");
    		Request request = new Request.Builder()
                    .url("https://api.sktnugu.com/v1/setting/deviceGateway/directive") //PRD directive URL
                    .addHeader("User-Id", userID)
                    .addHeader("Target-Device-Id", deviceID)
                    .addHeader("Auth-Token", oAuth_Token)
                    .addHeader("Content-Type", "application/json")
                    .post(body)
                    .build();
    		
    		try (Response response = httpClient.newCall(request).execute()) {

                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                // Get response body
                System.out.println(response.body().string());
    		}
    	} else if (Server.equals("STG") && Place.equals("in")) {
    		System.out.println("STG + in");
    		Request request = new Request.Builder()
                    .url("http://10.40.90.128:8080/v1/setting/deviceGateway/directive") //STG directive URL
                    .addHeader("User-Id", userID)
                    .addHeader("Target-Device-Id", deviceID)
                    .addHeader("Content-Type", "application/json")
                    .post(body)
                    .build();
    		
    		try (Response response = httpClient.newCall(request).execute()) {

                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                // Get response body
                System.out.println(response.body().string());
    		}
    	} else if (Server.equals("STG") && Place.equals("out")) {
    		System.out.println("STG + out");
    		Request request = new Request.Builder()
    				.url("https://stg-api.sktnugu.com/v1/setting/deviceGateway/directive") //STG directive URL
                    .addHeader("User-Id", userID)
                    .addHeader("Target-Device-Id", deviceID)
                    .addHeader("Auth-Token", oAuth_Token)
                    .addHeader("Content-Type", "application/json")
                    .post(body)
                    .build();
    		
    		try (Response response = httpClient.newCall(request).execute()) {

                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                // Get response body
                System.out.println(response.body().string());
    		}
    	} else {
    		System.out.println("서버 조건 불만족");
    	}

    }
    
    public String DateTime() throws Exception {

    	Calendar calendar = Calendar.getInstance();
        java.util.Date date = calendar.getTime();
        String today = (new SimpleDateFormat("yyyy.MM.dd HH").format(date));
        
        return today;
    }
    
    public String Time_min() throws Exception {

    	Calendar calendar = Calendar.getInstance();
        java.util.Date date = calendar.getTime();
        String today = (new SimpleDateFormat("mm").format(date));
        
        return today;
    }
    
    public String Time_HOUR_after() throws Exception {
    	
    	String today = null; 	 
    	Date date = new Date();
   
    	// 포맷변경 ( 년월일 시분초)
    	SimpleDateFormat sdformat = new SimpleDateFormat("yyyy.MM.dd HH"); 
    	 
    	// Java 시간 더하기
    	Calendar cal = Calendar.getInstance();

    	cal.setTime(date);
    	// 1시간 전
    	cal.add(Calendar.HOUR, -1);
    	today = sdformat.format(cal.getTime());  
        
        return today;
    	
    }
    
    public String TTS_JsonParsing(String userID, String deviceID, String Server, String Place ) throws Exception {
    	
    	Thread.sleep(9990);
    	
    	Calendar calendar = Calendar.getInstance();
        java.util.Date date = calendar.getTime();
        String today = (new SimpleDateFormat("yyyyMMdd").format(date));
        
        String logArray[];    
        
        String server = null;
        String urlStr = null;
        
        if(Server.equals("PRD")) {
        	server = "prd";
        } else if (Server.equals("STG")) {
        	server = "stg";
        }
        
        System.out.println(today);
    	
        if(Place.equals("in")) {
        	//사내망에서는 http://172.27.97.221:7090
        	urlStr = "http://172.27.97.221:7090/pulse_n/get_log/?size=7&env="+server+"&start_date="+today+"000000&unique_id="+userID+deviceID;
        	System.out.println(urlStr);
        	
        } else if (Place.equals("out")) {
        	//vpn으로는 http://10.40.89.245:8190
        	urlStr = "http://10.40.89.245:8190/pulse_n/get_log/?size=7&env="+server+"&start_date="+today+"000000&unique_id="+userID+deviceID;
        	System.out.println(urlStr);
        }
        
    	
    	URL url = new URL(urlStr);
    	
    	BufferedReader bf; 
    	String line = ""; 
    	String result=""; 

    	bf = new BufferedReader(new InputStreamReader(url.openStream())); 
    	
    	while((line=bf.readLine())!=null) { 
    		result=result.concat(line); 
    		//System.out.println(result); 
    	}
    	
    	JSONParser parser = new JSONParser(); 
    	JSONObject obj = (JSONObject) parser.parse(result);
    	
    	JSONArray parse_data_list = (JSONArray) obj.get("data");
    	System.out.println(parse_data_list.size()); 
    	
    	
    	JSONObject data;
    	String[] tts_strip = new String[7];
    	
    	for(int i = 0 ; i < parse_data_list.size(); i++) { 
    		data = (JSONObject) parse_data_list.get(i);
    			
    		JSONObject parse_source = (JSONObject) data.get("_source");
    		JSONObject parse_item = (JSONObject) parse_source.get("item");
    		
    		tts_strip[i] = (String) parse_item.get("tts_strip");

    	}
    	
    	//System.out.println(Arrays.deepToString(tts_strip));
    	
    	for(String str:tts_strip) {
    		System.out.println(str); 
    	}
    
    	logArray = tts_strip;
    	
    	String tts = Arrays.deepToString(logArray);
    	System.out.println(tts);
		return tts;

    }
    
  
    
	public void Android_BackKey() throws Exception {
		
		pressKey(new KeyEvent(AndroidKey.BACK));
	}
	public void AndroidKey_Num1() throws Exception {
		
		pressKey(new KeyEvent(AndroidKey.NUMPAD_1));
	}
	public void AndroidKey_Num2() throws Exception {
		
		pressKey(new KeyEvent(AndroidKey.NUMPAD_2));
	}
	public void AndroidKey_Num3() throws Exception {
		
		pressKey(new KeyEvent(AndroidKey.NUMPAD_3));
	}
	public void AndroidKey_Num4() throws Exception {
		
		pressKey(new KeyEvent(AndroidKey.NUMPAD_4));
	}
	public void AndroidKey_Num5() throws Exception {
		
		pressKey(new KeyEvent(AndroidKey.NUMPAD_5));
	}
	public void AndroidKey_Num6() throws Exception {
		
		pressKey(new KeyEvent(AndroidKey.NUMPAD_6));
	}
	public void AndroidKey_Num7() throws Exception {
		
		pressKey(new KeyEvent(AndroidKey.NUMPAD_7));
	}
	
	

	@Override
	public TouchScreen getTouch() {
		// TODO Auto-generated method stub
		return null;
	}

	public void ProgressBar_Loading() throws Exception {
		
		Thread.sleep(600);
		boolean 연결로딩 = this.isElementPresent(By.className("android.widget.ProgressBar"));
		if(연결로딩 == true) {
			Thread.sleep(2000);
		} else { 
			Thread.sleep(500);
		}
	}
	
	
	public boolean dataCheck_Contains(String text, Set<String> data_list) throws Exception {
  
		System.out.println("text data : " + text);
        for (String dataName : data_list) {
            if(text.contains(dataName)) {
            	System.out.println(dataName + " 에 매칭된: " + text);
            	return true;
            } 
        }
		return false;
    }
	
	public boolean dataCheck_Equals(String text, Set<String> data_list) throws Exception {
		  
		System.out.println("text data : " + text);
        for (String dataName : data_list) {
            System.out.println(text + " : " + dataName);
        	
        	if(text.equals(dataName)) {
            	System.out.println(dataName + " 에 매칭된: " + text);
            	return true;
            } 
        }
		return false;
    }

}
