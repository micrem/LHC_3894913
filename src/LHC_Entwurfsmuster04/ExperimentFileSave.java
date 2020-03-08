package LHC_Entwurfsmuster04;

import Infrastructure.LHC.Block;
import Infrastructure.LHC.IExperiment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class ExperimentFileSave {
    static public void save(IExperiment experiment) {
        try (PrintWriter writer = new PrintWriter(new File(String.format("proton%02d_proton%02d.txt", experiment.getProton01ID(), experiment.getProton02ID())))) {
            for (Block block : experiment.getBlocks()) {
                writer.println(block.getStructure());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
