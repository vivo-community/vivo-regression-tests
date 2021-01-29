package ca.uqam.vivo.testbench.en_CA.test;

import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;

import ca.uqam.vivo.testbench.model.HeadOfFacultyUnitTest;
import ca.uqam.vivo.testbench.model.ResearchOverviewToPersonUnitTest;
import ca.uqam.vivo.testbench.model.UserMenuUnitTest;
import ca.uqam.vivo.testbench.util.SampleGraphUtil;
import ca.uqam.vivo.testbench.util.SeleniumHelper;

public class UserMenuUnitTest_en_CA extends UserMenuUnitTest {
    private static final Log log = LogFactory.getLog(UserMenuUnitTest_en_CA.class);
    @BeforeClass
    public void setUpBeforeClass() throws Exception {
        log.info("Setup before Class");
        isI18nInstance=true;
        super.setUpBeforeClass();
        SeleniumHelper.getInstance().setSelectedLangage("en_CA");
    }
    @DataProvider
    public Object[][] dp() {
        return UserMenuUnitTest.dp_en_CA();
    }
}