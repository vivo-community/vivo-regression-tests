/**
 * 
 */
package ca.uqam.vivo.testbench.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Michel Heon
 *
 */
public class TestBenchConstant {
	private static final Log log = LogFactory.getLog(TestBenchConstant.class);

	/**
	 * System properties keys for Selenium
	 */
	private static final String SELENIUM_DRIVER_KEY = "selenium.driver";
	private static final String SELENIUM_DRIVER_LOCATION_KEY = "selenium.driver.location";

	/**
	 * System properties values for Selenium
	 */
	public static String SELENIUM_DRIVER ;
	public static String SELENIUM_DRIVER_LOCATION;

	/*
	 * System property keys for vivo-i18n used in runtime.properties
	 */
	private static final String VIVO_ROOT_LOGIN_KEY_I18N 					= "vivo.i18n.rootlogin";
	private static final String VIVO_ROOT_PASSWD_KEY_I18N 					= "vivo.i18n.password";
	private static final String VIVO_SPARQL_UPDATE_ENDPOINT_URL_KEY_I18N	= "vivo.i18n.sparqlUpdateEndpointUrl";
	private static final String VIVO_SPARQL_QUERY_ENDPOINT_URL_KEY_I18N 	= "vivo.i18n.sparqlQueryEndpointUrl";
	private static final String VIVO_SAMPLE_FILENAME_KEY_I18N 				= "vivo.i18n.sampleFileName";
	private static final String VIVO_SAMPLE_FILENAME_KEY_I18N_fr_CA 		= "vivo.i18n.fr_CA.sampleFileName";
	private static final String VIVO_SAMPLE_FILENAME_KEY_I18N_fr_FR 		= "vivo.i18n.fr_FR.sampleFileName";
	private static final String VIVO_SAMPLE_FILENAME_KEY_I18N_en_US 		= "vivo.i18n.en_US.sampleFileName";
	private static final String VIVO_SAMPLE_FILENAME_KEY_I18N_en_CA 		= "vivo.i18n.en_CA.sampleFileName";
	private static final String VIVO_SAMPLE_FILENAME_KEY_I18N_de_DE 		= "vivo.i18n.de_DE.sampleFileName";
	private static final String VIVO_SAMPLE_GRAPH_URI_KEY_I18N 				= "vivo.i18n.sample.graphURI";
	private static final String VIVO_SAMPLE_INDIVIDUAL_BASE_URI_KEY_I18N 	= "vivo.i18n.sample.baseIndividualURI";
	private static final String VIVO_URL_BASE_KEY_I18N 						= "vivo.i18n.url.base";
	private static final String VIVO_SOLR_URL_BASE_KEY_I18N 				= "solr.i18n.url.base";

	/*
	 * System property values for vivo-i18n used in runtime.properties
	 */
	public static String VIVO_ROOT_LOGIN_I18N;
	public static String VIVO_ROOT_PASSWD_I18N;
	public static String VIVO_SPARQL_UPDATE_ENDPOINT_URL_I18N;
	public static String VIVO_SPARQL_QUERY_ENDPOINT_URL_I18N;
	public static String VIVO_SAMPLE_FILENAME_I18N;
	public static String VIVO_SAMPLE_FILENAME_I18N_FR_CA;
	public static String VIVO_SAMPLE_FILENAME_I18N_FR_FR;
	public static String VIVO_SAMPLE_FILENAME_I18N_EN_US;
	public static String VIVO_SAMPLE_FILENAME_I18N_EN_CA;
	public static String VIVO_SAMPLE_FILENAME_I18N_DE_DE;
	public static String VIVO_SAMPLE_GRAPH_URI_I18N;
	public static String VIVO_URL_BASE_I18N;
	public static String VIVO_SOLR_URL_BASE;
	public static String VIVO_SAMPLE_INDIVIDUAL_BASE_URI_I18N;

	/*
	 * System property keys for the original-vivo (vivo non i18n)
	 */
	private static final String VIVO_ROOT_LOGIN_ORIG_KEY 					= "vivo.orig.rootlogin";
	private static final String VIVO_ROOT_PASSWD_ORIG_KEY 					= "vivo.orig.password";
	private static final String VIVO_SPARQL_UPDATE_ENDPOINT_URL_ORIG_KEY 	= "vivo.orig.sparqlUpdateEndpointUrl";
	private static final String VIVO_SPARQL_QUERY_ENDPOINT_URL_ORIG_KEY 	= "vivo.orig.sparqlQueryEndpointUrl";
	private static final String VIVO_SAMPLE_FILENAME_ORIG_KEY 				= "vivo.orig.sampleFileName";
	private static final String VIVO_SAMPLE_GRAPH_URI_ORIG_KEY 				= "vivo.orig.sample.graphURI";
	private static final String VIVO_SAMPLE_INDIVIDUAL_BASE_URI_KEY_ORIG 	= "vivo.orig.sample.baseIndividualURI";
	private static final String VIVO_URL_BASE_ORIG_KEY 						= "vivo.orig.url.base";
	private static final String VIVO_SOLR_URL_BASE_ORIG_KEY 				= "sorl.orig.url.base";

	/*
	 * System property values for the original-vivo (vivo non i18n)
	 */
	public static String VIVO_ROOT_LOGIN_ORIG;
	public static String VIVO_ROOT_PASSWD_ORIG;
	public static String VIVO_SPARQL_UPDATE_ENDPOINT_URL_ORIG;
	public static String VIVO_SPARQL_QUERY_ENDPOINT_URL_ORIG;
	public static String VIVO_SAMPLE_FILENAME_ORIG;
	public static String VIVO_SAMPLE_GRAPH_URI_ORIG;
	public static String VIVO_URL_BASE_ORIG;
	public static String VIVO_SOLR_URL_BASE_ORIG;
	public static String VIVO_SAMPLE_INDIVIDUAL_BASE_URI_ORIG;

	private Properties systemProp;

	private TestBenchConstant() throws IOException {
        FileInputStream propFile = new FileInputStream(getClass().getClassLoader().getResource("runtime.properties").getPath());
        systemProp = new Properties(System.getProperties());
        systemProp.load(propFile);
        SELENIUM_DRIVER							= systemProp.getProperty(SELENIUM_DRIVER_KEY);
        SELENIUM_DRIVER_LOCATION				= systemProp.getProperty(SELENIUM_DRIVER_LOCATION_KEY);
        VIVO_ROOT_LOGIN_I18N					= systemProp.getProperty(VIVO_ROOT_LOGIN_KEY_I18N);
        VIVO_ROOT_PASSWD_I18N					= systemProp.getProperty(VIVO_ROOT_PASSWD_KEY_I18N);
        VIVO_SPARQL_UPDATE_ENDPOINT_URL_I18N	= systemProp.getProperty(VIVO_SPARQL_UPDATE_ENDPOINT_URL_KEY_I18N);
        VIVO_SPARQL_QUERY_ENDPOINT_URL_I18N		= systemProp.getProperty(VIVO_SPARQL_QUERY_ENDPOINT_URL_KEY_I18N);
        VIVO_SAMPLE_FILENAME_I18N				= systemProp.getProperty(VIVO_SAMPLE_FILENAME_KEY_I18N);
        VIVO_SAMPLE_FILENAME_I18N_FR_CA			= systemProp.getProperty(VIVO_SAMPLE_FILENAME_KEY_I18N_fr_CA);
        VIVO_SAMPLE_FILENAME_I18N_FR_FR			= systemProp.getProperty(VIVO_SAMPLE_FILENAME_KEY_I18N_fr_FR);
        VIVO_SAMPLE_FILENAME_I18N_EN_US			= systemProp.getProperty(VIVO_SAMPLE_FILENAME_KEY_I18N_en_US);
        VIVO_SAMPLE_FILENAME_I18N_EN_CA			= systemProp.getProperty(VIVO_SAMPLE_FILENAME_KEY_I18N_en_CA);
        VIVO_SAMPLE_FILENAME_I18N_DE_DE			= systemProp.getProperty(VIVO_SAMPLE_FILENAME_KEY_I18N_de_DE);
        VIVO_SAMPLE_GRAPH_URI_I18N				= systemProp.getProperty(VIVO_SAMPLE_GRAPH_URI_KEY_I18N);
        VIVO_URL_BASE_I18N						= systemProp.getProperty(VIVO_URL_BASE_KEY_I18N);
        VIVO_SOLR_URL_BASE						= systemProp.getProperty(VIVO_SOLR_URL_BASE_KEY_I18N);
        VIVO_ROOT_LOGIN_ORIG					= systemProp.getProperty(VIVO_ROOT_LOGIN_ORIG_KEY);
        VIVO_ROOT_PASSWD_ORIG					= systemProp.getProperty(VIVO_ROOT_PASSWD_ORIG_KEY);
        VIVO_SPARQL_UPDATE_ENDPOINT_URL_ORIG	= systemProp.getProperty(VIVO_SPARQL_UPDATE_ENDPOINT_URL_ORIG_KEY);
        VIVO_SPARQL_QUERY_ENDPOINT_URL_ORIG		= systemProp.getProperty(VIVO_SPARQL_QUERY_ENDPOINT_URL_ORIG_KEY);
        VIVO_SAMPLE_FILENAME_ORIG				= systemProp.getProperty(VIVO_SAMPLE_FILENAME_ORIG_KEY);
        VIVO_SAMPLE_GRAPH_URI_ORIG				= systemProp.getProperty(VIVO_SAMPLE_GRAPH_URI_ORIG_KEY);
        VIVO_URL_BASE_ORIG						= systemProp.getProperty(VIVO_URL_BASE_ORIG_KEY);
       	VIVO_SAMPLE_INDIVIDUAL_BASE_URI_I18N	= systemProp.getProperty(VIVO_SAMPLE_INDIVIDUAL_BASE_URI_KEY_I18N);
       	VIVO_SAMPLE_INDIVIDUAL_BASE_URI_ORIG	= systemProp.getProperty(VIVO_SAMPLE_INDIVIDUAL_BASE_URI_KEY_ORIG);

	}
	
	@Override
	public String toString() {
		return "TestBenchConstant [" + (systemProp != null ? "systemProp=" + systemProp : "") + "]";
	}

	/*
	 * Singleton configuration
	 */
    public static TestBenchConstant instance;
    public static synchronized TestBenchConstant getInstance() throws IOException 
    { 
		Class<?> clazz = null;
		if (instance == null)
//        	clazz = sun.reflect.Reflection.getCallerClass(1);
//				log.debug(clazz.getName());
					instance = new TestBenchConstant();
        return instance; 
    }

/*
 * This main is for testing the class
 */
    public static void main(String[] args) throws IOException {
    	TestBenchConstant constants = TestBenchConstant.getInstance();
    	log.info(constants.toString());
    	log.debug("Done!");

    }
}
