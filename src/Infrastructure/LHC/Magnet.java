package Infrastructure.LHC;

public class Magnet {
    private boolean isActivated=false;
    private MagneticDirection direction=MagneticDirection.N;
    private int fieldStrength=1; //should be double

    public void activate(){
        isActivated = true;
    }

    public void deactivate(){
        isActivated = false;
    }

}
