package HumanResources;

public abstract class Employee extends Person {
    private boolean isManager;
    private boolean isMentor;
    private boolean hasBudgetResponsibility;

    public Employee(String name) {
        super(name);
    }
}
