package Infrastructure.Security.IDCard;

import HumanResources.Person;

public interface IPasswordPad {

    void readUserInput(Person person);

    String getBufferedInput();

}
