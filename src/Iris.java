import java.security.SecureRandom;

public class Iris{
    private static SecureRandom secureRandom = new SecureRandom();
    private int[][] values;

    public Iris(){
        values = new int[10][10];
        for (int[] row: values) {
            for (int cell: row) {
                cell = secureRandom.nextInt(255);
            }
        }
    }

    public int[][] toIntMatrix(){
        int[][] irisIntMatrix = new int[10][10];
        for (int i = 0; i < 10; i++) {
            System.arraycopy(values[i], 0, irisIntMatrix[i], 0, 10);
        }
        return irisIntMatrix;

    }

    @Override
    public String toString(){
        StringBuilder returnString = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                returnString.append((char) (values[i][j]));
            }
        }
        return returnString.toString();
    }
}
