package ca.uqam.vivo.testbench.de_DE.test;

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

public class UserMenuUnitTest_de_DE extends UserMenuUnitTest {
    private static final Log log = LogFactory.getLog(UserMenuUnitTest_de_DE.class);
    @BeforeClass
    public void setUpBeforeClass() throws Exception {
        log.info("Setup before Class");
        isI18nInstance=true;
        super.setUpBeforeClass();
        SeleniumHelper.getInstance().setSelectedLangage("de_DE");
    }
    @DataProvider
    public Object[][] dp()  {
            return UserMenuUnitTest.dp_de_DE();
    }
//    {
//        ArrayList<String> menuValues = new ArrayList<String>();
//        menuValues.add("Zugehörigkeit");
//        menuValues.add("Publikationen");
//        menuValues.add("Forschung"); 
//        menuValues.add("Lehre"); 
//        menuValues.add("Dienstleistung"); 
//        menuValues.add("Hintergrund"); 
//        menuValues.add("Kontakt"); 
//        menuValues.add("Identität");
//        menuValues.add("Anderes"); 
//        menuValues.add("Alle anzeigen");
//        return new Object[][] {
//            new Object[] { menuValues },
//        };
//    }

}