package Infrastructure.Security.IDCard;

import Cryptography.ICryptograph;
import HumanResources.Person;
import Infrastructure.Security.Permission;
import Infrastructure.Security.SecurityConfiguration;

import java.time.LocalDate;

public class CardWriter extends CardReader implements ICardWriter {

    private IIDCard writeableCard;
    private IIDCardMultichip writeableMultiChipCard;

    public CardWriter(boolean useIrisScanner) {
        super(useIrisScanner);
    }

    @Override
    public ICardWriter scanIrisToCard(Person person) {
        if (canWrite()) {
            writeableCard.setIrisStructure(irisScanner.scanIris(person));
        }
        return this;
    }

    @Override
    public void insertCard(IROIDCard idCard) {
        super.insertCard(idCard);
        getWriteAccess();
        if (idCard.getVersion() == IDCardVersion.MultiChip) {
            writeableMultiChipCard = idCard.getMultichipWriteAccess(this);
        }
    }

    @Override
    public IROIDCard ejectCard() {
        IROIDCard tempCard = idCard;
        idCard = null;
        writeableCard = null;
        writeableMultiChipCard = null;
        return tempCard;
    }

    private void getWriteAccess() {
        writeableCard = idCard.grantWriteAccess(this);
    }

    @Override
    public ICardWriter writePassword() {
        if (!canWrite()) return this;
        ICryptograph cryptograph = SecurityConfiguration.instance.cryptograph;
        String encodedPassword = cryptograph.encode(this.passwordPad.getBufferedInput());
        writeableCard.setPassword(encodedPassword);
        return this;
    }

    @Override
    public ICardWriter finalizeCard(Person person) {
        writeableCard.setIrisStructure(person.getIrisScan(irisScanner));
        writeableCard.setLocked(false);
        writeableCard.setPerson(person);
        writeableCard.setValidFrom(LocalDate.now());
        writeableCard.setValidTo(LocalDate.now().plusDays(7));
        if (writeableMultiChipCard != null) {
            String userFingerPrint = person.getFingerScan(fingerScanner);
            writeableMultiChipCard.writeFingerprintData(userFingerPrint);
        }
        return this;
    }

    @Override
    public ICardWriter setPermission(Permission permission) {
        if (canWrite()) {
            writeableCard.setPermission(Permission.Visitor, true);
        }
        return this;
    }

    @Override
    public ICardWriter clearCard() {
        if (writeableCard == null) return this;
        if (writeableMultiChipCard != null) writeableMultiChipCard.writeFingerprintData("");
        writeableCard.setIrisStructure(new int[10][10]);
        writeableCard.setLocked(false);
        writeableCard.setPerson(null);
        writeableCard.setValidFrom(LocalDate.now().minusYears(1));
        writeableCard.setValidTo(LocalDate.now().minusYears(1));
        writeableCard.setPassword("dieser satz kein verb!! °¿º ");
        for (Permission p : Permission.values()
        ) {
            writeableCard.setPermission(p, false);
        }
        return this;
    }

    @Override
    public ICardWriter lockCard() {
        writeableCard.setLocked(true);
        return this;
    }

    private boolean canWrite() {
        return hasCard() && writeableCard != null;
    }
}
