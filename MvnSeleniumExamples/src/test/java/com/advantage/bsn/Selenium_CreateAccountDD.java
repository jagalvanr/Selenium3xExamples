package com.advantage.bsn;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import org.testng.Assert;
import org.testng.annotations.*;

import com.fmk.base.TSD_BaseClassDrivers;

import advantage.model.CreateAccountModel;
import static org.testng.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import java.io.File;
import java.time.Duration;
import org.apache.commons.io.FileUtils;

public class Selenium_CreateAccountDD extends TSD_BaseClassDrivers {


  @Test(description="Goto URL",priority=5)
  public void testGotoUrl() throws Exception {
	  try {
		    driver.get(baseUrl);
		    Thread.sleep(5000);
		    driver.findElement(By.id("menuUser")).click();
		    Thread.sleep(5000);
	  } catch (Exception e) {
		 Assert.fail(e.getMessage());
	  }
  }
//
  String userName ="jose.tester";
  String password = "S0f773k.001";
  String firstName = "jose";
  String lastName = "tester";
  String eMail = "jose.tester@gmail.com";
  String phoneNumber = "+521234567890";
  String country = "Mexico";
  String city = "MTY";
  String address = "Conocida";
  String state = "NL";
  String zipCode = "66350";
//   
  @Test(description="Create Account",dataProvider = "dataTest", priority=10)
  public void testCreateAccount(CreateAccountModel dataTest) throws Exception {
    try {
//
    	  userName =dataTest.getUserName();
    	  password = dataTest.getPassword();
    	  firstName = dataTest.getFirstName();
    	  lastName = dataTest.getLastName();
    	  eMail = dataTest.geteMail();
    	  phoneNumber = dataTest.getPhoneNumber();
    	  country = dataTest.getCountry();
    	  city = dataTest.getCity();
    	  address = dataTest.getAddress();
    	  state = dataTest.getState();
    	  zipCode = dataTest.getZipCode();
//
  
		driver.findElement(By.linkText("CREATE NEW ACCOUNT")).click();
		driver.findElement(By.xpath("//div[@id='formCover']/div/div/sec-view/div")).click();
		driver.findElement(By.xpath("//div[@id='formCover']/div/div/sec-view/div/label")).click();
		driver.findElement(By.name("usernameRegisterPage")).clear();
		driver.findElement(By.name("usernameRegisterPage")).sendKeys(userName + randomUser());
		driver.findElement(By.name("emailRegisterPage")).clear();
		driver.findElement(By.name("emailRegisterPage")).sendKeys(eMail);
		driver.findElement(By.name("passwordRegisterPage")).clear();
		driver.findElement(By.name("passwordRegisterPage")).sendKeys(password);
		driver.findElement(By.name("confirm_passwordRegisterPage")).clear();
		driver.findElement(By.name("confirm_passwordRegisterPage")).sendKeys(password);
		driver.findElement(By.name("first_nameRegisterPage")).clear();
		driver.findElement(By.name("first_nameRegisterPage")).sendKeys(firstName);
		driver.findElement(By.name("last_nameRegisterPage")).clear();
		driver.findElement(By.name("last_nameRegisterPage")).sendKeys(lastName);
		driver.findElement(By.name("phone_numberRegisterPage")).clear();
		driver.findElement(By.name("phone_numberRegisterPage")).sendKeys(phoneNumber);
		driver.findElement(By.name("countryListboxRegisterPage")).click();
		new Select(driver.findElement(By.name("countryListboxRegisterPage"))).selectByVisibleText(country);
		driver.findElement(By.xpath("//div[@id='formCover']/div[3]/div/sec-view[2]/div/label")).click();
		driver.findElement(By.name("cityRegisterPage")).clear();
		driver.findElement(By.name("cityRegisterPage")).sendKeys(city);
		driver.findElement(By.xpath("//div[@id='formCover']/div[3]/div[2]/sec-view/div/label")).click();
		driver.findElement(By.name("addressRegisterPage")).clear();
		driver.findElement(By.name("addressRegisterPage")).sendKeys(address);
		driver.findElement(By.name("state_/_province_/_regionRegisterPage")).clear();
		driver.findElement(By.name("state_/_province_/_regionRegisterPage")).sendKeys(state);
		driver.findElement(By.name("postal_codeRegisterPage")).clear();
		driver.findElement(By.name("postal_codeRegisterPage")).sendKeys(zipCode);
		driver.findElement(By.name("i_agree")).click();
		driver.findElement(By.id("register_btnundefined")).click();
		Thread.sleep(5000);
		driver.findElement(By.id("menuUser")).click();
		driver.findElement(By.xpath("//div[@id='loginMiniTitle']/label[3]")).click();
	} catch (InterruptedException e) {
		Assert.fail(e.getMessage());
	}
  }

  
 
  @DataProvider(name = "dataTest")
  public Object[][] dataTest(){
	  List<CreateAccountModel> dataAccount= new ArrayList<>();
	  CreateAccountModel accounts = new CreateAccountModel();
	  accounts.setUserName("jose.tester");
	  accounts.seteMail("jose.tester@gmail.com");
	  accounts.setFirstName("jose");
	  accounts.setLastName("tester");
	  accounts.setAddress("conocida");
	  accounts.setPhoneNumber("+521234567890");
	  accounts.setZipCode("99350");
	  accounts.setCity("MTY");
	  accounts.setState("NL");
	  accounts.setCountry("Mexico");
	  dataAccount.add(accounts);
	  Object[][] objData = new Object[1][1];
	  objData[0][0]= accounts;
	  return objData;
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
