package HumanResources;

public class Visitor extends Person {
    static String alphaNum = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private String password;
    public Visitor(String name) {
        super(name);
        password = getAlphaNum(5);
    }

    private String getAlphaNum(int length){
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int charPos = Visitor.secureRandom.nextInt(alphaNum.length());
            stringBuilder.append(alphaNum.charAt(charPos));
        }
        return stringBuilder.toString();
    }

    public String enterPassword(){
        return password;
    }

    public void registerWithReceptionist(Receptionist receptionist){
        receptionist.processVisitor(this);
    }
}
