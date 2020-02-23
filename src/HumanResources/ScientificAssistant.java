package HumanResources;

import java.util.ArrayList;
import java.util.Date;

public class ScientificAssistant extends Employee {
    private ArrayList<ResearchGroup> researchGroup;
    private Date periodFrom;
    private Date periodUntil;

    public ScientificAssistant(String name) {
        super(name);
    }
}
