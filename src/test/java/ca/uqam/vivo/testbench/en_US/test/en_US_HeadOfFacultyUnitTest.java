package ca.uqam.vivo.testbench.en_US.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.annotations.BeforeClass;

import ca.uqam.vivo.testbench.core.test.HeadOfFacultyUnitTest;
import ca.uqam.vivo.testbench.core.test.ResearchOverviewToPersonUnitTest;
import ca.uqam.vivo.testbench.util.SeleniumHelper;

public class en_US_HeadOfFacultyUnitTest extends HeadOfFacultyUnitTest {
    private static final Log log = LogFactory.getLog(en_US_HeadOfFacultyUnitTest.class);
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        ResearchOverviewToPersonUnitTest.setUpBeforeClass();
        SeleniumHelper.getInstance().setSelectedLangage("en_US");
    }

}
