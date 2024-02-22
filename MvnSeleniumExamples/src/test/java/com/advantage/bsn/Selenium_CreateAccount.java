package com.advantage.bsn;

import java.util.Iterator;
import java.util.Random;
import org.testng.Assert;
import org.testng.annotations.*;

import com.fmk.base.TSD_BaseClassDrivers;

import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

/***
 * 
 * @author jose.galvan
 * references: 
 * 	https://groups.google.com/g/selenium-users/c/Y08WMiT5Meo?pli=1
*  	https://github.com/sahajamit/chrome-devtools-webdriver-integration/tree/master
 
 */
public class Selenium_CreateAccount extends TSD_BaseClassDrivers {

    Actions actions = null;
    WebElement element = null ;
    
  @Test(description="Goto URL",priority=5)
  public void testGotoUrl() throws Exception {
	  try {
		     driver.get(baseUrl);
		     SyncPageToLoad();
//
//		     getLogEntries();
//		     
		     Thread.sleep(5000);
		     waitForElementPresent(By.id("menuUser"), 5);
		     //waitForElement.until(ExpectedConditions.invisibilityOfElementLocated (By.id("menuUser")));
		    //waitForElement.until(ExpectedConditions.elementToBeClickable (By.id("menuUser")));
		     WebElement element = driver.findElement(By.id("menuUser"));
		     actions = new Actions(driver);
		     actions.moveToElement(element).click().build().perform();
		     Thread.sleep(3000);
		     
	  } catch (Exception e) {
		 Assert.fail(e.getMessage());
	  }
  }

   public void getLogEntries() {
	   try {
           String currentURL = driver.getCurrentUrl();
           LogEntries logs = driver.manage().logs().get("performance");
           int status = -1;
           System.out.println("\\nList of log entries:\\n");
           for (Iterator<LogEntry> it = logs.iterator(); it.hasNext();) {
               LogEntry entry = it.next();
               try {
                   JSONObject json = new JSONObject(entry.getMessage());

                   // System json messenger 
                   System.out.println(json.toString());
                   JSONObject message = json.getJSONObject("message");
                   String method = message.getString("method");

                   if (method != null && "Network.responseReceived".equals(method)) {
                       JSONObject params = message.getJSONObject("params");
                       JSONObject response = params.getJSONObject("response");
                       String messageUrl = response.getString("url");
                       if (currentURL.equals(messageUrl)) {

                           status = response.getInt("status");

                           System.out.println("---------- !!!!!!!!!!!!!! returned response for " + messageUrl
                                   + ": " + status);

                           System.out.println("=======================================================");
                           System.out.println("=======================================================");
                           System.out.println("=======================================================");
                           System.out.println("---------- bingo !!!!!!!!!!!!!! headers: " + response.get("headers"));
                       }
                   }
               } catch (JSONException e) {
                   // TODO Auto-generated catch block
                   e.printStackTrace();
               }
           }
           System.out.println("\nstatus code: " + status);
       } finally {

           if (driver != null) {
               System.out.println("stop ");
           }
       }   
   }
   
  @Test(description="Create Account",priority=10)
  public void testCreateAccount() throws Exception {
	     SyncPageToLoad();
	     waitForElementPresent(By.linkText("CREATE NEW ACCOUNT"),30);
	     element = driver.findElement(By.linkText("CREATE NEW ACCOUNT"));
	     actions = new Actions(driver);
	     actions.moveToElement(element).click().build().perform();
		waitForElementPresent(By.xpath("//div[@id='formCover']/div/div/sec-view/div"),30);		
		driver.findElement(By.xpath("//div[@id='formCover']/div/div/sec-view/div")).click();
		driver.findElement(By.xpath("//div[@id='formCover']/div/div/sec-view/div/label")).click();
		driver.findElement(By.name("usernameRegisterPage")).clear();
		driver.findElement(By.name("usernameRegisterPage")).sendKeys("jose.tester" + randomUser());
		driver.findElement(By.name("emailRegisterPage")).clear();
		driver.findElement(By.name("emailRegisterPage")).sendKeys("jose.tester@gmail.com");
		driver.findElement(By.name("passwordRegisterPage")).clear();
		driver.findElement(By.name("passwordRegisterPage")).sendKeys("S0ftt3k.001");
		driver.findElement(By.name("confirm_passwordRegisterPage")).clear();
		driver.findElement(By.name("confirm_passwordRegisterPage")).sendKeys("S0ftt3k.001");
		driver.findElement(By.name("first_nameRegisterPage")).clear();
		driver.findElement(By.name("first_nameRegisterPage")).sendKeys("jose");
		driver.findElement(By.name("last_nameRegisterPage")).clear();
		driver.findElement(By.name("last_nameRegisterPage")).sendKeys("tester");
		driver.findElement(By.name("phone_numberRegisterPage")).clear();
		driver.findElement(By.name("phone_numberRegisterPage")).sendKeys("+521234567890");
		driver.findElement(By.name("countryListboxRegisterPage")).click();
		new Select(driver.findElement(By.name("countryListboxRegisterPage"))).selectByVisibleText("Mexico");
		driver.findElement(By.xpath("//div[@id='formCover']/div[3]/div/sec-view[2]/div/label")).click();
		driver.findElement(By.name("cityRegisterPage")).clear();
		driver.findElement(By.name("cityRegisterPage")).sendKeys("MTY");
		driver.findElement(By.xpath("//div[@id='formCover']/div[3]/div[2]/sec-view/div/label")).click();
		driver.findElement(By.name("addressRegisterPage")).clear();
		driver.findElement(By.name("addressRegisterPage")).sendKeys("COnocida");
		driver.findElement(By.name("state_/_province_/_regionRegisterPage")).clear();
		driver.findElement(By.name("state_/_province_/_regionRegisterPage")).sendKeys("NL");
		driver.findElement(By.name("postal_codeRegisterPage")).clear();
		driver.findElement(By.name("postal_codeRegisterPage")).sendKeys("66350");
		driver.findElement(By.name("i_agree")).click();
		driver.findElement(By.id("register_btn")).click();
		//Thread.sleep(5000);
		//driver.findElement(By.id("menuUser")).click();
		//driver.findElement(By.xpath("//div[@id='loginMiniTitle']/label[3]")).click();
  }
	
	 private int randomUser() {
			Random rand = new Random(); //instance of random class
	      int upperbound = 9999;
	        //generate random values from 0-24
	      int int_random = rand.nextInt(upperbound); 
	      double double_random=rand.nextDouble();
	      float float_random=rand.nextFloat();
	      return int_random;
	}
	 
	 
}
