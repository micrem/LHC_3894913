package Infrastructure.Security.IDCard;

import HumanResources.Person;

public interface ICardWriter {
    void readIris(Person person);
    void enterPassword(String password);
    void insertCard(IROIDCard idCard);
    void getWriteAccess(IROIDCard idCard);
}
