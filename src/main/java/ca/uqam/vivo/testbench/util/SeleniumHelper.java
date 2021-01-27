package ca.uqam.vivo.testbench.util;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * @author Michel Heon
 * SeleniumHelper.java
 * 
 * 2020-04-21
 *
 */
public class SeleniumHelper {
    private static final Log log = LogFactory.getLog(SeleniumHelper.class);
    public static String sparqlUpdateEndpointUrl;
    public static String sparqlQueryEndpointUrl ;
    public static String sampleFileName ;
    public static SeleniumHelper single_instance;
//    public static Properties systemProp;
    public static String userName;
    public static String password;
    public static SampleGraphUtil sgu;
    public static String selectedLangage = null;
    private  FirefoxDriver driver;
    private String vivoUrl;
	private TestBenchConstant tbConstant;
    public static JavascriptExecutor js;
    public static HashMap<String, Object> vars;
	private static Boolean LoadI18n = null;
    public void seleniumSetupTestCase(boolean LoadI18N){
    	LoadI18n=LoadI18N;
        SeleniumHelper.getInstance();
    }
    /*
     * required to reset the Helper between each unit test.
     */
    public void quit(){
        single_instance=null;
    }
    private SeleniumHelper() throws IOException{
        sgu = SampleGraphUtil.getInstance();
        tbConstant = TestBenchConstant.getInstance();
        // Cleaning all sample-data
        sgu.delete(LoadI18n);
        // Loading all sample-data
        sgu.load(LoadI18n);
        try {
            System.setProperty(tbConstant.SELENIUM_DRIVER, tbConstant.SELENIUM_DRIVER_LOCATION);            
        } catch (Exception e) {
            throw e;
        }
        driver					= new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        js 						= (JavascriptExecutor) driver;
        vars 					= new HashMap<String, Object>();
        if (LoadI18n){
            userName				= tbConstant.VIVO_ROOT_LOGIN_I18N;
            password				= tbConstant.VIVO_ROOT_PASSWD_I18N;
            vivoUrl 				= tbConstant.VIVO_URL_BASE_I18N;
            sparqlUpdateEndpointUrl	= tbConstant.VIVO_SPARQL_UPDATE_ENDPOINT_URL_I18N;
            sparqlQueryEndpointUrl	= tbConstant.VIVO_SPARQL_QUERY_ENDPOINT_URL_I18N;        	
        } else {
            userName				= tbConstant.VIVO_ROOT_LOGIN_ORIG;
            password				= tbConstant.VIVO_ROOT_PASSWD_ORIG;
            vivoUrl 				= tbConstant.VIVO_URL_BASE_ORIG;
            sparqlUpdateEndpointUrl	= tbConstant.VIVO_SPARQL_UPDATE_ENDPOINT_URL_ORIG;
            sparqlQueryEndpointUrl	= tbConstant.VIVO_SPARQL_QUERY_ENDPOINT_URL_ORIG;
        }
    }
    
    public static void init(boolean LoadI18N){
    	LoadI18n=LoadI18N;
    }
    public static SeleniumHelper getInstance() 
    { 
        if (single_instance == null)
            try {
            	if (LoadI18n == null) LoadI18n=true;
                single_instance = new SeleniumHelper();
            } catch (IOException e) {
                e.printStackTrace();
            } 
  
        return single_instance; 
    }

    public void login() {
        driver.get(vivoUrl);
        driver.manage().window().setSize(new Dimension(1024, 1208));
        driver.findElement(By.id("loginName")).click();
        driver.findElement(By.id("loginPassword")).sendKeys(password);
        driver.findElement(By.id("loginName")).sendKeys(userName);
        driver.findElement(By.name("loginForm")).click();
        if (LoadI18n) selectLanguage();
        log.info("Cleaning solr index");
        driver.get( vivoUrl+"/SearchIndex?rebuild=true");
    }
    public void selectLanguage(){
        if (selectedLangage!=null && !selectedLangage.isEmpty())
        driver.get( vivoUrl+"/selectLocale?selection="+selectedLangage);
    }
    public void selectLanguage(String lang){
        setSelectedLangage(lang);
        if (selectedLangage!=null && !selectedLangage.isEmpty())
        driver.get( vivoUrl+"/selectLocale?selection="+selectedLangage);
    }
    public FirefoxDriver getDriver() {
        return driver;
    }

    public void logout() {
        driver.get(vivoUrl+"/logout");
    }

    public String getSelectedLangage() {
        return selectedLangage;
    }

    public void setSelectedLangage(String selectedLangage) {
        SeleniumHelper.selectedLangage = selectedLangage;
    } 
}
