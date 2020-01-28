package cryptography;

public class AESCryptograph implements ICryptograph {
    private String secretKey = "orange eagle monkey";

    public AESCryptograph() {
        //init aes?
    }

    @Override
    public String encode(String string) {
        return AES.encrypt(string, secretKey);
    }

    @Override
    public String decode(String string) {
        return AES.decrypt(string, secretKey);
    }
}
