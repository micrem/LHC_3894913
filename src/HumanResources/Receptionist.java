package HumanResources;

import Infrastructure.LHC.Building;
import Infrastructure.Security.IDCard.ICardWriter;
import Infrastructure.Security.IDCard.IROIDCard;
import Infrastructure.Security.Permission;
import Infrastructure.Security.Reception;

public class Receptionist extends Employee {

    private Reception reception;
    private ICardWriter cardWriter;

    public Receptionist(String name) {
        super(name);
    }

    public static void main(String[] args) {
        Reception reception = new Reception(new Building());
        Receptionist receptionist = new Receptionist("Alfred Hitchblock");
        receptionist.setReception(reception);
        Visitor visitor = new Visitor("Dumbledore Blunderbuss");
        visitor.registerWithReceptionist(receptionist);
    }

    public Reception getReception() {
        return reception;
    }

    public void setReception(Reception reception) {
        this.reception = reception;
    }

    public void processVisitor(Visitor visitor) {
        String visitorName = visitor.getName();
        IROIDCard idCard = reception.getBlankIDCard();
        cardWriter = reception.getCardWriter(this);
        cardWriter.insertCard(idCard);
        cardWriter.setPermission(Permission.Visitor);
        cardWriter.getPasswordInput(visitor);
        cardWriter.writePassword();
        cardWriter.scanIrisToCard(visitor);
        cardWriter.finalizeCard(visitor);
        IROIDCard authorizedCard = cardWriter.ejectCard();
        visitor.receiveCard(authorizedCard);
    }
}
