package HumanResources;

import Infrastructure.Security.IDCard.IROIDCard;
import Infrastructure.Security.Reception;

public class Receptionist extends Employee {
    private Reception reception;


    public void processVisitor( Visitor visitor ){
        //todo: receptiopn.getCardWriter, CardWriter.getInputDevice()
        String visitorName = visitor.getName();
        IROIDCard idCard = reception.getBlankIDCard();
        reception.insertCardSlot(idCard);
        reception.getPassword(visitor);
    }

    public Receptionist(String name) {
        super(name);
    }
}
