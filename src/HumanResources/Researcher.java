package HumanResources;

import java.util.ArrayList;

public class Researcher extends Employee {
    private boolean isSenior;
    private ArrayList<ResearchGroup> researchGroup;

    public Researcher(String name) {
        super(name);
    }
}
