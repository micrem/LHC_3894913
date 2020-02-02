package Infrastructure.Security;


import Cryptography.CryptographyType;
import Cryptography.ICryptograph;

import java.io.FileInputStream;
import java.util.Properties;
;


public enum CryptographyConfiguration {
    instance;
    public String userDirectory = System.getProperty("user.dir");
    public String fileSeparator = System.getProperty("file.separator");
    public CryptographyType cryptographyType;
    public ICryptograph cryptograph=null;

    CryptographyConfiguration(){
        String masterPassword;
        try (FileInputStream fileInputStream = new FileInputStream(userDirectory + fileSeparator + "src" + fileSeparator + "configuration.props")){
            Properties properties = new Properties();
            properties.load(fileInputStream);
            fileInputStream.close();
            if (properties.getProperty("CryptographyType").equals("AES")){cryptographyType =CryptographyType.AES;}
            masterPassword = properties.getProperty("MasterPassword");
            if (cryptographyType == CryptographyType.AES) {
                cryptograph = new Cryptography.AESCryptograph(masterPassword);
            } else {
                //default
                cryptograph = new Cryptography.AESCryptograph(masterPassword);
            }
        } catch (Exception e) {
            System.out.println("Couldn't load cryptography configuration.props!");
            System.out.println(e.getMessage());
        }


    }
//
//    CryptographyType getCryptoType() {
//        try (FileInputStream fileInputStream = new FileInputStream(userDirectory + fileSeparator + "src" + fileSeparator + "configuration.props")){
//            Properties properties = new Properties();
//            properties.load(fileInputStream);
//            fileInputStream.close();
//            if (properties.getProperty("CryptographyType").equals("AES"))
//                return CryptographyType.AES;
//            else
//                return null;
//        } catch (Exception e) {
//            System.out.println("Couldn't load cryptography configuration.props!");
//            System.out.println(e.getMessage());
//        }
//        return null;
//    }
//
//    ICryptograph getCryptograph(){
//        ICryptograph cryptograph;
//        if (CryptographyConfiguration.instance.cryptographyType == CryptographyType.AES) {
//            cryptograph = new Cryptography.AESCryptograph();
//        } else {
//            //default
//            cryptograph = new Cryptography.AESCryptograph();
//        }
//        return cryptograph;
//    }

    public static void main(String[] args) {
        System.out.println(CryptographyConfiguration.instance.cryptographyType);
    }
}