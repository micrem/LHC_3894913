package Infrastructure.Security.Biometrics;

import HumanResources.Person;

public class IrisScanner {
    public int[][] scanIris(Person person) {
        return person.getIrisScan(this);
    }
}
