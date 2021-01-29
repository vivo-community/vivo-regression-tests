package ca.uqam.vivo.testbench.testrunner;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.testng.TestNG;
import org.testng.xml.XmlSuite;

public class MainTestRunner {
    public void run(String suite) {
        List<String> file = new ArrayList<String>();
        file.add(getClass().getClassLoader().getResource(suite).getPath());
        TestNG testNG = new TestNG();
        testNG.setTestSuites(file);
        testNG.setParallel(XmlSuite.ParallelMode.NONE);
        testNG.run();
    }

    public static void main(String[] args) throws IOException {
        MainTestRunner runner = new MainTestRunner();
        FileInputStream testRunnerFileName =  new FileInputStream(runner.getClass().getClassLoader().getResource("TestRunner.properties").getPath());
        Properties systemProp = new Properties();
        systemProp.load(testRunnerFileName);
        Enumeration<Object> keySet = systemProp.keys();
        while (keySet.hasMoreElements()) {
            String aKey = (String) keySet.nextElement();
            if ( Boolean.valueOf(systemProp.get(aKey).toString())){
                runner.run("testsuites/"+aKey+"-testSuite.xml");
            }
        }
        System.out.print("Done!");
        System.exit(0);
    }

}
