package com.advantage.bsn;
import org.testng.annotations.*;

import com.fmk.base.TSD_BaseClassDrivers;

import static org.testng.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;

public class Selenium_ContactUs extends TSD_BaseClassDrivers  {
  
  @Test(description="Contact Us - Happy Path")
  public synchronized void testContactUs() throws Exception {
	  		System.out.println("The Thread ID for "+ Thread.currentThread().getId());
		  	driver.get(baseUrl);
		  	SyncPageToLoad();
		    //driver.findElement(By.linkText("CONTACT US")).click();
		  	Thread.sleep(5000);
		  	WebElement lnkContactUs =  driver.findElement(By.linkText("CONTACT US"));
		  	js.executeScript("arguments[0].click()", lnkContactUs);
		    SyncPageToLoad();
		    Thread.sleep(5000);
		    driver.findElement(By.name("categoryListboxContactUs")).click();
		    new Select(driver.findElement(By.name("categoryListboxContactUs"))).selectByVisibleText("Laptops");
		    driver.findElement(By.name("productListboxContactUs")).click();
		    new Select(driver.findElement(By.name("productListboxContactUs"))).selectByVisibleText("HP ENVY x360 - 15t Laptop");
		    SyncPageToLoad();
		    driver.findElement(By.name("emailContactUs")).click();
		    driver.findElement(By.name("emailContactUs")).clear();
		    driver.findElement(By.name("emailContactUs")).sendKeys("jose.tester@gmail.com");
		    driver.findElement(By.name("subjectTextareaContactUs")).click();
		    driver.findElement(By.name("subjectTextareaContactUs")).clear();
		    driver.findElement(By.name("subjectTextareaContactUs")).sendKeys("Help me Oviwan Kenobi");
		    driver.findElement(By.id("send_btn")).click();
		    //driver.findElement(By.xpath("//div[@id='registerSuccessCover']/div/p")).click();
		    SyncPageToLoad();
		    assertEquals(driver.findElement(By.xpath("//div[@id='registerSuccessCover']/div/p")).getText(), "Thank you for contacting Advantage support.");
		    driver.findElement(By.linkText("CONTINUE SHOPPING")).click();

  }
}
