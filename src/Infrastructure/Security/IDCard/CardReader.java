package Infrastructure.Security.IDCard;

import Cryptography.ICryptograph;
import HumanResources.Person;
import Infrastructure.Security.Permission;

public class CardReader implements ICardReader {

    protected IPasswordPad passwordPad = new TouchPad();

    protected IROIDCard idCard;
    protected int[][] irisData;
    private boolean validPasswordEntered = false;

    @Override
    public void scanIris(Person person) {
        irisData = person.getIrisScan();
    }

    public boolean getUserPassword(Person person) {
        passwordPad.readUserInput(person);
        if (!hasValidSymbols(passwordPad.getInput())) {return false;}
        if (idCard.hasPermission(Permission.Visitor)) {
            return passwordPad.getInput().length() == 5;
        }
        validPasswordEntered = true;
        return true;
    }

    @Override
    public boolean verifyCardUser(Person person) {
        if (!(hasCard() && validPasswordEntered)) {
            return false;
        }
        //todo : scan biometrics

        ICryptograph cryptograph = new Cryptography.AESCryptograph(); //todo: select cryptograph by config
        String encodedPassword = cryptograph.encode(this.passwordPad.getInput());
        if (encodedPassword.equals(idCard.getPassword())) {return true;}
        return false;
    }

    @Override
    public void insertCard(IROIDCardRFID idCard) {
        insertCard(idCard);
    }

    @Override
    public void insertCard(IROIDCard idCard) {
        validPasswordEntered = false;
        this.idCard = idCard;
    }

    @Override
    public IROIDCard ejectCard() {
        IROIDCard tempCard = idCard;
        idCard = null;
        return tempCard;
    }


    private boolean hasValidSymbols(String password) {
        for (char ch : password.toLowerCase().toCharArray()) {
            if (!((ch >= 'a' && ch <= 'z') || "0123456789".contains(""+ch))) {
                return false;
            }
        }
        return true;
    }

    protected boolean hasCard(){
        return idCard!=null;
    }

}
