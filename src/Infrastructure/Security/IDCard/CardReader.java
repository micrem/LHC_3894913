package Infrastructure.Security.IDCard;

import Cryptography.ICryptograph;
import HumanResources.Employee;
import HumanResources.Person;
import Infrastructure.Security.Biometrics.FingerprintScanner;
import Infrastructure.Security.Biometrics.IrisScanner;
import Infrastructure.Security.SecurityConfiguration;
import Infrastructure.Security.Permission;

import java.time.LocalDate;

public class CardReader implements ICardReader {

    protected IrisScanner irisScanner=null;
    protected FingerprintScanner fingerScanner=new FingerprintScanner();
    protected IPasswordPad passwordPad = new TouchPad();
    protected IROIDCard idCard;
    protected boolean validPasswordEntered = false;

    protected boolean useIrisScanner;

    public CardReader(boolean useIrisScanner) {
        this.useIrisScanner = useIrisScanner;
        if(useIrisScanner) irisScanner = new IrisScanner();
    }

    public boolean getPasswordInput(Person person) {
        validPasswordEntered = false;
        passwordPad.readUserInput(person);
        if (!hasValidSymbols(passwordPad.getBufferedInput())) {return false;}
        if (idCard.hasPermission(Permission.Visitor)) {
            return passwordPad.getBufferedInput().length() == 5;
        }
        validPasswordEntered = true;
        return true;
    }

    @Override
    public boolean verifyCardUser(Person person) {
        if (!hasCard() || !getPasswordInput(person) ) {
            return false;
        }
        if (idCard.isLocked()) {return false;}
        if (!verifyPassword(person)) {return false;}
        if (useIrisScanner){
            if(!verifyIris(person)){return false;}
        }
        if (!verifyFingerprint(person)) { return false;}

        if(LocalDate.now().isAfter(idCard.getValidTo()) || LocalDate.now().isBefore(idCard.getValidFrom())){
            return false;
        }
        if (verifyPassword(person)) return true;
        return false;
    }

    private boolean verifyFingerprint(Person person) {
        if (idCard.getVersion()==IDCardVersion.MultiChip){
            String userFingerPrint = person.getFingerScan(fingerScanner);
            String cardFingerPrint = idCard.getMultichipReadAccess(this).getFingerprint(this);
            if (userFingerPrint!=cardFingerPrint){ return false;}
        }
        return true;
    }

    private boolean verifyIris(Person person) {
        final int[][] cardIrisStructure = idCard.getIrisStructure();
        final int[][] userIrisStructure = person.getIrisScan(irisScanner);
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (cardIrisStructure[i][j] != userIrisStructure[i][j]){return false;}
            }
        }
        return true;
    }

    protected boolean verifyPassword(Person person) {
        if(!hasCard() || !validPasswordEntered) {return false;}
        ICryptograph cryptograph = SecurityConfiguration.instance.cryptograph;
        String encodedDefaultPassword = cryptograph.encode(SecurityConfiguration.instance.defaultPassword);
        if(encodedDefaultPassword.equals(idCard.getPassword()) && !person.getCard(this).hasPermission(Permission.Visitor)){
            return changeDefaultPassword(person);
        }
        String encodedPassword = cryptograph.encode(this.passwordPad.getBufferedInput());
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
        validPasswordEntered = false;
        this.idCard = idCard;
    }

    @Override
    public IROIDCard ejectCard() {
        IROIDCard tempCard = idCard;
        idCard = null;
        return tempCard;
    }

    public void changePassword(Person person){
        if (!hasCard()) return;
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

    protected boolean changeDefaultPassword(Person person){
        if (!hasCard()) return false;
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
            if (!((ch >= 'a' && ch <= 'z') || "0123456789".contains(""+ch))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean verifyPermission(Permission permission) {
        return idCard.hasPermission(permission);
    }

    protected boolean hasCard(){
        return idCard!=null;
    }

}
