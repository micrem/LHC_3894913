package Infrastructure.LHC;

import java.util.UUID;

public class Block {
    private final UUID uuid = UUID.randomUUID();
    private String structure="";

    private String experimentUUID="";

    public UUID getUuid() {
        return uuid;
    }

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
}
