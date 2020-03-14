package HumanResources;

public abstract class Employee extends Person {
    private boolean isManager;
    private boolean isMentor;
    private boolean hasBudgetResponsibility;

    public Employee(String name) {
        super(name);
    }

    @Override
    public String toString() {
        return "Employee ID:" + this.id + " name:" + this.name + " hasValidCard:" + (this.getCard() != null);
    }

    public void registerWithSecCenter(SecurityOfficer secOfficer) {
        secOfficer.processEmployee(this);
    }
}
