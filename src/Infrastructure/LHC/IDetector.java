package Infrastructure.LHC;

import HumanResources.Person;
import com.google.common.eventbus.Subscribe;

import java.util.List;

public interface IDetector extends IRODetector{
    boolean isActivated();

    void setActivated(boolean activated);

    void search(Experiment experiment);

    @Subscribe
    void receive(EventAnalyse event);

    void addExperiment(Experiment experiment);

}
