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
    public static Properties systemProp;
    public static String userName;
    public static String password;
    public static SampleGraphUtil sgu;
    public static String selectedLangage = null;
    private  FirefoxDriver driver;
    private String vivoUrl;
    public static JavascriptExecutor js;
    public static HashMap<String, Object> vars;
    public void seleniumSetupTestCase(){
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
        systemProp = sgu.getSystemProp();
        // Cleaning all sample-data
        sgu.delete();
        // Loading all sample-data
        sgu.load();
        try {
            System.setProperty(systemProp.getProperty("selenium.driver"), systemProp.getProperty("selenium.driver.location"));            
        } catch (Exception e) {
            throw e;
        }
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        js = (JavascriptExecutor) driver;
        vars = new HashMap<String, Object>();
        userName=systemProp.getProperty("vivo.rootlogin");
        password=systemProp.getProperty("vivo.password");
        vivoUrl = systemProp.getProperty("url.vivo");
        sparqlUpdateEndpointUrl=systemProp.getProperty("vivo.sparqlUpdateEndpointUrl");
        sparqlQueryEndpointUrl=systemProp.getProperty("vivo.sparqlQueryEndpointUrl");
    }
    public static SeleniumHelper getInstance() 
    { 
        if (single_instance == null)
            try {
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
        assertNotNull(driver);
        selectLanguage();
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
