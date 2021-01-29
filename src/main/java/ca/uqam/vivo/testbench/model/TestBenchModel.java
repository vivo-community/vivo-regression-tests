package ca.uqam.vivo.testbench.model;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
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
    protected boolean isI18nInstance = true;


    protected String getUsrURI(String name) {
        String value = "";
        if (LoadI18N) {
            value = tbConstant.VIVO_SAMPLE_INDIVIDUAL_BASE_URI_I18N + "/" + name;
        } else {
            value = tbConstant.VIVO_SAMPLE_INDIVIDUAL_BASE_URI_ORIG + "/" + name;
        }
        return value;
    }

    protected String getUsrDisplay(String name) throws UnsupportedEncodingException {
        String value = "";
        if (LoadI18N) {
            String q;
            value = tbConstant.VIVO_SAMPLE_INDIVIDUAL_BASE_URI_I18N + "?uri="
                    + URLEncoder.encode(getUsrURI(name), StandardCharsets.UTF_8.toString());
        } else {
            value = tbConstant.VIVO_SAMPLE_INDIVIDUAL_BASE_URI_ORIG + "?uri="
                    + URLEncoder.encode(getUsrURI(name), StandardCharsets.UTF_8.toString());
        }
        return value;
    }

    protected void initSelenium(boolean LoadI18N) throws IOException {
        this.LoadI18N = LoadI18N;
        SeleniumHelper.init(LoadI18N);
        sh = SeleniumHelper.getInstance();
        sh.seleniumSetupTestCase(LoadI18N);
        driver = sh.getDriver();
        js = (JavascriptExecutor) driver;
        vars = new HashMap<String, Object>();
        tbConstant = TestBenchConstant.getInstance();
<<<<<<< HEAD
    }

=======
	}
	
>>>>>>> 1eebe18e9a788b1f5349fd0c80151e57756eaeec
}
