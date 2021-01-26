package ca.uqam.vivo.testbench.de_DE.test;

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
public class EmailAddressUnitTest_de_DE extends EmailAddressUnitTest {
    private static final Log log = LogFactory.getLog(EmailAddressUnitTest_de_DE.class);
    @BeforeClass
    public void setUpBeforeClass() throws Exception {
        isI18nInstance=true;
        super.setUpBeforeClass();
        SeleniumHelper.getInstance().setSelectedLangage("de_DE");
    }
}
