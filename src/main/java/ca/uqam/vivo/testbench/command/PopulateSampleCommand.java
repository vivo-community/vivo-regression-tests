package ca.uqam.vivo.testbench.command;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ca.uqam.vivo.testbench.util.SampleGraphUtil;

public class PopulateSampleCommand {
    private static final Log log = LogFactory.getLog(PopulateSampleCommand.class);


    public static void main(String[] args) {
        SampleGraphUtil.getInstance().load();
        System.out.println("Populate done!");
    }

}
