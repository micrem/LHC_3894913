package Infrastructure.LHC;

public class ProtonTrap {
    private Ring ring;
    private Proton[] protons = new Proton[50];
    private ProtonTrapID id;
    int protonCounter=0;
    public void loadData(String dataFilePath){}

    public void release(){}

    public Proton getProton() {
        if(protonCounter>=50) return null;
        return protons[protonCounter++];
    }
}
