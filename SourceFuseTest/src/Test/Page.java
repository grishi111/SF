package Test;

import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;

import org.testng.Assert;
import org.testng.asserts.SoftAssert;

public class Page {
	static WebDriver driver;
	Page(WebDriver driver){
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(css = "label")
	static List <WebElement> labels;
	
	@FindBy(xpath = "//div[@id='fnameInput']/input")
	WebElement inputFirstName;
	
	@FindBy(xpath = "//div[@id='lnameInput']/input")
	WebElement inputLastName;
	
	@FindBy(xpath = "//div[@id='emailInput']/input")
	WebElement inputEmail;
	
	@FindBy(xpath = "//div[@id='curCompanyInput']/input")
	WebElement inputCurrentCompany;
	
	@FindBy(xpath = "//div[@id='mobInput']/input")
	WebElement inputMobile;
	
	@FindBy(xpath = "//div[@class='input-group date']/input")
	WebElement inputDOB;
	//@FindBy(xpath = "//div[@class='input-group-addon']")
	//WebElement inputDOB;
	
	@FindBy(xpath = "//div[@id='positionInput']/input")
	WebElement inputPosition;
	
	@FindBy(xpath = "//div[@id='portfolioInput']/input")
	WebElement inputPortfolio;
	
	@FindBy(xpath = "//div[@id='salaryInput']/input")
	WebElement inputSalary;
	
	@FindBy(xpath = "//div[@id='whenStartInput']/input")
	WebElement inputStart;
	
	@FindBy(xpath = "//textarea[@id='address']")
	WebElement inputAddress;
	
	@FindBy(xpath = "//input[@type='file']")
	WebElement inputUpload;
	
	@FindBy(xpath = "//input[@id='yes']")
	WebElement inputYes;
	
	@FindBy(xpath = "//input[@id='no']")
	WebElement inputNo;
	
	@FindBy(xpath = "//input[@id='notSure']")
	WebElement inputNotSure;
	
	@FindBy(xpath = "//button[@type='submit']")
	WebElement btnSubmit;
	
	@FindBy(xpath = "//button[@type='reset']")
	WebElement btnReset;
	
	
	public void VerifyFieldsLabels(String Asserttype) {
		String type=Asserttype;
		TreeMap<String, Object[]>TreeMapFieldsLabels=new TreeMap<String, Object[]>();
		TreeMapFieldsLabels.put("FirstName", new Object[]{labels.get(0), inputFirstName,"First Name *"});
		TreeMapFieldsLabels.put("LastName", new Object[]{labels.get(1), inputLastName, "Last Name *"});
		TreeMapFieldsLabels.put("Email", new Object[]{labels.get(2),inputEmail,"Email *"});
		TreeMapFieldsLabels.put("CurrentCompany", new Object[]{labels.get(3),inputCurrentCompany,"Current Company *"});
		TreeMapFieldsLabels.put("Mobile", new Object[]{labels.get(4),inputMobile, "Mobile No. *"});
		TreeMapFieldsLabels.put("DOB", new Object[]{labels.get(5),inputDOB,"Date of Birth *"});
		TreeMapFieldsLabels.put("Position", new Object[]{labels.get(6),inputPosition,"Position you are applying for *"});
		TreeMapFieldsLabels.put("Portfolio", new Object[]{labels.get(7),inputPortfolio,"Portfolio Website *"});
		TreeMapFieldsLabels.put("Salary", new Object[]{labels.get(8),inputSalary,"Salary requirements *"});
		TreeMapFieldsLabels.put("Start", new Object[]{labels.get(9),inputStart,"When can you start?"});
		TreeMapFieldsLabels.put("Address", new Object[]{labels.get(10),inputAddress,"Address"});
		TreeMapFieldsLabels.put("Resume", new Object[]{labels.get(11),inputUpload,"Upload Your Resume *"});
		TreeMapFieldsLabels.put("RadioYes", new Object[]{labels.get(13),inputYes,"Yes"});
		TreeMapFieldsLabels.put("RadioNo", new Object[]{labels.get(14),inputNo,"No"});
		TreeMapFieldsLabels.put("RadioNotSure", new Object[]{labels.get(15),inputNotSure,"Not Sure"});
		
		for(Entry<String, Object[]> entry:TreeMapFieldsLabels.entrySet()) {
			Object[] arr=entry.getValue();
			if(type.equalsIgnoreCase("softAssert"))
				VerifyfieldsSoftAssert((WebElement)arr[0], (WebElement)arr[1],(String)arr[2]);
			else
				VerifyFieldsHardAssert((WebElement)arr[0],(WebElement)arr[1],(String)arr[2]);
		}
	}


	private static void VerifyfieldsSoftAssert(WebElement actualField, WebElement inputField, String expectedFieldLabels) {
		SoftAssert softAssert = new SoftAssert();
		softAssert.assertTrue(actualField.isDisplayed());
		softAssert.assertTrue(actualField.getText().equals(expectedFieldLabels));
		softAssert.assertTrue(inputField.isDisplayed());
		softAssert.assertTrue(Boolean.parseBoolean(inputField.getAttribute("required")), "This is required and message should be showing");
	}


	public void VerifyFieldsHardAssert(WebElement actualField, WebElement inputField,String expectedFieldLabels) {
		Assert.assertTrue(actualField.isDisplayed());
		Assert.assertTrue(actualField.getText().equals(expectedFieldLabels));
		Assert.assertTrue(inputField.isDisplayed());
		if(actualField.getText().contains("*")) {
			//System.out.println(Boolean.parseBoolean(inputField.getAttribute("required")));
			Assert.assertTrue(Boolean.parseBoolean(inputField.getAttribute("required")), "This is required and message should be showing");
		}
	}
	
	public synchronized static void WaitforBrowserToLoad() {
		try {
			boolean isReady = checkBrowserReadyState();
			if(isReady==false) {
				for(int i=0;i<90;i++) {
					try {
					Thread.sleep(1000);
					}catch(InterruptedException e) {
						
					}
					isReady=checkBrowserReadyState();
					if(isReady==true) {
						break;
					}
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
		
	public synchronized static boolean checkBrowserReadyState() {
		JavascriptExecutor js=(JavascriptExecutor) driver;
		if(js.executeScript("return document.readyState").toString().equals("complete")) {
			return true;
		}else {
			return false;
		}
		
	}
	
	public void WaitForUrltoContainString(WebDriver driver, String expectedText) {
		WebDriverWait wait=new WebDriverWait(driver, 25);
		wait.until(ExpectedConditions.urlContains(expectedText));
	}
}
