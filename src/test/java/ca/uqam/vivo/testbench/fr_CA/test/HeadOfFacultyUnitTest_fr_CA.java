package ca.uqam.vivo.testbench.fr_CA.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;

import ca.uqam.vivo.testbench.core.test.HeadOfFacultyUnitTest;
import ca.uqam.vivo.testbench.core.test.ResearchOverviewToPersonUnitTest;
import ca.uqam.vivo.testbench.util.SampleGraphUtil;
import ca.uqam.vivo.testbench.util.SeleniumHelper;

public class HeadOfFacultyUnitTest_fr_CA extends HeadOfFacultyUnitTest {
    private static final Log log = LogFactory.getLog(HeadOfFacultyUnitTest_fr_CA.class);
    @BeforeClass
    public void setUpBeforeClass() {
        log.info("Setup before Class");
        SampleGraphUtil.SAMPLE_LOAD_I18N=true;
        super.setUpBeforeClass();
        SeleniumHelper.getInstance().setSelectedLangage("fr_CA");
    }
    @DataProvider
    public Object[][] dp() {
      return new Object[][] {
        new Object[] { "Collège", "Collège des arts et des sciences humaines","#AffiliationsGroup" },

      };
    }
}
