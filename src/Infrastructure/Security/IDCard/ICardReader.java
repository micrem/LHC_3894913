package Infrastructure.Security.IDCard;

import HumanResources.Person;

public interface ICardReader {

    void readIris(Person person);

    void insertCardSlot(IROIDCard idCard);

    void removeCard();

    boolean getUserPassword(Person person);
}
