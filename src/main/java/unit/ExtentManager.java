package unit;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.AnalysisStrategy;
import com.aventstack.extentreports.ExtentReports; //3.x
import com.aventstack.extentreports.ExtentTest; //3.x
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;



public class ExtentManager {
	
	private static ExtentReports extent;
	private static ExtentTest test;
	private static String filePath = "./extentreport.html";
	
	private static final ExtentReports extentReports = new ExtentReports();
	
    public synchronized static ExtentReports getExtentReports(String OS, String AppName, String Server, String Project, String TestPlace) {
        ExtentSparkReporter reporter = new ExtentSparkReporter(filePath);
        reporter.config().setReportName("QA 자동화 테스트 결과");
        reporter.config().setDocumentTitle("QA 자동화 테스트 결과");
        reporter.config().setEncoding("UTF-8");
        reporter.config().setOfflineMode(true);
        reporter.config().setTimelineEnabled(true);
        reporter.config().setTimeStampFormat("yyyyy.MMM.ddd, HH:mm:ss a");
        extentReports.attachReporter(reporter);
        
        extentReports.setSystemInfo("OS", OS);
        extentReports.setSystemInfo("AppName", AppName);
        extentReports.setSystemInfo("ServerName", Server);
        extentReports.setSystemInfo("Project", Project);
        extentReports.setSystemInfo("Place", TestPlace);
        return extentReports;
    }
	
	public static ExtentTest createTest(String name, String description){
		test = extent.createTest(name, description);
		
		return test;
	}
	
	public static String CaptureScreen(WebDriver driver, String ImagesPath) {
		TakesScreenshot oScn = (TakesScreenshot) driver;
		File oScnShot = oScn.getScreenshotAs(OutputType.FILE);
		File oDest = new File(ImagesPath+".jpg");
		try {
			FileUtils.copyFile(oScnShot, oDest);
		} catch (IOException e) {System.out.println(e.getMessage());}
		return ImagesPath+".jpg";
     }
	
	
	
}
