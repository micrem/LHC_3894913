package Infrastructure.Security.IDCard;

public interface IROIDCardMultichip {
    String getFingerprint(ICardReader iCardReader);
}
