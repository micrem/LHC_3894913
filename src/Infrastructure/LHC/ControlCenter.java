package Infrastructure.LHC;

import com.google.common.eventbus.EventBus;



public class ControlCenter {

    private EventBus eventBus = new EventBus("LHC");
    private Workplace workplace = new Workplace();
    private final String roomID="C01";


    public EventBus getEventBus() {
        return eventBus;
    }

    public Workplace getWorkplace() {
        return workplace;
    }

    public String getRoomID() {
        return roomID;
    }

    public void addSubscriber(Subscriber subscriber) {
        eventBus.register(subscriber);
    }

    public void startExperiment(){
        eventBus.post(new EventRunExperimentFull(InitialEnergy.e50));
    }

    public void startExperment(ExperimentScope scope){
        eventBus.post(new EventRunExperimentPartial(scope,InitialEnergy.e25));
    }

    private void analyseAll() {
        eventBus.post(new EventAnalyse());
    }

    public static void main(String[] args) {
        ControlCenter cc = new ControlCenter();
        Detector detector = new Detector();
        LargeHadronCollider lhc = new LargeHadronCollider();

        Ring ring = new Ring(lhc, detector);
        lhc.setRing(ring);
        lhc.setControlCenter(cc);

        cc.addSubscriber(ring);
        cc.addSubscriber(detector);
        for (int i = 0; i < 25; i++) {
            System.out.println("running experiment "+i);
            cc.startExperment(ExperimentScope.ESFull);
        }
        cc.analyseAll();
        //detector.writeToDB();
    }
}
