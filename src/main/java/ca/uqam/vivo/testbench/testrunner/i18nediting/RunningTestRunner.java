package ca.uqam.vivo.testbench.testrunner.i18nediting;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.testng.TestNG;
import org.testng.xml.XmlSuite;

public class RunningTestRunner {
	public void run(String suite) {
		List<String> file = new ArrayList<String>();
        file.add(getClass().getClassLoader().getResource(suite).getPath());
        TestNG testNG = new TestNG();
        testNG.setTestSuites(file);
        testNG.run();
	}

	public static void main(String[] args) {
		RunningTestRunner runner = new RunningTestRunner();
		runner.run("runner/i18ncomparaison/vivo-1.11.1-Non-i18n-testSuite.xml");
	}

}
