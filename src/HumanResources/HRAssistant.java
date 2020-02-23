package HumanResources;

import Infrastructure.Security.IDCard.IROIDCard;

public class HRAssistant extends Employee {
    public HRAssistant(String name) {
        super(name);
    }

    public void assignCard(Employee employee, IROIDCard idCard) {
        employee.receiveCard(idCard);
    }
}
