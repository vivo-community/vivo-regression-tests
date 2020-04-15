package ca.uqam.vivo.testbench;

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
import static org.junit.jupiter.api.Assertions.*;

public class AddResearchOverviewToPersonTest {
    private static WebDriver driver;
    private static JavascriptExecutor js;
    private static Map<String, Object> vars;
    private static String sparqlEndpointUrl;
    private static String userName;
    private static String password;
    private static String textToVerify = "Add new research overview";


    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
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
        phase4();
    }

    private void phase4() throws InterruptedException {
        // 3 | click | css=.delete-researchOverview > .delete-individual | 
        driver.findElement(By.cssSelector(".delete-researchOverview > .delete-individual")).click();
        // 4 | click | id=submit | 
        TimeUnit.SECONDS.sleep(1);
        driver.findElement(By.id("submit")).click();
        // 5 | click | linkText=Log out | 
        TimeUnit.SECONDS.sleep(1);
        driver.get("http://localhost:8080/vivo/logout");
//        driver.findElement(By.linkText("root")).click();
//        driver.findElement(By.linkText("Log out")).click();
        System.out.println("Phase 4 clear and logout");
    }

    private void phase3() {
        String usrURI = "http://vivo.mydomain.edu/individual/n733";
        String roURI = "http://vivoweb.org/ontology/core#researchOverview";
        String queryStr = "DESCRIBE"  + "<"+ usrURI +">";
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
            String roValue = objs.get(0).asLiteral().getLexicalForm();
            assertTrue(textToVerify.equals(roValue));
            System.out.println("Phase 3 Content validation done");
        } catch (Exception e) {
            e.printStackTrace();
        }
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
