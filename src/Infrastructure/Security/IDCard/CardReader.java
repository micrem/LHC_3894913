package Infrastructure.Security.IDCard;

import Cryptography.ICryptograph;
import HumanResources.Person;
import Infrastructure.Security.Biometrics.FingerprintScanner;
import Infrastructure.Security.Biometrics.IrisScanner;
import Infrastructure.Security.Permission;
import Infrastructure.Security.SecurityConfiguration;

import java.time.LocalDate;

public class CardReader implements ICardReader {

    protected IrisScanner irisScanner = null;
    protected FingerprintScanner fingerScanner = new FingerprintScanner();
    protected IPasswordPad passwordPad = new TouchPad();
    protected IROIDCard idCard;
    protected boolean validSymbolsEntered = false;

    protected boolean useIrisScanner;

    public CardReader(boolean useIrisScanner) {
        this.useIrisScanner = useIrisScanner;
        if (useIrisScanner) irisScanner = new IrisScanner();
    }

    public boolean getPasswordInput(Person person) {

        validSymbolsEntered = false;
        passwordPad.readUserInput(person);
        if (!hasValidSymbols(passwordPad.getBufferedInput())) {
            return false;
        }
        if (idCard.hasPermission(Permission.Visitor)) {
            if (passwordPad.getBufferedInput().length() != 5) {
                return false;
            }
        }
        validSymbolsEntered = true;
        return true;
    }

    @Override
    public boolean verifyCardUser(Person person) {
        int passwordAttempts = 0;
        if (!hasValidCard()) {
            return false;
        }
        //count impossible passwords (bad symbols or format) as attempts || count wrong password attempts
        while (!getPasswordInput(person) || !verifyPassword(person)) {
            if (++passwordAttempts >= 3) {
                idCard.grantLockAccess(this).lock();
                return false;
            }
        }
        if (idCard.isLocked()) {
            return false;
        }

        if (useIrisScanner) {
            if (!verifyIris(person)) {
                return false;
            }
        }
        if (!verifyFingerprint(person)) {
            return false;
        }

        if (LocalDate.now().isAfter(idCard.getValidTo()) || LocalDate.now().isBefore(idCard.getValidFrom())) {
            return false;
        }
        if (verifyPassword(person)) return true;
        return false;
    }

    private boolean verifyFingerprint(Person person) {
        if (idCard.getVersion() == IDCardVersion.MultiChip) {
            String userFingerPrint = person.getFingerScan(fingerScanner);
            String cardFingerPrint = idCard.getMultichipReadAccess(this).getFingerprint(this);
            if (!userFingerPrint.equals(cardFingerPrint)) {
                return false;
            }
        }
        return true;
    }

    private boolean verifyIris(Person person) {
        final int[][] cardIrisStructure = idCard.getIrisStructure();
        final int[][] userIrisStructure = person.getIrisScan(irisScanner);
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (cardIrisStructure[i][j] != userIrisStructure[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    protected boolean verifyPassword(Person person) {
        ICryptograph cryptograph = SecurityConfiguration.instance.cryptograph;
        String encodedDefaultPassword = cryptograph.encode(SecurityConfiguration.instance.defaultPassword);
        String encodedPassword = cryptograph.encode(this.passwordPad.getBufferedInput());
        if (!hasValidCard() || !validSymbolsEntered) {
            return false;
        }
        //change default pw for non-visitors
        if (encodedDefaultPassword.equals(idCard.getPassword()) && !person.getCard(this).hasPermission(Permission.Visitor)) {
            return changeDefaultPassword(person);
        }
        if (encodedPassword.equals(idCard.getPassword())) {
            return true;
        }
        return false;
    }

    @Override
    public void insertCard(IROIDCardRFID idCard) {
        insertCard((IROIDCard) idCard);
    }

    @Override
    public void insertCard(IROIDCard idCard) {
        validSymbolsEntered = false;
        this.idCard = idCard;
    }

    @Override
    public IROIDCard ejectCard() {
        IROIDCard tempCard = idCard;
        idCard = null;
        return tempCard;
    }

    public void changePassword(Person person) {
        if (!hasValidCard()) return;
        //check old password
        if (!getPasswordInput(person)) return;
        if (!verifyPassword(person)) return;
        //get read interface
        IIDCardPWedit idCardPWwriteable = idCard.grantPasswordChangeAccess(this);
        //write new password
        person.generateNewPassword(this);
        ICryptograph cryptograph = SecurityConfiguration.instance.cryptograph;
        String encodedPassword = cryptograph.encode(this.passwordPad.getBufferedInput());
        idCardPWwriteable.setNewPassword(encodedPassword);
    }

    protected boolean changeDefaultPassword(Person person) {
        if (!hasValidCard()) return false;
        if (!getPasswordInput(person)) return false;
        //get read interface
        IIDCardPWedit idCardPWwriteable = idCard.grantPasswordChangeAccess(this);
        //write new password
        ICryptograph cryptograph = SecurityConfiguration.instance.cryptograph;
        String encodedPassword = cryptograph.encode(this.passwordPad.getBufferedInput());
        idCardPWwriteable.setNewPassword(encodedPassword);
        return true;
    }


    private boolean hasValidSymbols(String password) {
        for (char ch : password.toLowerCase().toCharArray()) {
            if (!((ch >= 'a' && ch <= 'z') || "0123456789".contains("" + ch))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean verifyPermission(Permission permission) {
        return idCard.hasPermission(permission);
    }

    protected boolean hasValidCard() {
        if (idCard == null) return false;
        if (idCard.isLocked()) return false;
        if (idCard.getValidFrom().isAfter(LocalDate.now())) return false;
        if (idCard.getValidTo().isBefore(LocalDate.now())) return false;
        return true;
    }

}
