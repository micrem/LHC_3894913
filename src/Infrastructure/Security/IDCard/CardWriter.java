package Infrastructure.Security.IDCard;

import HumanResources.Person;
import Cryptography.ICryptograph;
import Infrastructure.Security.Permission;

import java.time.LocalDate;

public class CardWriter extends CardReader implements ICardWriter {

    private IIDCard writeableCard;

    @Override
    public void scanIris(Person person) {
        irisData = person.getIrisScan();
        if(canWrite()){
            writeableCard.setIrisStructure(irisData);
        }
    }

    @Override
    public void insertCard(IROIDCard idCard) {
        super.insertCard(idCard);
        getWriteAccess();
    }

    @Override
    public IROIDCard ejectCard() {
        IROIDCard tempCard = idCard;
        idCard = null;
        writeableCard = null;
        return tempCard;
    }


    private void getWriteAccess() {
        writeableCard = idCard.grantWriteAccess(this);
    }

    @Override
    public void writePassword() {
        if (!canWrite()) return;
        ICryptograph cryptograph = new Cryptography.AESCryptograph(); //todo: select cryptograph by config
        String encodedPassword = cryptograph.encode(this.passwordPad.getInput());
        writeableCard.setPassword(encodedPassword);
    }

    @Override
    public void finalizeCard(Person person) {
        writeableCard.setIrisStructure(person.getIrisScan());
        writeableCard.setLocked(false);
        writeableCard.setPerson(person);
        writeableCard.setValidFrom(LocalDate.now());
        writeableCard.setValidTo(LocalDate.now().plusDays(7));

    }

    @Override
    public void setPermission(Permission permission) {
        if (canWrite()){
            writeableCard.setPermission(Permission.Visitor, true);
        }
    }


    private boolean canWrite() {
        return hasCard() && writeableCard != null;
    }


}
