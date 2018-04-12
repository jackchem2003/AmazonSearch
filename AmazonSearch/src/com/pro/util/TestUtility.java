//Test Utility lookup for RUN_MODE, Place the screenshot and write the results back to TC_INFO sheet.

package com.pro.util;


import java.util.Hashtable;


public class TestUtility {
	
	
	/**********************************************************************************************************************************/	
		//Checking the Run mode for TestCase and skipping the test case, if Run mode set to "N"
		// true- test has to be executed
		// false- test has to be skipped
		public static boolean isExecutable(String testName, Xls_Reader xls){
			
			for(int rowNum=2;rowNum<=xls.getRowCount("TestCases");rowNum++){
				
				if(xls.getCellData("TestCases", "TCID", rowNum).equals(testName)){
				
					if(xls.getCellData("TestCases", "Runmode", rowNum).equals("Y"))
					{
					//	System.out.println("Run mode for "+testName+" is :Y");
						return true;
					}
						
					else 
						return false;
				}
				// print the test cases with RUnmode Y
			}
			
			return false;
		}
	
	/**********************************************************************************************************************************/	
		// To Fetch data from given xls, Sheet, test name
		public static Object[][] getData(String testName,Xls_Reader xls){
			// find the row num from which test starts
			// number of cols in the test
			// number of rows
			// put the data in hastable and put hastable in array
			
			int testStartRowNum=0;
			// find the row num from which test starts
			for(int rNum=1;rNum<=xls.getRowCount(testName);rNum++){
				if(xls.getCellData(testName, 0, rNum).equals(testName)){
					testStartRowNum=rNum;
					break;
				}
			}
		//	System.out.println("Test "+ testName +" starts from "+ testStartRowNum);
			
			int colStartRowNum=testStartRowNum+2;
			int totalCols=0;
			while(!xls.getCellData(testName, totalCols, colStartRowNum).equals("")){
				totalCols++;
			}
			System.out.println("Total Cols in test "+ testName + " are "+ totalCols);
			
			//rows
			int dataStartRowNum=testStartRowNum+3;
			int totalRows=0;
			while(!xls.getCellData(testName, 0, dataStartRowNum+totalRows).equals("")){
				totalRows++;
			}
			System.out.println("Total Rows in test "+ testName + " are "+ totalRows);

			System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			// extract data
			Object[][] data = new Object[totalRows][1];
			int index=0;
			Hashtable<String,String> table=null;
			for(int rNum=dataStartRowNum;rNum<(dataStartRowNum+totalRows);rNum++){
				table = new Hashtable<String,String>();
				for(int cNum=0;cNum<totalCols;cNum++){
					table.put(xls.getCellData(testName, cNum, colStartRowNum), xls.getCellData(testName, cNum, rNum));
					System.out.print(xls.getCellData(testName, cNum, rNum) +" -- ");
				}
				data[index][0]= table;
				index++;
				System.out.println();
			}
			
			//System.out.println("done");
			
			return data;
		}
		
		
		/**********************************************************************************************************************************/		
			

	}
