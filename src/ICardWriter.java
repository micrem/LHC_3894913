public interface ICardWriter {
    // int[][] currentIris = new int[10][10];
    public void readIris(Person person);
    public void enterPassword(String password);
    public void writeCard();
    public void insertCard( IDCard idCard);
}
