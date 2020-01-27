public interface ICardWriter {
    // int[][] currentIris = new int[10][10];
    void readIris(Person person);
    void enterPassword(String password);
    void writeCard();
    void insertCard( IDCard idCard);
}
