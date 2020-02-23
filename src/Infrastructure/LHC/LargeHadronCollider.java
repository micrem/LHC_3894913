package Infrastructure.LHC;

import Infrastructure.Energy.USP;

public class LargeHadronCollider {
    private USP[] usps = new USP[2];
    private Building building;
    private Ring ring;
    private ControlCenter controlCenter;

    public LargeHadronCollider() {
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public Ring getRing() {
        return ring;
    }

    public void setRing(Ring ring) {
        this.ring = ring;
    }

    public ControlCenter getControlCenter() {
        return controlCenter;
    }

    public void setControlCenter(ControlCenter controlCenter) {
        this.controlCenter = controlCenter;
    }

    public USP[] getUSPs() {
        return usps;
    }
}
