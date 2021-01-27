package ca.uqam.vivo.testbench.en_CA.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;

import ca.uqam.vivo.testbench.model.EmailAddressUnitTest;
import ca.uqam.vivo.testbench.model.ResearchOverviewToPersonUnitTest;
import ca.uqam.vivo.testbench.util.SampleGraphUtil;
import ca.uqam.vivo.testbench.util.SeleniumHelper;

/**
 * @author Michel Heon
 * en_US_EmailAddressUnitTest.java
 * 
 * 2020-04-24
 *
 */
public class EmailAddressUnitTest_en_CA extends EmailAddressUnitTest {
    private static final Log log = LogFactory.getLog(EmailAddressUnitTest_en_CA.class);
    @BeforeClass
    public void setUpBeforeClass() throws Exception {
        isI18nInstance=true;
        super.setUpBeforeClass();
        SeleniumHelper.getInstance().setSelectedLangage("en_CA");
    }
}
