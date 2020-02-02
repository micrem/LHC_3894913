package Infrastructure.Security.IDCard;

import Cryptography.ICryptograph;
import HumanResources.Employee;
import HumanResources.Person;
import Infrastructure.Security.Biometrics.IrisScanner;
import Infrastructure.Security.CryptographyConfiguration;
import Infrastructure.Security.Permission;

import java.time.LocalDate;

public class CardReader implements ICardReader {

    protected IrisScanner irisScanner=null;
    protected IPasswordPad passwordPad = new TouchPad();
    protected IROIDCard idCard;
    protected boolean validPasswordEntered = false;

    protected boolean useIrisScanner;

    public CardReader(boolean useIrisScanner) {
        this.useIrisScanner = useIrisScanner;
        if(useIrisScanner) irisScanner = new IrisScanner();
    }

    public boolean getUserPassword(Person person) {
        validPasswordEntered = false;
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
        if (!hasCard() || !getUserPassword(person) ) {
            return false;
        }
        if (idCard.isLocked()) {return false;}
        if (!verifyPassword()) {return false;}
        if (useIrisScanner){
            final int[][] cardIrisStructure = idCard.getIrisStructure();
            final int[][] userIrisStructure = person.getIrisScan(irisScanner);
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    if (cardIrisStructure[i][j] != userIrisStructure[i][j]){return false;}
                }
            }

        }

        if(LocalDate.now().isAfter(idCard.getValidTo()) || LocalDate.now().isBefore(idCard.getValidFrom())){
            return false;
        }
        if (verifyPassword()) return true;

        return false;
    }

    protected boolean verifyPassword() {
        if(!hasCard() || !validPasswordEntered) {return false;}
        ICryptograph cryptograph = CryptographyConfiguration.instance.cryptograph;
        String encodedPassword = cryptograph.encode(this.passwordPad.getInput());
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

    public void changePassword(Employee employee){
        if (!hasCard()) return;
        //check old password
        if (!getUserPassword(employee)) return;
        if (!verifyPassword()) return;
        //get read interface
        IIDCardPWedit idCardPWwriteable = idCard.grantPasswordChangeAccess(this);
        //write new password
        employee.generateNewPassword(this);
        ICryptograph cryptograph = CryptographyConfiguration.instance.cryptograph;
        String encodedPassword = cryptograph.encode(this.passwordPad.getInput());
        idCardPWwriteable.setNewPassword(encodedPassword);
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
