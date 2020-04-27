package ca.uqam.vivo.testbench.en_US.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.annotations.BeforeClass;

import ca.uqam.vivo.testbench.core.test.EmailAddressUnitTest;
import ca.uqam.vivo.testbench.core.test.ResearchOverviewToPersonUnitTest;
import ca.uqam.vivo.testbench.util.SeleniumHelper;

/**
 * @author Michel Heon
 * en_US_EmailAddressUnitTest.java
 * 
 * 2020-04-24
 *
 */
public class en_US_EmailAddressUnitTest extends EmailAddressUnitTest {
    private static final Log log = LogFactory.getLog(en_US_EmailAddressUnitTest.class);
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        ResearchOverviewToPersonUnitTest.setUpBeforeClass();
        SeleniumHelper.getInstance().setSelectedLangage("en_US");
    }
}
