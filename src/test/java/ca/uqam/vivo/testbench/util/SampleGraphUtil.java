package ca.uqam.vivo.testbench.util;

import java.net.URL;

import org.apache.jena.sparql.modify.UpdateProcessRemoteBase;
import org.apache.jena.update.Update;
import org.apache.jena.update.UpdateExecutionFactory;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateProcessor;
import org.apache.jena.update.UpdateRequest;

public class SampleGraphUtil {
    private String userName;
    private String password;
    private Update udateStr;
    private String sparqlEndpointUrl;
    private URL resUrl;
    private String graphURI;
    public SampleGraphUtil(){
        resUrl = getClass().getClassLoader().getResource("kg/sample-data_orig_localhost.ttl");
        userName="vivo@uqam.ca";
        password="Vivo2435....";
        sparqlEndpointUrl = "http://localhost:8080/vivo/api/sparqlUpdate";
        graphURI = "<http://localhost:8080/data-graph>";
    }

    public void load() {
        UpdateRequest request = UpdateFactory.create("LOAD <"+resUrl.toString()+"> into graph "+graphURI) ;
        UpdateProcessor processor = UpdateExecutionFactory.createRemoteForm(request, sparqlEndpointUrl);
        ((UpdateProcessRemoteBase)processor).addParam("email", userName);
        ((UpdateProcessRemoteBase)processor).addParam("password", password) ;
        processor.execute();

    }
    public void delete() {
        UpdateRequest request = UpdateFactory.create("CLEAR GRAPH "+graphURI ) ;
        UpdateProcessor processor = UpdateExecutionFactory.createRemoteForm(request, sparqlEndpointUrl);
        ((UpdateProcessRemoteBase)processor).addParam("email", userName);
        ((UpdateProcessRemoteBase)processor).addParam("password", password) ;
        processor.execute();

    }
    public static void main(String[] args) {
        SampleGraphUtil lg = new SampleGraphUtil();
        lg.delete();
        System.out.println("Done!");
    }

}
    