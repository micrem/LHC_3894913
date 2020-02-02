package Infrastructure.Security.IDCard;


import HumanResources.Person;
import Infrastructure.Security.Permission;

public interface ICardWriter extends ICardReader {
    void writePassword();

    void finalizeCard(Person person);

    void setPermission(Permission permission);
}
