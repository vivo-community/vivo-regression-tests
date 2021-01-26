package ca.uqam.vivo.testbench.orig.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.annotations.BeforeClass;

import ca.uqam.vivo.testbench.model.EmailAddressUnitTest;
/**
 * 
 * @author Michel Heon
 * EmailAdressTest_orig.java
 * 
 * 2020-04-16
 * This test validates the addition, modification and deletion of an email address for a user on non i18n vivo's
 */

public class EmailAddressUnitTest_orig extends EmailAddressUnitTest {
	private static final Log log = LogFactory.getLog(EmailAddressUnitTest_orig.class);
	@BeforeClass
	public void setUpBeforeClass() throws Exception {
		log.info("Setup before Class");
		isI18nInstance=false;
		super.setUpBeforeClass();
	}
}
