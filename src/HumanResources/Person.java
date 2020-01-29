package HumanResources;

import Infrastructure.Security.Biometrics.Iris;
import Infrastructure.Security.IDCard.IDCard;

import java.util.Random;

public abstract class Person {
    static Random r = new Random();

    static int idIncrement=0;
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
        return password = Integer.toHexString(r.nextInt());
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


}

