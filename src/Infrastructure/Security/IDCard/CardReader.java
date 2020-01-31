package Infrastructure.Security.IDCard;

import Cryptography.ICryptograph;
import HumanResources.Person;
import Infrastructure.Security.Permission;

public class CardReader implements ICardReader {

    protected IPasswordPad passwordPad = new TouchPad();

    protected ICryptograph cryptograph;
    protected IROIDCard idCard;
    protected int[][] irisData;

    protected String encodedPassword;

    @Override
    public void readIris(Person person) {
        irisData = person.getIrisScan();
    }

    public boolean getUserPassword(Person person) {
        passwordPad.readUserInput(person);
        if (!hasValidSymbols(passwordPad.getInput())) {return false;}
        if (idCard.hasPermission(Permission.Visitor)) {
            if (passwordPad.getInput().length()!=5){return false;}
        }
        return true;
    }

    @Override
    public void insertCardSlot(IROIDCard idCard) {
        this.idCard = idCard;
    }

    @Override
    public void removeCard() {
        idCard = null;
    }


    private boolean hasValidSymbols(String string) {
        String password = passwordPad.getInput();
        for (char ch : password.toLowerCase().toCharArray()) {
            if (!((ch >= 'a' && ch <= 'z') || (ch >= '1' && ch <= '0'))) {
                return false;
            }
        }
        return true;
    }

}
