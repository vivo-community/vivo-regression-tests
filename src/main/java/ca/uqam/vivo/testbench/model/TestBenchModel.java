package ca.uqam.vivo.testbench.model;

import java.io.IOException;
import java.util.HashMap;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.firefox.FirefoxDriver;

import ca.uqam.vivo.testbench.util.SeleniumHelper;
import ca.uqam.vivo.testbench.util.TestBenchConstant;

public abstract class TestBenchModel {
	protected SeleniumHelper sh;
	protected FirefoxDriver driver;
	protected JavascriptExecutor js;
	protected HashMap<String, Object> vars;
	private TestBenchConstant tbConstant;
	private boolean LoadI18N;

	protected String getUsrURI(String name) {
		String value = "";
		if (LoadI18N){
			value=tbConstant.VIVO_SAMPLE_INDIVIDUAL_BASE_URI_I18N+name;
		} else {
			value=tbConstant.VIVO_SAMPLE_INDIVIDUAL_BASE_URI_ORIG+name;			
		}
		return value;
	}

	protected void initSelenium(boolean LoadI18N) throws IOException{
		this.LoadI18N=LoadI18N;
		SeleniumHelper.init(LoadI18N);
        sh = SeleniumHelper.getInstance();
        sh.seleniumSetupTestCase(LoadI18N);
        driver = sh.getDriver();
        js = (JavascriptExecutor) driver;
        vars = new HashMap<String, Object>();
        tbConstant = TestBenchConstant.getInstance();
	}
	
}
