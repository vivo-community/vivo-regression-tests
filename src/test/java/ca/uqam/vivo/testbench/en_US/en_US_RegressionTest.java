package ca.uqam.vivo.testbench.en_US;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import org.testng.TestNG;
import org.testng.annotations.Test;

import ca.uqam.vivo.testbench.en_US.test.en_US_EmailAddressUnitTest;
import ca.uqam.vivo.testbench.en_US.test.en_US_HeadOfFacultyUnitTest;
import ca.uqam.vivo.testbench.en_US.test.en_US_ResearchOverviewToPersonUnitTest;

/**
 * @author Michel Heon
 * en_US_RegressionTest.java
 * 
 * 2020-04-23
 *
 */
@Test
public class en_US_RegressionTest {
    private static final Log log = LogFactory.getLog(en_US_RegressionTest.class);
    @SuppressWarnings("unused")
    private int rc;

    public void test() {
        TestListenerAdapter tla = new TestListenerAdapter();
        en_US_EmailAddressUnitTest thisTest = new en_US_EmailAddressUnitTest();
        TestNG testng = new TestNG();
        testng.setTestClasses(new Class[] {
                en_US_EmailAddressUnitTest.class,
                en_US_HeadOfFacultyUnitTest.class,
                en_US_ResearchOverviewToPersonUnitTest.class
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
        log.info(en_US_RegressionTest.class.getName()+" "+"Done");
    }

}
