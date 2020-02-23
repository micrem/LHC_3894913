package Infrastructure.Security;

import HumanResources.Employee;
import HumanResources.HRAssistant;
import HumanResources.Person;
import Infrastructure.Security.IDCard.CardWriter;
import Infrastructure.Security.IDCard.ICardWriter;
import Infrastructure.Security.IDCard.IROIDCard;

import java.util.HashMap;

public enum IDCardManagement implements IIDCardManagement {
    INSTANCE;

    private HRAssistant hrWorker = new HRAssistant(Person.getRandomName());
    private HashMap<Integer, IROIDCard> idCardHashMap = new HashMap<>();
    private ICardWriter cardWriter;

    IDCardManagement() {
        cardWriter = new CardWriter(true);
    }

    @Override
    public void assignIDCard(IROIDCard idCard, Employee employee) {
        hrWorker.assignCard(employee, idCard);
        idCardHashMap.put(idCard.getID(), idCard);
    }

    @Override
    public void lockIDCard(IROIDCard idCard) {
        cardWriter.insertCard(idCard);
        cardWriter.lockCard().ejectCard();
    }

    @Override
    public void clearIDCard(IROIDCard idCard) {
        cardWriter.insertCard(idCard);
        cardWriter.clearCard().ejectCard();
        idCardHashMap.remove(idCard.getID());
    }

    public HRAssistant getHrWorker() {
        return hrWorker;
    }

    public void setHrWorker(HRAssistant hrWorker) {
        this.hrWorker = hrWorker;
    }

}
