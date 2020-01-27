import java.security.SecureRandom;

public class Iris{
    private static SecureRandom secureRandom = new SecureRandom();
    private int[][] values;

    public Iris(){
        values = new int[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                values[i][j] =  secureRandom.nextInt(255);
            }
        }
    }

    public int[][] toIntMatrix(){
        int[][] irisIntMatrix = new int[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                irisIntMatrix[i][j] =  values[i][j];
            }
        }
        return irisIntMatrix;

    }

    @Override
    public String toString(){
        String returnString = "";
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                returnString = returnString + (char) ( values[i][j] );
            }
        }
        return returnString;
    }
}
