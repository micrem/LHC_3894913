package Infrastructure.Security.Biometrics;

import HumanResources.Person;
import HumanResources.Visitor;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class Fingerprint {
    public String getFingerprint() {
        return fingerprint;
    }

    private String fingerprint;

    public void scanFingerprint(Person person) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
            md.update(person.getName().getBytes());
            byte[] digest = md.digest();
            String myChecksum = bytesToHex(digest).toUpperCase();
            fingerprint = myChecksum;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private static String bytesToHex(byte[] hashInBytes) {

        StringBuilder sb = new StringBuilder();
        for (byte b : hashInBytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();

    }

    public static void main(String[] args) {
        Fingerprint f = new Fingerprint();
        Person person = new Visitor(Person.getRandomName());
        Person person2 = new Visitor("aaa bbb");

        f.scanFingerprint(person);
        System.out.println(f.getFingerprint());
        f.scanFingerprint(person2);
        System.out.println(f.getFingerprint());
    }
}
