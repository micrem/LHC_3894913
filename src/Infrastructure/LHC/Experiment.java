package Infrastructure.LHC;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class Experiment {

    private UUID uuid = UUID.randomUUID();
    private String dateTimeStamp;
    private boolean isHiggsBosonFound;
    private String higgsBlockID = "";
    private ExperimentScope scope = ExperimentScope.ESFull;

    private int proton01ID = 0;
    private int proton02ID = 0;
    private Block[] blocks = new Block[200000];

    public ExperimentScope getScope() {
        return scope;
    }

    public void setScope(String scopeName) {
        this.scope = ExperimentScope.valueOf(scopeName);
        //safety not needed, should be reasonable to crash if this fails
//        boolean found = false;
//        for (ExperimentScope scope: ExperimentScope.values()) {
//            if (scopeName.equals(scope)) found = true;
//        }
//         if(found) {this.scope = ExperimentScope.valueOf(scopeName);}
//         else { this.scope = ExperimentScope.ESFull ;}
    }

    public Block[] getBlocks() {
        return blocks;
    }

    public void setBlocks(Block[] blocks) {
        this.blocks = blocks;
    }

    public String getUuid() {
        return uuid.toString();
    }

    public void setUuid(String uuid) {
        this.uuid = UUID.fromString(uuid);
    }

    public String getDateTimeStamp() {
        return dateTimeStamp;
    }

    public void setDateTimeStamp(String dateTimeStamp) {
        this.dateTimeStamp = dateTimeStamp;
    }

    public boolean isHiggsBosonFound() {
        return isHiggsBosonFound;
    }

    public void setHiggsBosonFound(boolean higgsBosonFound) {
        isHiggsBosonFound = higgsBosonFound;
    }

    public String getHiggsBlockID() {
        return higgsBlockID;
    }

    public void setHiggsBlockID(String higgsBlockID) {
        this.higgsBlockID = higgsBlockID;
    }

    public int getProton01ID() {
        return proton01ID;
    }

    public void setProton01ID(int proton01ID) {
        this.proton01ID = proton01ID;
    }

    public int getProton02ID() {
        return proton02ID;
    }

    public void setProton02ID(int proton02ID) {
        this.proton02ID = proton02ID;
    }

    @Override
    public String toString() {
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append("Experiment: ").append(uuid.toString().substring(0, 5)).append(".. ");
        strBuilder.append(" ProtID: ").append(proton01ID).append(":").append(proton02ID);
        if (isHiggsBosonFound) {
            strBuilder.append(" higgsBlock ID:").append(higgsBlockID.substring(0, 5)).append(".. ");
            //comment left in code for dedactic purposes
//            for (int i=0;i<blocks.length;i++
//            ) {
//                Block block = blocks[i];
//                if (block==null)continue;
//                if (block.getUuid().equals(higgsBlockID)){
//
//                    break;
//                }
//            }
            final List<Block> blocksFiltered = Arrays.stream(this.blocks).filter(block -> block.getUuid().equals(higgsBlockID)).collect(Collectors.toList());
            strBuilder.append(" '").append(blocksFiltered.get(0).getStructure()).append("' ");
        }
        strBuilder.append(" timestamp:   ").append(getDateTimeStamp());

        return strBuilder.toString();
    }
}
