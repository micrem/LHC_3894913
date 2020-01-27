public class USP {
    private Battery batteries[] = new Battery[25];
    private LargeHadronCollider lhc;

    private boolean isActivated;
    private boolean isStandBy;

    public void determineChargeState(){};
    public void charge(ThreePinPlug plug){};
    public void takeOut(){};
}
