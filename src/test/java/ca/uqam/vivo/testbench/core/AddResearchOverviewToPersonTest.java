package ca.uqam.vivo.testbench.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.ResourceFactory;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.sparql.engine.http.QueryEngineHTTP ;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;

import ca.uqam.vivo.testbench.util.SampleGraphUtil;

import static org.junit.jupiter.api.Assertions.*;

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
public class AddResearchOverviewToPersonTest {
    private static WebDriver driver;
    private static JavascriptExecutor js;
    private static Map<String, Object> vars;
    private static String sparqlEndpointUrl;
    private static String userName;
    private static String password;
    private static String textToVerify = "Add new research overview";
    private static SampleGraphUtil sgu;


    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        sgu = new SampleGraphUtil();
        // Loading all sample-data
        sgu.load();
        System.setProperty("webdriver.gecko.driver", "./lib/geckodriver-v0.26.0-win64/geckodriver.exe");
        driver = new FirefoxDriver();
        js = (JavascriptExecutor) driver;
        vars = new HashMap<String, Object>();
        sparqlEndpointUrl = "http://localhost:8080/vivo/api/sparqlQuery";
        userName="vivo@uqam.ca";
        password="Vivo2435....";
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
        // Cleaning all sample-data
        sgu.delete();
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
        TimeUnit.SECONDS.sleep(1);
        driver.get("http://localhost:8080/vivo/logout");
        System.out.println("Phase 5 logout");
    }

    private void phase4() throws InterruptedException {
        // 3 | click | css=.delete-researchOverview > .delete-individual | 
        driver.findElement(By.cssSelector(".delete-researchOverview > .delete-individual")).click();
        // 4 | click | id=submit | 
        TimeUnit.SECONDS.sleep(1);
        driver.findElement(By.id("submit")).click();
        String roValue = getResearchOverviewTest();
        assertNull(roValue);
        System.out.println("Phase 5 Check delete done");
    }
    private String getResearchOverviewTest(){
        String usrURI = "http://localhost:8080/vivo/individual/n733";
        String roURI = "http://vivoweb.org/ontology/core#researchOverview";
        String queryStr = "DESCRIBE"  + "<"+ usrURI +">";
        String roValue = null;
        // la construction de la requête
        Query query = QueryFactory.create(queryStr);
        // Construction de l'exécuteur
        try ( QueryExecution qexec = QueryExecutionFactory.sparqlService(sparqlEndpointUrl, query) ) {
            // L'authentification administrateur de vivo
            ((QueryEngineHTTP)qexec).addParam("email", userName) ;
            ((QueryEngineHTTP)qexec).addParam("password", password) ;
            // Lancer l'exécution
            Model model = qexec.execDescribe();
            //Imprimer le résultat de la requête
            model.write(System.out, "TTL") ;
            List<RDFNode> objs = model.listObjectsOfProperty(ResourceFactory.createResource(usrURI), ResourceFactory.createProperty(roURI)).toList();
            assertNotNull(objs);
            try {
                roValue = objs.get(0).asLiteral().getLexicalForm();
            } catch (Exception e) {
                // TODO: nothing
            }
            System.out.println("Phase 3 Content validation done");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return roValue;
    }
    private void phase3() {
        String roValue = getResearchOverviewTest();
        assertTrue(textToVerify.equals(roValue));
        System.out.println("Phase 3 Content validation done");
    }

    private void phase2() throws InterruptedException {
        driver.findElement(By.name("loginForm")).click();
        driver.findElement(By.linkText("People")).click();
        {
            WebElement element = driver.findElement(By.linkText("A"));
            Actions builder = new Actions(driver);
            builder.moveToElement(element).perform();
        }
        {
            WebElement element = driver.findElement(By.tagName("body"));
            Actions builder = new Actions(driver);
            builder.moveToElement(element, 0, 0).perform();
        }
        driver.findElement(By.linkText("B")).click();
        TimeUnit.SECONDS.sleep(1);
        driver.findElement(By.linkText("Bogart, Andrew")).click();
        TimeUnit.SECONDS.sleep(1);
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
        assertNotNull(driver);
        System.out.println("Phase 2 Entry done");
    }

    private void phase1() throws InterruptedException {
        driver.get("http://localhost:8080/vivo/");
        driver.manage().window().setSize(new Dimension(1669, 1208));
        driver.findElement(By.id("loginName")).click();
        driver.findElement(By.id("loginPassword")).sendKeys(password);
        driver.findElement(By.id("loginName")).sendKeys(userName);
        assertNotNull(driver);
        System.out.println("Phase 1 Login done");
    }

}
