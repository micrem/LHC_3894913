package Infrastructure.Security.IDCard;

import HumanResources.Person;

public class Keyboard implements IPasswordPad {
    private String password;

    @Override
    public void readUserInput(Person person) {
        password =  person.enterPassword();
    }

    @Override
    public String getInput() {
        return password;
    }


}
