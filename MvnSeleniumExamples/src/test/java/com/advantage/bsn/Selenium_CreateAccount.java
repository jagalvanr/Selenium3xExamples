package com.advantage.bsn;

import java.util.Random;
import org.testng.Assert;
import org.testng.annotations.*;

import com.fmk.base.TSD_BaseClassDrivers;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;


public class Selenium_CreateAccount extends TSD_BaseClassDrivers {

    Actions actions = null;
    WebElement element = null ;
    
  @Test(description="Goto URL",priority=5)
  public void testGotoUrl() throws Exception {
	  try {
		     driver.get(baseUrl);
		     SyncPageToLoad();
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

  @Test(description="Create Account",priority=10)
  public void testCreateAccount() throws Exception {
	     SyncPageToLoad();
	     element = driver.findElement(By.linkText("CREATE NEW ACCOUNT"));
	     actions = new Actions(driver);
	     actions.moveToElement(element).click().build().perform();
	     Thread.sleep(3000);
		waitForElementPresent(By.xpath("//div[@id='formCover']/div/div/sec-view/div"),10);		
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
		driver.findElement(By.id("register_btnundefined")).click();
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
