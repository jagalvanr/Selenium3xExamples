package com.fmk.base;

import static org.testng.Assert.fail;

import java.net.URL;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import io.github.bonigarcia.wdm.WebDriverManager;

public class TSD_BaseClassDrivers {
	  public WebDriver driver;
	  public String baseUrl = "https://advantageonlineshopping.com/#/";
	  public boolean acceptNextAlert = true;
	  public  StringBuffer verificationErrors = new StringBuffer();
	  public JavascriptExecutor js;
	  public Map<String, Object> vars;
	  public WebDriverWait waitForElement;
	  String host_hub = "127.0.0.1" ;
//    Reporting 
	    String log4jConfPath = "log4j.properties";     
	    Logger logger = Logger.getLogger(TSD_BaseClassDrivers.class);     
	    String strPathFile ="";
	    String strPathResults="";
		Integer intIteration=0;
		String testCaseName   ;
		String htmlFileName="";
//
		public String strSuiteName ;
		public String strTestNameXml;
		
		public static String currentBrowser;
		public static boolean blnRunRemote = false;
//
	  @BeforeSuite(alwaysRun=true)
	    synchronized public void SetupEnv(ITestContext ctx ) {
	    	logger.info("=====> Before Suite ");
	    	logger.info("=====> Before Suite - Setting Suite Name ");
	    	strSuiteName = (ctx.getSuite().getName().toString());
	    	logger.info("Suite Name: " + strSuiteName);
	    	strTestNameXml = ctx.getName().toString();    	
	    }
	  
	  	
	    // public  static String strBrowser="chrome";
	    @Parameters({"browser","remotePort"})    
	    @BeforeTest(alwaysRun=true)
	     synchronized protected void SetupTestSuite(@Optional("chrome") String strBrowser ,
	    						                    @Optional("") String strRemotePort ) {
	    	String envBrowser    = System.getenv("browserType");
	    	String envPortRemote = System.getenv("portRemote");
	    	if (envBrowser != null) {
	    		currentBrowser = envBrowser;
	    	}else {
	 		   currentBrowser = strBrowser;	    			
	    	}
	    	if (envPortRemote != null) {
	    		strRemotePort = envPortRemote;
	    	}
	    	if(!strRemotePort.isEmpty()) {
	    		blnRunRemote = true;
	    	}
	    	System.out.println("Current browser = " + currentBrowser);
	    }

	    private WebDriver setDriverBrowser(String _browser) {
	    	switch (_browser.toLowerCase()) {
			case "chrome":{
		  		WebDriverManager.chromedriver().setup();
		  		ChromeOptions options = new ChromeOptions();
// Capabilities		  		
		  		DesiredCapabilities cap = DesiredCapabilities.chrome();
		  		 cap.setCapability(ChromeOptions.CAPABILITY, options);
		  		LoggingPreferences logPrefs = new LoggingPreferences();
		  		logPrefs.enable(LogType.PERFORMANCE, Level.ALL);
		  		options.setCapability("goog:loggingPrefs", logPrefs);
//		  		
		  		driver = new ChromeDriver(options);
		  		return driver;
			}
			case "firefox":{
				String geckoDriverVersion = System.getProperty("geckoDriverVersion");
				if (geckoDriverVersion != null) {
					
				}else {
					geckoDriverVersion = "0.30.0";
				}
				WebDriverManager.firefoxdriver().driverVersion(geckoDriverVersion).setup();
		  		driver = new FirefoxDriver();
		  		return driver;
			}
			case "edge":{
		  		WebDriverManager.edgedriver().setup();;
		  		driver = new EdgeDriver();
		  		return driver;
			}
			
			default:
				return null;
			}
	    }

	    DesiredCapabilities capability = new DesiredCapabilities();
	    private void  setRemoteCapabilities(String _browser) {
	       	OsCheck.OSType ostype=OsCheck.getOperatingSystemType();
	       	Platform platform=null;
	       	switch (ostype) {
	       	    case Windows: {
	       	    	platform= Platform.WINDOWS;
	       	    	break;
	       	    	}
	       	    case MacOS:{
	       	    	platform= Platform.MAC;
	       	    	break;
	       	    	}
	       	    case Linux: {
	       	    	platform= Platform.LINUX;
	       	    	break;
	       	    	}
	       	}

	    	capability.setPlatform(platform);
	    	
	    	try {
		    	switch (_browser.toLowerCase()) {
				case "chrome":{
			    	capability.setBrowserName("chrome");
			    	break;
				}
				case "firefox":{
			    	capability.setBrowserName("firefox");
			    	break;
				}
				case "edge":{
			    	capability.setBrowserName("edge");
			    	break;
				}
				
				default:
			    	capability.setBrowserName("chrome");
			    	break;
				}
	    	}catch(Exception ex) {
	    		System.out.println(ex.getMessage());
	    	}
	    }

	  @BeforeClass(alwaysRun = true)
	  public void setUpClass() throws Exception {
		  	try {
		  		if (blnRunRemote) {
		  			setRemoteCapabilities(currentBrowser);
					String ip=InetAddress.getLocalHost().getHostAddress();
					System.out.println("Current Ip:" + ip );
					if (System.getenv("HUB_HOST") != null) {
						host_hub = System.getenv("HUB_HOST") ;	
					}
					System.out.println("Connecting : " + "http://" + host_hub +":4444/wd/hub");
					driver = new RemoteWebDriver(new URL("http://" + host_hub +":4444/wd/hub"), capability);		  			
		  		}else {
		  			driver = setDriverBrowser(currentBrowser);
		  		}

				js = (JavascriptExecutor) driver;
				vars = new HashMap<String, Object>();
				driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
				waitForElement = new WebDriverWait(driver,30);
				//this.currentBrowser = core.currentBrowser;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	  }

	  @AfterClass(alwaysRun = true)
	  public void tearDown() throws Exception {
		  if (driver != null) {
			    driver.close();			  
		  }
	  }

	    
	    @AfterSuite(alwaysRun=true)
	    synchronized protected void afterSuite() {
	    	logger.info("=====> After Suite ");
			if (driver !=null) {
				System.out.println("Closing suite...");
				driver.quit();	
			}
	    }
	    
// Common methods
	    
	  public boolean isElementPresent(By by) {
	    try {
	      driver.findElement(by);
	      return true;
	    } catch (NoSuchElementException e) {
	      return false;
	    }
	  }

	  public boolean isAlertPresent() {
	    try {
	      driver.switchTo().alert();
	      return true;
	    } catch (NoAlertPresentException e) {
	      return false;
	    }
	  }

	  public String closeAlertAndGetItsText() {
	    try {
	      Alert alert = driver.switchTo().alert();
	      String alertText = alert.getText();
	      if (acceptNextAlert) {
	        alert.accept();
	      } else {
	        alert.dismiss();
	      }
	      return alertText;
	    } finally {
	      acceptNextAlert = true;
	    }
	  }
	   public boolean waitForElementPresent(By by, int intTimeOut) {
	    	driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
	    	int intCount=0;
	    	while(intCount < intTimeOut) {
	            try {
	                driver.findElement(by);
	                break;
	              } catch (Exception e) { }
	            try {
	                intCount++;
	                Thread.sleep(1000);				
				} catch (Exception e) {}
	    	}   
	          driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	          if (intCount >= intTimeOut ) {
	        	  return false;
	          }else {
	        	  return true;
	          }
	      }
	   
	      
	      public  void  SyncPageToLoad(){
	      	String s="";
	      	JavascriptExecutor js = (JavascriptExecutor) driver;
	          while(!s.equals("complete")){
	  	        s=(String) js.executeScript("return document.readyState");
	  	        try {
	  	            Thread.sleep(1000);
	  	        } catch (InterruptedException e) {
	  	            // TODO Auto-generated catch block
	  	            return;
	  	        }
	          }
	          return;
	      }
	 
}

