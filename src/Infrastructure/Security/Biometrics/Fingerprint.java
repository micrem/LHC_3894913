package Infrastructure.Security.Biometrics;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class Fingerprint {
    public String getFingerprint() {
        return fingerprint;
    }

    private String fingerprint;

    public void generateFingerprint(String name) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
            md.update(name.getBytes());
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
        f.generateFingerprint("Alfonse Brightflank");
        System.out.println(f.getFingerprint());
    }
}
