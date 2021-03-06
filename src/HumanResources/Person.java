package HumanResources;

import Infrastructure.Security.Biometrics.Fingerprint;
import Infrastructure.Security.Biometrics.FingerprintScanner;
import Infrastructure.Security.Biometrics.Iris;
import Infrastructure.Security.Biometrics.IrisScanner;
import Infrastructure.Security.IDCard.ICardReader;
import Infrastructure.Security.IDCard.IPasswordPad;
import Infrastructure.Security.IDCard.IROIDCard;

import java.security.SecureRandom;
import java.util.ArrayList;


public abstract class Person {
    protected static int idIncrement = 0;
    static SecureRandom secureRandom = new SecureRandom();
    static ArrayList<String> names = new ArrayList<>();
    protected int id;
    protected String name;
    protected Iris iris;
    protected Fingerprint fingerprint;
    protected String password;
    private IROIDCard idCard;

    public Person(String name) {
        this.id = idIncrement++;
        this.name = name;
        this.iris = new Iris();
        this.password = generatePassword();
        this.fingerprint = new Fingerprint();
        fingerprint.scanFingerprint(this);
    }

    public static String getRandomName() {
        if (names.isEmpty()) {
            names.add("Nelchael");
            names.add("Jeremiel");
            names.add("Algemos Vibiutus");
            names.add("Elatutus Corastus");
            names.add("Juliuverus Aquitanus");
            names.add("Armaron Boulderhowl");
            names.add("Jonold Firegaze");
            names.add("Armaron Boulderhowl");
            names.add("Hectic Rockfall");
            names.add("Eoninid Hestaddas");
            names.add("Targuram Vidake");
            names.add("Agaresh Nidya");
        }
        int randomNameIndex = secureRandom.nextInt(names.size());
        return names.get(randomNameIndex);

    }

    public String typePassword(IPasswordPad passwordPad) {
        return password;
    }

    public String getName() {
        return name;
    }

    public int[][] getIrisScan(IrisScanner irisScanner) {
        return iris.toIntMatrix();
    }

    public String getFingerScan(FingerprintScanner fingerprintScanner) {
        return fingerprint.getFingerprint();
    }

    protected IROIDCard getCard() {
        return idCard;
    }

    public IROIDCard getCard(ICardReader cardReader) {
        return getCard();
    }

    public void receiveCard(IROIDCard idCard) {
        this.idCard = idCard;
    }

    protected String generatePassword() {
        return Integer.toHexString(secureRandom.nextInt());
    }

    public void generateNewPassword(ICardReader cardReader) {
        this.password = generatePassword();
    }

    public int getId() {
        return id;
    }
}

