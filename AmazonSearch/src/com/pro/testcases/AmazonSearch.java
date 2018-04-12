//AmazonSearch class performs search on the using different TESTNG annotations.
package com.pro.testcases;

import java.util.Hashtable;
import org.openqa.selenium.support.PageFactory;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.pro.util.BaseClass;
import com.pro.util.TestUtility;
import com.pro.amazon.*;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.LogStatus;

public class AmazonSearch extends BaseClass
{

	private static int dataRowNo = 3;
	boolean skip = false;
	boolean pass = false;
	public int k =0;
	public String testCaseNo ="";
	public AmazonSearchAPI amzonSearchPOB;

	@BeforeTest
	  public void beforeTest() throws Exception 
	  {
			//Here config.properties file is loading
		    propertyConfig =initConfigurations(CONFIG_FILE_PATH);
		    reports = new ExtentReports(System.getProperty("user.dir")+"//src//com//pro//TestOutput//Reports//"+this.getClass().getSimpleName()+".html");
		    logger = reports.startTest("Starting Extent Reports");
		    //Killing IE Driver Server
		    //killIeDriverServer();
		    //Initializing Driver for First Instance
			initDriver();
			APPLICATION_LOGS.debug("**************************************************************");
			APPLICATION_LOGS.debug("Initializing PageFactory for Amazon Category API"); 	
			amzonSearchPOB =PageFactory.initElements(driver, AmazonSearchAPI.class);
			
			APPLICATION_LOGS.debug("Opening the given URL");
			openURL(propertyConfig.getProperty("BaseURL"));
			logger.log(LogStatus.INFO, "Opened Url Successfully");

	  }
	
	
	@Test(dataProvider="getCategoryDetails")
	  public void searchCategory(Hashtable<String,String> inputData) throws Exception 
	  {
		    k++;
		  	dataRowNo ++;
		  	testCaseNo= "TC_"+k+"_"; 
		  	
		  	//In What Situation our Test case is going to Skip
		  	if(!TestUtility.isExecutable(this.getClass().getSimpleName(), xls) || inputData.get("Runmode").equals("N"))
			  {
				  skip = true;
				  throw new SkipException("Skipping the test");
			  }
		  	
	        Thread.sleep(2000);
	        amzonSearchPOB.searchCategory(inputData,this.getClass().getSimpleName(),dataRowNo);
	  		
		      pass = true;
			
}


@AfterMethod
public void setStatus() throws Exception
{
  if(skip==false && pass==false)
  {
	  xls.setCellData(this.getClass().getSimpleName(), "Result", dataRowNo, "Failed");
  }
	skip = false;
	pass = false;
	reports.endTest(logger);
	

}
@AfterTest
public void close()
{
	driver.quit();
	
	reports.flush();
	reports.close();
	
	
}

@DataProvider
public Object[][] getCategoryDetails(){
return TestUtility.getData(this.getClass().getSimpleName(),xls);
}
}
