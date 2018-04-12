//Use of AmazonSearchAPI to define all the web elements that is used to search the Amazon web site//

package com.pro.amazon;

import java.util.Hashtable;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import com.pro.util.BaseClass;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class AmazonSearchAPI extends BaseClass
{

	private WebDriver driver =null;
	public String refNo;
	ExtentReports reports ;
	ExtentTest logger;
	
	public AmazonSearchAPI(WebDriver driver)
	{
		this.driver = driver;
	
		
	}
	//Category - Declaration for all WebElements
	@FindBy(id="searchDropdownBox")
	public WebElement category;
	
	@FindBy(id="twotabsearchtextbox")
	public WebElement searchBox;
	
	@FindBy(xpath="//input[@type='submit']")
	public WebElement clickSearch;
	
	@FindBy(xpath="//li[@id='mediaTab_heading_0']/a/span/div[2]/span")
	public WebElement kindlePrice;	
	
	@FindBy(xpath="//ul[@class='a-unordered-list a-nostyle a-button-list a-horizontal']/li/span/span/span/a/span/span")
	public WebElement kindlePrice1;	
	
	@FindBy(xpath="//li[@id='mediaTab_heading_1']/a/span/div[2]/span")
	public WebElement paperBackPrice;
	
	@FindBy(xpath="//ul[@class='a-unordered-list a-nostyle a-button-list a-horizontal']/li[2]/span/span/span/a/span/span")
	public WebElement hardCoverPrice;	
	
	@FindBy(xpath="//span[@class='author notFaded']/span/a")
	public WebElement authorName1;
	
	@FindBy(xpath="//span[@class='author notFaded']/a")
	public WebElement authorName2;
	
	@FindBy(xpath="//*[@id='title']/span")
	public WebElement bookTitleName;
	
	@FindBy(id="acrCustomerReviewText")
	public WebElement totalCustomerReviews;
	

	
	    
	public void searchCategory(Hashtable<String,String> inputData,String sheetName,int dataRowNo) throws Exception
	{
		
		reports = BaseClass.reports;
		logger = BaseClass.logger;
		
		
		String kPrice = "";
		String paperOrHardCoverPrice="";
		String authorName = "";
		String bookTitle="";
		String custReviews="";
		
		int selectItemNumber = convertToInt(inputData.get("searchFromResult"));
		
		
		System.out.println("Item no is "+selectItemNumber);
				
		selectFromDDL(category, inputData.get("selectCategory"));
		setData(searchBox, inputData.get("searchContent"));
		clickOn(clickSearch);
		
		
		driver.findElement(By.xpath("//ul[@id='s-results-list-atf']/li["+selectItemNumber+"]/div/div/div/div[2]/div/div/a")).click();
		// Getting Author Name
		try {
			authorName = authorName1.getText();
			System.out.println(authorName);
			xls.setCellData(sheetName,"authorName", dataRowNo, authorName);
			logger.log(LogStatus.INFO, "Author Name is "+authorName);
		} catch (NoSuchElementException e) {
			authorName = authorName2.getText();
			System.out.println(authorName);
			xls.setCellData(sheetName,"authorName", dataRowNo, authorName);
			logger.log(LogStatus.INFO, "Author Name is "+authorName);
		}		
		
		// Getting Kindle Price
		try {
			kPrice = kindlePrice.getText();
			System.out.println(kPrice);
			xls.setCellData(sheetName,"kindlePrice", dataRowNo, kPrice);
			logger.log(LogStatus.INFO, "kindle Price is "+kPrice);
		} catch (NoSuchElementException e) {
			try {
				kPrice = kindlePrice1.getText();
				System.out.println(kPrice);
				xls.setCellData(sheetName,"kindlePrice", dataRowNo, kPrice);
				logger.log(LogStatus.INFO, "kindle Price is "+kPrice);
			} catch (Exception e2) {
				System.out.println("Not able to find KINDLE PRICE");
			}
		}
		
		// Getting Paper Back / Hard Cover Price
		try {
			paperOrHardCoverPrice = paperBackPrice.getText();
			System.out.println(paperOrHardCoverPrice);
			xls.setCellData(sheetName,"paperOrHardPrice", dataRowNo, paperOrHardCoverPrice);
			logger.log(LogStatus.INFO, "paperOrHardPrice  is "+paperOrHardCoverPrice);
		} catch (NoSuchElementException e) {
			try {
				paperOrHardCoverPrice = hardCoverPrice.getText();
				System.out.println(paperOrHardCoverPrice);
				xls.setCellData(sheetName,"paperOrHardPrice", dataRowNo, paperOrHardCoverPrice);
				logger.log(LogStatus.INFO, "paperOrHardPrice  is "+paperOrHardCoverPrice);
			} catch (Exception e2) {
				System.out.println("Not able to find Paper or HardCover PRICE");
			}
		}
		//Getting Book title 
		bookTitle=bookTitleName.getText();
		xls.setCellData(sheetName,"bookTitle", dataRowNo, bookTitle);
		logger.log(LogStatus.INFO, "bookTitle  is "+bookTitle);
		
		try {
			custReviews = totalCustomerReviews.getText();
			xls.setCellData(sheetName,"customerReviews", dataRowNo, custReviews);
			logger.log(LogStatus.PASS, "customerReviews  is "+custReviews,logger.addScreenCapture(captureScreenshot("CustomerReviews")));
		} catch (Exception e) {
			System.out.println("Not able to find Customer Revies");
			logger.log(LogStatus.FAIL, "customerReviews  is "+custReviews,logger.addScreenCapture(captureScreenshot("CustomerReviews")));
		}
		
	}
	
	public static int convertToInt(String data)
	{
		int a =0;
		try {
			a = Integer.parseInt(data);
		} catch (NumberFormatException e) {
		  double d = Double.parseDouble(data);
		  a = (int) d;
		}
		return a;
		
	}
	
	
}
