package ca.uqam.vivo.testbench.model;

import static org.testng.Assert.assertNull;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import ca.uqam.vivo.testbench.util.SampleGraphUtil;

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
public abstract class ResearchOverviewToPersonUnitTest  extends TestBenchModel  {
    private static final Log log = LogFactory.getLog(ResearchOverviewToPersonUnitTest.class);
    protected String usrURI = null;
    protected static String textToVerify = "NO-TEXT";
    protected String roURI = "http://vivoweb.org/ontology/core#researchOverview";
    protected boolean isI18nInstance = false;

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

    @Test()
    protected void phase1() throws InterruptedException {
        this.textToVerify=new String(textToVerify.getBytes(), StandardCharsets.UTF_8);
        log.info("Phase 1 Login");
        sh.login();
        log.info("Phase 1 Login done");
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

    @Test(dependsOnMethods={"phase2"})
    protected void phase3() throws IOException {
        log.info("Phase 3 Content validation");
        String roValue = SampleGraphUtil.getValueFromTripleStore(query(), usrURI, roURI, isI18nInstance);
        log.info("Exected value = ["+textToVerify+"]; Actual value = ["+roValue+"]");
        AssertJUnit.assertEquals(roValue, textToVerify);
        log.info("Phase 3 Content validation done");
    }

    /*
     * The signature guarantees compatibility with the successors of the class.
     */
    @Test(dataProvider = "dp",dependsOnMethods={"phase3"})
    protected void phase4(String lang, String textToVerify) throws InterruptedException, IOException {
        log.info("Phase 4 check delete");
        // 3 | click | css=.delete-researchOverview > .delete-individual | 
        driver.findElement(By.cssSelector(".delete-researchOverview > .delete-individual")).click();
        // 4 | click | id=submit | 
        driver.findElement(By.id("submit")).click();
        String roValue = SampleGraphUtil.getValueFromTripleStore(query(), usrURI, roURI, isI18nInstance);
        assertNull(roValue,"There is a returned value for ("+usrURI+") ("+roURI+") at isI18nInstance="+isI18nInstance);
        log.info("Phase 4 Check delete done");
    }

    @Test(dependsOnMethods={"phase4"})
    protected void phase5() throws InterruptedException {
        log.info("Phase 5 login out");
        sh.logout();
        log.info("Phase 5 logout");
    }

    @AfterClass
    public void tearDownAfterClass() throws Exception {
        log.info("Teardown after Class");
        driver.quit();
        sh.quit();
    }
    protected String query() {
        String queryStr = "DESCRIBE"  + " <"+ usrURI +">";
        return queryStr;
    }
    @DataProvider
    public Object[][] dp() {
        return new Object[][] {
            new Object[] {"en_US", "Add new research overview" },
        };
    }

}
