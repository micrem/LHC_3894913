package Infrastructure.LHC;

import HumanResources.Person;

import java.util.List;

public interface IRODetector {

    List<Experiment> getExperiments(Person scientist);
}
