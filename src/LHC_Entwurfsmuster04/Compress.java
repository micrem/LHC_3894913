package LHC_Entwurfsmuster04;

import Infrastructure.LHC.Block;
import Infrastructure.LHC.IExperiment;

public class Compress extends ExperimentFileDecorator {
    private IExperiment experiment;
    private Block[] compressedBlocks;

    public Compress(IExperiment experiment) {
        super(experiment);
        this.experiment = experiment;
        //this.setBlocks( compress(this.experiment.getBlocks()) );
    }


    /**
     * shorten Structure of Blocks where repeating letters with count>3
     * are replaced with count+letter: "aaaa" to "4a"
     *
     * @param blocks blocks to be compressed
     * @return compressed blocks
     */
    private Block[] compress(Block[] blocks) {
        Block[] retBlocks = new Block[blocks.length];
        int returnedBlocksCounter = 0;
        String blockStr;
        for (Block b : blocks) {
            if (b == null) {
                continue;
            }
            Block retBlock = new Block(b);
            blockStr = retBlock.getStructure();
            for (int i = 0; i < blockStr.length() - 2; i++) { //check all chars in string
                int j = i;
                char charVal = blockStr.charAt(i);
                if (!Character.isLetter(charVal)) continue;
                while (j < blockStr.length() && blockStr.charAt(j) == charVal) { //count repeating
                    j++;
                }
                int letterCount = j - i;
                if (letterCount >= 3) { //for count>3
                    String partStr = Character.toString(charVal).repeat(letterCount);
                    blockStr = blockStr.replaceFirst(partStr, letterCount + Character.toString(charVal));
                    i -= (letterCount - 2);
                    continue;
                }
                i = j - 1;
            }
            retBlock.setStructure(blockStr);
            retBlocks[returnedBlocksCounter++] = retBlock;
        }
        return retBlocks;
    }

    @Override
    public Block[] getBlocks() {
        //ensure changes to actual experiment are propagated
        this.setBlocks(compress(this.experiment.getBlocks()));
        return super.getBlocks();
    }
}
