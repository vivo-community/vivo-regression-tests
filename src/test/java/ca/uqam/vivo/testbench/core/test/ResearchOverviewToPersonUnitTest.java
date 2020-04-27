package ca.uqam.vivo.testbench.core.test;

import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
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
    private static WebDriver driver;
    private static JavascriptExecutor js;
    private static Map<String, Object> vars;
    private static String sparqlEndpointUrl;
    private static String textToVerify = "Add new research overview";
    private static SampleGraphUtil sgu;
    private static SeleniumHelper sh;
    private String usrURI = "http://localhost:8080/vivo/individual/n733";
    private String roURI = "http://vivoweb.org/ontology/core#researchOverview";


    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        sh = SeleniumHelper.getInstance();
        sh.seleniumSetupTestCase();
        sgu = sh.sgu;
        driver = sh.getDriver();
        js = (JavascriptExecutor) driver;
        vars = new HashMap<String, Object>();
        sparqlEndpointUrl = SeleniumHelper.sparqlQueryEndpointUrl;
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @BeforeMethod
    public void setUp() throws Exception {
    }

    @AfterMethod
    public void tearDown() throws Exception {
        driver.quit();
    }

    @Test
    public void test() throws InterruptedException {
        /*
         * Login
         */
        phase1();
        /*
         * add text in research overview
         */
        phase2();
        /*
         * Validate the text creation in the triplestore
         */
        phase3();
        /*
         * Deleting text and check that the text is deleted
         */
        phase4();
        /*
         * logout
         */
        phase5();
    }

    private void phase5() throws InterruptedException {
        log.info("Phase 5 login out");
        TimeUnit.SECONDS.sleep(1);
        sh.logout();
        log.info("Phase 5 logout");
    }

    private void phase4() throws InterruptedException {
        log.info("Phase 4 check delete");
        // 3 | click | css=.delete-researchOverview > .delete-individual | 
        driver.findElement(By.cssSelector(".delete-researchOverview > .delete-individual")).click();
        // 4 | click | id=submit | 
        TimeUnit.SECONDS.sleep(1);
        driver.findElement(By.id("submit")).click();
        String roValue = SampleGraphUtil.getValueFromTripleStore(query(), usrURI, roURI);
        assertNull(roValue);
        log.info("Phase 4 Check delete done");
    }
    private String query() {
        String queryStr = "DESCRIBE"  + "<"+ usrURI +">";
        return queryStr;
    }

    private void phase3() {
        log.info("Phase 3 Content validation");
        String roValue = SampleGraphUtil.getValueFromTripleStore(query(), usrURI, roURI);
        AssertJUnit.assertTrue(textToVerify.equals(roValue));
        log.info("Phase 3 Content validation done");
    }

    private void phase2() throws InterruptedException {
        log.info("Phase 2 New entry");
        driver.get(usrURI);
        TimeUnit.SECONDS.sleep(2);
        driver.findElement(By.cssSelector(".nonSelectedGroupTab:nth-child(20)")).click();
        TimeUnit.SECONDS.sleep(1);
        driver.findElement(By.cssSelector(".nonSelectedGroupTab:nth-child(4)")).click();
        TimeUnit.SECONDS.sleep(1);
        driver.findElement(By.cssSelector(".nonSelectedGroupTab:nth-child(6)")).click();
        TimeUnit.SECONDS.sleep(2);
        driver.findElement(By.cssSelector(".add-researchOverview > .add-individual")).click();
        TimeUnit.SECONDS.sleep(1);
        driver.switchTo().frame(0);
        TimeUnit.SECONDS.sleep(1);
        driver.findElement(By.cssSelector("html")).click();
        {
            WebElement element = driver.findElement(By.id("tinymce"));
            js.executeScript("if(arguments[0].contentEditable === 'true') {arguments[0].innerText = '"+textToVerify+"'}", element);
        }
        driver.switchTo().defaultContent();
        driver.findElement(By.cssSelector(".editForm")).click();
        driver.findElement(By.id("submit")).click();
        js.executeScript("window.scrollTo(0,803)");       
        AssertJUnit.assertNotNull(driver);
        log.info("Phase 2 New entry done");
    }

    private void phase1() throws InterruptedException {
        log.info("Phase 1 Login");
        sh.login();
        log.info("Phase 1 Login done");
    }

}
