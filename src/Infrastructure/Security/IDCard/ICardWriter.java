package Infrastructure.Security.IDCard;


public interface ICardWriter extends ICardReader {
    public void writePassword(String encodedPassword);


}
