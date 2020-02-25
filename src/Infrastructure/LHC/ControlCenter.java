package Infrastructure.LHC;

import com.google.common.eventbus.EventBus;

public class ControlCenter {

    private final String roomID ;
    private EventBus eventBus;
    private Workplace workplace;

    public ControlCenter() {
        roomID = "C01";
        eventBus = new EventBus("LHC");
        workplace = new Workplace();
    }

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

    public void removeSubscriber(Subscriber subscriber) {
        eventBus.unregister(subscriber);
    }

    public void startExperiment() {
        System.out.println("event: cc:startExp");
        eventBus.post(new EventRunExperimentFull(InitialEnergy.e50));
    }

    public void startExperment(ExperimentScope scope) {
        System.out.println("event: cc:startExp");
        eventBus.post(new EventRunExperimentPartial(scope, InitialEnergy.e25));
    }

    public void analyseAll() {
        System.out.println("event: cc:analyseAll");
        eventBus.post(new EventAnalyse());
    }
}
