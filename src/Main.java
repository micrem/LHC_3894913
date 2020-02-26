import Infrastructure.LHC.*;

public class Main {

    public static void main(String[] args) {

        //loads protons from files, runs experiments, overwrites into DB
        ControlCenter cc = new ControlCenter();
        Detector detector = new Detector();
        LargeHadronCollider lhc = new LargeHadronCollider();

        Ring ring = new Ring(lhc, detector);
        lhc.setRing(ring);
        lhc.setControlCenter(cc);

        cc.addSubscriber(ring);
        cc.addSubscriber(detector);
        ring.loadProtonTxts();

        System.out.println("running experiment (limited)");
        for (int i = 0; i < 10; i++) {
            System.out.print(" " + i);
            cc.startExperment(ExperimentScope.ESFull);
        }
        System.out.println();
        cc.analyseAll();
        detector.saveExperimentsToDB();
    }
}
