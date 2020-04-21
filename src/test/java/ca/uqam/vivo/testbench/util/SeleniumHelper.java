package ca.uqam.vivo.testbench.util;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

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
    private  FirefoxDriver driver;
    public static JavascriptExecutor js;
    public static HashMap<String, Object> vars;
    public void seleniumSetupTestCase(){
        SeleniumHelper.getInstance();
        sgu = SampleGraphUtil.getInstance();
        // Cleaning all sample-data
        sgu.delete();
        // Loading all sample-data
        sgu.load();
        System.setProperty(systemProp.getProperty("selenium.driver"), systemProp.getProperty("selenium.driver.location"));
        driver = new FirefoxDriver();
        js = (JavascriptExecutor) driver;
        vars = new HashMap<String, Object>();
        userName=systemProp.getProperty("vivo.rootlogin");
        password=systemProp.getProperty("vivo.password");
        sparqlUpdateEndpointUrl=systemProp.getProperty("vivo.sparqlUpdateEndpointUrl");
        sparqlQueryEndpointUrl=systemProp.getProperty("vivo.sparqlQueryEndpointUrl");
    }
    
    private SeleniumHelper() throws IOException{
        sgu = SampleGraphUtil.getInstance();
        systemProp = sgu.getSystemProp();
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
        driver.get("http://localhost:8080/vivo/");
        driver.manage().window().setSize(new Dimension(1024, 1208));
        driver.findElement(By.id("loginName")).click();
        driver.findElement(By.id("loginPassword")).sendKeys(password);
        driver.findElement(By.id("loginName")).sendKeys(userName);
        driver.findElement(By.name("loginForm")).click();
        assertNotNull(driver);
        log.info("Cleaning solr index");
        driver.get( "http://localhost:8080/vivo/SearchIndex?rebuild=true");
        
    }

    public FirefoxDriver getDriver() {
        return driver;
    } 
}
