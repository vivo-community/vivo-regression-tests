package ca.uqam.vivo.testbench.core.test;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.testng.Assert.assertEquals;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.text.StringEscapeUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import ca.uqam.vivo.testbench.util.SampleGraphUtil;
import ca.uqam.vivo.testbench.util.SeleniumHelper;

/**
 * 
 * @author Michel Heon
 * AddResearchOverviewToPersonTest.java
 * 
 * 2020-04-16
 *
 * This test case validates the edition of the addition of a Research Overview for a person.
 * The triplestore is populated before the start of the script and then deleted at the end of the script execution with the
 * file kb/sample-data_orig_localhost.ttl
 * 
 * The test is divided into four phases:
 * 1- Login phase to connect to the Vivo server.
 * 2- Navigate in the edition of reserach overview and add the text described by the textToVerify variable
 * 3- Verify by a SPARQL query if the text is correctly integrated in the vivo triplestore
 * 4- erase the text and validate in the triplestore
 * 5- logout
 * 
 */
public class ResearchOverviewToPersonUnitTest {
    private static final Log log = LogFactory.getLog(ResearchOverviewToPersonUnitTest.class);
    public static WebDriver driver;
    private static JavascriptExecutor js;
    private static Map<String, Object> vars;
    private static String sparqlEndpointUrl;
    protected static String textToVerify = "NO-TEXT";
    private static SampleGraphUtil sgu;
    protected static SeleniumHelper sh;
    protected String usrURI = "http://localhost:8080/vivo/individual/n733";
    protected String roURI = "http://vivoweb.org/ontology/core#researchOverview";


    @BeforeClass
    public void setUpBeforeClass() throws Exception {
        try {
            log.info("Setup before Class");
            sh = SeleniumHelper.getInstance();
            sh.seleniumSetupTestCase();
            driver = sh.getDriver();
            js = (JavascriptExecutor) driver;            
            vars = new HashMap<String, Object>();
            sparqlEndpointUrl = SeleniumHelper.sparqlQueryEndpointUrl;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @AfterClass
    public void tearDownAfterClass() throws Exception {
        log.info("Teardown after Class");
        driver.quit();
        sh.quit();
    }
    @Test(dependsOnMethods={"phase4"})
    protected void phase5() throws InterruptedException {
        log.info("Phase 5 login out");
        sh.logout();
        log.info("Phase 5 logout");
    }
    /*
     * The signature guarantees compatibility with the successors of the class.
     */
    @Test(dataProvider = "dp",dependsOnMethods={"phase3"})
    protected void phase4(String lang, String textToVerify) throws InterruptedException {
          log.info("Phase 4 check delete");
          // 3 | click | css=.delete-researchOverview > .delete-individual | 
          driver.findElement(By.cssSelector(".delete-researchOverview > .delete-individual")).click();
          // 4 | click | id=submit | 
          driver.findElement(By.id("submit")).click();
          String roValue = SampleGraphUtil.getValueFromTripleStore(query(), usrURI, roURI);
          assertNull(roValue);
          log.info("Phase 4 Check delete done");
    }
    protected String query() {
        String queryStr = "DESCRIBE"  + "<"+ usrURI +">";
        return queryStr;
    }
    @Test(dependsOnMethods={"phase2"})
    private void phase3() throws UnsupportedEncodingException {
        log.info("Phase 3 Content validation");
        String roValue = SampleGraphUtil.getValueFromTripleStore(query(), usrURI, roURI);
        log.info("Exected value = ["+textToVerify+"]; Actual value = ["+roValue+"]");
        assertEquals(roValue, textToVerify);
        log.info("Phase 3 Content validation done");
    }
    @DataProvider
    public Object[][] dp() {
      return new Object[][] {
        new Object[] {"en_US", "Add new research overview" },
      };
    }
    @Test(dataProvider = "dp", dependsOnMethods={"phase1"})
    protected void phase2(String lang, String textToVerify) throws InterruptedException {      
        log.info("Phase 2 New entry");
        this.textToVerify=textToVerify;
        sh.selectLanguage(lang);
        driver.get(usrURI);
        driver.findElement(By.cssSelector(".nonSelectedGroupTab:nth-child(20)")).click();
        driver.findElement(By.cssSelector(".nonSelectedGroupTab:nth-child(4)")).click();
        driver.findElement(By.cssSelector(".nonSelectedGroupTab:nth-child(6)")).click();
        try {
            driver.findElement(By.cssSelector(".add-researchOverview > .add-individual")).click();
        } catch (Exception e) {
            driver.findElement(By.cssSelector(".edit-researchOverview > .edit-individual")).click();
        }
        driver.switchTo().frame(0);
        driver.findElement(By.cssSelector("html")).click();
        {
            WebElement element = driver.findElement(By.id("tinymce"));
            js.executeScript("if(arguments[0].contentEditable === 'true') {arguments[0].innerText = '"+textToVerify+"'}", element);
        }
        driver.switchTo().defaultContent();
        driver.findElement(By.cssSelector(".editForm")).click();
        driver.findElement(By.id("submit")).click();
//        js.executeScript("window.scrollTo(0,803)");       
        AssertJUnit.assertNotNull(driver);
        log.info("Phase 2 New entry done");
    }
    @Test()
    protected void phase1() throws InterruptedException {
        this.textToVerify=new String(textToVerify.getBytes(), StandardCharsets.UTF_8);
        log.info("Phase 1 Login");
        sh.login();
        log.info("Phase 1 Login done");
    }

}
