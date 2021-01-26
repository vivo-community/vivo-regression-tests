package ca.uqam.vivo.testbench.util.test;

import static org.junit.Assert.fail;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.SolrPingResponse;
import org.junit.Test;

import ca.uqam.vivo.testbench.util.SampleGraphUtil;

/**
 * @author Michel Heon
 * SolrPingTest.java
 * 
 * 2020-04-23
 *
 */
public class SolrPingUnitTest {
    private static final Log log = LogFactory.getLog(SolrPingUnitTest.class);

    @Test
    public void test() throws SolrServerException, IOException {
        String solrUrl = (String) SampleGraphUtil.getInstance().getSystemProp().get("url.solr");
        SolrClient client = new HttpSolrClient.Builder(solrUrl).build();
        SolrPingResponse pingResponse = client.ping();
        int status = pingResponse.getStatus();
        if (pingResponse.getStatus() != 0) {
            fail("There was an unexpected error when pinging the Solr server (status code is " + pingResponse.getStatus() + ")");
        }
        else {
            log.info("Ping to the Solr server was successful and lasted " + pingResponse.getQTime() + " ms");
        }
    }

}
