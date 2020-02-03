package Infrastructure.LHC;

import com.google.common.eventbus.EventBus;



public class ControlCenter {

    private EventBus eventBus = new EventBus("LHC");
    private Workplace workplace = new Workplace();
    private final String roomID="C01";
    private Experiment experiment;

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
        eventBus.post(new EventRunExperimentFull(InitialEnergy.e25));
    }

    public void startExperment(ExperimentScope scope){
        eventBus.post(new EventRunExperimentPartial(scope,InitialEnergy.e25));
    }

    public void setExperiment(Experiment experiment){
        this.experiment = experiment;
    };

    public static void main(String[] args) {
        ControlCenter cc = new ControlCenter();
        IDetector detector = new Detector();

        Ring ring = new Ring(new LargeHadronCollider(), detector);

    }
}
