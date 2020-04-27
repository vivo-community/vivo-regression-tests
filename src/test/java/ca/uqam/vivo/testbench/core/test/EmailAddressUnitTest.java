package ca.uqam.vivo.testbench.core.test;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNotNull;
import static org.testng.AssertJUnit.assertNull;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.Assert;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.internal.runners.statements.Fail;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import ca.uqam.vivo.testbench.util.SampleGraphUtil;
import ca.uqam.vivo.testbench.util.SeleniumHelper;
/**
 * 
 * @author Michel Heon
 * EmailAdressTest.java
 * 
 * 2020-04-16
 * This test validates the addition, modification and deletion of an email address for a user.
 */

public class EmailAddressUnitTest {
    private static final Log log = LogFactory.getLog(EmailAddressUnitTest.class);
    private WebDriver driver;
    JavascriptExecutor js;
    private String usrURI = "http://localhost:8080/vivo/individual/n6870";
    private String predicatToTestURI = "http://www.w3.org/2006/vcard/ns#email";
    private SeleniumHelper sh;

    @BeforeMethod
    public void setUp() {
        sh = SeleniumHelper.getInstance();
        sh.seleniumSetupTestCase();
        driver = sh.getDriver();
        js = (JavascriptExecutor) driver;
    }
    @AfterMethod
    public void tearDown() {
        driver.quit();
        // Cleaning all sample-data
  //      sgu.delete();
    }
    @Test
    public void EmailAdressTest() {
        try {
            /*
             * Login
             */
            phase1();
            /*
             * add email to Peter Japer
             */
            driver.manage().window().setSize(new Dimension(1024, 1208));
            // 3 | click | linkText=Peters, Jasper I | 
            TimeUnit.SECONDS.sleep(5);
            phase2();
            /*
             * Modify email
             */
            phase3();
            /*
             * Deleting email
             */
            phase4();
            /*
             * logout
             */
            phase5();
            
        } catch (Exception e) {
            Assert.fail("EmailAdressTest not completed; "+e.getCause()+ " " +e.getMessage());
        }
    }

    private void phase5() throws InterruptedException {
        log.info("Phase 5 login out");
        TimeUnit.SECONDS.sleep(1);
        sh.logout();
        log.info("Phase 5 logout");
    }
    private void phase4() {
        log.info("Phase 4 Email modification validation");
        // 12 | click | css=#primary-email .delete-individual | 
        log.info("deleting email address ");
        driver.findElement(By.cssSelector("#primary-email .delete-individual")).click();
        // 13 | click | id=submit | 
        driver.findElement(By.id("submit")).click();
        String returnEmail = SampleGraphUtil.getValueFromTripleStore(query(), usrURI, predicatToTestURI);
        assertNull(returnEmail);
        log.info("Phase 4 Email modification validation done");
        
    }
    private void phase3() {
        log.info("Phase 3 Email modification validation");
        String emailToTest = "japer@someemail.org";
        // 8 | click | css=#primary-email .edit-individual | 
        driver.findElement(By.cssSelector("#primary-email .edit-individual")).click();
        // 9 | click | id=emailAddress | 
        driver.findElement(By.id("emailAddress")).click();
//        driver.findElement(By.id("emailAddress")).sendKeys(Keys.CONTROL,"a",Keys.DELETE);
        // 10 | type | id=emailAddress | peter.japer@someemail.com
        log.info("replacing the actual address by: "+emailToTest);
        driver.findElement(By.id("emailAddress")).sendKeys(Keys.CONTROL,"a",Keys.DELETE,emailToTest);
        // 11 | click | id=submit | 
        driver.findElement(By.id("submit")).click();
        String returnEmail = SampleGraphUtil.getValueFromTripleStore(query(), usrURI, predicatToTestURI);
        assertNotNull(returnEmail);
        assertEquals(emailToTest, returnEmail);
        log.info("Phase 3 Email modification validation done");
    }

    private void phase2() throws InterruptedException {
        log.info("Phase 2 Email validation");
        String emailToTest = "peter.japer@someemail.org";
        /*  Equivalent to
         *         driver.findElement(By.linkText("Peters, Jasper I")).click();
         */
        driver.get(usrURI);
        TimeUnit.SECONDS.sleep(1);
        // 4 | click | xpath=(//img[@alt='add'])[2] | 
        driver.findElement(By.xpath("(//img[@alt=\'add\'])[2]")).click();
        // 5 | click | id=emailAddress | 
        driver.findElement(By.id("emailAddress")).click();
        // 6 | type | id=emailAddress | peter.japer@someemail.org
        log.info("adding "+emailToTest);
        driver.findElement(By.id("emailAddress")).sendKeys(emailToTest);
        // 7 | click | id=submit | 
        driver.findElement(By.id("submit")).click();
        String returnEmail = SampleGraphUtil.getValueFromTripleStore(query(), usrURI, predicatToTestURI);
        assertNotNull(returnEmail);
        assertEquals(emailToTest, returnEmail);
        log.info("Phase 2 Email validation done");
    }
    private void phase1() throws InterruptedException {
        log.info("Phase 1 Login");
        sh.login();
        log.info("Phase 1 Login done");

    }

    private String query() {
        String queryStr = SampleGraphUtil.getPrefixList()+ 
                "construct { <"+ usrURI +"> vcard:email  ?email . } \n"
                + "where {  \n"
                + "<"+ usrURI +"> obo:ARG_2000028/vcard:hasEmail/vcard:email  ?email . \n"
                +"}";
        return queryStr;
    }
}
