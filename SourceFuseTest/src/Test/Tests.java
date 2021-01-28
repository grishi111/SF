package Test;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class Tests {

	public static WebDriver driver;
	static String TEST_ENVIRONMENT = "sfwebhtml.sourcefuse.com/automation-form-demo-for-interview/";
	static String uname = "sfwebhtml";
	static String password = "t63KUfxL5vUyFLG4eqZNUcuRQ";
	static String url = "http://" + uname + ":" + password + "@" + TEST_ENVIRONMENT;
	static String key = "webdriver.chrome.driver";
	static String driverPath = "//home//reishi//Documents//chromedriver";
	static Page landingPage;
	
	public static ExtentReports extent;
	public ExtentHtmlReporter	htmlReporter;
	public static ExtentTest test;

	@BeforeMethod
	public void setup() {
		System.setProperty(key, driverPath);
		ChromeOptions options=new ChromeOptions();
		options.addArguments("window-size=1400,800");
		options.addArguments("headless");
		driver = new ChromeDriver(options);
		System.out.println("launching Chrome browser");
		driver.manage().window().maximize();
		driver.get(url);
		
	}
	
	@BeforeTest
	public void SetExtent() {
		htmlReporter =new ExtentHtmlReporter(System.getProperty("user.dir")+"/test-output/myReport.html");
		htmlReporter.config().setDocumentTitle("Test Automation Report");
		htmlReporter.config().setReportName("Automagtion test result");
		htmlReporter.config().setTheme(Theme.DARK);
		
		extent=new ExtentReports();
		extent.attachReporter(htmlReporter);
		
		extent.setSystemInfo("OS", "Linux");
		extent.setSystemInfo("Environment", "QA");
		extent.setSystemInfo("QA Name", "Jason");
		}

	@Test
	public static void Test_Case_1() {
		test = extent.createTest("Print Labels");
		landingPage = new Page(driver);
		landingPage.WaitforBrowserToLoad();
		System.out.println("********List of Labels********");
		for (int i = 0; i < landingPage.labels.size(); i++) {
			System.out.println(landingPage.labels.get(i).getText());
		}
		System.out.println("****End of the list********");
		landingPage.btnSubmit.click();

	}
	
	@Test
	public static void Test_Case_2() throws InterruptedException {
		test = extent.createTest("SoftAssert");
		landingPage = new Page(driver);
		landingPage.WaitforBrowserToLoad();
		landingPage.VerifyFieldsLabels("softAssert");
		Thread.sleep(3000);
	}

	@Test
	public static void Test_Case_3() throws InterruptedException {
		test = extent.createTest("HardAssert");
		landingPage = new Page(driver);
		landingPage.WaitforBrowserToLoad();
		landingPage.VerifyFieldsLabels("hardAssert");
		Thread.sleep(3000);
	}
	
	@AfterTest
	public void EndReport() {
		extent.flush();
	}

	@AfterMethod
	public void TearDown(ITestResult result) throws IOException  {
		
		if(result.getStatus()==ITestResult.FAILURE) {
			test.log(Status.FAIL, "TEST CASE FAILED IS " + result.getName());
			test.log(Status.FAIL, "TEST CASE FAILED IS" + result.getThrowable());
			String screenshotPath= Tests.GetScreenshot(driver,result.getName());
			test.addScreenCaptureFromPath(screenshotPath);
		}
		else if(result.getStatus()==ITestResult.SKIP) {
			test.log(Status.SKIP, "TEST CASE SKIPPED IS " + result.getName());
		}
		else if(result.getStatus()==ITestResult.SUCCESS) {
			test.log(Status.PASS, "TEST CASE PASSED IS "+ result.getName());
		}
		
		driver.quit();
	}
	
	public static String GetScreenshot(WebDriver driver,String screenshotname) throws IOException {
		
		String datename= new SimpleDateFormat("yyyymmddhhmmss").format(new Date());
		TakesScreenshot ts= (TakesScreenshot) driver;
		File source=ts.getScreenshotAs(OutputType.FILE);
		
		String destination=System.getProperty("usr.dir")+"/screenshots"+screenshotname+datename+".png";
		
		File finaldestination=new File(destination);
		FileUtils.copyFile(source, finaldestination);
		return destination;
	}
/*	
	@Test public static void Test_Case_4() throws InterruptedException {
		
	landingPage=new Page(driver); 
	landingPage.WaitforBrowserToLoad();
	landingPage.inputFirstName.sendKeys("Tony");
	landingPage.inputLastName.sendKeys("Greg");
	landingPage.inputEmail.sendKeys("tony.greg@test.test");
	landingPage.inputCurrentCompany.sendKeys("Test Company");
	landingPage.inputMobile.sendKeys("9898989898"); landingPage.inputDOB.click();
	landingPage.inputDOB.sendKeys("11/11/1990");
	landingPage.inputPosition.sendKeys("Analyst");
	landingPage.inputPortfolio.clear();
	landingPage.inputPortfolio.sendKeys("https://linkedin.com");
	landingPage.inputSalary.sendKeys("1200000");
	landingPage.inputStart.sendKeys("11/11/2020"); 
	landingPage.inputAddress.sendKeys("39, Park street, Omega-3, Gr. Delhi-110002");
	landingPage.inputUpload.sendKeys("/home/reishi/Documents/testfile.docx");
	landingPage.inputYes.click(); landingPage.btnSubmit.click();
	landingPage.WaitForUrltoContainString(driver, "resume="); 
	
}
	

	@Test
	public void Test_Case_5() {

		DBConnection.SetDBConnection();

		try {
			String query = "select * from testingdata where email='tony.greg@test.test' order by date DESC LIMIT 1";
			ResultSet res = DBConnection.stmt.executeQuery(query);
			while (res.next()) {
				Assert.assertTrue(res.getString(1).equals("Tony"));
				Assert.assertTrue(res.getString(2).equals("Greg"));
				Assert.assertTrue(res.getString(3).equals("tony.greg@test.test"));
				Assert.assertTrue(res.getString(4).equals("Test Company"));
				Assert.assertTrue(res.getString(5).equals("9898989898"));
				Assert.assertTrue(res.getString(6).equals("11/11/1990"));
				Assert.assertTrue(res.getString(7).equals("Analyst"));
				Assert.assertTrue(res.getString(8).equals("https://linkedin.com"));
				Assert.assertTrue(res.getString(9).equals("1200000"));
				Assert.assertTrue(res.getString(10).equals("11/11/2020"));
				Assert.assertTrue(res.getString(11).equals("39, Park street, Omega-3, Gr. Delhi-110002"));
				Assert.assertTrue(res.getString(12).equals("Yes"));
				Assert.assertTrue(res.getString(13).equals("testfile.docx"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		DBConnection.CloseDBConncetion();

	}

	@Test
	public void Test_Case_6() {

		DBConnection.SetDBConnection();

		try {
			String query = "select email_sent from testingdata where email='tony.greg@test.test' order by date DESC LIMIT 1";
			ResultSet res = DBConnection.stmt.executeQuery(query);
			while (res.next()) {
				//assuming 0->not sent, 1->sent
				Assert.assertTrue(res.getString(1).equals("1"));

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		DBConnection.CloseDBConncetion();

	}
	*/
}