package ca.uqam.vivo.testbench.i18n.test;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.testng.Assert.fail;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.text.StringEscapeUtils;
import org.apache.jena.rdf.model.Literal;
import org.openqa.selenium.By;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import ca.uqam.vivo.testbench.model.ResearchOverviewToPersonUnitTest;
import ca.uqam.vivo.testbench.util.SampleGraphUtil;
import ca.uqam.vivo.testbench.util.SeleniumHelper;

/**
 * @author Michel Heon
 * en_US_ResearchOverviewToPersonUnitTest.java
 * 
 * 2020-04-24
 *
 */
public class ResearchOverviewToPersonUnitTest_i18n extends ResearchOverviewToPersonUnitTest {
    private static final Log log = LogFactory.getLog(ResearchOverviewToPersonUnitTest_i18n.class);

    @BeforeClass
    public void setUpBeforeClass() throws Exception {
        log.info("Setup before Class");
        isI18nInstance=true;
        super.setUpBeforeClass();
        SeleniumHelper.getInstance().setSelectedLangage("fr_CA");
    }

    @DataProvider
    public Object[][] dp() {
        return new Object[][] {
            new Object[] { "fr_CA", "Voici un nouvel aperçu de la recherche" },
            new Object[] { "en_CA", "This is a new research overview" },
            new Object[] { "en_US", "This is a new research overview" },
        };
    }

    @Test(dependsOnMethods={"phase2"})
    protected void phase3() throws IOException {
        log.info("Phase 3 Content validation");
        List<Literal> literals = SampleGraphUtil.getValuesFromTripleStore(query(), usrURI, roURI, isI18nInstance);
        String lang = null;
        String sentence = "";
        String actualSentences = "";
        Object[][] data = dp();
        int sentenceFound = 0;
        for (Iterator iterator = literals.iterator(); iterator.hasNext();) {
            Literal literal = (Literal) iterator.next();
            lang = literal.getLanguage();
            if (!sentence.isEmpty()) actualSentences += sentence;
            sentence = new String(StringEscapeUtils.unescapeHtml4(literal.getLexicalForm()).getBytes(), StandardCharsets.UTF_8);
            if (!actualSentences.isEmpty()) actualSentences += "-";
            for (int i = 0; i < data.length; i++) {
                String objLang = data[i][0].toString().replace('_', '-');
                String objSentence = new String(data[i][1].toString().getBytes(), StandardCharsets.UTF_8);
                if(objLang.equals(lang) && objSentence.equals(sentence)){
                    sentenceFound ++;
                }
            }            
        }
        actualSentences += sentence;
        if(sentenceFound != data.length) {
            log.info("sentenceFound "+sentenceFound + " data.length " +data.length);
            fail("expected (" + data[0][1] + "-" + data[1][1] + ") actual ("+actualSentences +")" );
        }
        log.info("Phase 3 Content validation done");
    }

    @Test(dependsOnMethods={"phase4"})
    protected void phase5() throws InterruptedException {
        super.phase5();
    }

    @Test(dataProvider = "dp",dependsOnMethods={"phase3"})
    protected void phase4(String lang, String textToVerify) throws InterruptedException, IOException {
        String roValue=null;
        log.info("Phase 4 check delete for "+lang);
        sh.selectLanguage(lang);
        driver.get(usrURI);
        driver.findElement(By.cssSelector(".nonSelectedGroupTab:nth-child(20)")).click();
        driver.findElement(By.cssSelector(".nonSelectedGroupTab:nth-child(4)")).click();
        driver.findElement(By.cssSelector(".nonSelectedGroupTab:nth-child(6)")).click();
        driver.findElement(By.cssSelector(".delete-researchOverview > .delete-individual")).click();
        driver.findElement(By.id("submit")).click();
        List<Literal> literals = SampleGraphUtil.getValuesFromTripleStore(query(), usrURI, roURI, isI18nInstance);
        for (Iterator iterator = literals.iterator(); iterator.hasNext();) {
            roValue = null;
            Literal literal = (Literal) iterator.next();
            String roLang = literal.getLanguage();
            if (roLang.equals(lang)) {
                roValue= new String(StringEscapeUtils.unescapeHtml4(literal.getLexicalForm()).getBytes(), StandardCharsets.UTF_8);
                break;
            }
        }
        assertNull(roValue);
        log.info("Phase 4 Check delete for "+lang+ " done");
    }

    @Test(dataProvider = "dp", dependsOnMethods={"phase1"})
    protected void phase2(String lang, String textToVerify) throws InterruptedException {
        super.phase2(lang, textToVerify);
    }

    @Test()
    protected void phase1() throws InterruptedException {
        super.phase1();
    }
}
