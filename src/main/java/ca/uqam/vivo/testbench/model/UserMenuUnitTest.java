package ca.uqam.vivo.testbench.model;

import org.testng.AssertJUnit;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import ca.uqam.vivo.testbench.util.SampleGraphUtil;

import org.testng.annotations.BeforeClass;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNotNull;
import static org.testng.AssertJUnit.assertNull;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Alert;
import org.openqa.selenium.Keys;
import java.util.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
public class UserMenuUnitTest extends TestBenchModel{
    private static final Log log = LogFactory.getLog(UserMenuUnitTest.class);
    private String usrURI;
    private String usrDISPLAY;
    protected static Object[][] dp_en_US() {
        ArrayList<String> menuValues = new ArrayList<String>();
        menuValues.add("Affiliation");
        menuValues.add("Publications");
        menuValues.add("Research"); 
        menuValues.add("Teaching"); 
        menuValues.add("Service"); 
        menuValues.add("Background"); 
        menuValues.add("Contact"); 
        menuValues.add("Identity");
        menuValues.add("Other"); 
        menuValues.add("View All");
        return new Object[][] {
            new Object[] {"en_US", menuValues },
        };
    }
    public static Object[][] dp_en_CA() {
        ArrayList<String> menuValues = new ArrayList<String>();
        menuValues.add("Affiliation");
        menuValues.add("Publications");
        menuValues.add("Research"); 
        menuValues.add("Teaching"); 
        menuValues.add("Service"); 
        menuValues.add("Background"); 
        menuValues.add("Contact"); 
        menuValues.add("Identity");
        menuValues.add("Other"); 
        menuValues.add("View All");
        return new Object[][] {
            new Object[] {"en_CA", menuValues },
        };
    }
    protected static Object[][] dp_fr_CA() {
        ArrayList<String> menuValues = new ArrayList<String>();
        menuValues.add("Affiliations");
        menuValues.add("Publications");
        menuValues.add("Recherche"); 
        menuValues.add("Enseignement"); 
        menuValues.add("Services"); 
        menuValues.add("Expérience"); 
        menuValues.add("Coordonnées"); 
        menuValues.add("Identifiants");
        menuValues.add("Autre"); 
        menuValues.add("Tout afficher");
        return new Object[][] {
            new Object[] {"fr_CA",  menuValues },
        };
    }
    protected static Object[][] dp_fr_FR() {
        ArrayList<String> menuValues = new ArrayList<String>();
        menuValues.add("Affiliations");
        menuValues.add("Publications");
        menuValues.add("Recherche"); 
        menuValues.add("Enseignement"); 
        menuValues.add("Services"); 
        menuValues.add("Expérience"); 
        menuValues.add("Coordonnées"); 
        menuValues.add("Identifiants");
        menuValues.add("Autre"); 
        menuValues.add("Tout afficher");
        return new Object[][] {
            new Object[] {"fr_FR", menuValues },
        };
    }
    protected static Object[][] dp_de_DE() {
        ArrayList<String> menuValues = new ArrayList<String>();
        menuValues.add("Zugehörigkeit");
        menuValues.add("Publikationen");
        menuValues.add("Forschung"); 
        menuValues.add("Lehre"); 
        menuValues.add("Dienstleistung"); 
        menuValues.add("Hintergrund"); 
        menuValues.add("Kontakt"); 
        menuValues.add("Identität");
        menuValues.add("Anderes"); 
        menuValues.add("Alle anzeigen");
        return new Object[][] {
            new Object[] {"de_DE", menuValues },
        };
    }

    @BeforeClass
    public void setUpBeforeClass() throws Exception {
        try {
            log.info("Setup before Class");
            initSelenium(isI18nInstance);
            String id = "n733";
            usrURI=this.getUsrURI(id);
            usrDISPLAY = this.getUsrDisplay(id);
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
        log.info("Phase 5 do logout");
        sh.logout();
        log.info("Phase 5 logout");
    }
    @Test(dependsOnMethods={"phase3"})
    private void phase4() throws IOException {
        log.info("Phase 4 Nothing to do");
    }
    @Test(dependsOnMethods={"phase2"})
    private void phase3() throws IOException {
        log.info("Phase 3 Nothing to do");
    }
    @Test(dependsOnMethods={"phase1"}, dataProvider = "dp")
    public void phase2( String lang, ArrayList<String> menuValues) {
        log.info("Phase 2 Processing " + lang);
        driver.get(usrURI);
        ArrayList<String> groupNameList = new ArrayList<String>();
        WebElement propertyTabsList = driver.findElement(By.className("propertyTabsList"));
        List<WebElement> liList = propertyTabsList.findElements(By.tagName("li"));
        for (Iterator iterator = liList.iterator(); iterator.hasNext();) {
            WebElement webElement = (WebElement) iterator.next();
            String elementText = webElement.getText();
           if (elementText!=null && !elementText.isEmpty() && !elementText.equals(" ") ){
               groupNameList.add(elementText);
            }
        }
        log.info("Comparaison menu values result = "+menuValues.equals(groupNameList));
        log.info("Phase 2 Done");
        AssertJUnit.assertEquals("At least one value of the menu items is not the same as expected", menuValues, groupNameList);
    }
    @Test()
    private void phase1() throws InterruptedException {
        log.info("Phase 1 Login");
        sh.login();
        log.info("Phase 1 Login Done");
    }

}
