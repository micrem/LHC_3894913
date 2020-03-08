package LHC_Entwurfsmuster04;

import Infrastructure.LHC.Block;
import Infrastructure.LHC.IExperiment;

public class Denoise extends ExperimentFileDecorator {
    private IExperiment experiment;

    public Denoise(IExperiment experiment) {
        super(experiment);
        this.experiment = experiment;
        //this.setBlocks( denoise(this.experiment.getBlocks()) );
    }

    private Block[] denoise(Block[] blocks) {
        Block[] retBlocks = new Block[blocks.length];
        int i = 0;
        for (Block b : blocks) {
            if (b == null) {
                continue;
            }
            Block retBlock = new Block(b);
            retBlock.setStructure(retBlock.getStructure().replaceAll("([\\^#<>])", ""));
            retBlocks[i++] = retBlock;
        }
        return retBlocks;
    }

    @Override
    public Block[] getBlocks() {
        //ensure changes to actual experiment are propagated
        this.setBlocks(denoise(this.experiment.getBlocks()));
        return super.getBlocks();
    }
}
