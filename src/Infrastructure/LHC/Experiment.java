package Infrastructure.LHC;

import java.util.UUID;

public class Experiment {
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

    private UUID uuid;
    private String dateTimeStamp;
    private boolean isHiggsBosonFound;

    public Experiment() {
    }
}
