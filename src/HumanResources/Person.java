package HumanResources;

import Infrastructure.Security.Biometrics.Iris;
import Infrastructure.Security.IDCard.IDCard;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Iterator;

public abstract class Person {
    static SecureRandom secureRandom = new SecureRandom();
    static ArrayList<String> names = new ArrayList<>();

    protected static int idIncrement=0;
    int id;
    String name;
    Iris iris;
    private IDCard idcard;
    private String password;

    public Person(String name) {
        this.id = idIncrement++;
        this.name = name;
        this.iris = new Iris();
        this.password = genPassword();
    }



    private String genPassword() {
        return password = Integer.toHexString(secureRandom.nextInt());
    }

    public int[][] getIrisScan(){
        return  iris.toIntMatrix();
    }

    public String getName(){
        return name.toString();
    }

    public String enterPassword(){
        return password;
    }

    protected static String getRandomName(){
        if(names.isEmpty()){
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
}

