package Infrastructure.Security.IDCard;

import Cryptography.ICryptograph;
import HumanResources.Person;
import Cryptography.CryptographyType;
import Infrastructure.Security.CryptographyConfiguration;
import Infrastructure.Security.Permission;

import java.time.LocalDate;

public class CardWriter extends CardReader implements ICardWriter {

    private IIDCard writeableCard;

    public CardWriter(boolean useIrisScanner) {
        super(useIrisScanner);
    }

    @Override
    public void scanIris(Person person) {
        if(canWrite()){
            writeableCard.setIrisStructure(irisScanner.scanIris(person));
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
        ICryptograph cryptograph = CryptographyConfiguration.instance.cryptograph;
        String encodedPassword = cryptograph.encode(this.passwordPad.getInput());
        writeableCard.setPassword(encodedPassword);
    }

    @Override
    public void finalizeCard(Person person) {
        writeableCard.setIrisStructure(person.getIrisScan(irisScanner));
        writeableCard.setLocked(false);
        writeableCard.setPerson(person);
        writeableCard.setValidFrom(LocalDate.now());
        writeableCard.setValidTo(LocalDate.now().plusDays(7));
        if (idCard.getVersion()==IDCardVersion.MultiChip){
            String userFingerPrint = person.getFingerScan(fingerScanner);
            idCard.getMultichipWriteAccess(this).writeFingerprintData(userFingerPrint);
        }
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

    public static void main(String[] args) {
        CardWriter writer = new CardWriter(false);
    }

}
