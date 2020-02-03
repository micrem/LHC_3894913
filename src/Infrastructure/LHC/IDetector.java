package Infrastructure.LHC;

import com.google.common.eventbus.Subscribe;

public interface IDetector {
    boolean isActivated();

    void setActivated(boolean activated);

    void search(Experiment experiment);

    @Subscribe
    void receive(EventAnalyse event);

    void addExperiment(Experiment experiment);
}
