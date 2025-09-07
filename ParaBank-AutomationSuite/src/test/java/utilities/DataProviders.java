package utilities;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.DataProvider;

import testbase.BaseClass;

public class DataProviders extends BaseClass{

	//DataProvider 1
	
	@DataProvider(name="Login")
	public String [][] getData() throws IOException
	{
		String path=".\\testData\\Opencart_LoginData.xlsx";//taking xl file from testData
		
		ExcelUtility xlutil=new ExcelUtility(path);//creating an object for XLUtility
		
		int totalrows=xlutil.getRowCount("Sheet1");	
		int totalcols=xlutil.getCellCount("Sheet1",1);
				
		String logindata[][]=new String[totalrows][totalcols];//created for two dimension array which can store the data user and password
		
		for(int i=1;i<=totalrows;i++)  //1   //read the data from xl storing in two deminsional array
		{		
			for(int j=0;j<totalcols;j++)  //0    i is rows j is col
			{
				logindata[i-1][j]= xlutil.getCellData("Sheet1",i, j);  //1,0
			}
		}
	return logindata;//returning two dimension array
				
	}
	
	@DataProvider(name = "dp")
	public Object[][] accountCreation(Method m) {
	    String sheetName = m.getName(); // Using method name for sheet name
	    int rows = excel.getRowCount(sheetName);
	    int column = excel.getColumnCount(sheetName);
	    int actRows = rows - 1;
	    Object[][] data = new Object[actRows][1];
	    
	    // Starting from row index 2 to skip the header row
	    for (int i = 2; i <= rows; i++) {
	        Map<String, String> hashMap = new HashMap<>();
	        for (int j = 0; j < column; j++) {
	            hashMap.put(excel.getCellData(sheetName, j, 1), // Assuming the header row is at index 1
	                        excel.getCellData(sheetName, j, i));
	        }
	        data[i - 2][0] = hashMap;
	    }
	    return data;
	}

	
	@DataProvider(name="TransferData")
	public Object[][] transferData(Method m) {
	
		String sheetName = m.getName();
		
		int rows = excel.getRowCount(sheetName);
		int cols = excel.getColumnCount(sheetName);
			
		Object[][] data = new Object[rows-1][cols];
	
		
		for(int rowNum=2; rowNum<=rows; rowNum++) {
			
			for(int colNum=0; colNum<cols;colNum++) {
				
				data[rowNum-2][colNum] = excel.getCellData(sheetName, colNum, rowNum);
				
			}
			
		}
		
		
		return data;
		
	}	
	
	@DataProvider(name="data")
	public Object[][] loginData(Method m) {
	
		String sheetName = m.getName();
		
		int rows = excel.getRowCount(sheetName);
		int cols = excel.getColumnCount(sheetName);
			
		Object[][] data = new Object[rows-1][cols];
	
		
		for(int rowNum=2; rowNum<=rows; rowNum++) {
			
			for(int colNum=0; colNum<cols;colNum++) {
				
				data[rowNum-2][colNum] = excel.getCellData(sheetName, colNum, rowNum);
				
			}
			
		}
		
		
		return data;
		
	}	
	//DataProvider 4
}