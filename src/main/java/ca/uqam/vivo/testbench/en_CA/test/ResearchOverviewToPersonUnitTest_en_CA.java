package ca.uqam.vivo.testbench.en_CA.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;

import ca.uqam.vivo.testbench.model.ResearchOverviewToPersonUnitTest;
import ca.uqam.vivo.testbench.util.SeleniumHelper;

/**
 * @author Michel Heon
 * en_US_ResearchOverviewToPersonUnitTest.java
 * 
 * 2020-04-24
 *
 */
public class ResearchOverviewToPersonUnitTest_en_CA extends ResearchOverviewToPersonUnitTest {
    private static final Log log = LogFactory.getLog(ResearchOverviewToPersonUnitTest_en_CA.class);
    @BeforeClass
    public void setUpBeforeClass() throws Exception {
        log.info("Setup before Class");
        isI18nInstance=true;
        super.setUpBeforeClass();
        SeleniumHelper.getInstance().setSelectedLangage("en_CA");
    }

    @DataProvider
    public Object[][] dp() {
      return new Object[][] {
        new Object[] { "en_CA", "This is a new research overview" },
      };
    }

}
