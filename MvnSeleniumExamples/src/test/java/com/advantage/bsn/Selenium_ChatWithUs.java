package com.advantage.bsn;

import java.util.Set;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.fmk.base.TSD_BaseClassDrivers;

public class Selenium_ChatWithUs extends TSD_BaseClassDrivers   {
	  
	  public String waitForWindow(int timeout) {
	    try {
	      Thread.sleep(timeout);
	    } catch (InterruptedException e) {
	      e.printStackTrace();
	    }
	    Set<String> whNow = driver.getWindowHandles();
	    Set<String> whThen = (Set<String>) vars.get("window_handles");
	    if (whNow.size() > whThen.size()) {
	      whNow.removeAll(whThen);
	    }
	    return whNow.iterator().next();
	  }
	  
	  @Test(description="Chat With US - Happy path ")
	  public synchronized  void chatWithUs() throws InterruptedException {
			 driver.get(baseUrl);
			 SyncPageToLoad();
			driver.manage().window().setSize(new Dimension(1900, 1020));
			{
			  WebElement element = driver.findElement(By.id("chatLogo"));
			  ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);
			  Actions builder = new Actions(driver);
			  builder.moveToElement(element).clickAndHold().perform();
			}
			vars.put("window_handles", driver.getWindowHandles());
			waitForElementPresent(By.id("chatLogo"), 5);
			driver.findElement(By.id("chatLogo")).click();
			vars.put("win9984", waitForWindow(2000));
			vars.put("root", driver.getWindowHandle());
			driver.switchTo().window(vars.get("win9984").toString());
			SyncPageToLoad();
			driver.findElement(By.cssSelector(".ballon")).click();
			//assert(driver.findElement(By.cssSelector("p")).getText().toString(),"Server connect.");
			driver.close();
			driver.switchTo().window(vars.get("root").toString());
			driver.findElement(By.cssSelector("a > .roboto-medium")).click();
			Assert.assertTrue(waitForElementPresent(By.xpath("//label[@class='roboto-bold ng-scope']"), 10));
	  }
}
