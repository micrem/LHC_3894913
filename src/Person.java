import java.util.Random;

public abstract class Person {
    static Random r = new Random();

    static int idIncrement=0;
    int id;
    String name;
    Iris iris;
    private Passport passport;
    private IDCard idcard;
    private String password;

    public Person(String name) {
        this.id = idIncrement++;
        this.name = name;
        this.iris = new Iris();
        this.password = genPassword();
        this.passport = new Passport( Integer.toString(this.id), this ); //akjfhak
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

    public void enterPassword(PasswordPad passwordPad){
        //possibly add check if entering password is a good idea right now
        passwordPad.enterPass(this.password);
    }


}

