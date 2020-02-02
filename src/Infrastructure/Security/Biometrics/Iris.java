package Infrastructure.Security.Biometrics;

import java.security.SecureRandom;

public class Iris{
    private static SecureRandom secureRandom = new SecureRandom();
    private int[][] values;

    public Iris(){
        values = new int[10][10];
        for (int[] row: values) {
            for (int i=0;i<10;i++) {
                row[i] = secureRandom.nextInt(255);
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


    public static void main(String[] args) {
        Iris iris = new Iris();
        int [][] test = iris.toIntMatrix();
        for (int[] row:test
             ) {
            for (int j:row
                 ) {
                System.out.print(j + " ");
            }
            System.out.println();
        }
        System.out.println(iris.toString());
        String test2 = iris.toString();
        for (int j = 0; j < 10; j++) {
            for (int k = 0; k < 10; k++) {
                System.out.print((int)test2.charAt(j*10+k));
                System.out.print(" ");
            }
            System.out.println();
        }
    }
}
