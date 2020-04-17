package ca.uqam.vivo.testbench.fr_CA;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import org.testng.TestNG;
import org.testng.annotations.Test;

/**
 * @author Michel Heon
 * fr_CA_RegressionTest.java
 * 
 * 2020-04-23
 *
 */
public class fr_CA_RegressionTest {
    private static final Log log = LogFactory.getLog(fr_CA_RegressionTest.class);
    private int rc;

    @Test
    public void test() {
        TestListenerAdapter tla = new TestListenerAdapter();
        TestNG testng = new TestNG();
        testng.setTestClasses(new Class[] {
//                fr_CA_EmailAddressUnitTest.class,
//                fr_CA_HeadOfFacultyUnitTest.class,
//                fr_CA_ResearchOverviewToPersonUnitTest.class
                });
        testng.run();
        tla.getPassedTests();
        List<ITestResult> failedTestsResults = tla.getFailedTests();
        List<ITestResult> successTestsResults = tla.getPassedTests();

        final int failureCount = failedTestsResults.size();

        if (failureCount == 0) {
            log.info(String.format("Test case %s passed", "en_US_RegressionTest"));
            rc = 0;
        } else {
            log.info(String.format("Test case %s failed", "en_US_RegressionTest"));
            rc = -1;
        }
        log.info(fr_CA_RegressionTest.class.getName()+" "+"Done");
    }

}
