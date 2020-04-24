package ca.uqam.vivo.testbench.core;

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
import ca.uqam.vivo.testbench.util.test.SolrPingUnitTest;

/**
 * @author Michel Heon
 * CoreRegressionTest.java
 * 
 * 2020-04-23
 *
 */
public class CoreRegressionTest {
    private static final Log log = LogFactory.getLog(CoreRegressionTest.class);

    @Test
    public void test() {
        Result result = JUnitCore.runClasses(SolrPingUnitTest.class);
        for (Failure failure : result.getFailures()) {
            fail(SolrPingUnitTest.class.getName()+" "+failure.toString());
        }
        result = JUnitCore.runClasses(
                EmailAddressUnitTest.class,
                HeadOfFacultyUnitTest.class,
                ResearchOverviewToPersonUnitTest.class);
        for (Failure failure : result.getFailures()) {
            fail(CoreRegressionTest.class.getName()+" "+failure.toString());
        }
        log.info(CoreRegressionTest.class.getName()+" "+result.wasSuccessful());
        log.info(CoreRegressionTest.class.getName()+" "+"Done");
    }

}
