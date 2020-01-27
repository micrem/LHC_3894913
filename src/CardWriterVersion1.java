public class CardWriterVersion1 implements ICardWriter {

    private ICryptograph cryptograph;
    private IDCard idCard;
    private int[][] irisData;
    private String encryptedPassword;
    private String encodePassword;


    @Override
    public void readIris(Person person) {
        irisData = person.getIrisScan();
    }

    @Override
    public void enterPassword(String password) {
        //todo: encrypt pass
        this.encodePassword = cryptograph.encode(password);
    }

    @Override
    public void writeCard() {

    }

    @Override
    public void insertCard(IDCard idCard) {
        this.idCard = idCard;
    }
}
