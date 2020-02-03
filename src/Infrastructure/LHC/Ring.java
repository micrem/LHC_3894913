package Infrastructure.LHC;
import com.google.common.eventbus.Subscribe;

public class Ring {
    public String userDirectory = System.getProperty("user.dir");
    public String fileSeparator = System.getProperty("file.separator");
    public String fileEnding = ".txt";
    public String fileNamePart = "";

    private LargeHadronCollider lhc;
    private ProtonTrap[] protonTraps;
    private Detector detector;
    private Magnet[] magnets = new Magnet[72];


    private boolean isActivated;
    private int energy;

    public Experiment getCurrentExperiment() {
        return currentExperiment;
    }

    public void setCurrentExperiment(Experiment currentExperiment) {
        this.currentExperiment = currentExperiment;
    }

    private Experiment currentExperiment;

    public Ring(LargeHadronCollider lhc, Detector detector) {
        this.lhc = lhc;
        this.detector = detector;
        protonTraps = new ProtonTrap[2];
        protonTraps[0] = new ProtonTrap(ProtonTrapID.A);
        protonTraps[1] = new ProtonTrap(ProtonTrapID.B);
        for (int i = 0; i < 25; i++) {
            protonTraps[0].loadData(filename);
            protonTraps[1].loadData("filename");
        }
    }


    @Subscribe
    public void receive(EventRunExperimentFull event){

    }

    @Subscribe
    public void receive(EventRunExperimentPartial event){
        int initE=0;
        switch (event.getInitialEnergy()){
            case e25:
                initE=25000;
                break;
            case e50:
                initE=50000;
                break;
            case e100:
                initE=100000;
                break;
        }
        activate(initE);
        activateMagneticField();
        releaseProton();
        releaseProton();
        while (energy<300000){
            increaseEnergy(25000);
        }
        collide(protonTraps[0].getProton(),protonTraps[1].getProton());
        lhc.getControlCenter().setExperiment(currentExperiment);
    }

    public void activate() {
        energy=0;
    }

    public void activate(int initialEnergy){
        energy=initialEnergy;
    }

    public void activateMagneticField(){
        //todo: prevent Resonance Cascade
    }

    public void releaseProton(){
        protonTraps[0].release();
        protonTraps[1].release();
    }

    public void increaseEnergy(int delta){
        energy += Math.min(energy+delta,300000);
    }

    public void collide(Proton proton01, Proton proton02){
        //Block block = new Block();
        proton01.getStructure()[0][0][0]=1;
        //currentExperiment.
    }

    public int decreaseEnergy(){
        return 0;
    }

    public void shutdown(){}

}
