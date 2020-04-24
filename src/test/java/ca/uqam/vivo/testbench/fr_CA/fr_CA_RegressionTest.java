package ca.uqam.vivo.testbench.fr_CA;

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
public class fr_CA_RegressionTest {
    private static final Log log = LogFactory.getLog(fr_CA_RegressionTest.class);

    @Test
    public void test() {
        Result result = JUnitCore.runClasses(SolrPingUnitTest.class);
        for (Failure failure : result.getFailures()) {
            fail(SolrPingUnitTest.class.getName()+" "+failure.toString());
        }
//        result = JUnitCore.runClasses(
//                EmailAddressTest.class,
//                HeadOfFacultyTest.class,
//                ResearchOverviewToPersonTest.class);
//        for (Failure failure : result.getFailures()) {
//            fail(CoreRegressionTest.class.getName()+" "+failure.toString());
//        }
        log.info(fr_CA_RegressionTest.class.getName()+" "+result.wasSuccessful());
        log.info(fr_CA_RegressionTest.class.getName()+" "+"Done");
    }

}
