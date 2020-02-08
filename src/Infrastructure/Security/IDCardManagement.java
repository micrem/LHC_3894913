package Infrastructure.Security;

import HumanResources.Employee;
import HumanResources.HRAssistant;
import HumanResources.Person;
import Infrastructure.Security.IDCard.CardWriter;
import Infrastructure.Security.IDCard.ICardWriter;
import Infrastructure.Security.IDCard.IROIDCard;


import java.util.HashMap;

public enum IDCardManagement {
    INSTANCE;

    private HRAssistant hrWorker = new HRAssistant(Person.getRandomName());
    private HashMap<Integer, IROIDCard> idCardHashMap;
    private ICardWriter cardWriter;

    IDCardManagement() {
        cardWriter= new CardWriter(true);
    }

    public void assignIDCard(IROIDCard idCard, Employee employee){
        //todo: where does this idCard come from? why is it not generated here?
        hrWorker.assignCard(employee,idCard);
        idCardHashMap.put(idCard.getID(),idCard);
    }

    public void lockIDCard(IROIDCard idCard){
        cardWriter.insertCard(idCard);
        cardWriter.lockCard().ejectCard();
    }

    public void clearIDCard(IROIDCard idCard){
        cardWriter.insertCard(idCard);
        cardWriter.clearCard().ejectCard();
        idCardHashMap.remove(idCard.getID());
    }

}
