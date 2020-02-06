package Infrastructure.Security.IDCard;

import HumanResources.Employee;
import HumanResources.Person;
import Infrastructure.Security.Permission;

public interface ICardReader {

    void insertCard(IROIDCard idCard);

    IROIDCard ejectCard();

    boolean getPasswordInput(Person person);

    boolean verifyCardUser(Person person);

    void insertCard(IROIDCardRFID idCard);

    void changePassword(Person person);

    boolean verifyPermission(Permission permission);
}
