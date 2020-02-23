package Infrastructure.Security;

import Cryptography.CryptographyType;
import Cryptography.ICryptograph;

import java.io.FileInputStream;
import java.util.Properties;

public enum SecurityConfiguration {
    instance;
    public String userDirectory = System.getProperty("user.dir");
    public String fileSeparator = System.getProperty("file.separator");
    public CryptographyType cryptographyType;
    public ICryptograph cryptograph = null;
    public String defaultPassword; //"helloLHC2020" as of 2020

    SecurityConfiguration() {
        String masterPassword;
        try (FileInputStream fileInputStream = new FileInputStream(userDirectory + fileSeparator + "src" + fileSeparator + "configuration.props")) {
            Properties properties = new Properties();
            properties.load(fileInputStream);
            fileInputStream.close();
            if (properties.getProperty("CryptographyType").equals("AES")) {
                cryptographyType = CryptographyType.AES;
            }
            masterPassword = properties.getProperty("MasterPassword");
            if (cryptographyType == CryptographyType.AES) {
                cryptograph = new Cryptography.AESCryptograph(masterPassword);
            } else {
                //default
                cryptograph = new Cryptography.AESCryptograph(masterPassword);
            }
            defaultPassword = properties.getProperty("defaultPassword");
        } catch (Exception e) {
            System.out.println("Couldn't load cryptography configuration.props!");
            System.out.println(e.getMessage());
        }
    }
}