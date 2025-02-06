package org.example;

import org.apache.sysds.api.jmlc.Connection;
import org.apache.sysds.api.jmlc.PreparedScript;
import org.apache.sysds.conf.DMLConfig;

import java.io.IOException;

public class VectorSearch
{

public static void main(String[] args) throws IOException
{
    DMLConfig config = new DMLConfig();

    config.setTextValue(DMLConfig.BUFFERPOOL_LIMIT, "90"); // in GB
    //config.setTextValue(DMLConfig.SCRATCH_SPACE_LIMIT, "2G");
    // obtain connection to SystemML
    Connection conn = new Connection();
    double top10[][] = null;
    conn.setStatistics(true);
    conn.gatherMemStats(true);

    // read in and precompile DML script, registering inputs and outputs
    String dml = conn.readScript(
        "/Users/yashrb/e6-planner/src/main/java/io/e6x/ml/scripts/VectorSimilarityUsingCosine.dml");
    PreparedScript script = conn.prepareScript(dml, new String[]{}, new String[]{"top10"});

    script.executeScript();

    System.out.println("Script Explain: " + script.explain());
    System.out.println("Script Statistics: " + script.statistics());

    top10 = script.executeScript().getMatrix("top10");

    // close connection
    conn.close();
}

}