package ca.uqam.vivo.testbench.core.test;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNotNull;
import static org.testng.AssertJUnit.assertNull;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import ca.uqam.vivo.testbench.util.SampleGraphUtil;
import ca.uqam.vivo.testbench.util.SeleniumHelper;
@Test
public class HeadOfFacultyUnitTest {
    private static final Log log = LogFactory.getLog(HeadOfFacultyUnitTest.class);
    private WebDriver driver;
    private Map<String, Object> vars;
    JavascriptExecutor js;
    private SeleniumHelper sh;
    private SampleGraphUtil sgu;
    private String usrURI = "http://localhost:8080/vivo/individual/n733";
    private String predicatToTestURI = "http://www.w3.org/2000/01/rdf-schema#label";

    @BeforeMethod
    public void setUp() {
        sh = SeleniumHelper.getInstance();
        sh.seleniumSetupTestCase();
        driver = sh.getDriver();
        js = (JavascriptExecutor) driver;
        vars = new HashMap<String, Object>();
    }
    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
    public void edit() throws InterruptedException {
        /*
         * Phase 1 login
         */
        phase1();
        /*
         * Phase 2 Add college administrator
         */
        phase2();

        /*
         * Phase 3 replace Administrator by Faculty Dean
         */
        phase3();

        /*
         * Phase 4 Delete role
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

        private void phase4() {
        log.info("Phase 4 Role deletion validation");
        // 12 | click | css=#primary-email .delete-individual | 
        log.info("deleting role ");
        driver.findElement(By.cssSelector(".delete-RO_0000053:nth-child(4) > .delete-individual")).click();
        driver.findElement(By.id("submit")).click();
        String returnEmail = SampleGraphUtil.getValueFromTripleStore(queryRole(), usrURI, predicatToTestURI);
        assertNull(returnEmail);
        log.info("Phase 4 Role deletion validation done");

    }
    /**
     * Phase 3 replace Administrator by Faculty Dean
     */
    private void phase3() {
        log.info("Phase 3 Replacing entry");
        String expectedValue = "Faculty Dean";
        log.info("replacing the actual role by: "+expectedValue);
        driver.findElement(By.cssSelector(".edit-RO_0000053:nth-child(3) > .edit-individual")).click();
        driver.findElement(By.id("roleLabel")).click();
        driver.findElement(By.id("roleLabel")).sendKeys(Keys.CONTROL,"a",Keys.DELETE);
        driver.findElement(By.id("roleLabel")).sendKeys(expectedValue);
        driver.findElement(By.id("submit")).click();

        /*
         * Search title
         */
        log.debug("testing role : "+expectedValue);
        String returnedvalue = SampleGraphUtil.getValueFromTripleStore(queryRole(), usrURI, predicatToTestURI);
        assertNotNull(returnedvalue);
        assertEquals(expectedValue, returnedvalue);
        log.info("Phase 3 Replacing entry Done");

    }
    /**
     * Phase 2 Add college administrator
     */
    private void phase2() throws InterruptedException {
        log.info("Phase 2 New entry");
        String expectedValue = "Administrator";
        String expectedCoreEndValue = "2020";
        String expectedCoreStartValue = "2010";

        driver.get(usrURI);
        TimeUnit.SECONDS.sleep(1);
        driver.manage().window().setSize(new Dimension(1024, 1208));
        TimeUnit.SECONDS.sleep(1);
        //        driver.findElement(By.cssSelector(".nonSelectedGroupTab:nth-child(2)")).click();
        //        TimeUnit.SECONDS.sleep(1);
        driver.findElement(By.cssSelector("#affiliationGroup > .property:nth-child(1) > #RO_0000053 .add-individual")).click();
        TimeUnit.SECONDS.sleep(1);
        driver.findElement(By.id("typeSelector")).click();
        {
            WebElement dropdown = driver.findElement(By.id("typeSelector"));
            dropdown.findElement(By.xpath("//option[. = 'College']")).click();
        }
        TimeUnit.SECONDS.sleep(1);
        driver.findElement(By.cssSelector("option:nth-child(6)")).click();
        driver.findElement(By.id("activity")).click();
        driver.findElement(By.id("activity")).sendKeys("Colle");
        TimeUnit.SECONDS.sleep(1);
        driver.findElement(By.id("activity")).sendKeys(Keys.DOWN);
        driver.findElement(By.id("activity")).sendKeys(Keys.DOWN);
        driver.findElement(By.id("activity")).sendKeys(Keys.ENTER);
        driver.findElement(By.id("roleLabel")).click();
        driver.findElement(By.id("roleLabel")).sendKeys(expectedValue);
        TimeUnit.SECONDS.sleep(1);
        driver.findElement(By.id("startField-year")).click();
        driver.findElement(By.id("startField-year")).sendKeys(expectedCoreStartValue);
        driver.findElement(By.id("endField-year")).click();
        driver.findElement(By.id("endField-year")).sendKeys(expectedCoreEndValue);
        driver.findElement(By.id("submit")).click();
        TimeUnit.SECONDS.sleep(1);

        /*
         * Search title
         */
        String returnedvalue = SampleGraphUtil.getValueFromTripleStore(queryRole(), usrURI, predicatToTestURI);
        assertNotNull(returnedvalue);
        assertEquals(expectedValue, returnedvalue);

        /*
         * Search start date
         */
        String returnedCoreStartvalue = SampleGraphUtil.getValueFromTripleStore(queryCoreStart(), usrURI, predicatToTestURI);
        assertNotNull(returnedCoreStartvalue);
        assertEquals(expectedCoreStartValue+"-01-01T00:00:00", returnedCoreStartvalue);

        /*
         * Search end date
         */
        String returnedCoreEndvalue = SampleGraphUtil.getValueFromTripleStore(queryCoreEnd(), usrURI, predicatToTestURI);
        assertNotNull(returnedCoreEndvalue);
        assertEquals(expectedCoreEndValue+"-01-01T00:00:00", returnedCoreEndvalue);
        log.info("Phase 2 New entry Done");

    }
    private void phase1() throws InterruptedException {
        log.info("Phase 1 Login");
        sh.login();
        TimeUnit.SECONDS.sleep(2);
        log.info("Phase 1 Login Done");
    }
    private String queryRole() {
        String queryStr = SampleGraphUtil.getPrefixList()+ 
                "construct { <"+ usrURI +"> rdfs:label  ?label . } \n"
                + "where {  \n"
                + "<"+ usrURI +"> obo:RO_0000053/rdfs:label  ?label . \n"
                +"}";
        log.debug(queryStr);
        return queryStr;
    }
    private String queryCoreEnd() {
        String queryStr = SampleGraphUtil.getPrefixList()+ 
                "construct { <"+ usrURI +"> rdfs:label  ?label . } \n"
                + "where {  \n"
                + "<"+ usrURI +">  obo:RO_0000053/core:dateTimeInterval/core:end/core:dateTime  ?label . \n"
                +"}";
        log.debug(queryStr);
        return queryStr;
    }
    private String queryCoreStart() {
        String queryStr = SampleGraphUtil.getPrefixList()+ 
                "construct { <"+ usrURI +"> rdfs:label  ?label . } \n"
                + "where {  \n"
                + "<"+ usrURI +">  obo:RO_0000053/core:dateTimeInterval/core:start/core:dateTime  ?label . \n"
                +"}";
        log.debug(queryStr);
        return queryStr;
    }
}
