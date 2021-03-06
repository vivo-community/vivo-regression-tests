package ca.uqam.vivo.testbench.fr_CA.test;

import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;

import ca.uqam.vivo.testbench.model.ResearchOverviewToPersonUnitTest;
import ca.uqam.vivo.testbench.util.SampleGraphUtil;
import ca.uqam.vivo.testbench.util.SeleniumHelper;
import net.bytebuddy.implementation.bind.annotation.Super;

/**
 * @author Michel Heon
 * en_US_ResearchOverviewToPersonUnitTest.java
 * 
 * 2020-04-24
 *
 */
public class ResearchOverviewToPersonUnitTest_fr_CA extends ResearchOverviewToPersonUnitTest {
    private static final Log log = LogFactory.getLog(ResearchOverviewToPersonUnitTest_fr_CA.class);
    @BeforeClass
    public void setUpBeforeClass() throws Exception {
        log.info("Setup before Class");
        isI18nInstance=true;
        super.setUpBeforeClass();
        SeleniumHelper.getInstance().setSelectedLangage("fr_CA");
    }
    @DataProvider
    public Object[][] dp() {
      return new Object[][] {
        new Object[] { "Français (Canada)", "Voici un nouvel aperçu de la recherche" },
      };
    }
}
