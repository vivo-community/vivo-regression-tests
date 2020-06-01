package ca.uqam.vivo.testbench.orig.test;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;

import ca.uqam.vivo.testbench.model.HeadOfFacultyUnitTest;

public class HeadOfFacultyUnitTest_orig extends HeadOfFacultyUnitTest {
    private static final Log log = LogFactory.getLog(HeadOfFacultyUnitTest_orig.class);
    @BeforeClass
    public void setUpBeforeClass() throws Exception {
        log.info("Setup before Class");
        isI18nInstance=false;
        super.setUpBeforeClass();
    }
    @DataProvider
    public Object[][] dp() {
      return new Object[][] {
        new Object[] { "College", "College of Arts and Humanities", "#affiliationGroup","Administrator" },
      };
    }
}