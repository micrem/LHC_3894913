package Infrastructure.LHC;

import Infrastructure.Energy.USP;

public class LargeHadronCollider {
    private USP[] usp = new USP[2];
    private Building building;
    private Ring ring;

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

    public void setControlCenter(ControlCenter controlCenter) {
        this.controlCenter = controlCenter;
    }

    private ControlCenter controlCenter;

    public LargeHadronCollider(Building building, Ring ring, ControlCenter controlCenter) {
        setBuilding(building);
        setControlCenter(controlCenter);
        setRing(ring);
    }

    public ControlCenter getControlCenter() {
        return controlCenter;
    }
    //do work
}
