package HumanResources;

import Infrastructure.Security.IDCard.IROIDCard;
import Infrastructure.Security.Reception;

public class Receptionist extends Employee {
    private Reception reception;


    public void processVisitor( Visitor visitor ){
        String visitorName = visitor.getName();
        IROIDCard idCard = reception.getBlankIDCard();
        reception.insertCardSlot(idCard);
        reception.getPassword(visitor);
    }
}
