package ca.uqam.vivo.testbench.i18n.test;

import static org.testng.Assert.fail;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
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
	private Hashtable<String, String> data_orig;
	private Hashtable<String, String> data_i18n;
	private Hashtable<String, String> data;
	private String ID;

	@BeforeClass
	public void setUpBeforeClass() throws Exception {
		data_orig = new Hashtable<String, String>();
		data_i18n = new Hashtable<String, String>();
	}
	@AfterClass
	public void tearDownAfterClass() throws Exception {
		log.info("Teardown after Class");
	}
	/*
	 * Looping
	 */
	@Test(dataProvider = "dp")
	private void mainProcess(String id) throws Exception {
		log.info("main process: Looping for " +id);
		ID = id;
		List<String> vivoSites = new ArrayList<>();
		vivoSites.add("vivo_i18n");
		vivoSites.add("vivo_orig");
		try {
			for (String vivoSite : vivoSites) {
				phase1(vivoSite); // login to vivoSite
				phase2(vivoSite); // Retrieving data
				phase3(vivoSite); // logout vivoSite
			}
		} catch (Exception e) {
			fail(e.getMessage());
		}
		assertEquals(data_orig, data_i18n);
		log.info("main process: Looping  done");
	}

	@DataProvider
	public Object[][] dp() {
		return new Object[][] {
			new Object[] {"n4869"}, //Event - 17th Annual Conference on Philosophy and Rhetoric
			new Object[] {"n1246"}, //Event - ENC 1114 -- Introduction to Rhetoric
			new Object[] {"n2837"}, //History
			new Object[] {"n128" },	//Powell, Suzanne Katrinsky
			new Object[] {"n1736"}, //Roberts, Patricia
			new Object[] {"n3910"}, //College of Arts and Humanities
			new Object[] {"n3954"}, //Derrida's influence on political rhetoric
			new Object[] {"n733" }, //Bogart, Andrew
			new Object[] {"n6870"} //Peters, Jasper I
		};
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
			usrURI=this.getUsrURI(ID);
			usrDISPLAY = this.getUsrDisplay(ID);
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
			String groupName;
			if (vivoSite.contentEquals("vivo_i18n")) {
				sh.selectLanguage("en_US");
				groupName = "other";
				data = data_i18n;
			} else {
				groupName = "other";       	
				data = data_orig;
			}
			driver.get(usrDISPLAY);
			driver.findElement(By.xpath("//*[@groupname=\""+groupName+"\"]")).click(); // find for groupName in page and click on it 
			List<WebElement> results = driver.findElementsByXPath("//*[@title]");	// Retrieve the title ...
			for (Iterator iterator = results.iterator(); iterator.hasNext();) {
				WebElement webElement = (WebElement) iterator.next();
				String text = webElement.getText(); // ... and retrieve title attribute
				if (text != null && !text.isEmpty() ) {
					String value = webElement.getAttribute("title")+" ->" + text +"<- ";
					String key =  String.valueOf(webElement.getLocation().getX())+"-"+String.valueOf(webElement.getLocation().getY());
					//Exclude some values that we know are different
					if (value.contains("Version")) {
					} else {
						value = value.replace(vivoSite, "VIVO");
						log.info("["+key +"] "+value);
						data.put(key, value);
					}
				}
			}
			sort(data);
		} catch (Exception e) {
			log.error("Problem with " + usrURI);
			throw e;
		}

		log.info("Phase 2 Email validation done");
	}
	private void sort(Hashtable<String, String> map) {
		TreeMap<String, String> sorted = new TreeMap<>(); 
		// Copy all data from hashMap into TreeMap 
		sorted.putAll(map); 
		map.clear();
		map.putAll(sorted);
	} 		

}
