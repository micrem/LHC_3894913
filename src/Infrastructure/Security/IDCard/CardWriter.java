package Infrastructure.Security.IDCard;

import HumanResources.Person;
import Cryptography.ICryptograph;

public class CardWriter extends CardReader implements ICardWriter {

    private IIDCard writeableCard;

    @Override
    public void readIris(Person person) {
        irisData = person.getIrisScan();
    }

    @Override
    public void insertCardSlot(IROIDCard idCard) {
        this.idCard = idCard;
        getWriteAccess();
    }

    @Override
    public void removeCard() {
        idCard = null;
        writeableCard = null;
    }


    private void getWriteAccess() {
        writeableCard = idCard.grantWriteAccess(this);
    }

    @Override
    public void writePassword(String cleartextPassword) {
        if (!hasCard()) return; //todo nocard exception
        ICryptograph cryptograph = new Cryptography.AESCryptograph(); //todo: select cryptograph by config
        String encodedPassword = cryptograph.encode(cleartextPassword);
        writeableCard.setPassword(encodedPassword);
    }

    private boolean hasCard(){
        return idCard!=null;
    }

    private boolean canWrite(){
        return hasCard() && writeableCard != null;
    }


}
