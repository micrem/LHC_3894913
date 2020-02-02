package Infrastructure.Security.IDCard;

import HumanResources.Person;

public interface ICardReader {

    void scanIris(Person person);

    void insertCard(IROIDCard idCard);

    IROIDCard ejectCard();

    boolean getUserPassword(Person person);

    boolean verifyCardUser(Person person);

    void insertCard(IROIDCardRFID idCard);
}
