package Infrastructure.LHC;
import com.google.common.eventbus.Subscribe;

import java.time.LocalTime;
import java.util.Iterator;

public class Ring extends Subscriber {
    public String userDirectory = System.getProperty("user.dir");
    public String fileSeparator = System.getProperty("file.separator");
    public String fileEnding = ".txt";
    public String fileNamePart = "proton_";
    public String fileDirectory = userDirectory + fileSeparator + "rsc" + fileSeparator + "protoData" +fileSeparator;

    private LargeHadronCollider lhc;
    private ProtonTrap[] protonTraps;
    private IDetector detector;
    private Magnet[] magnets = new Magnet[72];
    private Experiment currentExperiment;
    private Proton protonA;
    private Proton protonB;
    private boolean isActivated;
    private int energy;

    public ProtonTrap[] getProtonTraps() {
        return protonTraps;
    }

    public void setProtonTraps(ProtonTrap[] protonTraps) {
        this.protonTraps = protonTraps;
    }

    public Experiment getCurrentExperiment() {
        return currentExperiment;
    }

    public void setCurrentExperiment(Experiment currentExperiment) {
        this.currentExperiment = currentExperiment;
    }



    public Ring(LargeHadronCollider lhc, IDetector detector) {
        int numOfFiles = 50;
        this.lhc = lhc;
        this.detector = detector;
        protonTraps = new ProtonTrap[2];
        protonTraps[0] = new ProtonTrap(ProtonTrapID.A);
        protonTraps[1] = new ProtonTrap(ProtonTrapID.B);
        protonTraps[0].setRing(this);
        protonTraps[1].setRing(this);
        for (int i = 1; i <= numOfFiles; i++) {
            int trapIndex = (i-1)%2; //0 or 1, first or second trap
            String filename = fileDirectory+fileNamePart+(i<10?("0"+i):i)+fileEnding;
            protonTraps[trapIndex].loadData(filename, i);
        }
    }

    @Subscribe
    public void receive(EventRunExperimentFull event){
        runExperiment(event.getScope(), event.getInitialEnergy());
    }

    @Subscribe
    public void receive(EventRunExperimentPartial event){
        runExperiment(event.getScope(), event.getInitialEnergy());
    }

    private void runExperiment(ExperimentScope expScope, InitialEnergy initialEnergy) {
        int initE = 0;
        setCurrentExperiment(new Experiment());
        switch (initialEnergy) {
            case e25:
                initE = 25000;
                break;
            case e50:
                initE = 50000;
                break;
            case e100:
                initE = 100000;
                break;
        }
        currentExperiment.setScope(expScope);
        activate(initE);
        performExperiment();
        detector.addExperiment(currentExperiment);
        shutdown();
    }

    private void performExperiment() {
        if(!isActivated) return;
        activateMagneticField();
        releaseProtons();
        while (energy < 300000) {
            increaseEnergy(25000);
        }
        collide();
    }

    public void activate() {
        isActivated=true;
        energy=0;
    }

    public void activate(int initialEnergy){
        isActivated=true;
        energy=initialEnergy;
    }

    public void activateMagneticField(){
        //todo: prevent Resonance Cascade
        for(Magnet magnet : magnets){
            if (magnet!=null) magnet.activate();
        }
    }

    public void releaseProtons(){
        protonTraps[0].release();
        protonTraps[1].release();
    }

    public void increaseEnergy(int delta){
        energy = Math.min(energy+delta,300000);
    }

    public void collide(){
        Block blocks[] = assembleBlocks(protonA,  protonB);
        currentExperiment.setBlocks(blocks);
        currentExperiment.setDateTimeStamp(LocalTime.now().toString());
        currentExperiment.setProton01ID(protonA.getId());
        currentExperiment.setProton02ID(protonB.getId());
    }

    private Block[] assembleBlocks(Proton proton01, Proton proton02) {
        Block[] returnBlocks=new Block[200000];
        Iterator<String> proton1Iterator = proton01.getBlockIterator();
        Iterator<String> proton2Iterator = proton02.getBlockIterator();
        for (int blockIndex = 0; blockIndex < returnBlocks.length; blockIndex++) {
            returnBlocks[blockIndex] = new Block();
            returnBlocks[blockIndex].setStructure(proton1Iterator.next()+proton2Iterator.next());
        }
        return returnBlocks;
    }

    public int decreaseEnergy(){
        return 0;
    }

    public void shutdown(){
        isActivated=false;
    }

    public void receiveProton(ProtonTrapID id, Proton proton) {
        switch (id) {
            case A:
                protonA=proton;
                break;
            case B:
                protonB=proton;
                break;
        }
    }

    public static void main(String[] args) {
        Ring ring = new Ring(new LargeHadronCollider(), new Detector());
        for (int i=0; i<25;i++) {
            ring.collide();
        }
    }

}
