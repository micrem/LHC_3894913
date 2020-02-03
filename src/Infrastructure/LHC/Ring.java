package Infrastructure.LHC;
import com.google.common.eventbus.Subscribe;

import java.time.LocalTime;
import java.time.ZoneId;

public class Ring {
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
        for (int i = 1; i <= numOfFiles; i++) {
            String filename1 = fileDirectory+fileNamePart+(i<10?("0"+i):i)+fileEnding;
            int j = numOfFiles-i+1;
            String filename2 = fileDirectory+fileNamePart+(j<10?("0"+j):j)+fileEnding;
            protonTraps[0].loadData(filename1);
            protonTraps[1].loadData(filename2);
        }
    }


    @Subscribe
    public void receive(EventRunExperimentFull event){
        int initE=0;
        setCurrentExperiment(new Experiment());
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
        currentExperiment.setScope(event.getScope());
        activate(initE);
        performExperiment();
        shutdown();
    }

    @Subscribe
    public void receive(EventRunExperimentPartial event){
        int initE=0;
        setCurrentExperiment(new Experiment());
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
        currentExperiment.setScope(event.getScope());
        activate(initE);
        performExperiment();
        shutdown();
    }

    private void performExperiment() {
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
            magnet.activate();
        }
    }

    public void releaseProton(){
        protonTraps[0].release();
        protonTraps[1].release();
    }

    public void increaseEnergy(int delta){
        energy += Math.min(energy+delta,300000);
    }

    public void collide(Proton proton01, Proton proton02){
        Block blocks[] = assembleBlocks( proton01,  proton02);
        currentExperiment.setBlocks(blocks);
        currentExperiment.setDateTimeStamp(LocalTime.now().toString());
        currentExperiment.setProton01ID(proton01.getId());
        currentExperiment.setProton02ID(proton02.getId());
        detector.addExperiment(currentExperiment);
    }

    private Block[] assembleBlocks(Proton proton01, Proton proton02) {
        Block[] returnBlocks=new Block[200000];
        StringBuilder strBuilder = new StringBuilder();
        StringBuilder strBuilder2 = new StringBuilder();

        int dim0,dim1,dim2,partOffset;
        for (int part = 0; part < 100000; part++) {
            partOffset=10*part;
            dim0 = partOffset/10000;
            dim1 = (partOffset/100)%100;
            dim2 = partOffset%100;
            for (int i = 0; i < 5; i++) {
                strBuilder.append((char) proton01.getStructure()[dim0][dim1][dim2 + i]);
                strBuilder.append((char) proton02.getStructure()[dim0][dim1][dim2 + i]);
            }
            returnBlocks[2*part] = new Block();
            returnBlocks[2*part+1]= new Block();
            returnBlocks[2*part].setStructure(strBuilder.toString());
            returnBlocks[2*part+1].setStructure(strBuilder2.toString());

            strBuilder.setLength(0);
            strBuilder2.setLength(0);
        }
        return returnBlocks;
    }

    public int decreaseEnergy(){
        return 0;
    }

    public void shutdown(){
        isActivated=false;
    }

    public static void main(String[] args) {
        Ring ring = new Ring(new LargeHadronCollider(), new Detector());
        for (int i=0; i<50;i++) {
            ring.collide(ring.getProtonTraps()[0].getProton(),ring.getProtonTraps()[1].getProton());
        }
    }
}
