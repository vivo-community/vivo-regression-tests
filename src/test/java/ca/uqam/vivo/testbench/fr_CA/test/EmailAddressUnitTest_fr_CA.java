package ca.uqam.vivo.testbench.fr_CA.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;

import ca.uqam.vivo.testbench.core.test.EmailAddressUnitTest;
import ca.uqam.vivo.testbench.util.SampleGraphUtil;
import ca.uqam.vivo.testbench.util.SeleniumHelper;

/**
 * @author Michel Heon
 * en_US_EmailAddressUnitTest.java
 * 
 * 2020-04-24
 *
 */
public class EmailAddressUnitTest_fr_CA extends EmailAddressUnitTest {
    private static final Log log = LogFactory.getLog(EmailAddressUnitTest_fr_CA.class);
    @BeforeClass
    public void setUpBeforeClass() {
        log.info("Setup before Class");
        SampleGraphUtil.SAMPLE_LOAD_I18N=true;
        super.setUpBeforeClass();
        SeleniumHelper.getInstance().setSelectedLangage("fr_CA");
    }
}
