package ca.uqam.vivo.testbench.i18n.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import ca.uqam.vivo.testbench.model.HeadOfFacultyUnitTest;
import ca.uqam.vivo.testbench.model.ResearchOverviewToPersonUnitTest;
import ca.uqam.vivo.testbench.model.TestBenchModel;
import ca.uqam.vivo.testbench.model.UserMenuUnitTest;
import ca.uqam.vivo.testbench.util.SampleGraphUtil;
import ca.uqam.vivo.testbench.util.SeleniumHelper;

public class UserMenuUnitTest_i18n extends TestBenchModel {
    private String usrURI;
    private String usrDISPLAY;
    private static final Log log = LogFactory.getLog(UserMenuUnitTest_i18n.class);
    @BeforeClass
    @DataProvider
    public Object[][] dp() {
        return UserMenuUnitTest.dp_en_CA();
    }
    @BeforeClass
    public void setUpBeforeClass() throws Exception {
        try {
            log.info("Setup before Class");
            initSelenium(isI18nInstance);
            String id = "n733";
            usrURI=this.getUsrURI(id);
            usrDISPLAY = this.getUsrDisplay(id);
            isI18nInstance=true;
            SeleniumHelper.getInstance().setSelectedLangage("en_US");
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
    public void phase2( String lang, ArrayList<String> menuValues) throws InterruptedException {
        log.info("Phase 2 Processing " + lang);
        driver.get(usrURI);
        
        log.info("Phase 2 Showing " + "en_US");
        SeleniumHelper.getInstance().selectLanguage("en_US");
        driver.get(usrURI);
        TimeUnit.SECONDS.sleep(1);
        
        log.info("Phase 2 Showing " + "de_DE");
        SeleniumHelper.getInstance().selectLanguage("de_DE");
        driver.get(usrURI);
        TimeUnit.SECONDS.sleep(1);

        log.info("Phase 2 Showing " + "fr_CA");
        SeleniumHelper.getInstance().selectLanguage("fr_CA");
        driver.get(usrURI);
        TimeUnit.SECONDS.sleep(1);

        log.info("Phase 2 Showing " + "fr_FR");
        SeleniumHelper.getInstance().selectLanguage("fr_FR");
        driver.get(usrURI);
        TimeUnit.SECONDS.sleep(1);

        log.info("Phase 2 Showing " + lang);
        SeleniumHelper.getInstance().selectLanguage(lang);
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