package HumanResources;

import Infrastructure.Security.IDCard.ICardReader;

public abstract class Employee extends Person {
    private boolean isManager;
    private boolean isMentor;
    private boolean hasBudgetResponsibility;

    public Employee(String name) {
        super(name);
    }

    public void generateNewPassword(ICardReader cardReader) {
        this.password = generatePassword();
    }
}
