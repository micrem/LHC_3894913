public class Ring {
    private LargeHadronCollider lhc;
    private Building building;
    private ProtonTrap[] protonTraps = new ProtonTrap[2];
    private Detector detector;
    private Magnet[] magnets = new Magnet[72];


    private boolean isActivated;
    private int energy;
    private Experiment currentExperiment;

    public void activate() {

    }

    public void activate(int initialEnergy){

    }

    public void activateMagneticField(){

    }

    public void releaseProton(){

    }

    public void increaseEnergy(int delta){

    }

    public void collide(Proton proton01, Proton proton02){

    }

    public int decreaseEnergy(){
        return 0;
    }

    public void shutdown(){}

}
