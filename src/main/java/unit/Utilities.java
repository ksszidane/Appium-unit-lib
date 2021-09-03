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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ByteArrayInputStream;
import java.io.BufferedReader;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.ZoneId;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Set;
import java.util.TimeZone;
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
import org.testng.SkipException;
import org.testng.annotations.Parameters;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.Connection;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import static org.testng.Assert.fail;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.relevantcodes.extentreports.LogStatus;

import TestNG_Set.Chips_TestCase;
import TestNG_Set.NUGU_TestCase;

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
	 * 현재 열려있는 창의 인덱스 수집 후 그 번호에 맞는 창전환
	 * @param 윈도우 URL 값의 contains
	 * @throws Exception
	 */
	public void switchToWindwosURL(String URL) throws Exception {
		
		Set<String> allWindows = getWindowHandles();
		if(!allWindows.isEmpty()) {
			for (String windowId : allWindows) {
				if(switchTo().window(windowId).getCurrentUrl().contains(URL)) {
					waitForPageToLoad();
					break;
				}
			}
		}
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
	
	/**
	 * 현재 열려있는 창의 인덱스 수집 후 갯수 카운터로 반환
	 * @param 윈도우 인덱스 번호
	 * @throws Exception
	 */
	public int allWindwosIndexCount() throws Exception {
		
		
		ArrayList<String> AllWindows = new ArrayList<String>(getWindowHandles());
		return AllWindows.size();
       
	}
	
	public String getTime() {
		
		Calendar cal = Calendar.getInstance();
		//SimpleDateFormat form = new SimpleDateFormat("yyyy.MM.dd-");
		SimpleDateFormat form = new SimpleDateFormat("yyyyMMddHHmmss");
		
		return form.format(cal.getTime());	
	}
	
	public int getHour() {
		int xInt = 0;
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Seoul"),Locale.KOREA);
		SimpleDateFormat form = new SimpleDateFormat("H");
		xInt = Integer.parseInt(form.format(cal.getTime()));
		return xInt;
	}
	
	public int getHourMinute() {
		int xInt = 0;
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Seoul"),Locale.KOREA);
		SimpleDateFormat form = new SimpleDateFormat("Hmm");
		xInt = Integer.parseInt(form.format(cal.getTime()));
		return xInt;
	}
	
	public int getMonth() {
		int xInt = 0;
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Seoul"),Locale.KOREA);
		SimpleDateFormat form = new SimpleDateFormat("M");
		xInt = Integer.parseInt(form.format(cal.getTime()));
		return xInt;
	}
	public int getMonthDay() {
		int xInt = 0;
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Seoul"),Locale.KOREA);
		SimpleDateFormat form = new SimpleDateFormat("MMdd");
		xInt = Integer.parseInt(form.format(cal.getTime()));
		return xInt;
	}
	
	public String StockTimeTable() throws Exception {
		String section = "" ;
		int xInt = getHourMinute();
		System.out.println(xInt);
		
		if (0 <= xInt && xInt < 900 ) {
			section = "장전";
		} else if (900 <= xInt && xInt < 1530) {
			section = "장중";
		} else {
			section = "장종";
		}
		return section;
	}
	
	public String Hour00to18() throws Exception {
		String section = "" ;
		int xInt = getHour();
		
		if (0 <= xInt && xInt < 18 ) {
			section = "A구간";
		} else if (18 <= xInt && xInt < 24) {
			section = "B구간";
		}
		return section;
	}
	
	public String Hour00to06() throws Exception {
		String section = "" ;
		int xInt = getHour();
		
		if (0 <= xInt && xInt < 6 ) {
			section = "A구간";
		} else if (6 <= xInt && xInt < 24) {
			section = "B구간";
		}
		return section;
	}
	
	public String Month5to9() throws Exception {
		String section = "" ;
		int xInt = getMonth();
		
		if (5 <= xInt && xInt <= 9 ) {
			section = "A구간";
		} else {
			section = "B구간";
		}
		return section;
	}
	
	public String Month4to11() throws Exception {
		String section = "" ;
		int xInt = getMonth();
		
		if (4 <= xInt && xInt <= 11 ) {
			section = "A구간";
		} else {
			section = "B구간";
		}
		return section;
	}
	
	public String Month1015to90415() throws Exception {
		String section = "" ;
		int xInt = getMonthDay();
		
		if (1014 < xInt && xInt <= 1231 ) {
			section = "B구간";
		} else if (0101 <= xInt && xInt < 0415 ) {
			section = "C구간";
		} else {
			section = "A구간";
		}
		return section;
	}
	
	/**
	 * 현재 날짜 및 시간 정보 [yyyy.MM.dd_HH:mm:ss]
	 * @throws Exception - Exception
	 */
	public String getDate() {
		
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Seoul"),Locale.KOREA);
		SimpleDateFormat form = new SimpleDateFormat("[yyyy.MM.dd_HH:mm:ss]");
		return form.format(cal.getTime());	
	}
	
	public String getSimpleDate() {
		
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Seoul"),Locale.KOREA);
		SimpleDateFormat form = new SimpleDateFormat("yyMMdd");
		return form.format(cal.getTime());	
	}
	
	public String getKoreaDate() {
		
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Seoul"),Locale.KOREA);
		SimpleDateFormat form = new SimpleDateFormat("M월 d일");
		return form.format(cal.getTime());	
		
	}
	
	public String getJustYesterdayDate() {
		
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Seoul"),Locale.KOREA);
		SimpleDateFormat form = new SimpleDateFormat("M월 d일");
		cal.add(Calendar.DATE, -1);
		return form.format(cal.getTime());	
	}
	
	public String getChangePreviousDate(int date) {
		
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Seoul"),Locale.KOREA);
		SimpleDateFormat form = new SimpleDateFormat("M월 d일");
		cal.add(Calendar.DATE, date);
		return form.format(cal.getTime());	
	}
	
	/**
	 * 특정 날짜에 대하여 요일을 구함(일 ~ 토)
	 * @param date 금일기준의 +/- 일자 0이면 오늘
	 * @return
	 * @throws Exception
	 */
	public String getDayOfWeek(int date) throws Exception {
	 
	    String day = "" ;
	     
	    Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Seoul"),Locale.KOREA) ;
	    cal.add(Calendar.DATE, date);
	     
	    int dayNum = cal.get(Calendar.DAY_OF_WEEK) ;

	    switch(dayNum){
	        case 1:
	            day = "일";
	            break ;
	        case 2:
	            day = "월";
	            break ;
	        case 3:
	            day = "화";
	            break ;
	        case 4:
	            day = "수";
	            break ;
	        case 5:
	            day = "목";
	            break ;
	        case 6:
	            day = "금";
	            break ;
	        case 7:
	            day = "토";
	            break ;
	             
	    }
	    return day ;
	}
	
	public String getWeekSaturday() throws Exception {
		
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Seoul"),Locale.KOREA);
		SimpleDateFormat form = new SimpleDateFormat("M월 d일");
		cal.set(Calendar.DAY_OF_WEEK,Calendar.SATURDAY);
		return form.format(cal.getTime());	
	}
	
	public String getNextWeekSaturday() throws Exception {
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Seoul"),Locale.KOREA);
		SimpleDateFormat form = new SimpleDateFormat("M월 d일");
		cal.add(Calendar.DATE, 7);
		cal.set(Calendar.DAY_OF_WEEK,Calendar.SATURDAY);
		return form.format(cal.getTime());	
	}
	public String getLastWeekSunday() throws Exception {
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Seoul"),Locale.KOREA);
		SimpleDateFormat form = new SimpleDateFormat("M월 d일");
		cal.add(Calendar.DATE, 0);
		cal.set(Calendar.DAY_OF_WEEK,Calendar.SUNDAY);
		return form.format(cal.getTime());	
	}
	
	public String compare9Time() throws ParseException {
		String section = "" ;
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Seoul"),Locale.KOREA);
	    
		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
	    Date time = format.parse(format.format(cal.getTime()));
		Date date1 = format.parse("00:00:00");
		Date date2 = format.parse("09:00:00");
		
		System.out.println(time);
		System.out.println(date1);
		System.out.println(date2);
		
	    if(time.after(date1) && time.before(date2)){
	    	section = "9시이전";
	    } else {
	    	section = "9시이후";
	    }
	    return section;
	   
	}
	
	public String compare12Time() throws ParseException {
		String section = "" ;
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Seoul"),Locale.KOREA);
	    
		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
	    Date time = format.parse(format.format(cal.getTime()));
		Date date1 = format.parse("00:00:00");
		Date date2 = format.parse("12:00:00");
		
		System.out.println(time);
		System.out.println(date1);
		System.out.println(date2);
		
	    if(time.after(date1) && time.before(date2)){
	    	section = "12시이전";
	    } else {
	    	section = "12시이후";
	    }
	    return section;
	   
	}
	
	public int calDateBetweenAandB() throws Exception {
		
		int xInt = 0;
		
	    try{ 

			SimpleDateFormat form = new SimpleDateFormat("M월 d일");
	 
	        Date FirstDate = form.parse(this.getKoreaDate());
	        Date SecondDate = form.parse(this.getNextWeekSaturday());
	
	        long calDate = FirstDate.getTime() - SecondDate.getTime(); 
	        
	        long calDateDays = calDate / ( 24*60*60*1000); 
	 
	        calDateDays = Math.abs(calDateDays);
	        
	        System.out.println("두 날짜의 날짜 차이: "+calDateDays);
	        xInt = Long.valueOf(calDateDays).intValue();
	        
	        }
	        catch(ParseException e)
	        {
	            // 예외 처리
	        }
	    return xInt;
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
		
		Thread.sleep(1500);
	    TouchAction touchAction = new TouchAction(this);
	    

	    touchAction.press(PointOption.point(startx, starty))
	    		   //.waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1)))
	    		   .waitAction()
	               .moveTo(PointOption.point(endx, endy))
	               .release()
	               .perform();
	    
	    Thread.sleep(1500);

	}
	
	/** 
	 * @param int startx - the starting x position
	 * @param int starty - the starting y position
	 * @param int endx - the ending x position
	 * @param int endy - the ending y position
	 * @throws InterruptedException 
	 */
	@SuppressWarnings("rawtypes")
	public void fastSwipe(int startx, int starty, int endx, int endy) throws InterruptedException {
		
		Thread.sleep(100);
	    TouchAction touchAction = new TouchAction(this);
	    

	    touchAction.press(PointOption.point(startx, starty))
	    		   //.waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1)))
	    		   .waitAction()
	               .moveTo(PointOption.point(endx, endy))
	               .release()
	               .perform();
	    
	    Thread.sleep(100);

	}
	
	public void swipe (int startx, int starty, int endx, int endy, int count) throws Exception {
		
		for (int i = 0 ; i < count ; i ++) this.swipe(startx, starty, endx, endy);
	}
	
	public void fastSwipe (int startx, int starty, int endx, int endy, int count) throws Exception {
		
		for (int i = 0 ; i < count ; i ++) this.swipe(startx, starty, endx, endy);
	}
	
	@SuppressWarnings("rawtypes")
	public void touchTab(int x, int y) throws InterruptedException {
		
		Thread.sleep(2000);
	    TouchAction touchAction = new TouchAction(this);
	    
	    touchAction.press(PointOption.point(x, y)).release().perform();
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

	public void refresh() {
		
			this.refresh();
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
		
		Thread.sleep(500);
		for (int i = 1 ; i < 5 ; i ++) {
			try {	
				if(this.isElementPresent(By.xpath("//android.widget.FrameLayout/android.widget.LinearLayout"
						+ "/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout"
						+ "/android.widget.FrameLayout/android.widget.LinearLayout"
						+ "/android.widget.TextView[@text='연결을 기다리는 디바이스가 있어요.']"))) {
					System.out.println("연결을 기다리는 디바이스 상단 알림창 [있음] "+i+"/4");	
					System.out.println("상단 푸쉬배너 스와이프하여 없애기 \n");
					this.fastSwipe(560, 180, 560, 78);
					i = i+4;
					Thread.sleep(300);
					break;

				} else {
					System.out.println("연결을 기다리는 디바이스 상단 알림창 [없음] "+i+"/4 \n");
					Thread.sleep(100);
				}

			} catch (StaleElementReferenceException e) {
				System.out.println("연결을 기다리는 디바이스 상단 알림창 [없음]");
			}
		}   		
	}
	
	public void connectingDevice_SkipCheck() throws Exception {

		Thread.sleep(300);
		for (int i = 1 ; i < 5 ; i ++) {
			//boolean view = this.isElementPresent(By.xpath("//*[@text='연결을 기다리는 디바이스가 있어요.']"));
			if(this.isElementPresent(By.xpath("//android.widget.FrameLayout/android.widget.LinearLayout"
					+ "/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout"
					+ "/android.widget.FrameLayout/android.widget.LinearLayout"
					+ "/android.widget.TextView[@text='연결을 기다리는 디바이스가 있어요.']"))) {
				System.out.println("연결을 기다리는 디바이스 상단 알림창 [있음] "+i+"/4 \n");
			
			} else {
				System.out.println("연결을 기다리는 디바이스 상단 알림창 [없음]\n");
				System.out.println("테스트 스킵");
				throw new SkipException("연결을 기다리는 디바이스 상단 알림창 [없음] "+i+"/4 \n");
				
			}
		}
		Thread.sleep(300);

	}
	

	public boolean pushMessage(String Text) throws Exception {
		
		boolean result = false;
		for (int i = 1 ; i < 8 ; i ++) {
			//boolean view = this.isElementPresent(By.xpath("//*[@text='연결을 기다리는 디바이스가 있어요.']"));
			try {	
				if(this.isElementPresent(By.xpath("//*[@text='"+Text+"']"))) {
					System.out.println(Text + "메시지 푸쉬  [있음] "+i+"/5");	
					result = true;
					break;
					
				} else {
					System.out.println(Text + "메시지 푸쉬  [없음] "+i+"/5");	
				}
			} catch (StaleElementReferenceException e) {
					System.out.println("연결을 기다리는 디바이스 상단 알림창 [없음]");
			}
		}
			return result;	
			
		
			
	}
	
	public String toastMessage(String containText) throws Exception {

		//WebDriverWait wait = (WebDriverWait) new WebDriverWait(this, TIME_OUT_SEC);
		
		//wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/hierarchy/android.widget.Toast")));
		String toastMessage = this.getText(By.xpath("//*[contains(@text,'" + containText + "')]"));
		return toastMessage;
	
		
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
	
	/**
	 * Element가 fucused 되었는지 확인하는 메소드
	 * @param locator 존재 확인 할 Element를 지정
	 * @return boolean
	 */
	public boolean isFucused(By locator) throws Exception{
		
		boolean result = false;
		String value = this.findElement(locator).getAttribute("fucused");
		if (value.equals("true")) {
			System.out.println(1);
			result = true;
		} else if (value.equals("false")) {
			System.out.println(2);
			result = false;
		}
	
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
	 * Element click / toggle & switch off
	 * @param locator swtich on 상태이면 클릭 (set off)
	 * @return 
	 * @throws Exception - Selenium Exception
	 */
	public boolean isChecked(By locator) throws Exception {
		
		boolean result = false;
		WebElement element = waitForIsElementPresent (locator);
		String isChecked = element.getAttribute("checked");
		
		if (isChecked.equals("true")) {
			return true;
		}
		return result;
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
	
	public String fast_getText(By locator) throws Exception {
	
		WebElement element = null;
		element = findElement (locator);
		
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
	
	public int getAttribute_index(By locator, String attr) throws Exception {
		
		
		List<WebElement> myElements = this.findElements(locator);
		int count = 0;
		for(WebElement e : myElements) {

		    count++;
		    if(e.getText().equals(attr)) {
		        System.out.println(count); //This will give the index value
		    }
		    else{
		        //do something else
		    }
		}
		return count;
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
    	
    	Thread.sleep(6000);
    	
    	Set<String> contextNames = getContextHandles(); 
        for (String contextName : contextNames) {
            System.out.println("context 목록  : " + contextName);
            if(contextName.contains(context)) {
            	System.out.println("적용 될 context 이름은 : " +contextName);
            	this.context(contextName);
            	break;
            }
        }
        System.out.println("스위칭 완료 context : " + getContext() + "\n");
        
        Thread.sleep(700);
 	
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
    	
    	System.out.println("sendPost 발동 옵션: | 발화문 :  "+ command +" | 서버 : "+ Server +" | Auth_Token : "+ oAuth_Token);
    	
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
    	
    	Thread.sleep(9000);

    }
    
    @SuppressWarnings("unchecked")
   	public void SWFsendPost(String command, String Server, String Access_Token) throws Exception {
       	
       	System.out.println("sendPost 발동 옵션: | 발화문 :  "+ command +" | 서버 : "+ Server +" | Access_Token : "+ Access_Token);
       	
       	String CommandText = command;
       	
       	JSONObject Main_jsonObject = new JSONObject();
       	JSONObject clientStatus_data = new JSONObject();
       	clientStatus_data.put("nugu_sdk_version", "4.5.0");
       	
       	Main_jsonObject.put("requestId", "ALDF3NYA6C0D0C654DD2;asr;;210415-220425;43033381;");  
       	Main_jsonObject.put("requestText", CommandText);
       	Main_jsonObject.put("accessToken", Access_Token);
       	Main_jsonObject.put("clientStatus", clientStatus_data);
       	Main_jsonObject.put("clientVersion", "1.1.0");
       	Main_jsonObject.put("flowCode", "NLU01");
       	
       	System.out.println(Main_jsonObject);
       	
       	MediaType JSON = MediaType.parse("application/json; charset=utf-8");
       	
       	// form parameters
       	@SuppressWarnings("deprecation")
   		RequestBody body = RequestBody.create(JSON, Main_jsonObject.toString());
       	

       	if (Server.equals("PRD")) {
       		System.out.println("PRD");
       		Request request = new Request.Builder()
                       .url("https://pif.t-aicloud.co.kr/nlp/rest/nlu") //PRD directive URL
                       .addHeader("Content-Type", "application/json")
                       .addHeader("cache-control", "no-cache")
                       .addHeader("X-AI-Access-Token", Access_Token)
                       .addHeader("Accept", "application/json")
                       .post(body)
                       .build();
       		
       		try (Response response = httpClient.newCall(request).execute()) {

                   if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                   // Get response body
                   System.out.println(response.body().string());
               }
       	} else if (Server.equals("RTG")) {
       		System.out.println("RTG");
       		Request request = new Request.Builder()
                       .url("http://rtg-pif-ai.aicloud.kr/nlp/rest/nlu") //PRD directive URL
                       .addHeader("Content-Type", "application/json")
                       .addHeader("cache-control", "no-cache")
                       .addHeader("X-AI-Access-Token", Access_Token)
                       .addHeader("Accept", "application/json")
                       .post(body)
                       .build();
       		
       		try (Response response = httpClient.newCall(request).execute()) {

                   if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                   // Get response body
                   System.out.println(response.body().string());
               }
       	} else if (Server.equals("STG")) {
       		System.out.println("STG");
       		Request request = new Request.Builder()
       				.url("http://stg-pif-ai.aicloud.kr/nlp/rest/nlu") //STG directive URL
                       .addHeader("Content-Type", "application/json")
                       .addHeader("cache-control", "no-cache")
                       .addHeader("X-AI-Access-Token", Access_Token)
                       .addHeader("Accept", "application/json")
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
       	
       	Thread.sleep(8000);

       }
    
    @SuppressWarnings("unchecked")
   	public void SWFsendPost_playStatus(String command, String Server, String Access_Token, String play_type) throws Exception {
       	
       	System.out.println("sendPost 발동 옵션: | 발화문 :  "+ command +" | 서버 : "+ Server +" | Access_Token : "+ Access_Token +" | play_type : " + play_type);
       	
       	String CommandText = command;
       	
       	JSONObject Main_jsonObject = new JSONObject();
       	JSONObject clientStatus_data = new JSONObject();
       	clientStatus_data.put("nugu_sdk_version", "4.5.0");
       	clientStatus_data.put("play_type", play_type);
       	clientStatus_data.put("play_status", "play");
       	
       	Main_jsonObject.put("requestId", "ALDF3NYA6C0D0C654DD2;asr;;210415-220425;43033381;");  
       	Main_jsonObject.put("requestText", CommandText);
       	Main_jsonObject.put("accessToken", Access_Token);
       	Main_jsonObject.put("clientStatus", clientStatus_data);
       	Main_jsonObject.put("clientVersion", "1.1.0");
       	Main_jsonObject.put("flowCode", "NLU01");
       	
       	System.out.println(Main_jsonObject);
       	
       	MediaType JSON = MediaType.parse("application/json; charset=utf-8");
       	
       	// form parameters
       	@SuppressWarnings("deprecation")
   		RequestBody body = RequestBody.create(JSON, Main_jsonObject.toString());
       	

       	if (Server.equals("PRD")) {
       		System.out.println("PRD");
       		Request request = new Request.Builder()
                       .url("https://pif.t-aicloud.co.kr/nlp/rest/nlu") //PRD directive URL
                       .addHeader("Content-Type", "application/json")
                       .addHeader("cache-control", "no-cache")
                       .addHeader("X-AI-Access-Token", Access_Token)
                       .addHeader("Accept", "application/json")
                       .post(body)
                       .build();
       		
       		try (Response response = httpClient.newCall(request).execute()) {

                   if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                   // Get response body
                   System.out.println(response.body().string());
               }
       	} else if (Server.equals("RTG")) {
       		System.out.println("RTG");
       		Request request = new Request.Builder()
                       .url("http://rtg-pif-ai.aicloud.kr/nlp/rest/nlu") //PRD directive URL
                       .addHeader("Content-Type", "application/json")
                       .addHeader("cache-control", "no-cache")
                       .addHeader("X-AI-Access-Token", Access_Token)
                       .addHeader("Accept", "application/json")
                       .post(body)
                       .build();
       		
       		try (Response response = httpClient.newCall(request).execute()) {

                   if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                   // Get response body
                   System.out.println(response.body().string());
               }
       	} else if (Server.equals("STG")) {
       		System.out.println("STG");
       		Request request = new Request.Builder()
       				.url("http://stg-pif-ai.aicloud.kr/nlp/rest/nlu") //STG directive URL
                       .addHeader("Content-Type", "application/json")
                       .addHeader("cache-control", "no-cache")
                       .addHeader("X-AI-Access-Token", Access_Token)
                       .addHeader("Accept", "application/json")
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
       	
       	Thread.sleep(7000);

       }
    
    
    @SuppressWarnings("unchecked")
   	public String NUGU_Insight_Token(String TestPlace) throws Exception {
       	
       	System.out.println("NUGU_Insight_Token 발동 옵션: | 장소 :  "+ TestPlace);
       	
       	String s = null;
       	
       	JSONObject Main_jsonObject = new JSONObject();

       	Main_jsonObject.put("identity", "kei_es");  
       	Main_jsonObject.put("password", "nhn12345!!");
       	
       	MediaType JSON = MediaType.parse("application/json; charset=utf-8");
       	
       	// form parameters
       	@SuppressWarnings("deprecation")
   		RequestBody body = RequestBody.create(JSON, Main_jsonObject.toString());
       	

       	if (TestPlace.equals("in")) {
       		System.out.println("in");
       		Request request = new Request.Builder()
                       .url("http://172.27.97.221:7090/auth") 
                       .addHeader("Content-Type", "application/json")
                       .post(body)
                       .build();
       		
       		try (Response response = httpClient.newCall(request).execute()) {

                   if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                   // Get response body
                   //System.out.println(response.body().string());
                   s = response.body().string();
               }
       	} else if (TestPlace.equals("out")) {
       		System.out.println("out");
       		Request request = new Request.Builder()
                       .url("http://10.40.89.245:8190/auth") 
                       .post(body)
                       .build();
       		
       		try (Response response = httpClient.newCall(request).execute()) {

                   if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                   // Get response body
                   //System.out.println(response.body().string());
                   s = response.body().string();
               }
       	} else {
       		System.out.println("조회 조건 불만족");
       	}

       	JSONParser parser = new JSONParser(); 
    	JSONObject obj = (JSONObject) parser.parse(s);
        String access_token = (String) obj.get("access_token");
    
		return access_token;
       	

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
    	
    	String access_token = NUGU_Insight_Token(Place);
    	
    	Thread.sleep(7000);
    	String transaction_id="";
    	
    	Calendar calendar = Calendar.getInstance();
        java.util.Date date = calendar.getTime();
        String today = (new SimpleDateFormat("yyyyMMdd").format(date));
        
        String logArray[];    
        
        String server = null;
        String urlStr = null;
        int size = 3;
        int repeat = 1;
        
        if(Server.equals("PRD")) {
        	server = "prd";
        } else if (Server.equals("STG")) {
        	server = "stg";
        } else if (Server.equals("RTG")) {
        	server = "rtg";
        }
        
        System.out.println("오늘날짜 : " + today);
        System.out.println("대상서버 : " + server);
    	
        if(Place.equals("in")) {
        	//사내망에서는 http://172.27.97.221:7090
        	urlStr = "http://172.27.97.221:7090/pulse_n/get_log/?size="+size+"&env="+server+"&start_date="+today+"000000&unique_id="+userID+deviceID;
        	System.out.println(urlStr);
        	
        } else if (Place.equals("out")) {
        	//vpn으로는 http://10.40.89.245:8190
        	urlStr = "http://10.40.89.245:8190/pulse_n/get_log/?size="+size+"&env="+server+"&start_date="+today+"000000&unique_id="+userID+deviceID;
        	System.out.println(urlStr);
        }
        
    	
    	URL url = new URL(urlStr);
    	Request request = new Request.Builder()
        		.url(url) 
                .addHeader("Authorization", "Bearer " + access_token)
                .get()
                .build();
		
        Response response = httpClient.newCall(request).execute();

        // Get response body
		//System.out.println(response.body().string());
        String api_get_result = response.body().string();
    	
    	BufferedReader bf; 
    	String line = ""; 
    	
    	int x = 0;
    	String[] tts_strip = new String[size*repeat];
    	//String[] tts_strip = new String[size];
    	
    	for (int y=0; y < repeat; y++) {
    		Thread.sleep(2000);
    		
    		String result=""; 
    		InputStream is = new ByteArrayInputStream(api_get_result.getBytes());
			bf = new BufferedReader(new InputStreamReader(is)); 
        	
        	while((line=bf.readLine())!=null) { 
        		result=result.concat(line); 
        		//System.out.println(result); 
        	}
        	
        	JSONParser parser = new JSONParser(); 
        	JSONObject obj = (JSONObject) parser.parse(result);
        	
        	JSONArray parse_data_list = (JSONArray) obj.get("data");
        	//System.out.println("parse_data_list.size() : " + parse_data_list.size()); 
        	
        	JSONObject data;
        	
        	for(int i = 0 ; i < parse_data_list.size(); i++) { 
        		data = (JSONObject) parse_data_list.get(i);
        			
        		JSONObject parse_source = (JSONObject) data.get("_source");
        		JSONObject parse_item = (JSONObject) parse_source.get("item");
        		
        		tts_strip[i] = (String) parse_item.get("tts_strip");
        		tts_strip[x] = (String) parse_item.get("tts_strip");
        		transaction_id = (String) parse_item.get("transaction_id");
        		System.out.println("transaction_id : " + transaction_id);
        		x++;

        	}
    	}
    	
    	
    	//System.out.println(Arrays.deepToString(tts_strip));
    	
    	for(String str:tts_strip) {
    		//System.out.println(str); 
    	}
    
    	logArray = tts_strip;
    	
    	String tts = Arrays.deepToString(logArray);
    	System.out.println("tts : " + tts);
		return tts;

    }
    
    public String TTS_JsonParsing_delay(String userID, String deviceID, String Server, String Place, int time) throws Exception {
    	
    	String access_token = NUGU_Insight_Token(Place);
    	
    	Thread.sleep(time);
    	String transaction_id="";
    	
    	Calendar calendar = Calendar.getInstance();
        java.util.Date date = calendar.getTime();
        String today = (new SimpleDateFormat("yyyyMMdd").format(date));
        
        String logArray[];    
        
        String server = null;
        String urlStr = null;
        int size = 1;
        int repeat = 1;
        
        if(Server.equals("PRD")) {
        	server = "prd";
        } else if (Server.equals("STG")) {
        	server = "stg";
        } else if (Server.equals("RTG")) {
        	server = "rtg";
        }
        
        System.out.println("오늘날짜 : " + today);
        System.out.println("대상서버 : " + server);
    	
        if(Place.equals("in")) {
        	//사내망에서는 http://172.27.97.221:7090
        	urlStr = "http://172.27.97.221:7090/pulse_n/get_log/?size="+size+"&env="+server+"&start_date="+today+"000000&unique_id="+userID+deviceID;
        	System.out.println(urlStr);
        	
        } else if (Place.equals("out")) {
        	//vpn으로는 http://10.40.89.245:8190
        	urlStr = "http://10.40.89.245:8190/pulse_n/get_log/?size="+size+"&env="+server+"&start_date="+today+"000000&unique_id="+userID+deviceID;
        	System.out.println(urlStr);
        }
        
    	
    	URL url = new URL(urlStr);
    	Request request = new Request.Builder()
        		.url(url) 
                .addHeader("Authorization", "Bearer " + access_token)
                .get()
                .build();
		
        Response response = httpClient.newCall(request).execute();

        // Get response body
		//System.out.println(response.body().string());
        String api_get_result = response.body().string();
    	
    	BufferedReader bf; 
    	String line = ""; 
    	
    	int x = 0;
    	String[] tts_strip = new String[size*repeat];
    	//String[] tts_strip = new String[size];
    	
    	for (int y=0; y < repeat; y++) {
    		Thread.sleep(2000);
    		
    		String result=""; 
    		InputStream is = new ByteArrayInputStream(api_get_result.getBytes());
			bf = new BufferedReader(new InputStreamReader(is)); 
        	
        	while((line=bf.readLine())!=null) { 
        		result=result.concat(line); 
        		//System.out.println(result); 
        	}
        	
        	
        	JSONParser parser = new JSONParser(); 
        	JSONObject obj = (JSONObject) parser.parse(result);
        	
        	JSONArray parse_data_list = (JSONArray) obj.get("data");
        	//System.out.println("parse_data_list.size() : " + parse_data_list.size()); 
        	
        	JSONObject data;
        	
        	for(int i = 0 ; i < parse_data_list.size(); i++) { 
        		data = (JSONObject) parse_data_list.get(i);
        			
        		JSONObject parse_source = (JSONObject) data.get("_source");
        		JSONObject parse_item = (JSONObject) parse_source.get("item");
        		
        		tts_strip[i] = (String) parse_item.get("tts_strip");
        		tts_strip[x] = (String) parse_item.get("tts_strip");
        		transaction_id = (String) parse_item.get("transaction_id");
        		System.out.println("transaction_id : " + transaction_id);
        		x++;

        	}
    	}
    	
    	
    	//System.out.println(Arrays.deepToString(tts_strip));
    	
    	for(String str:tts_strip) {
    		//System.out.println(str); 
    	}
    
    	logArray = tts_strip;
    	
    	String tts = Arrays.deepToString(logArray);
    	System.out.println("tts : " + tts);
		return tts;

    }
    
    public String TTS_JsonParsing_most_recent(String userID, String deviceID, String Server, String Place ) throws Exception {
    	
    	String access_token = NUGU_Insight_Token(Place);
    	
    	Calendar calendar = Calendar.getInstance();
        java.util.Date date = calendar.getTime();
        String today = (new SimpleDateFormat("yyyyMMdd").format(date));
        
        String logArray[];    
        String transaction_id="";
        
        String server = null;
        String urlStr = null;
        int size = 1;
        int repeat = 1;
        
        if(Server.equals("PRD")) {
        	server = "prd";
        } else if (Server.equals("STG")) {
        	server = "stg";
        } else if (Server.equals("RTG")) {
        	server = "rtg";
        }
        
        System.out.println("오늘날짜 : " + today);
        System.out.println("대상서버 : " + server);
    	
        if(Place.equals("in")) {
        	//사내망에서는 http://172.27.97.221:7090
        	urlStr = "http://172.27.97.221:7090/pulse_n/get_log/?size="+size+"&env="+server+"&start_date="+today+"000000&unique_id="+userID+deviceID;
        	System.out.println(urlStr);
        	
        } else if (Place.equals("out")) {
        	//vpn으로는 http://10.40.89.245:8190
        	urlStr = "http://10.40.89.245:8190/pulse_n/get_log/?size="+size+"&env="+server+"&start_date="+today+"000000&unique_id="+userID+deviceID;
        	System.out.println(urlStr);
        }
        
    	URL url = new URL(urlStr);
    	
    	Request request = new Request.Builder()
        		.url(url) 
                .addHeader("Authorization", "Bearer " + access_token)
                .get()
                .build();
		
        Response response = httpClient.newCall(request).execute();

        // Get response body
		//System.out.println(response.body().string());
        String api_get_result = response.body().string();
    	
    	BufferedReader bf; 
    	String line = ""; 
    	
    	int x = 0;
    	String[] tts_strip = new String[size*repeat];
    	//String[] tts_strip = new String[size];
    	
    	Thread.sleep(7000);
    	for (int y=0; y < repeat; y++) {
    		
    		String result=""; 
    		InputStream is = new ByteArrayInputStream(api_get_result.getBytes());
			bf = new BufferedReader(new InputStreamReader(is)); 
        	
        	while((line=bf.readLine())!=null) { 
        		result=result.concat(line); 
        		//System.out.println(result); 
        	}
        	
        	
        	JSONParser parser = new JSONParser(); 
        	JSONObject obj = (JSONObject) parser.parse(result);
        	
        	JSONArray parse_data_list = (JSONArray) obj.get("data");
        	//System.out.println("parse_data_list.size() : " + parse_data_list.size()); 
        	
        	JSONObject data;
        	
        	for(int i = 0 ; i < parse_data_list.size(); i++) { 
        		data = (JSONObject) parse_data_list.get(i);
        			
        		JSONObject parse_source = (JSONObject) data.get("_source");
        		JSONObject parse_item = (JSONObject) parse_source.get("item");
        		
        		tts_strip[i] = (String) parse_item.get("tts_strip");
        		tts_strip[x] = (String) parse_item.get("tts_strip");
        		transaction_id = (String) parse_item.get("transaction_id");
        		System.out.println("transaction_id : " + transaction_id);
        		x++;

        	}
    	}
    	
    	
    	//System.out.println(Arrays.deepToString(tts_strip));
    	
    	for(String str:tts_strip) {
    		//System.out.println(str); 
    	}
    
    	logArray = tts_strip;
    	
    	String s = Arrays.deepToString(logArray);
    	String s1 = s.replace("[", "");
    	String tts = s1.replace("]", "");
    	System.out.println("tts : " + tts);
		return tts;
		
    }
    
    public String TTS_JsonParsing(String userID, String deviceID, String Server, String Place, int size) throws Exception {
    	
    	String access_token = NUGU_Insight_Token(Place);
    	
    	Calendar calendar = Calendar.getInstance();
        java.util.Date date = calendar.getTime();
        String today = (new SimpleDateFormat("yyyyMMdd").format(date));
        
        String logArray[];    
        String transaction_id="";
        
        String server = null;
        String urlStr = null;
        
        int repeat = 2;
        
        if(Server.equals("PRD")) {
        	server = "prd";
        } else if (Server.equals("STG")) {
        	server = "stg";
        } else if (Server.equals("RTG")) {
        	server = "rtg";
        }
        
        System.out.println("오늘날짜 : " + today);
        System.out.println("대상서버 : " + server);
    	
        if(Place.equals("in")) {
        	//사내망에서는 http://172.27.97.221:7090
        	urlStr = "http://172.27.97.221:7090/pulse_n/get_log/?size="+size+"&env="+server+"&start_date="+today+"000000&unique_id="+userID+deviceID;
        	System.out.println(urlStr);
        	
        } else if (Place.equals("out")) {
        	//vpn으로는 http://10.40.89.245:8190
        	urlStr = "http://10.40.89.245:8190/pulse_n/get_log/?size="+size+"&env="+server+"&start_date="+today+"000000&unique_id="+userID+deviceID;
        	System.out.println(urlStr);
        }
        
    	URL url = new URL(urlStr);
    	
    	Request request = new Request.Builder()
        		.url(url) 
                .addHeader("Authorization", "Bearer " + access_token)
                .get()
                .build();
		
        Response response = httpClient.newCall(request).execute();

        // Get response body
		//System.out.println(response.body().string());
        String api_get_result = response.body().string();
    	
    	BufferedReader bf; 
    	String line = ""; 
    	
    	int x = 0;
    	String[] tts_strip = new String[size*repeat];
    	//String[] tts_strip = new String[size];
    	
    	Thread.sleep(7000);
    	for (int y=0; y < repeat; y++) {
    		Thread.sleep(2000);
    		
    		String result=""; 
    		InputStream is = new ByteArrayInputStream(api_get_result.getBytes());
			bf = new BufferedReader(new InputStreamReader(is)); 
        	
        	while((line=bf.readLine())!=null) { 
        		result=result.concat(line); 
        		//System.out.println(result); 
        	}
        	
        	
        	JSONParser parser = new JSONParser(); 
        	JSONObject obj = (JSONObject) parser.parse(result);
        	
        	JSONArray parse_data_list = (JSONArray) obj.get("data");
        	//System.out.println("parse_data_list.size() : " + parse_data_list.size()); 
        	
        	JSONObject data;
        	
        	for(int i = 0 ; i < parse_data_list.size(); i++) { 
        		data = (JSONObject) parse_data_list.get(i);
        			
        		JSONObject parse_source = (JSONObject) data.get("_source");
        		JSONObject parse_item = (JSONObject) parse_source.get("item");
        		
        		tts_strip[i] = (String) parse_item.get("tts_strip");
        		tts_strip[x] = (String) parse_item.get("tts_strip");
        		transaction_id = (String) parse_item.get("transaction_id");
        		System.out.println("transaction_id : " + transaction_id);
        		x++;

        	}
    	}
    	
    	
    	//System.out.println(Arrays.deepToString(tts_strip));
    	
    	for(String str:tts_strip) {
    		//System.out.println(str); 
    	}
    
    	logArray = tts_strip;
    	
    	String tts = Arrays.deepToString(logArray);
    	System.out.println("tts : " + tts);
		return tts;

    }
    
    public String Domain_JsonParsing_most_recent(String userID, String deviceID, String Server, String Place ) throws Exception {
    	
    	String access_token = NUGU_Insight_Token(Place);
    	
    	Calendar calendar = Calendar.getInstance();
        java.util.Date date = calendar.getTime();
        String today = (new SimpleDateFormat("yyyyMMdd").format(date));
        
        String logArray[];    
        
        String server = null;
        String urlStr = null;
        int size = 1;
        int repeat = 1;
        
        if(Server.equals("PRD")) {
        	server = "prd";
        } else if (Server.equals("STG")) {
        	server = "stg";
        } else if (Server.equals("RTG")) {
        	server = "rtg";
        }
        
        System.out.println("오늘날짜 : " + today);
        System.out.println("대상서버 : " + server);
    	
        if(Place.equals("in")) {
        	//사내망에서는 http://172.27.97.221:7090
        	urlStr = "http://172.27.97.221:7090/pulse_n/get_log/?size="+size+"&env="+server+"&start_date="+today+"000000&unique_id="+userID+deviceID;
        	System.out.println(urlStr);
        	
        } else if (Place.equals("out")) {
        	//vpn으로는 http://10.40.89.245:8190
        	urlStr = "http://10.40.89.245:8190/pulse_n/get_log/?size="+size+"&env="+server+"&start_date="+today+"000000&unique_id="+userID+deviceID;
        	System.out.println(urlStr);
        }
        
    	URL url = new URL(urlStr);
    	
    	Request request = new Request.Builder()
        		.url(url) 
                .addHeader("Authorization", "Bearer " + access_token)
                .get()
                .build();
		
        Response response = httpClient.newCall(request).execute();

        // Get response body
		//System.out.println(response.body().string());
        String api_get_result = response.body().string();
    	
    	BufferedReader bf; 
    	String line = ""; 
    	
    	int x = 0;
    	String[] domain = new String[size*repeat];
    	//String[] tts_strip = new String[size];
    	
    	Thread.sleep(7000);
    	for (int y=0; y < repeat; y++) {
    		
    		String result=""; 
    		InputStream is = new ByteArrayInputStream(api_get_result.getBytes());
			bf = new BufferedReader(new InputStreamReader(is)); 
        	
        	while((line=bf.readLine())!=null) { 
        		result=result.concat(line); 
        		//System.out.println(result); 
        	}
        	
        	
        	JSONParser parser = new JSONParser(); 
        	JSONObject obj = (JSONObject) parser.parse(result);
        	
        	JSONArray parse_data_list = (JSONArray) obj.get("data");
        	//System.out.println("parse_data_list.size() : " + parse_data_list.size()); 
        	
        	JSONObject data;
        	
        	for(int i = 0 ; i < parse_data_list.size(); i++) { 
        		data = (JSONObject) parse_data_list.get(i);
        			
        		JSONObject parse_source = (JSONObject) data.get("_source");
        		JSONObject parse_item = (JSONObject) parse_source.get("item");
        		
        		domain[i] = (String) parse_item.get("domain");
        		domain[x] = (String) parse_item.get("domain");
        		x++;

        	}
    	}
    	
    	
    	//System.out.println(Arrays.deepToString(tts_strip));
    	
    	for(String str:domain) {
    		//System.out.println(str); 
    	}
    
    	logArray = domain;
    	
    	String s = Arrays.deepToString(logArray);
    	String s1 = s.replace("[", "");
    	String domain_String = s1.replace("]", "");
    	System.out.println(domain_String);
		return domain_String;
		
    }
    
    public String intent_JsonParsing_most_recent(String userID, String deviceID, String Server, String Place ) throws Exception {
    	
    	String access_token = NUGU_Insight_Token(Place);
    	
    	Calendar calendar = Calendar.getInstance();
        java.util.Date date = calendar.getTime();
        String today = (new SimpleDateFormat("yyyyMMdd").format(date));
        
        String logArray[];    
        
        String server = null;
        String urlStr = null;
        int size = 1;
        int repeat = 1;
        
        if(Server.equals("PRD")) {
        	server = "prd";
        } else if (Server.equals("STG")) {
        	server = "stg";
        } else if (Server.equals("RTG")) {
        	server = "rtg";
        }
        
        System.out.println("오늘날짜 : " + today);
        System.out.println("대상서버 : " + server);
    	
        if(Place.equals("in")) {
        	//사내망에서는 http://172.27.97.221:7090
        	urlStr = "http://172.27.97.221:7090/pulse_n/get_log/?size="+size+"&env="+server+"&start_date="+today+"000000&unique_id="+userID+deviceID;
        	System.out.println(urlStr);
        	
        } else if (Place.equals("out")) {
        	//vpn으로는 http://10.40.89.245:8190
        	urlStr = "http://10.40.89.245:8190/pulse_n/get_log/?size="+size+"&env="+server+"&start_date="+today+"000000&unique_id="+userID+deviceID;
        	System.out.println(urlStr);
        }
        
    	URL url = new URL(urlStr);
    	
    	Request request = new Request.Builder()
        		.url(url) 
                .addHeader("Authorization", "Bearer " + access_token)
                .get()
                .build();
		
        Response response = httpClient.newCall(request).execute();

        // Get response body
		//System.out.println(response.body().string());
        String api_get_result = response.body().string();
    	
    	BufferedReader bf; 
    	String line = ""; 
    	
    	int x = 0;
    	String[] intent = new String[size*repeat];
    	//String[] tts_strip = new String[size];
    	
    	Thread.sleep(7000);
    	for (int y=0; y < repeat; y++) {
    		
    		String result=""; 
    		InputStream is = new ByteArrayInputStream(api_get_result.getBytes());
			bf = new BufferedReader(new InputStreamReader(is)); 
        	
        	while((line=bf.readLine())!=null) { 
        		result=result.concat(line); 
        		//System.out.println(result); 
        	}
        	
        	
        	JSONParser parser = new JSONParser(); 
        	JSONObject obj = (JSONObject) parser.parse(result);
        	
        	JSONArray parse_data_list = (JSONArray) obj.get("data");
        	//System.out.println("parse_data_list.size() : " + parse_data_list.size()); 
        	
        	JSONObject data;
        	
        	for(int i = 0 ; i < parse_data_list.size(); i++) { 
        		data = (JSONObject) parse_data_list.get(i);
        			
        		JSONObject parse_source = (JSONObject) data.get("_source");
        		JSONObject parse_item = (JSONObject) parse_source.get("item");
        		
        		intent[i] = (String) parse_item.get("intent");
        		intent[x] = (String) parse_item.get("intent");
        		x++;

        	}
    	}
    	
    	
    	//System.out.println(Arrays.deepToString(tts_strip));
    	
    	for(String str:intent) {
    		//System.out.println(str); 
    	}
    
    	logArray = intent;
    	
    	String s = Arrays.deepToString(logArray);
    	String s1 = s.replace("[", "");
    	String intent_String = s1.replace("]", "");
    	System.out.println(intent_String);
		return intent_String;
		
    }
    
    public String context_JsonParsing(String userID, String deviceID, String Server, String Place ) throws Exception {
    	
    	String access_token = NUGU_Insight_Token(Place);
    	
    	Calendar calendar = Calendar.getInstance();
        java.util.Date date = calendar.getTime();
        String today = (new SimpleDateFormat("yyyyMMdd").format(date));
        
        String logArray[];    
        
        String server = null;
        String urlStr = null;
        int size = 7;
        int repeat = 2;
        
        if(Server.equals("PRD")) {
        	server = "prd";
        } else if (Server.equals("STG")) {
        	server = "stg";
        } else if (Server.equals("RTG")) {
        	server = "rtg";
        }
        
        System.out.println("오늘날짜 : " + today);
        System.out.println("대상서버 : " + server);
    	
        if(Place.equals("in")) {
        	//사내망에서는 http://172.27.97.221:7090
        	urlStr = "http://172.27.97.221:7090/pulse_n/get_log/?size="+size+"&env="+server+"&start_date="+today+"000000&unique_id="+userID+deviceID;
        	System.out.println(urlStr);
        	
        } else if (Place.equals("out")) {
        	//vpn으로는 http://10.40.89.245:8190
        	urlStr = "http://10.40.89.245:8190/pulse_n/get_log/?size="+size+"&env="+server+"&start_date="+today+"000000&unique_id="+userID+deviceID;
        	System.out.println(urlStr);
        }
        
    	URL url = new URL(urlStr);
    	
    	Request request = new Request.Builder()
        		.url(url) 
                .addHeader("Authorization", "Bearer " + access_token)
                .get()
                .build();
		
        Response response = httpClient.newCall(request).execute();

        // Get response body
		//System.out.println(response.body().string());
        String api_get_result = response.body().string();
    	
    	BufferedReader bf; 
    	String line = ""; 
    	
    	int x = 0;
    	String[] api_context = new String[size*repeat];
    	//String[] tts_strip = new String[size];
    	
    	Thread.sleep(8000);
    	for (int y=0; y < repeat; y++) {
    		Thread.sleep(2000);
    		
    		String result=""; 
    		InputStream is = new ByteArrayInputStream(api_get_result.getBytes());
			bf = new BufferedReader(new InputStreamReader(is)); 
        	
        	while((line=bf.readLine())!=null) { 
        		result=result.concat(line); 
        		//System.out.println(result); 
        	}
        	
        	
        	JSONParser parser = new JSONParser(); 
        	JSONObject obj = (JSONObject) parser.parse(result);
        	
        	JSONArray parse_data_list = (JSONArray) obj.get("data");
        	//System.out.println("parse_data_list.size() : " + parse_data_list.size()); 
        	
        	JSONObject data;
        	
        	for(int i = 0 ; i < parse_data_list.size(); i++) { 
        		data = (JSONObject) parse_data_list.get(i);
        			
        		JSONObject parse_source = (JSONObject) data.get("_source");
        		JSONObject parse_item = (JSONObject) parse_source.get("item");
        		JSONObject api_event_in = (JSONObject) parse_item.get("api_event_in");
        		
        		api_context[i] = (String) api_event_in.get("context");
        		api_context[x] = (String) api_event_in.get("context");
        		x++;

        	}
    	}
    	
    	
    	//System.out.println(Arrays.deepToString(tts_strip));
    	
    	for(String str:api_context) {
    		//System.out.println(str); 
    	}
    
    	logArray = api_context;
    	
    	String context = Arrays.deepToString(logArray);
    	System.out.println(context);
		return context;

    }
    
    public String context_JsonParsing(String userID, String deviceID, String Server, String Place, int size) throws Exception {
    	
    	String access_token = NUGU_Insight_Token(Place);
    	
    	Calendar calendar = Calendar.getInstance();
        java.util.Date date = calendar.getTime();
        String today = (new SimpleDateFormat("yyyyMMdd").format(date));
        
        String logArray[];    
        
        String server = null;
        String urlStr = null;
        int repeat = 2;
        
        if(Server.equals("PRD")) {
        	server = "prd";
        } else if (Server.equals("STG")) {
        	server = "stg";
        } else if (Server.equals("RTG")) {
        	server = "rtg";
        }
        
        System.out.println("오늘날짜 : " + today);
        System.out.println("대상서버 : " + server);
    	
        if(Place.equals("in")) {
        	//사내망에서는 http://172.27.97.221:7090
        	urlStr = "http://172.27.97.221:7090/pulse_n/get_log/?size="+size+"&env="+server+"&start_date="+today+"000000&unique_id="+userID+deviceID;
        	System.out.println(urlStr);
        	
        } else if (Place.equals("out")) {
        	//vpn으로는 http://10.40.89.245:8190
        	urlStr = "http://10.40.89.245:8190/pulse_n/get_log/?size="+size+"&env="+server+"&start_date="+today+"000000&unique_id="+userID+deviceID;
        	System.out.println(urlStr);
        }
        
    	URL url = new URL(urlStr);
    	
    	Request request = new Request.Builder()
        		.url(url) 
                .addHeader("Authorization", "Bearer " + access_token)
                .get()
                .build();
		
        Response response = httpClient.newCall(request).execute();

        // Get response body
		//System.out.println(response.body().string());
        String api_get_result = response.body().string();
    	
    	BufferedReader bf; 
    	String line = ""; 
    	
    	int x = 0;
    	String[] api_context = new String[size*repeat];
    	//String[] tts_strip = new String[size];
    	
    	Thread.sleep(8000);
    	for (int y=0; y < repeat; y++) {
    		Thread.sleep(2000);
    		
    		String result=""; 
    		InputStream is = new ByteArrayInputStream(api_get_result.getBytes());
			bf = new BufferedReader(new InputStreamReader(is)); 
        	
        	while((line=bf.readLine())!=null) { 
        		result=result.concat(line); 
        		//System.out.println(result); 
        	}
        	
        	
        	JSONParser parser = new JSONParser(); 
        	JSONObject obj = (JSONObject) parser.parse(result);
        	
        	JSONArray parse_data_list = (JSONArray) obj.get("data");
        	//System.out.println("parse_data_list.size() : " + parse_data_list.size()); 
        	
        	JSONObject data;
        	
        	for(int i = 0 ; i < parse_data_list.size(); i++) { 
        		data = (JSONObject) parse_data_list.get(i);
        			
        		JSONObject parse_source = (JSONObject) data.get("_source");
        		JSONObject parse_item = (JSONObject) parse_source.get("item");
        		JSONObject api_event_in = (JSONObject) parse_item.get("api_event_in");
        		
        		api_context[i] = (String) api_event_in.get("context");
        		api_context[x] = (String) api_event_in.get("context");
        		x++;

        	}
    	}
    	
    	
    	//System.out.println(Arrays.deepToString(tts_strip));
    	
    	for(String str:api_context) {
    		//System.out.println(str); 
    	}
    
    	logArray = api_context;
    	
    	String context = Arrays.deepToString(logArray);
    	System.out.println(context);
		return context;

    }
    
    public String event_JsonParsing(String userID, String deviceID, String Server, String Place, int size) throws Exception {
    	
    	String access_token = NUGU_Insight_Token(Place);
    	
    	Calendar calendar = Calendar.getInstance();
        java.util.Date date = calendar.getTime();
        String today = (new SimpleDateFormat("yyyyMMdd").format(date));
        
        String logArray[];    
        
        String server = null;
        String urlStr = null;
       
        int repeat = 3;
        
        if(Server.equals("PRD")) {
        	server = "prd";
        } else if (Server.equals("STG")) {
        	server = "stg";
        } else if (Server.equals("RTG")) {
        	server = "rtg";
        }
        
        System.out.println("오늘날짜 : " + today);
        System.out.println("대상서버 : " + server);
    	
        if(Place.equals("in")) {
        	//사내망에서는 http://172.27.97.221:7090
        	urlStr = "http://172.27.97.221:7090/pulse_n/get_log/?size="+size+"&env="+server+"&start_date="+today+"000000&unique_id="+userID+deviceID;
        	System.out.println(urlStr);
        	
        } else if (Place.equals("out")) {
        	//vpn으로는 http://10.40.89.245:8190
        	urlStr = "http://10.40.89.245:8190/pulse_n/get_log/?size="+size+"&env="+server+"&start_date="+today+"000000&unique_id="+userID+deviceID;
        	System.out.println(urlStr);
        }
        
    	URL url = new URL(urlStr);
    	
    	Request request = new Request.Builder()
        		.url(url) 
                .addHeader("Authorization", "Bearer " + access_token)
                .get()
                .build();
		
        Response response = httpClient.newCall(request).execute();

        // Get response body
		//System.out.println(response.body().string());
        String api_get_result = response.body().string();
    	
    	BufferedReader bf; 
    	String line = ""; 
    	
    	int x = 0;
    	String[] api_event = new String[size*repeat];
    	//String[] tts_strip = new String[size];
    	
    	Thread.sleep(8000);
    	for (int y=0; y < repeat; y++) {
    		Thread.sleep(2000);
    		
    		String result=""; 
    		InputStream is = new ByteArrayInputStream(api_get_result.getBytes());
			bf = new BufferedReader(new InputStreamReader(is)); 
        	
        	while((line=bf.readLine())!=null) { 
        		result=result.concat(line); 
        		//System.out.println(result); 
        	}
        	
        	
        	JSONParser parser = new JSONParser(); 
        	JSONObject obj = (JSONObject) parser.parse(result);
        	
        	JSONArray parse_data_list = (JSONArray) obj.get("data");
        	//System.out.println("parse_data_list.size() : " + parse_data_list.size()); 
        	
        	JSONObject data;
        	
        	for(int i = 0 ; i < parse_data_list.size(); i++) { 
        		data = (JSONObject) parse_data_list.get(i);
        			
        		JSONObject parse_source = (JSONObject) data.get("_source");
        		JSONObject parse_item = (JSONObject) parse_source.get("item");
        		JSONObject api_event_in = (JSONObject) parse_item.get("api_event_in");
        		
        		api_event[i] = (String) api_event_in.get("event");
        		api_event[x] = (String) api_event_in.get("event");
        		x++;

        	}
    	}
    	
    	
    	//System.out.println(Arrays.deepToString(tts_strip));
    	
    	for(String str:api_event) {
    		//System.out.println(str); 
    	}
    
    	logArray = api_event;
    	
    	String event = Arrays.deepToString(logArray);
    	System.out.println(event);
		return event;

    }
    
	public String audio_activity_JsonParsing(String userID, String deviceID, String Server, String Place, int size ) throws Exception {
    	
		String access_token = NUGU_Insight_Token(Place);
		
    	Calendar calendar = Calendar.getInstance();
        java.util.Date date = calendar.getTime();
        String today = (new SimpleDateFormat("yyyyMMdd").format(date));
        
        String logArray[];    
        
        String server = null;
        String urlStr = null;
        
        int repeat = 2;
        
        if(Server.equals("PRD")) {
        	server = "prd";
        } else if (Server.equals("STG")) {
        	server = "stg";
        } else if (Server.equals("RTG")) {
        	server = "rtg";
        }
        
        System.out.println("오늘날짜 : " + today);
        System.out.println("대상서버 : " + server);
    	
        if(Place.equals("in")) {
        	//사내망에서는 http://172.27.97.221:7090
        	urlStr = "http://172.27.97.221:7090/pulse_n/get_log/?size="+size+"&env="+server+"&start_date="+today+"000000&unique_id="+userID+deviceID;
        	System.out.println(urlStr);
        	
        } else if (Place.equals("out")) {
        	//vpn으로는 http://10.40.89.245:8190
        	urlStr = "http://10.40.89.245:8190/pulse_n/get_log/?size="+size+"&env="+server+"&start_date="+today+"000000&unique_id="+userID+deviceID;
        	System.out.println(urlStr);
        }
        
    	URL url = new URL(urlStr);
    	
    	Request request = new Request.Builder()
        		.url(url) 
                .addHeader("Authorization", "Bearer " + access_token)
                .get()
                .build();
		
        Response response = httpClient.newCall(request).execute();

        // Get response body
		//System.out.println(response.body().string());
        String api_get_result = response.body().string();
    	
    	BufferedReader bf; 
    	String line = ""; 
    	
    	int x = 0;
    	String[] audio_activity = new String[size*repeat];
    	//String[] tts_strip = new String[size];
    	
    	Thread.sleep(8000);
    	for (int y=0; y < repeat; y++) {
    		Thread.sleep(2000);
    		
    		String result=""; 
    		InputStream is = new ByteArrayInputStream(api_get_result.getBytes());
			bf = new BufferedReader(new InputStreamReader(is)); 
        	
        	while((line=bf.readLine())!=null) { 
        		result=result.concat(line); 
        		//System.out.println(result); 
        	}
        	
        	
        	JSONParser parser = new JSONParser(); 
        	JSONObject obj = (JSONObject) parser.parse(result);
        	
        	JSONArray parse_data_list = (JSONArray) obj.get("data");
        	//System.out.println("parse_data_list.size() : " + parse_data_list.size()); 
        	
        	JSONObject data;
        	
        	for(int i = 0 ; i < parse_data_list.size(); i++) { 
        		data = (JSONObject) parse_data_list.get(i);
        			
        		JSONObject parse_source = (JSONObject) data.get("_source");
        		JSONObject parse_item = (JSONObject) parse_source.get("item");
        		JSONObject play_in = (JSONObject) parse_item.get("play_in");
        		
        		audio_activity[i] = (String) play_in.get("audio_player_activity");
        		audio_activity[x] = (String) play_in.get("audio_player_activity");
        		x++;

        	}
    	}
    	
    	
    	//System.out.println(Arrays.deepToString(tts_strip));
    	
    	for(String str:audio_activity) {
    		//System.out.println(str); 
    	}
    
    	logArray = audio_activity;
    	
    	String activity = Arrays.deepToString(logArray);
    	System.out.println(activity);
		return activity;

    }
	
	public String TransactionID_JsonParsing(String userID, String deviceID, String Server, String Place) throws Exception {
    	
		String access_token = NUGU_Insight_Token(Place);
		
		Thread.sleep(12000);
		
    	Calendar calendar = Calendar.getInstance();
        java.util.Date date = calendar.getTime();
        String today = (new SimpleDateFormat("yyyyMMdd").format(date));
        
        String logArray[];    
        
        String server = null;
        String urlStr = null;
        String api_get_result = null;
        
        int size = 1;
        
        if(Server.equals("PRD")) {
        	server = "prd";
        } else if (Server.equals("STG")) {
        	server = "stg";
        } else if (Server.equals("RTG")) {
        	server = "rtg";
        }
        
        System.out.println("오늘날짜 : " + today);
        System.out.println("대상서버 : " + server);
    	
        if(Place.equals("in")) {
        	//사내망에서는 http://172.27.97.221:7090
        	urlStr = "http://172.27.97.221:7090/pulse_n/get_log/?size="+size+"&env="+server+"&start_date="+today+"000000&unique_id="+userID+deviceID;
        	System.out.println(urlStr);
        	
        } else if (Place.equals("out")) {
        	//vpn으로는 http://10.40.89.245:8190
        	urlStr = "http://10.40.89.245:8190/pulse_n/get_log/?size="+size+"&env="+server+"&start_date="+today+"000000&unique_id="+userID+deviceID;
        	System.out.println(urlStr);
        }
        
        URL url = new URL(urlStr);

        Request request = new Request.Builder()
        		.url(url) 
                .addHeader("Authorization", "Bearer " + access_token)
                .get()
                .build();
		
        Response response = httpClient.newCall(request).execute();

        // Get response body
		//System.out.println(response.body().string());
		api_get_result = response.body().string();
    	
		BufferedReader bf; 
		String line = ""; 
    	
		int x = 0;
		String[] transaction_id = new String[size];
		//String[] tts_strip = new String[size];
    	
		for (int y=0; y < size; y++) {
    		
			String result=""; 
			InputStream is = new ByteArrayInputStream(api_get_result.getBytes());
			bf = new BufferedReader(new InputStreamReader(is)); 
        	
        	while((line=bf.readLine())!=null) { 
        		result=result.concat(line); 
        		//System.out.println(result); 
        	}
        	
        	
        	JSONParser parser = new JSONParser(); 
        	JSONObject obj = (JSONObject) parser.parse(result);
        	
        	JSONArray parse_data_list = (JSONArray) obj.get("data");
        	//System.out.println("parse_data_list.size() : " + parse_data_list.size()); 
        	
        	JSONObject data;
        	
        	for(int i = 0 ; i < parse_data_list.size(); i++) { 
        		data = (JSONObject) parse_data_list.get(i);
        			
        		JSONObject parse_source = (JSONObject) data.get("_source");
        		JSONObject parse_item = (JSONObject) parse_source.get("item");
        		
        		transaction_id[i] = (String) parse_item.get("transaction_id");
        		transaction_id[x] = (String) parse_item.get("transaction_id");
        		x++;

        	}
    	}
    	
    	
    	//System.out.println(Arrays.deepToString(tts_strip));
    	
    	for(String str:transaction_id) {
    		//System.out.println(str); 
    	}
    
    	logArray = transaction_id;
    	
    	String s = Arrays.deepToString(logArray);
    	String s1 = s.replace("[", "");
    	String tid = s1.replace("]", "");
    	System.out.println(tid);
		return tid;

    }
	
	public String acceesToken_JsonParsing(String Server, String Place, String tid) throws Exception {
    	
		String access_token = NUGU_Insight_Token(Place);
		
    	Calendar calendar = Calendar.getInstance();
        java.util.Date date = calendar.getTime();
        String today = (new SimpleDateFormat("yyyyMMdd").format(date));
        
        String logArray[];    
        
        String server = null;
        String urlStr = null;
        String actn = null; 
        
        int size = 1;
        
        if(Server.equals("PRD")) {
        	server = "prd";
        } else if (Server.equals("STG")) {
        	server = "stg";
        } else if (Server.equals("RTG")) {
        	server = "rtg";
        }  
        
        System.out.println("오늘날짜 : " + today);
        System.out.println("대상서버 : " + server);
    	
        Thread.sleep(4000);
        if(Place.equals("in")) {
        	//사내망에서는 http://172.27.97.221:7090
        	urlStr = "http://172.27.97.221:7090/pulse_n/get_raw_log/v3/?env="+server+"&transaction_id="+tid;
        	System.out.println(urlStr);
        	
        } else if (Place.equals("out")) {
        	//vpn으로는 http://10.40.89.245:8190
        	urlStr = "http://10.40.89.245:8190/pulse_n/get_raw_log/v3/?env="+server+"&transaction_id="+tid;
        	System.out.println(urlStr);
        }

    	URL url = new URL(urlStr);
    	
    	Request request = new Request.Builder()
        		.url(url) 
                .addHeader("Authorization", "Bearer " + access_token)
                .get()
                .build();
		
        Response response = httpClient.newCall(request).execute();

        // Get response body
		//System.out.println(response.body().string());
        String api_get_result = response.body().string();
    	
    	BufferedReader bf; 
    	String line = ""; 
    	
    	int x = 0;
    	String[] source_access_token = new String[size];
    	//String[] tts_strip = new String[size];
    	
    	
    	for (int y=0; y < size; y++) {
    		Thread.sleep(5000);	
    		
    		if (Server.equals("PRD") || Server.equals("STG")) {
    			String result=""; 
    			InputStream is = new ByteArrayInputStream(api_get_result.getBytes());
    			bf = new BufferedReader(new InputStreamReader(is)); 
            	
            	while((line=bf.readLine())!=null) { 
            		result=result.concat(line); 
            		//System.out.println(result); 
            	}
            	
            	
            	JSONParser parser = new JSONParser(); 
            	JSONObject obj = (JSONObject) parser.parse(result);
            	
            	JSONObject parse_data = (JSONObject) obj.get("data");
            	JSONObject parse_relation= (JSONObject) parse_data.get("relation");
            	JSONArray parse_rdv_in_list = (JSONArray) parse_relation.get("rdv_in");
            	//System.out.println("parse_data_list.size() : " + parse_data_list.size()); 
            	
            	JSONObject rdv_in;
            	
            	for(int i = 0 ; i < parse_rdv_in_list.size(); i++) { 
            		rdv_in = (JSONObject) parse_rdv_in_list.get(i);
            			
            		JSONObject parse_value= (JSONObject) rdv_in.get("value");
            		JSONObject parse_body1 = (JSONObject) parse_value.get("body");
            		JSONObject parse_body2 = (JSONObject) parse_body1.get("body");
            		JSONObject parse_access_token = (JSONObject) parse_body2.get("access_token");
            		
            		source_access_token[i] = (String) parse_access_token.get("source_access_token");
            		source_access_token[x] = (String) parse_access_token.get("source_access_token");
            		x++;
            		
            		for(String str:source_access_token) {
                		//System.out.println(str); 
                	}
                
                	logArray = source_access_token;
                	
                	String s = Arrays.deepToString(logArray);
                	String s1 = s.replace("[", "");
                	actn = s1.replace("]", "");
                	System.out.println(actn);
            	}
    		} else if(Server.equals("RTG")) {
    			String result=""; 
    			InputStream is = new ByteArrayInputStream(api_get_result.getBytes());
    			bf = new BufferedReader(new InputStreamReader(is)); 
            	
            	while((line=bf.readLine())!=null) { 
            		result=result.concat(line); 
            		//System.out.println(result); 
            	}
            	
            	
            	JSONParser parser = new JSONParser(); 
            	JSONObject obj = (JSONObject) parser.parse(result);
            	
            	JSONObject parse_data = (JSONObject) obj.get("data");
            	JSONObject parse_relation= (JSONObject) parse_data.get("relation");
            	JSONArray parse_rtm_list = (JSONArray) parse_relation.get("rtm");
            	//System.out.println("parse_data_list.size() : " + parse_data_list.size()); 
            	
            	JSONObject rtm;
            	
            	for(int i = 0 ; i < parse_rtm_list.size(); i++) { 
            		rtm = (JSONObject) parse_rtm_list.get(i);
            			
            		JSONObject parse_value= (JSONObject) rtm.get("value");
            		JSONObject parse_body1 = (JSONObject) parse_value.get("body");
            		JSONObject parse_access_token = (JSONObject) parse_body1.get("access_token");
            		
            		source_access_token[i] = (String) parse_access_token.get("source_access_token");
            		source_access_token[x] = (String) parse_access_token.get("source_access_token");
            		x++;
            		
            		for(String str:source_access_token) {
                		//System.out.println(str); 
                	}
                
                	logArray = source_access_token;
                	
                	String s = Arrays.deepToString(logArray);
                	String s1 = s.replace("[", "");
                	actn = s1.replace("]", "");
                	System.out.println(actn);
            		
            	}
        	}
    	}
    	return actn;
	}
  
    	
    	//System.out.println(Arrays.deepToString(tts_strip));
    	
    
	public String action_type_JsonParsing(String userID, String deviceID, String Server, String Place ) throws Exception {
    	
		String access_token = NUGU_Insight_Token(Place);
		
    	Calendar calendar = Calendar.getInstance();
        java.util.Date date = calendar.getTime();
        String today = (new SimpleDateFormat("yyyyMMdd").format(date));
        
        String logArray[];    
        
        String server = null;
        String urlStr = null;
        int size = 1;
        int repeat = 1;
        
        if(Server.equals("PRD")) {
        	server = "prd";
        } else if (Server.equals("STG")) {
        	server = "stg";
        } else if (Server.equals("RTG")) {
        	server = "rtg";
        }
        
        System.out.println("오늘날짜 : " + today);
        System.out.println("대상서버 : " + server);
    	
        if(Place.equals("in")) {
        	//사내망에서는 http://172.27.97.221:7090
        	urlStr = "http://172.27.97.221:7090/pulse_n/get_log/?size="+size+"&env="+server+"&start_date="+today+"000000&unique_id="+userID+deviceID;
        	System.out.println(urlStr);
        	
        } else if (Place.equals("out")) {
        	//vpn으로는 http://10.40.89.245:8190
        	urlStr = "http://10.40.89.245:8190/pulse_n/get_log/?size="+size+"&env="+server+"&start_date="+today+"000000&unique_id="+userID+deviceID;
        	System.out.println(urlStr);
        }
        
    	URL url = new URL(urlStr);
    	
    	Request request = new Request.Builder()
        		.url(url) 
                .addHeader("Authorization", "Bearer " + access_token)
                .get()
                .build();
		
        Response response = httpClient.newCall(request).execute();

        // Get response body
		//System.out.println(response.body().string());
        String api_get_result = response.body().string();
    	
    	BufferedReader bf; 
    	String line = ""; 
    	
    	int x = 0;
    	String[] action_type = new String[size*repeat];
    	//String[] tts_strip = new String[size];
    	
    	Thread.sleep(3000);
    	for (int y=0; y < repeat; y++) {
    		
    		String result=""; 
    		InputStream is = new ByteArrayInputStream(api_get_result.getBytes());
			bf = new BufferedReader(new InputStreamReader(is)); 
        	
        	while((line=bf.readLine())!=null) { 
        		result=result.concat(line); 
        		//System.out.println(result); 
        	}
        	
        	
        	JSONParser parser = new JSONParser(); 
        	JSONObject obj = (JSONObject) parser.parse(result);
        	
        	JSONArray parse_data_list = (JSONArray) obj.get("data");
        	//System.out.println("parse_data_list.size() : " + parse_data_list.size()); 
        	
        	JSONObject data;
        	
        	for(int i = 0 ; i < parse_data_list.size(); i++) { 
        		data = (JSONObject) parse_data_list.get(i);
        			
        		JSONObject parse_source = (JSONObject) data.get("_source");
        		JSONObject parse_item = (JSONObject) parse_source.get("item");
        		
        		action_type[i] = (String) parse_item.get("action_type");
        		action_type[x] = (String) parse_item.get("action_type");
        		x++;

        	}
    	}
    	
    	
    	//System.out.println(Arrays.deepToString(tts_strip));
    	
    	for(String str:action_type) {
    		//System.out.println(str); 
    	}
    
    	logArray = action_type;
    	
    	String actiontype = Arrays.deepToString(logArray);
    	System.out.println(actiontype);
		return actiontype;

    }
	
    public void AppCard_Delete(String userID, String deviceID, String AuthToken) throws Exception {
        
    	String get_urlStr = "https://api.sktnugu.com/v1/setting/appCard/test";
    	String result;
    	String logArray[];    
	
    	Request getRequest = new Request.Builder()
                .url(get_urlStr)
                .addHeader("User-Id", userID)
                .addHeader("Device-Id", deviceID)
                .addHeader("Auth-Token", AuthToken)
                .addHeader("Content-Type", "application/json")
                .build();

    	try (Response getResponse = httpClient.newCall(getRequest).execute()) {

            if (!getResponse.isSuccessful()) throw new IOException("Unexpected code " + getResponse);

            // Get response body
            result = getResponse.body().string();
			System.out.println(result);
        }
    		
    	JSONParser parser = new JSONParser(); 
    	JSONArray arr = (JSONArray) parser.parse(result);
    	JSONObject data;
    	String[] appcardID = new String[arr.size()];
    	
    	for(int i = 0 ; i < arr.size(); i++) { 
    		data = (JSONObject) arr.get(i);

    		appcardID[i] = (String) data.get("id");
    	}
    	
    	logArray = appcardID;
    	String id = Arrays.deepToString(logArray);
    	System.out.println(id);
  
    	for(int i = 0 ; i < arr.size(); i++) {
    		String delete_urlStr = "https://api.sktnugu.com/v1/setting/appCard/"+appcardID[i];
    		
    		System.out.println(delete_urlStr);
    		
        	Request deleteRequest = new Request.Builder()
        			.url(delete_urlStr)
        			.addHeader("User-Id", userID)
                    .addHeader("Device-Id", deviceID)
                    .addHeader("Auth-Token", AuthToken)
        			.delete()
        			.build();
        	
        	Response deleteResponse = httpClient.newCall(deleteRequest).execute();
    	}
    	
    	System.out.println("이전 솔루션메세지 (앱카드) 삭제 완료\n");
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
	public void AndroidKey_k() throws Exception {
		
		pressKey(new KeyEvent(AndroidKey.K));
	}
	public void AndroidKey_s() throws Exception {
		
		pressKey(new KeyEvent(AndroidKey.S));
	}
	public void AndroidKey_z() throws Exception {
		
		pressKey(new KeyEvent(AndroidKey.Z));
	}
	public void AndroidKey_i() throws Exception {
		
		pressKey(new KeyEvent(AndroidKey.I));
	}
	public void AndroidKey_d() throws Exception {
		
		pressKey(new KeyEvent(AndroidKey.D));
	}
	public void AndroidKey_a() throws Exception {
		
		pressKey(new KeyEvent(AndroidKey.A));
	}
	public void AndroidKey_n() throws Exception {
		
		pressKey(new KeyEvent(AndroidKey.N));
	}
	public void AndroidKey_e() throws Exception {
	
	pressKey(new KeyEvent(AndroidKey.E));
	}
	public void AndroidKey_j() throws Exception {
		
		pressKey(new KeyEvent(AndroidKey.J));
		}
	
	

	@Override
	public TouchScreen getTouch() {
		// TODO Auto-generated method stub
		return null;
	}

	public void ProgressBar_Loading() throws Exception {
		
		Thread.sleep(600);
		try {
			boolean 연결로딩 = this.isElementPresent(By.className("android.widget.ProgressBar"));
			if(연결로딩 == true) {
				Thread.sleep(3000);
			} else { 
				Thread.sleep(1000);
			}
		} catch (StaleElementReferenceException e) {
			
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
            	System.out.println(dataName + " : [매칭 완료] : " + text);
            	return true;
            } 
        }
		return false;
    }
	
	public void notice_popup_check() throws Exception {
		
		Thread.sleep(350);
		try {	
			if(this.isElementPresent(By.id("dialogLayout"))) {
				System.out.println("공지 안내 팝업 [있음] - 팝업 닫기 ");
				this.click(By.id("negativeButton"));

			} else {
				System.out.println("공지 안내 팝업 [없음]");
				Thread.sleep(100);
			}

		} catch (NoSuchElementException e) {
			System.out.println("공지 안내 팝업 [없음]");
		}		
	}
	
	public void view_close_btn_check() throws Exception {
		
		Thread.sleep(500);
		try {	
			if(this.isElementPresent(By.id("close"))) {
				System.out.println("play 카드 [있음] - 카드 닫기");
				this.click(By.id("com.skt.aidev.nugufriends:id/close"));

			} else {
				System.out.println("play 카드 [없음]");
				Thread.sleep(100);
			}

		} catch (NoSuchElementException e) {
			System.out.println("play 카드 [없음]");
		}
		Thread.sleep(1000);
	}
	
	public void chips_update_check(String Server) throws Exception {
		 
		if(Server.equals("PRD")) {
			System.out.println("서버 : PRD | dialogLayout [없음]");
        } else if (Server.equals("STG")) {
        	System.out.println("서버 : STG | dialogLayout [있음]");
        	this.click(By.id("negativeButton"));
        } else if (Server.equals("STG")) {
        	System.out.println("서버 : PRD | dialogLayout [없음]");
        }
		
	}
	
	public void goto_Background() throws Exception {
		 
		this.runAppInBackground(Duration.ofSeconds(3));
		
	}
	
	public String Naver_Stock_Crawler_rise(String sise) throws Exception {
		
		String URL = "";
		String stock = "";
		
		 if(sise.equals("상승")) {
			 URL = "https://finance.naver.com/sise/sise_rise.nhn";
			 System.out.println(URL);	
	     } else if (sise.equals("하락")) {
	    	 URL = "https://finance.naver.com/sise/sise_fall.nhn";
	     } else if (sise.equals("보합")) {
	    	 URL = "https://finance.naver.com/sise/sise_steady.nhn";
	     } else if (sise.equals("정지")) {
	    	 URL = "https://finance.naver.com/sise/trading_halt.nhn";
	     }
        try {
        	int sum = 0;
            Connection conn = Jsoup.connect(URL);
            Document html = conn.get(); // conn.post();
            
            Elements fileblocks = html.getElementsByClass("tltle");
            for( Element fileblock : fileblocks ) {
            	sum += 1;
            	Elements files = fileblock.getElementsByTag("a");
                String text = files.text();
                
                System.out.println(sise+" 종목 : "+ text );
                stock = text;
                if (sum == 1) {
                	 break;  
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stock;
    }
	

	

	

		

}
