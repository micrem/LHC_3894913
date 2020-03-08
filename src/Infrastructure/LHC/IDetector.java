package Infrastructure.LHC;

import com.google.common.eventbus.Subscribe;

public interface IDetector extends IRODetector {
    boolean isActivated();

    void setActivated(boolean activated);

    void analyse(IExperiment experiment);

    @Subscribe
    void receive(EventAnalyse event);

    void addExperiment(IExperiment experiment);

}
