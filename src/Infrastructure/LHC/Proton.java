package Infrastructure.LHC;

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

    public Proton() {
        id=999;
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
}
