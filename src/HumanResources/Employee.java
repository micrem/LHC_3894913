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
        StringBuilder builder = new StringBuilder();
        builder.append("Employee ID:").append(this.id).append(" name:").append(this.name).append(" hasCard:").append(this.getCard()!=null);
        return builder.toString();
    }
}
