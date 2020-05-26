package unit;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

//import static org.junit.Assert.*;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Rotatable;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.HasTouchScreen;
import org.openqa.selenium.interactions.touch.TouchActions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteTouchScreen;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.Reporter;
import static org.testng.Assert.fail;

import com.relevantcodes.extentreports.LogStatus;

import static org.testng.Assert.fail;

public class Utilities extends AndroidDriver<WebElement> implements TakesScreenshot { 
	
	
	RemoteTouchScreen touch;
	public TouchActions action;
	
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
	
	public static String hubAddress = "http://127.0.0.1:4723/wd/hub";
	
	public Utilities (DesiredCapabilities capability) throws MalformedURLException {
		
		this(hubAddress, capability);
		Wait = (WebDriverWait) new WebDriverWait(this, PAGE_LOAD_TIME_OUT);
	}
	
	public Utilities (String url, DesiredCapabilities capability) throws MalformedURLException { 
		
		super (new URL(url), capability);
		Wait = (WebDriverWait) new WebDriverWait(this, PAGE_LOAD_TIME_OUT);
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
	 * 단말기별 화면 해상도 저장
	 * scroll & swipe 동작 시 활용
	 */
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
		
		try {		
			WebElement element = locator.findElement((SearchContext) this);
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
	
		WebElement element = null;
		waitForIsElementPresent (locator);
		
		element = Wait.until(ExpectedConditions.elementToBeClickable(locator));
		element.click();
		
		waitProgressCompleted();
	}
	
	// to do : 앞 머리에 공백인 경우 전체 선택 안됨 -> 무한루프
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
			touchAction.longPress(element);
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
		
		WebElement element = null;
		element = waitForIsElementPresent (locator);
		
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
	
	/**
	 * Long press element action
	 * @param target element
	 * @return void
	 * @throws Exception - Exception
	 */
	public void longPress(WebElement locator) throws Exception {
		
		TouchAction action = new TouchAction(this);
		action.longPress(locator);
		action.release();
		action.perform();
	}
	
	public void longPress(By locator) throws Exception {
		
		WebElement element = null;
		element = waitForIsElementPresent (locator);
		
		TouchAction action = new TouchAction(this);
		action.longPress(element);
		action.release();
		action.perform();
	}
	
	
	/**
	 * Horizontal swipe
	 * @param direction : swipe 범위 (ex 2 : 가로 20% -> 80% 범위로 swipe)
	 * @return count : swipe 수행 횟수
	 * @throws Exception - Selenium Exception
	 */
	public void swipe(double direction, int count) throws Exception {
		
		TouchAction touchAction = new TouchAction (this);
		
		if (direction > 1.0 || direction < 0.1)
			fail ("swipe direction : 0.1 ~ 1.0");
		
        int startX = (int) (winX * direction);
        int startY = winY / 2;
        int endX = (int) (winX * (1 - direction));
        int endY = startY;
        
        for (int i = 0 ; i < count ; i ++) {
        	
        	// printLog ("swipe : [" +  startX + ", " + startY  + ", " + endX  + ", " + endY + "]");
        	touchAction.press(startX, startY).waitAction(300).moveTo(endX, endY).release().perform();
        
        	Wait.until(ExpectedConditions.refreshed(
    				ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.FrameLayout"))));
        	
        	waitProgressCompleted();
        }
	}
	
	/**
	 * Horizontal swipe
	 * @param direction : "right" or "left" / 1회 수행
	 * @throws Exception - Exception
	 */
	public void swipe (String direction) throws Exception {
		
		if (direction.equalsIgnoreCase("right")) 
			this.swipe(0.8, 1);
		else if (direction.equalsIgnoreCase("left")) 
			this.swipe(0.2, 1);
		else
			fail ("swipe direction : right or left");
	}
	
	/**
	 * Horizontal swipe
	 * @param direction : "right" or "left" / count : 수행 횟수
	 * @throws Exception - Exception
	 */
	public void swipe (String direction, int count) throws Exception {
		
		if (direction.equalsIgnoreCase("right"))
			this.swipe(0.8, count);
		else if (direction.equalsIgnoreCase("left")) 
			this.swipe(0.2, count);
		else
			fail ("scoll direction : up or down");
	}
	
	// to do : 슬라이드 메뉴 중 숨겨진 메뉴를 위한 메소드 : 실 사용을 위한 추가적인 보완 필요
	public void swipe (By locator) throws Exception {
		
		TouchAction touchAction = new TouchAction (this);

		WebElement target = null;
		target = waitForIsElementPresent(locator);
		
		int x = target.getLocation().getX();
		int y = target.getLocation().getY();
		int h = target.getSize().getHeight();
		int w = target.getSize().getWidth();
		
		//touchAction.longPress(ele1).moveTo(x1,580).release().perform();
    	touchAction.press(w-x, y).waitAction(300).moveTo(w/3, y).release().perform();
	}
	
	/**
	 * vertical scroll
	 * @param direction : scroll 범위 (ex 0.2 : 세로 20% -> 80% 범위로 하단 sroll)
	 * @return count : scroll 수행 횟수
	 * @throws Exception - Selenium Exception
	 */
	public void scroll(double direction, int count) throws Exception {
		
		TouchAction touchAction = new TouchAction (this);
		
		if (direction > 1.0 || direction < 0.1)
			fail ("scoll direction : 0.1 ~ 1.0");
		
        int startY = (int) (winY * direction);
        int startX = winX / 2;
        int endY = (int) (winY * (1 - direction));
        int endX = startX;
        
        for (int i = 0 ; i < count ; i ++) {
        
        	// printLog ("scroll : [" +  startX + ", " + startY  + ", " + endX  + ", " + endY + "]");
        	touchAction.press(startX, startY).waitAction(300).moveTo(endX, endY).release().perform();
        
        	Wait.until(ExpectedConditions.refreshed(
    				ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.FrameLayout"))));
        	
        	waitProgressCompleted();
        }
	}
	
	/**
	 * Vertical scroll
	 * @param direction : "up" or "down" / 1회 수행
	 * @throws Exception - Exception
	 */
	public void scrollUp () throws Exception {scroll(0.2, 1);}
	public void scrollUp (int count) throws Exception {
		for (int i = 0 ; i < count ; i ++) this.scrollUp();
	}
	public void scrollDown () throws Exception {scroll(0.7, 1);}
	public void scrollDown (int count) throws Exception {
		for (int i = 0 ; i < count ; i ++) this.scrollDown();
	}
	public void scrollTo (By locator, int count) throws Exception {
		
		for (int i = 0 ; i < count ; i ++) {
			if (this.isElementPresent(locator)) {
				return;
			}
			this.scrollDown();
		}
		//fail("scroll : element not found");
	}
	
	public void scrollToText (String text, int count) throws Exception {
		
		for (int i = 0 ; i < count ; i ++) {
			if (this.isTextPresent(text)) {
				return;
			}
			this.scroll(0.6, 1);
		}
		//fail("scroll : element not found");
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
	
	/**
	  * 물리적인 keyboard 입력을 처리하는 메소드
	  * @param value	Press할 키보드 값
	  * @return void
	  */
	public void pressKeys(Keys value) throws Exception {
		
		Actions actions = new Actions(this);
		actions.sendKeys(value).perform();
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
    
    public String ErrorScreenshots(WebDriver driver,String screenShotName) throws IOException
    {
        TakesScreenshot ts = (TakesScreenshot)driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        String dest = ".\\ErrorScreenshots\\"+screenShotName+".png";
        File destination = new File(dest);
        FileUtils.copyFile(source, destination);        
                     
        return dest;
    }
	
	
		
}
