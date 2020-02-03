package Infrastructure.LHC;

public class Proton {
    private ProtonTrap protonTrap;
    private int[][][] structure = new int[100][100][100];
    private double weight=1;

    public ProtonTrap getProtonTrap() {
        return protonTrap;
    }

    public void setProtonTrap(ProtonTrap protonTrap) {
        this.protonTrap = protonTrap;
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
}
