package ca.uqam.vivo.testbench.core;

import static org.junit.Assert.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import ca.uqam.vivo.testbench.core.test.EmailAddressTest;
import ca.uqam.vivo.testbench.core.test.HeadOfFacultyTest;
import ca.uqam.vivo.testbench.core.test.ResearchOverviewToPersonTest;
import ca.uqam.vivo.testbench.util.test.SolrPingTest;

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
        Result result = JUnitCore.runClasses(SolrPingTest.class);
        for (Failure failure : result.getFailures()) {
            fail(SolrPingTest.class.getName()+" "+failure.toString());
        }
        result = JUnitCore.runClasses(
                EmailAddressTest.class,
                HeadOfFacultyTest.class,
                ResearchOverviewToPersonTest.class);
        for (Failure failure : result.getFailures()) {
            fail(CoreRegressionTest.class.getName()+" "+failure.toString());
        }
        log.info(CoreRegressionTest.class.getName()+" "+result.wasSuccessful());
        log.info(CoreRegressionTest.class.getName()+" "+"Done");
    }

}
