package ca.uqam.vivo.testbench.model;


import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNotNull;
import static org.testng.AssertJUnit.assertNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import ca.uqam.vivo.testbench.util.SampleGraphUtil;
import ca.uqam.vivo.testbench.util.SeleniumHelper;
import ca.uqam.vivo.testbench.util.TestBenchConstant;
@Test
public abstract class HeadOfFacultyUnitTest extends TestBenchModel {
    private static final Log log = LogFactory.getLog(HeadOfFacultyUnitTest.class);
    protected String usrURI = null; 
    private String predicatToTestURI = "http://www.w3.org/2000/01/rdf-schema#label";

    @BeforeClass
    public void setUpBeforeClass() throws Exception {
        try {
            log.info("Setup before Class");
            this.initSelenium(isI18nInstance);
            usrURI = usrURI=this.getUsrURI("n733");
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
    private void phase5() throws InterruptedException {
        log.info("Phase 5 login out");
        sh.logout();
        log.info("Phase 5 logout");
    }

    @Test(dependsOnMethods={"phase3"})
    private void phase4() throws IOException {
        log.info("Phase 4 Role deletion validation");
        // 12 | click | css=#primary-email .delete-individual | 
        log.info("deleting role ");
        driver.findElement(By.cssSelector(".delete-RO_0000053:nth-child(4) > .delete-individual")).click();
        driver.findElement(By.id("submit")).click();
        String returnEmail = SampleGraphUtil.getValueFromTripleStore(queryRole(), usrURI, predicatToTestURI, isI18nInstance);
        assertNull(returnEmail);
        log.info("Phase 4 Role deletion validation done");

    }
    /**
     * Phase 3 replace Administrator by Faculty Dean
     * @throws IOException 
     */
    @Test(dependsOnMethods={"phase2"})
    private void phase3() throws IOException {
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
        String returnedvalue = SampleGraphUtil.getValueFromTripleStore(queryRole(), usrURI, predicatToTestURI, isI18nInstance);
        assertNotNull(returnedvalue);
        assertEquals(expectedValue, returnedvalue);
        log.info("Phase 3 Replacing entry Done");

    }
    @DataProvider
    public Object[][] dp() {
        return new Object[][] {
            new Object[] { "College", "College of Arts and Humanities", "#affiliationGroup", "Administrator" },
        };
    }
    /**
     * Phase 2 Add college administrator
     * @param orgName 
     * @param orgType 
     * @throws IOException 
     */
    @Test(dependsOnMethods={"phase1"}, dataProvider = "dp")
    private void phase2(String orgType, String orgName, String groupName, String title) throws InterruptedException, IOException {
        log.info("Phase 2 New entry");
        String expectedValue = title;
        String expectedCoreEndValue = "2020";
        String expectedCoreStartValue = "2010";

        driver.get(usrURI);
        driver.manage().window().setSize(new Dimension(1024, 1208));
        driver.findElement(By.cssSelector(groupName+" > .property:nth-child(1) > #RO_0000053 .add-individual")).click();
        driver.findElement(By.id("typeSelector")).click();
        {
            WebElement dropdown = driver.findElement(By.id("typeSelector"));
            dropdown.findElement(By.xpath("//option[. = '"+orgType+"']")).click();
        }
        driver.findElement(By.id("activity")).click();
        driver.findElement(By.id("activity")).sendKeys(orgName);
        driver.findElement(By.id("roleLabel")).click();
        driver.findElement(By.id("roleLabel")).sendKeys(expectedValue);
        driver.findElement(By.id("startField-year")).click();
        driver.findElement(By.id("startField-year")).sendKeys(expectedCoreStartValue);
        driver.findElement(By.id("endField-year")).click();
        driver.findElement(By.id("endField-year")).sendKeys(expectedCoreEndValue);
        driver.findElement(By.id("submit")).click();


        /*
         * Search title
         */
        String returnedvalue = SampleGraphUtil.getValueFromTripleStore(queryRole(), usrURI, predicatToTestURI, isI18nInstance);
        assertNotNull(returnedvalue);
        assertEquals(expectedValue, returnedvalue);

        /*
         * Search start date
         */
        String returnedCoreStartvalue = SampleGraphUtil.getValueFromTripleStore(queryCoreStart(), usrURI, predicatToTestURI, isI18nInstance);
        assertNotNull(returnedCoreStartvalue);
        assertEquals(expectedCoreStartValue+"-01-01T00:00:00", returnedCoreStartvalue);

        /*
         * Search end date
         */
        String returnedCoreEndvalue = SampleGraphUtil.getValueFromTripleStore(queryCoreEnd(), usrURI, predicatToTestURI, isI18nInstance);
        assertNotNull(returnedCoreEndvalue);
        assertEquals(expectedCoreEndValue+"-01-01T00:00:00", returnedCoreEndvalue);
        log.info("Phase 2 New entry Done");

    }
    @Test()
    private void phase1() throws InterruptedException {
        log.info("Phase 1 Login");
        sh.login();
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
