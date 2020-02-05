package Infrastructure.LHC;

import java.util.UUID;

public class Experiment {

    private UUID uuid = UUID.randomUUID();
    private String dateTimeStamp;
    private boolean isHiggsBosonFound;
    private int higgsBlockID=0;
    private ExperimentScope scope=ExperimentScope.ESFull;

    public ExperimentScope getScope() {
        return scope;
    }

    public void setScope(ExperimentScope scope) {
        this.scope = scope;
    }

    public void setBlocks(Block[] blocks) {
        this.blocks = blocks;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public void setDateTimeStamp(String dateTimeStamp) {
        this.dateTimeStamp = dateTimeStamp;
    }

    public void setHiggsBosonFound(boolean higgsBosonFound) {
        isHiggsBosonFound = higgsBosonFound;
    }

    private Block[] blocks = new Block[200000];

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

    public int getHiggsBlockID() {
        return higgsBlockID;
    }

    public void setHiggsBlockID(int higgsBlockID) {
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

    private int proton01ID=0;
    private int proton02ID=0;

    public Experiment() {
    }


    @Override
    public String toString() {
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append("Experiment: "+uuid.toString().substring(0,5)+".. ");
        if(isHiggsBosonFound) {
            strBuilder.append(" higgsBlock ID:"+higgsBlockID);
            strBuilder.append(" '"+blocks[higgsBlockID].getStructure()+"' ");
        }
        strBuilder.append(" ProtID: "+proton01ID+":"+proton02ID);
        strBuilder.append(" timestamp:   "+getDateTimeStamp());

        return strBuilder.toString();
    }
}
