package ca.uqam.vivo.testbench.en_US.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;

import ca.uqam.vivo.testbench.core.test.EmailAddressUnitTest;
import ca.uqam.vivo.testbench.core.test.ResearchOverviewToPersonUnitTest;
import ca.uqam.vivo.testbench.util.SampleGraphUtil;
import ca.uqam.vivo.testbench.util.SeleniumHelper;

/**
 * @author Michel Heon
 * en_US_EmailAddressUnitTest.java
 * 
 * 2020-04-24
 *
 */
public class EmailAddressUnitTest_en_US extends EmailAddressUnitTest {
    private static final Log log = LogFactory.getLog(EmailAddressUnitTest_en_US.class);
    @BeforeClass
    public void setUpBeforeClass() {
        SampleGraphUtil.SAMPLE_LOAD_I18N=true;
        log.info("Setup before Class");
        super.setUpBeforeClass();
        SeleniumHelper.getInstance().setSelectedLangage("en_US");
    }
}
