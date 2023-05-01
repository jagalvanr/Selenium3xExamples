package com.poc.selenium;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class DataProviderCSV_Example {
	
	 @Test(dataProvider = "loginData")
	    public void testLogin(String username, String password) {
	        /* System.setProperty("webdriver.chrome.driver", "path/to/chromedriver");
	        WebDriver driver = new ChromeDriver();
	        driver.get("https://example.com/login");
	        driver.findElement(By.id("username")).sendKeys(username);
	        driver.findElement(By.id("password")).sendKeys(password);
	        driver.findElement(By.id("login-button")).click();
	        //Assert the expected behavior after login
	        driver.quit(); */
		 	System.out.println("user:     " + username);
		 	System.out.println("password: " + password);
	    }

	    @DataProvider(name = "loginData")
	    public Object[][] getLoginData() throws IOException {
	        String csvFilePath = "src/test/resources/data/loginData.csv";
	        BufferedReader csvReader = new BufferedReader(new FileReader(csvFilePath));
	        String line;
	        int lines = 0;
	        while ((line = csvReader.readLine()) != null) {
	            lines++;
	        }
	        Object[][] data = new Object[lines][2];
	        csvReader = new BufferedReader(new FileReader(csvFilePath));
	        int i = 0;
	        while ((line = csvReader.readLine()) != null) {
	            String[] fields = line.split(",");
	            data[i][0] = fields[0];
	            data[i][1] = fields[1];
	            i++;
	        }
	        csvReader.close();
	        return data;
	    }
	    

}
