package ca.uqam.vivo.testbench.en_US.test;

import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.annotations.BeforeClass;

import ca.uqam.vivo.testbench.core.test.ResearchOverviewToPersonUnitTest;
import ca.uqam.vivo.testbench.util.SeleniumHelper;
import net.bytebuddy.implementation.bind.annotation.Super;

/**
 * @author Michel Heon
 * en_US_ResearchOverviewToPersonUnitTest.java
 * 
 * 2020-04-24
 *
 */
public class en_US_ResearchOverviewToPersonUnitTest extends ResearchOverviewToPersonUnitTest {
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        ResearchOverviewToPersonUnitTest.setUpBeforeClass();
        SeleniumHelper.getInstance().setSelectedLangage("en_US");
    }

    private static final Log log = LogFactory.getLog(en_US_ResearchOverviewToPersonUnitTest.class);

}
