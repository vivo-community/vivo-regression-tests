package ca.uqam.vivo.testbench.model;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNotNull;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import ca.uqam.vivo.testbench.util.SampleGraphUtil;

public class ContentComparisonTest extends TestBenchModel {
	private static final Log log = LogFactory.getLog(ContentComparisonTest.class);
    private String usrURI ;
    private String predicatToTestURI = "http://www.w3.org/2006/vcard/ns#email";
    protected boolean isI18nInstance = false;
	private String usrDISPLAY;

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
    @Test()
    private void phase1() throws InterruptedException {
        log.info("Phase 1 Login");
        sh.login();
        log.info("Phase 1 Login done");
    }
    @Test(dependsOnMethods={"phase1"})
    private void phase2() throws InterruptedException, IOException {
        log.info("Phase 2 Email validation");
        String emailToTest = "peter.japer@someemail.org";
    	try {
		/*  Equivalent to
         *         driver.findElement(By.linkText("Peters, Jasper I")).click();
         */
        driver.get(usrDISPLAY);
//        List<WebElement> results= driver.findElementsByXPath("//*[@title]");
//        for (Iterator iterator = results.iterator(); iterator.hasNext();) {
//			WebElement webElement = (WebElement) iterator.next();
//			String text = webElement.getText();
//			if (text != null && !text.isEmpty() )
//				System.out.println("["+webElement.getAttribute("class") +"] "+webElement.getAttribute("title")+" -)" + text +"(- ");
//		}
      driver.findElement(By.xpath("//*[@groupname=\"publications\"]")).click();;
        List<WebElement> results = driver.findElementsByXPath("//*[@title]");
        for (Iterator iterator = results.iterator(); iterator.hasNext();) {
			WebElement webElement = (WebElement) iterator.next();
			String text = webElement.getText();
			if (text != null && !text.isEmpty() )
				System.out.println("["+webElement.getLocation() +"] "+webElement.getAttribute("title")+" -)" + text +"(- ");
		}
//        
//        // 4 | click | xpath= data-range =  http://www.w3.org/2006/vcard/ns#Work (Primary email Adress)
//        driver.findElement(By.xpath("//*[@data-range='http://www.w3.org/2006/vcard/ns#Work']")).click();;
//        // 5 | click | id=emailAddress | 
//        driver.findElement(By.id("emailAddress")).click();
//        // 6 | type | id=emailAddress | peter.japer@someemail.org
//        log.info("adding "+emailToTest);
//        driver.findElement(By.id("emailAddress")).sendKeys(emailToTest);
//        // 7 | click | id=submit | 
//        driver.findElement(By.id("submit")).click();
		} catch (Exception e) {
			log.error("Problem with :"+emailToTest+" at " + usrURI +" at display uri "+usrDISPLAY);
			log.error("Problem with :"+emailToTest+" at " + usrURI);
			throw e;
		}

        log.info("Phase 2 Email validation done");
    }

}
