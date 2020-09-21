package ca.uqam.vivo.testbench.command;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ca.uqam.vivo.testbench.util.SampleGraphUtil;

public class DropGraphCommand {
    private static final Log log = LogFactory.getLog(DropGraphCommand.class);


    public static void main(String[] args) throws IOException {
    	Boolean i18m = false;
        SampleGraphUtil.getInstance().delete(i18m);
        System.out.println("Populate done!");
    }

}
