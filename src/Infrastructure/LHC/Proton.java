package Infrastructure.LHC;

import java.util.Iterator;

public class Proton {
    private ProtonTrap protonTrap;
    private int[][][] structure = new int[100][100][100];
    private double weight=1;
    private int id=0;

    public ProtonTrap getProtonTrap() {
        return protonTrap;
    }

    public void setProtonTrap(ProtonTrap protonTrap) {
        this.protonTrap = protonTrap;
    }

    public Proton(int id) {
        this.id = id;
    }

    public int[][][] getStructure() {
        return structure;
    }

    public void setStructure(int[][][] structure) {
        this.structure = structure.clone();
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public Iterator<String> getBlockIterator(){
        return new blockIterator(this);
    }

    private class blockIterator implements Iterator<String>{
        Proton proton;
        int blockIteratorCounter=0;
        public blockIterator(Proton proton) {
            this.proton = proton;
        }

        @Override
        public boolean hasNext() {
            return blockIteratorCounter<200000;
        }

        @Override
        public String next() {
            StringBuilder strBuilder = new StringBuilder();
            int dim0, dim1, dim2, partOffset;
            for (int i = 0; i < 5; i++) {
                partOffset = 5 * blockIteratorCounter+i;
                dim0 = partOffset / 10000;
                dim1 = (partOffset / 100) % 100;
                dim2 = partOffset % 100;
                strBuilder.append((char) structure[dim0][dim1][dim2]);
            }
            blockIteratorCounter++;
            return strBuilder.toString();
        }
    }

    public static void main(String[] args) {
        testIteratorCode(199999);
    }

    private static void testIteratorCode(int index) {
        //StringBuilder strBuilder = new StringBuilder();
        int blockIteratorCounter = index;
        int dim0, dim1, dim2, partOffset;
        for (int i = 0; i < 5; i++) {
            partOffset = 5 * blockIteratorCounter+i;
            dim0 = partOffset / 10000;
            dim1 = (partOffset / 100) % 100;
            dim2 = partOffset % 100;
            //strBuilder.append((char) proton.structure[dim0][dim1][dim2]);
            System.out.println("X:"+dim0+" Y:"+dim1+" Z:"+dim2);
        }
    }
}
