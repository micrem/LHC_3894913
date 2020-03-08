package LHC_Entwurfsmuster04;

import Infrastructure.LHC.Block;
import Infrastructure.LHC.IExperiment;
import Infrastructure.LHC.Experiment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public abstract class ExperimentFileDecorator extends Experiment {
    private IExperiment experiment;

    public ExperimentFileDecorator(IExperiment experiment) {
        this.experiment = experiment;
    }

    public void save(){
        try (PrintWriter writer = new PrintWriter(new File(String.format("proton%02d_proton%02d.txt", experiment.getProton01ID(), experiment.getProton01ID())))) {
            for (Block block:getBlocks()) {
                writer.println(block.getStructure());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
