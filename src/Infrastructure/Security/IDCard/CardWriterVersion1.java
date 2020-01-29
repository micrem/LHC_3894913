package Infrastructure.Security.IDCard;

import HumanResources.Person;
import Cryptography.ICryptograph;

public class CardWriterVersion1 implements ICardWriter {

    private ICryptograph cryptograph;
    private IROIDCard idCard;
    private int[][] irisData;
    private String encryptedPassword;
    private String encodedPassword;
    private IIDCard writeableCard;


    @Override
    public void readIris(Person person) {
        irisData = person.getIrisScan();
    }

    @Override
    public void enterPassword(String clearPassword) {
        //todo: encrypt pass
        this.encodedPassword = cryptograph.encode(clearPassword);
    }

    @Override
    public void insertCard(IROIDCard idCard) {
        this.idCard = idCard;
    }

    @Override
    public void getWriteAccess(IROIDCard idCard) {
        writeableCard = idCard.grantWriteAccess(this);
    }
}
