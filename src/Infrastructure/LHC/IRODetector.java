package Infrastructure.LHC;

import HumanResources.Person;

import java.util.List;

public interface IRODetector {

    List<IExperiment> getExperiments(Person scientist);
}
