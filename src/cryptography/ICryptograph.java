package cryptography;

public interface ICryptograph {
    String encode(String string);
    String decode(String string);
}