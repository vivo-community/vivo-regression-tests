package ca.uqam.vivo.testbench.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.text.StringEscapeUtils;
import org.apache.jena.atlas.web.HttpException;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.ResourceFactory;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.sparql.engine.http.QueryEngineHTTP;
import org.apache.jena.sparql.modify.UpdateProcessRemoteBase;
import org.apache.jena.update.Update;
import org.apache.jena.update.UpdateExecutionFactory;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateProcessor;
import org.apache.jena.update.UpdateRequest;

public class SampleGraphUtil {
    public static String SAMPLE_DATA="sample.sampleFileName";
    public static String SAMPLE_DATA_I18N="sample.sampleFileName.i18n";
    public static boolean SAMPLE_LOAD_I18N=false;
    public static String SAMPLE_GRAPH_IRI="sample.graphURI";
    private static final Log log = LogFactory.getLog(SampleGraphUtil.class);
    private static String userName;
    private static String password;
    private Update udateStr;
    private static String sparqlUpdateEndpointUrl;
    private static String sparqlQueryEndpointUrl ;
    private static String sampleFileName ;
    private static SampleGraphUtil single_instance;
    private URL resUrl;
    private String graphURI;
    private Properties systemProp;
    public Properties getSystemProp() {
        return systemProp;
    }
    public static SampleGraphUtil getInstance() 
    { 
        if (single_instance == null)
            try {
                single_instance = new SampleGraphUtil();
            } catch (IOException e) {
                e.printStackTrace();
            } 
  
        return single_instance; 
    } 
    private SampleGraphUtil() throws IOException{
        FileInputStream propFile = new FileInputStream(getClass().getClassLoader().getResource("runtime.properties").getPath());
        systemProp = new Properties(System.getProperties());
        systemProp.load(propFile);
        userName=systemProp.getProperty("vivo.rootlogin");
        password=systemProp.getProperty("vivo.password");
        sparqlUpdateEndpointUrl=systemProp.getProperty("vivo.sparqlUpdateEndpointUrl");
        sparqlQueryEndpointUrl=systemProp.getProperty("vivo.sparqlQueryEndpointUrl");
        graphURI = "<"+systemProp.getProperty(SAMPLE_GRAPH_IRI)+">";
        if (SAMPLE_LOAD_I18N){
            sampleFileName=systemProp.getProperty(SAMPLE_DATA_I18N);
        } else {
            sampleFileName=systemProp.getProperty(SAMPLE_DATA);
        }
        resUrl = getClass().getClassLoader().getResource(sampleFileName);
        if (resUrl==null || resUrl.toString().isEmpty()) throw new FileNotFoundException(sampleFileName);
    }

    public void load() {
        String query = "LOAD <"+resUrl.toString()+"> into graph "+graphURI;
        UpdateRequest request = UpdateFactory.create(query) ;
        log.debug("running SPARQL : "+query);
        UpdateProcessor processor = UpdateExecutionFactory.createRemoteForm(request, sparqlUpdateEndpointUrl);
        ((UpdateProcessRemoteBase)processor).addParam("email", userName);
        ((UpdateProcessRemoteBase)processor).addParam("password", password) ;
        log.info("Loading : "+sampleFileName);
        processor.execute();
        log.debug("load done ");

    }
    public void clear() {
        UpdateRequest request = UpdateFactory.create("CLEAR GRAPH "+graphURI ) ;
        UpdateProcessor processor = UpdateExecutionFactory.createRemoteForm(request, sparqlUpdateEndpointUrl);
        ((UpdateProcessRemoteBase)processor).addParam("email", userName);
        ((UpdateProcessRemoteBase)processor).addParam("password", password) ;
        processor.execute();
    }
    public void delete() {
        String DELETE = "\n"
                + "DELETE {  GRAPH ?g { ?s ?p ?o } }\n "
                + "    where {  GRAPH  ?g {\n"
                + "        ?s ?p ?o . \n"
                + "        FILTER(regex(str(?s), \"http://localhost:8080/vivo/individual/\") \n"
                + "             || regex(str(?p), \"http://localhost:8080/vivo/individual/\") \n"
                + "             || regex(str(?o), \"http://localhost:8080/vivo/individual/\") ) \n"
                + "     }}";
        log.debug(DELETE);
        UpdateRequest request = UpdateFactory.create(DELETE);
        UpdateProcessor processor = UpdateExecutionFactory.createRemoteForm(request, sparqlUpdateEndpointUrl);
        ((UpdateProcessRemoteBase)processor).addParam("email", userName);
        ((UpdateProcessRemoteBase)processor).addParam("password", password) ;
        try {
            processor.execute();      
//            System.exit(0);
        } catch (Exception e) {
            HttpException err = (org.apache.jena.atlas.web.HttpException)e;
            log.error(err.getResponse());
            throw e;
        }
    }
    public static String getValueFromTripleStore( String queryStr, String usrURI, String predicatToTestURI ){
        String roValue = null;
        // la construction de la requête
        Query query = QueryFactory.create(queryStr);
        // Construction de l'exécuteur
        try ( QueryExecution qexec = QueryExecutionFactory.sparqlService(sparqlQueryEndpointUrl, query) ) {
            // L'authentification administrateur de vivo
            ((QueryEngineHTTP)qexec).addParam("email", userName) ;
            ((QueryEngineHTTP)qexec).addParam("password", password) ;
            // Lancer l'exécution
            Model model =null;
            try {
                model = qexec.execDescribe();
            } catch (Exception e) {
                log.error(queryStr);
                throw e;
            }
            //Imprimer le résultat de la requête
            if (log.isDebugEnabled()) model.write(System.out, "TTL") ;
            List<RDFNode> objs = model.listObjectsOfProperty(ResourceFactory.createResource(usrURI), ResourceFactory.createProperty(predicatToTestURI)).toList();
            try {
                /*
                 * Unescape HTML 4 from the value before converted to UTF-8 (Usefull for latin languages)
                 */
                roValue = new String(StringEscapeUtils.unescapeHtml4(                     
                        objs.get(0).asLiteral().getLexicalForm()).getBytes(), StandardCharsets.UTF_8);
                log.info("return value is: "+roValue);
            } catch (Exception e) {
                log.debug("NULL return value");
            }
            log.debug("Content validation done");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return roValue;
    }
    public static List<Literal> getValuesFromTripleStore( String queryStr, String usrURI, String predicatToTestURI ){
        List<Literal> stringList = new ArrayList<>();
        // la construction de la requête
        Query query = QueryFactory.create(queryStr);
        // Construction de l'exécuteur
        try ( QueryExecution qexec = QueryExecutionFactory.sparqlService(sparqlQueryEndpointUrl, query) ) {
            // L'authentification administrateur de vivo
            ((QueryEngineHTTP)qexec).addParam("email", userName) ;
            ((QueryEngineHTTP)qexec).addParam("password", password) ;
            // Lancer l'exécution
            Model model =null;
            try {
                model = qexec.execDescribe();
            } catch (Exception e) {
                log.error(queryStr);
                throw e;
            }
            //Imprimer le résultat de la requête
            if (log.isDebugEnabled()) model.write(System.out, "TTL") ;
            List<Statement> stmts = model.listStatements(ResourceFactory.createResource(usrURI), ResourceFactory.createProperty(predicatToTestURI), (RDFNode) null).toList();
            try {
                for (Iterator iterator = stmts.iterator(); iterator.hasNext();) {
                    Statement stmt = (Statement) iterator.next();
                    /*
                     * Unescape HTML 4 from the value before converted to UTF-8 (Usefull for latin languages)
                     */
//                    String value = new String(StringEscapeUtils.unescapeHtml4(                     
//                            stmt.getObject().asLiteral().getLexicalForm()).getBytes(), StandardCharsets.UTF_8);
                    stringList.add(stmt.getObject().asLiteral());                   
                }
                log.info("return value is: "+stringList);
            } catch (Exception e) {
                log.debug("NULL return value");
            }
            log.debug("Content validation done");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringList;
    }
    /**
     * 
     * @return List of useful prefixes to build a SPARQL query
     */
    public static String getPrefixList(){
        return ""
                + "PREFIX rdf:      <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n"
                + "PREFIX rdfs:     <http://www.w3.org/2000/01/rdf-schema#> \n"
                + "PREFIX xsd:      <http://www.w3.org/2001/XMLSchema#> \n"
                + "PREFIX owl:      <http://www.w3.org/2002/07/owl#> \n"
                + "PREFIX swrl:     <http://www.w3.org/2003/11/swrl#> \n"
                + "PREFIX swrlb:    <http://www.w3.org/2003/11/swrlb#> \n"
                + "PREFIX vitro:    <http://vitro.mannlib.cornell.edu/ns/vitro/0.7#> \n"
                + "PREFIX bibo:     <http://purl.org/ontology/bibo/> \n"
                + "PREFIX c4o:      <http://purl.org/spar/c4o/> \n"
                + "PREFIX cito:     <http://purl.org/spar/cito/> \n"
                + "PREFIX dcterms:  <http://purl.org/dc/terms/> \n"
                + "PREFIX event:    <http://purl.org/NET/c4dm/event.owl#> \n"
                + "PREFIX fabio:    <http://purl.org/spar/fabio/> \n"
                + "PREFIX foaf:     <http://xmlns.com/foaf/0.1/> \n"
                + "PREFIX geo:      <http://aims.fao.org/aos/geopolitical.owl#> \n"
                + "PREFIX obo:      <http://purl.obolibrary.org/obo/> \n"
                + "PREFIX ocrer:    <http://purl.org/net/OCRe/research.owl#> \n"
                + "PREFIX ocresst:  <http://purl.org/net/OCRe/statistics.owl#> \n"
                + "PREFIX ocresd:   <http://purl.org/net/OCRe/study_design.owl#> \n"
                + "PREFIX ocresp:   <http://purl.org/net/OCRe/study_protocol.owl#> \n"
                + "PREFIX ro:       <http://purl.obolibrary.org/obo/ro.owl#> \n"
                + "PREFIX skos:     <http://www.w3.org/2004/02/skos/core#> \n"
                + "PREFIX swo:      <http://www.ebi.ac.uk/efo/swo/> \n"
                + "PREFIX vcard:    <http://www.w3.org/2006/vcard/ns#> \n"
                + "PREFIX vitro-public: <http://vitro.mannlib.cornell.edu/ns/vitro/public#> \n"
                + "PREFIX vivo:     <http://vivoweb.org/ontology/core#> \n"
                + "PREFIX core:     <http://vivoweb.org/ontology/core#> \n"
                + "PREFIX scires:   <http://vivoweb.org/ontology/scientific-research#> \n"
                + "PREFIX vann:     <http://purl.org/vocab/vann/> \n";
               }
/*
 * Mostly used for testing the class
 */
    public static void main(String[] args) {
        SampleGraphUtil lg = SampleGraphUtil.getInstance();
        lg.load();
        System.out.println("Done!");
    }

}
    