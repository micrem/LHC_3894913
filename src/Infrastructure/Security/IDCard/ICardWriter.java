package Infrastructure.Security.IDCard;


import HumanResources.Person;
import Infrastructure.Security.Permission;

public interface ICardWriter extends ICardReader {

    ICardWriter scanIrisToCard(Person person);

    ICardWriter writePassword();

    ICardWriter finalizeCard(Person person);

    ICardWriter setPermission(Permission permission);

    ICardWriter clearCard();

    ICardWriter lockCard();
}
