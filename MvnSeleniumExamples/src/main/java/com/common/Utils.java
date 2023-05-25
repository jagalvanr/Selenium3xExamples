package com.common;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

public class Utils {
    private Utils Utils;
    private String os;
    private ChromeDriverService service;
    private LoggingPreferences loggingPreferences = null;
    private WebDriver driver;
    private String wsURL;
    private static ThreadLocal<Utils> instance = new ThreadLocal<Utils>();
    private static final Logger logger = LoggerFactory.getLogger(Utils.class);
    public static Utils getInstance() {
        if (instance.get() == null) {
            instance.set(new Utils());
        }
        return instance.get();
    }

    public Utils(){
        loggingPreferences = new LoggingPreferences();
        loggingPreferences.enable(LogType.PERFORMANCE, Level.ALL);
        loggingPreferences.enable(LogType.BROWSER, Level.ALL);
        loggingPreferences.enable(LogType.DRIVER, Level.ALL);
    }
    public WebDriver launchBrowser() throws IOException {
        return launchBrowser(false);
    }

    public WebDriver launchBrowser(boolean isHeadless) throws IOException {
        return launchBrowser(isHeadless,null);
    }
    public WebDriver launchBrowser(boolean isHeadless, URL remoteDriverURL) throws IOException {
        logger.info("Launching the Chrome...");
        os = System.getProperty("os.name").toLowerCase();
        Map<String, Object> prefs=new HashMap<String,Object>();
        //1-Allow, 2-Block, 0-default
        prefs.put("profile.default_content_setting_values.notifications", 1);

        //0 is default , 1 is enable and 2 is disable
        prefs.put("profile.content_settings.exceptions.clipboard", getClipBoardSettingsMap(1));

        LoggingPreferences logPrefs = new LoggingPreferences();
        logPrefs.enable(LogType.BROWSER, Level.ALL);
        ChromeOptions options = new ChromeOptions();
        options.addArguments(Arrays.asList("--start-maximized"));
        options.addArguments(Arrays.asList("--ssl-protocol=any"));
        options.addArguments(Arrays.asList("--ignore-ssl-errors=true"));
        options.addArguments(Arrays.asList("--disable-extensions"));
        options.addArguments(Arrays.asList("--ignore-certificate-errors"));
        options.addArguments(Arrays.asList("--remote-debugging-port=9222"));
        options.addArguments(Arrays.asList("--remote-debugging-address=0.0.0.0"));

        options.setExperimentalOption("useAutomationExtension", false);
        if(isHeadless){
            options.addArguments(Arrays.asList("--headless","--disable-gpu"));
        }
        options.setExperimentalOption("prefs",prefs);
        options.setCapability("goog:loggingPrefs", loggingPreferences);

        DesiredCapabilities crcapabilities = DesiredCapabilities.chrome();
        crcapabilities.setCapability(ChromeOptions.CAPABILITY, options);
        crcapabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        crcapabilities.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);

//        System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "/dev/shm/chromedriver.log");
        System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, System.getProperty("user.dir") + "\\target\\chromedriver.log");

        if(os.indexOf("mac") >= 0)
            System.setProperty(ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY, System.getProperty("user.dir") + "/src/test/resources/drivers/chromedriver");
        else
            System.setProperty(ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY, System.getProperty("user.dir") + "\\src\\test\\resources\\drivers\\chromedriver.exe");
        
        service = new ChromeDriverService.Builder()
                .usingAnyFreePort()
                .withVerbose(true)
                .build();
        if(Objects.isNull(remoteDriverURL))
            service.start();

        try{
            if(Objects.isNull(remoteDriverURL))
                driver = new RemoteWebDriver(service.getUrl(),crcapabilities);
            else
                driver = new RemoteWebDriver(remoteDriverURL,crcapabilities);
        }catch (Exception e){
            throw e;
        }

        UIUtils.getInstance().setDriver(driver);
        return driver;
    }

    public String getWsURL(){
        return  wsURL;
    }

    public void stopChrome(){
    	if(driver != null) {
            driver.close();
            driver.quit();
    	}
    	if (service != null ) {
            service.stop();    			
    	}

    }

    public void waitFor(long time){
        try {
            TimeUnit.SECONDS.sleep(time);
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public int getDynamicID() {
        int min = 100000;
        int max = 999999;
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    public String getSWURL(String wsURL, String targetID){
        String[] arr = wsURL.split("page/");
        String id = arr[1];
        return wsURL.replace(id,targetID);
    }

    public String getWebSocketDebuggerUrlFromDriverLogs() throws IOException {
        String webSocketDebuggerUrl = "";
        String urlString = "http://localhost:9222/json";

        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        BufferedReader reader =
                new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String json = org.apache.commons.io.IOUtils.toString(reader);
        JSONArray jsonArray = new JSONArray(json);
        for(int i=0; i<jsonArray.length(); i++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            if(jsonObject.getString("type").equals("page")){
                webSocketDebuggerUrl = jsonObject.getString("webSocketDebuggerUrl");
                break;
            }
        }

        if(webSocketDebuggerUrl.equals(""))
            throw new RuntimeException("webSocketDebuggerUrl not found");

        return webSocketDebuggerUrl;
    }

    public String getWebSocketDebuggerUrl() throws IOException {
        return getWebSocketDebuggerUrlFromDriverLogs();
    }

    private static Map<String,Object> getClipBoardSettingsMap(int settingValue) throws JsonProcessingException {
        Map<String,Object> map = new HashMap<>();
        map.put("last_modified",String.valueOf(System.currentTimeMillis()));
        map.put("setting", settingValue);
        Map<String,Object> cbPreference = new HashMap<>();
        cbPreference.put("[*.],*",map);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(cbPreference);
        logger.info("clipboardSettingJson: " + json);
        return cbPreference;
    }
}
