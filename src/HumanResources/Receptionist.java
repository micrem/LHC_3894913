package HumanResources;

import Infrastructure.Security.IDCard.IROIDCard;
import Infrastructure.Security.Reception;

public class Receptionist extends Employee {
    private Reception reception;


    public void getVisitorName(String visitorName){
        IROIDCard idCard = reception.getBlankIDCard();

    }
}
