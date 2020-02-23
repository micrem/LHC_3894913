package Infrastructure.LHC;

import java.util.UUID;

public class Block {
    private UUID uuid;

    private String structure;
    private String experimentUUID;

    public Block() {
        initialise();
    }

    public Block(boolean initialise) {
        if (initialise) {
            initialise();
        }
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

    public String getUuid() {
        return uuid.toString();
    }

    public void setUuid(String uuid) {
        this.uuid = UUID.fromString(uuid);
    }

    @Override
    public String toString() {
        return "Block{" +
                "structure='" + structure + '\'' +
                '}';
    }

    private void initialise() {
        uuid = UUID.randomUUID();
        String structure = "";
        String experimentUUID = "";
    }


}
