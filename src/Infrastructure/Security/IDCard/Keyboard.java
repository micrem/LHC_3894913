package Infrastructure.Security.IDCard;

import HumanResources.Person;

public class Keyboard implements IPasswordPad {
    private String password;

    @Override
    public void readUserInput(Person person) {
        password =  person.typePassword(this);
    }

    @Override
    public String getInput() {
        return password;
    }


}
