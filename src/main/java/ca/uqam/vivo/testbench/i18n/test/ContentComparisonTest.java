package ca.uqam.vivo.testbench.i18n.test;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import ca.uqam.vivo.testbench.model.TestBenchModel;
import ca.uqam.vivo.testbench.util.SampleGraphUtil;
import ca.uqam.vivo.testbench.util.SeleniumHelper;

public class ContentComparisonTest extends TestBenchModel {
	private static final Log log = LogFactory.getLog(ContentComparisonTest.class);
	private String usrURI ;
	private String predicatToTestURI = "http://www.w3.org/2006/vcard/ns#email";
	protected boolean isI18nInstance = false;
	private String usrDISPLAY;

	@BeforeClass
	public void setUpBeforeClass() throws Exception {
	}
	@AfterClass
	public void tearDownAfterClass() throws Exception {
		log.info("Teardown after Class");
		driver.quit();
		sh.quit();
	}
	/*
	 * Looping
	 */
	@Test()
	private void mainProcess() throws Exception {
		log.info("main process: Looping");
		List<String> vivoSites = new ArrayList<>();
		vivoSites.add("vivo_i18n");
		vivoSites.add("vivo_orig");
		for (String vivoSite : vivoSites) {
			phase1(vivoSite);
			phase2(vivoSite);
			phase3(vivoSite);

		}
		sh.login();
		log.info("main process: Looping  done");
	}

	private void phase3(String vivoSite) throws IOException {
		stopSelenium();
	}
	/*
	 * Looping
	 */
	private void phase1(String vivoSite) throws Exception {
		log.info("Phase 1 Connecting VIVO: " + vivoSite);
		try {
			isI18nInstance = false;
			if (vivoSite.equals("vivo_i18n")) isI18nInstance = true;
			initSelenium(isI18nInstance);
			String id = "n733";
			usrURI=this.getUsrURI(id);
			usrDISPLAY = this.getUsrDisplay(id);
			sh.login();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		log.info("Phase 1 Login done");
	}
	//    @Test(dependsOnMethods={"phase1"})
	private void phase2(String vivoSite) throws InterruptedException, IOException {
		log.info("Phase 2 Get content data");
		try {
	        if (vivoSite.contentEquals("vivo_i18n")) sh.setSelectedLangage("en_US");
			driver.get(usrDISPLAY);
			driver.findElement(By.xpath("//*[@groupname=\"publications\"]")).click();;
			List<WebElement> results = driver.findElementsByXPath("//*[@title]");
			for (Iterator iterator = results.iterator(); iterator.hasNext();) {
				WebElement webElement = (WebElement) iterator.next();
				String text = webElement.getText();
				if (text != null && !text.isEmpty() )
					System.out.println("["+webElement.getLocation() +"] "+webElement.getAttribute("title")+" -)" + text +"(- ");
			}
		} catch (Exception e) {
			log.error("Problem with " + usrURI);
			throw e;
		}

		log.info("Phase 2 Email validation done");
	}

}
