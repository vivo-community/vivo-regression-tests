package ca.uqam.vivo.testbench.en_US;

import static org.junit.Assert.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import ca.uqam.vivo.testbench.core.test.EmailAddressUnitTest;
import ca.uqam.vivo.testbench.core.test.HeadOfFacultyUnitTest;
import ca.uqam.vivo.testbench.core.test.ResearchOverviewToPersonUnitTest;
import ca.uqam.vivo.testbench.util.SampleGraphUtil;
import ca.uqam.vivo.testbench.util.SeleniumHelper;
import ca.uqam.vivo.testbench.util.test.SolrPingUnitTest;

/**
 * @author Michel Heon
 * CoreRegressionTest.java
 * 
 * 2020-04-23
 *
 */
public class RegressionTest {
    private static final Log log = LogFactory.getLog(RegressionTest.class);

    @Test
    public void test() {
        SeleniumHelper.getInstance().setSelectedLangage("en_US");
        Result result = JUnitCore.runClasses(SolrPingUnitTest.class);
        for (Failure failure : result.getFailures()) {
            fail(SolrPingUnitTest.class.getName()+" "+failure.toString());
        }
        result = JUnitCore.runClasses(
                EmailAddressUnitTest.class
                , HeadOfFacultyUnitTest.class
                , ResearchOverviewToPersonUnitTest.class
                );
        for (Failure failure : result.getFailures()) {
            fail(RegressionTest.class.getName()+" "+failure.toString());
        }
        log.info(RegressionTest.class.getName()+" "+result.wasSuccessful());
        log.info(RegressionTest.class.getName()+" "+"Done");
    }

}
