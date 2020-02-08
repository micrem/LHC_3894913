package Infrastructure.LHC;

import Infrastructure.Security.Reception;

public class Building {
    private LargeHadronCollider lhc;
    private String owner="CERN";
    private String location ="Geneva";
    private Reception reception;

    public LargeHadronCollider getLhc() {
        return lhc;
    }

    public void setLhc(LargeHadronCollider lhc) {
        this.lhc = lhc;
    }
}
