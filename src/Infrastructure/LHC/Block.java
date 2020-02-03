package Infrastructure.LHC;

import java.util.UUID;

public class Block {
    private final UUID uuid = UUID.randomUUID();
    private String structure;

    public UUID getUuid() {
        return uuid;
    }



    public String getStructure() {
        return structure;
    }

    public void setStructure(String structure) {
        this.structure = structure;
    }
}
