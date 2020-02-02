package Cryptography;

public class AESCryptograph implements ICryptograph {
    private String secretKey;

    public AESCryptograph(String masterPassword) {
        secretKey = masterPassword;
    }

    @Override
    public String encode(String string) {
        return AES.encrypt(string, secretKey);
    }

//    @Override
//    public String decode(String string) {
//        return AES.decrypt(string, secretKey);
//    }
}
