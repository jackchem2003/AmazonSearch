//Base class contain the initialization of drivers,handle various click, select of the web element events and terminate browsers.
package com.pro.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Reporter;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

public class BaseClass {
	
	public static String CONFIG_FILE_PATH =System.getProperty("user.dir")+"//src//com//pro//Config/config.properties";
    public static Xls_Reader xls = new Xls_Reader(System.getProperty("user.dir")+"//src//com//pro//TestData/TC_Info.xls");
	public static Logger APPLICATION_LOGS = null;
	public Properties propertyConfig=null;
	public Properties CONFIG=null;
	public static WebDriver driver=null;
	public static ExtentReports reports = null;
	public static ExtentTest logger = null;
	public List<String> windowList = new ArrayList<String>();
	
/***********************************************************************************************/
	//By using this method, configuring the properties file
	
public Properties initConfigurations(String filePath)
	{
		
		// Logging
		APPLICATION_LOGS = Logger.getLogger("devpinoyLogger");
		// config.prop
		CONFIG = new Properties();
			try 
			{
				FileInputStream fs = new FileInputStream(filePath);
				CONFIG.load(fs);
			}catch (Exception e) 
			{
				e.printStackTrace();
			}
		
		return CONFIG;
		
	}
/***********************************************************************************************/
	//By using this method, initializing the WebDriver
	
public void initDriver()
	{
		//if(driver==null)
		{
			if(propertyConfig.getProperty("browser").equals("IE"))
			{
				System.setProperty("webdriver.ie.driver",System.getProperty("user.dir")+"//src//IEDriverServer.exe");
				driver=new InternetExplorerDriver();
			}
			else if(propertyConfig.getProperty("browser").equals("Firefox"))
			{
				System.setProperty("webdriver.gecko.driver",System.getProperty("user.dir")+"//src//geckodriver.exe");
				//File pathToBinary = new File("C:\\Program Files\\Mozilla Firefox\\firefox.exe");
				//FirefoxBinary ffBinary = new FirefoxBinary(pathToBinary);
				//FirefoxProfile firefoxProfile = new FirefoxProfile();       
				driver = new FirefoxDriver();
				
			}
			else if(propertyConfig.getProperty("browser").equals("Chrome"))
			{
				System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+"//src//chromedriver.exe");
				driver=new ChromeDriver();
			}
		 
		}
	}	
/***********************************************************************************************/	
	//By using this method, enter data into the particular field
	
	public static void setData(WebElement element, String inputdata)
	{
		if(!(inputdata==""))
		{
			try 
			{	element.clear();
				element.sendKeys(inputdata);
				element.sendKeys(Keys.RETURN);
				APPLICATION_LOGS.debug("Entered "+inputdata+" into "+element);
				
			}  catch (NoSuchElementException e)
			   {
				   System.out.println("Element not found to setData");  
			   } 
			   catch (ElementNotVisibleException e1) 
			   {
				   System.out.println("Element not Visible to setData");
			   }
				catch(NotFoundException e2)
				{
				System.out.println("Please check your "+inputdata+"to setData");
				}
		}
				
	}
/***********************************************************************************************/
	//By using this method, click on Buttons,Link Text, Check Boxes etc
	
	public static void clickOn(WebElement element)
	{
		try 
		{
			element.click();
			Thread.sleep(2000);
		} catch (NoSuchElementException e)
		   {
			   System.out.println(element+" not found");  
		   } 
		   catch (ElementNotVisibleException e1) 
		   {
			   System.out.println(element+" not Visible");
		   }
			catch(Exception e2)
			{
				e2.printStackTrace();
			}
			  
		
	}
/***********************************************************************************************/	
	
	//Use this method, first to find the element and then click on Buttons,Link Text, Check Boxes etc and also passing the Element Name.
	
	public static void clickOn(By by,String elementName)
	{
		try 
		{
			driver.findElement(by).click();
		} catch (NoSuchElementException e)
		   {
			   System.out.println(elementName+" not found to click");  
		   } 
		   catch (ElementNotVisibleException e1) 
		   {
			   System.out.println(elementName+" not Visible to click");
		   }
			catch(Exception e2)
			{
				e2.printStackTrace();
			}
	}
/***********************************************************************************************/
	//By using this method, select a particular element from the DDL
	
	public static void selectFromDDL(WebElement element, String inputdata)
	{
			try 
			{
				System.out.println("About to select from ddl "+inputdata);
			
				Select ddl = new Select(element);
				ddl.selectByVisibleText(inputdata);
				Thread.sleep(2000);
				
			} catch (Exception e) 
			{
				System.out.println(e.getMessage());
			}
	}

	//By using this method, we can get Current Date in the format of YYYY_MM_DD_HH_MM_SS
	 
	 public static String getCurrentDate()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
 		Date date = new Date();
 		String Date_time=dateFormat.format(date);
		return Date_time;
	}
/***********************************************************************************************/
	//By using this method to capture the screenshot
	 
	public static void getScreenShot(String fileName) {
		File srcFile = ((TakesScreenshot)(BaseClass.driver)).getScreenshotAs(OutputType.FILE);
	    try {
	    	FileUtils.copyFile(srcFile, new File(System.getProperty("user.dir")+"\\src\\com\\pro\\TestOutput\\ScreenShots\\"+fileName+".jpeg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
/***********************************************************************************************/
	//By using this method, we can open the URL
	
	public static void openURL(String URL)
	{
		try {
			driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
			driver.manage().timeouts().pageLoadTimeout(90,TimeUnit.SECONDS);
			driver.get(URL);
			Reporter.log("--login--");
			driver.manage().window().maximize();
			System.out.println("Required URL Opened Successfully");
			Thread.sleep(1000);
			
		} catch (Exception e) {
			System.out.println("LOGIN FAILED");
		}
	}

/***********************************************************************************************/
	
	 
	 
	//By using this method, we can quit the driver
	
	public static void quitDriver(){
		driver.quit();
		driver=null;
	}

/********************************************************************************************/


	
	//Used to take screenshot and return the path of screenshot to attach to extent Reports
	public static String captureScreenshot(String screenShotName)
	{
		File srcFile = ((TakesScreenshot)(BaseClass.driver)).getScreenshotAs(OutputType.FILE);
		String destFile = System.getProperty("user.dir")+"\\src\\com\\pro\\TestOutput\\ScreenShots\\"+screenShotName+".jpeg";
	    try {
	    	FileUtils.copyFile(srcFile, new File(destFile));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return destFile;
	}

/**************************************************************************************************/
	
}