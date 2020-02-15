package Infrastructure.LHC;

import java.util.UUID;

public class Experiment {

    private UUID uuid = UUID.randomUUID();
    private String dateTimeStamp;
    private boolean isHiggsBosonFound;
    private String higgsBlockID="";
    private ExperimentScope scope=ExperimentScope.ESFull;

    private int proton01ID=0;
    private int proton02ID=0;

    public ExperimentScope getScope() {
        return scope;
    }

    private Block[] blocks = new Block[200000];

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

    public void setBlocks(Block[] blocks) {
        this.blocks = blocks;
    }

    public void setUuid(String uuid) {
        this.uuid = UUID.fromString(uuid);
    }

    public void setDateTimeStamp(String dateTimeStamp) {
        this.dateTimeStamp = dateTimeStamp;
    }
    public void setHiggsBosonFound(boolean higgsBosonFound) {
        isHiggsBosonFound = higgsBosonFound;
    }

    public Block[] getBlocks() {
        return blocks;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getDateTimeStamp() {
        return dateTimeStamp;
    }

    public boolean isHiggsBosonFound() {
        return isHiggsBosonFound;
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
        strBuilder.append("Experiment: "+uuid.toString().substring(0,5)+".. ");
        if(isHiggsBosonFound) {
            strBuilder.append(" higgsBlock ID:"+higgsBlockID.substring(0,5)+".. ");
            for (Block block:blocks
            ) {
                if (block.getUuid().toString().equals(higgsBlockID)){
                    strBuilder.append(" '"+block.getStructure()+"' ");
                    break;
                }
            }
        }
        strBuilder.append(" ProtID: "+proton01ID+":"+proton02ID);
        strBuilder.append(" timestamp:   "+getDateTimeStamp());

        return strBuilder.toString();
    }
}
