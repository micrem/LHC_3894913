package Infrastructure.LHC;

import java.util.UUID;

public class Block {
    private UUID uuid = UUID.randomUUID();

    private String structure="";
    private String experimentUUID="";

    public String getStructure() {
        return structure;
    }

    public void setStructure(String structure) {
        this.structure = structure;
    }

    public String getExperimentUUID() {
        return experimentUUID;
    }

    public void setExperimentUUID(String experimentUUID) {
        this.experimentUUID = experimentUUID;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = UUID.fromString(uuid);
    }
}
