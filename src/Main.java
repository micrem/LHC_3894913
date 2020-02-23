import HumanResources.Person;
import HumanResources.Researcher;
import Infrastructure.LHC.Detector;

public class Main {

    public static void main(String[] args) {

        //print experiments from database
        Detector detector = new Detector();
        detector.getExperiments(new Researcher(Person.getRandomName())).forEach(System.out::println);
    }
}
