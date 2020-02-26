package HumanResources;

import Infrastructure.Security.Reception;

public class Receptionist extends Employee {

    private Reception reception;

    public Receptionist(String name) {
        super(name);
    }

    public Reception getReception() {
        return reception;
    }

    public void setReception(Reception reception) {
        this.reception = reception;
    }

    public void processVisitor(Visitor visitor) {
        String visitorName = visitor.getName();
        reception.createVisitorCard(visitor);
    }
}
