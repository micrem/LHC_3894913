package Infrastructure.LHC;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ProtonTrap {

    private Ring ring;
    private Proton[] protons = new Proton[50];
    private ProtonTrapID id;
    int protonCounter=0;

    public ProtonTrap(ProtonTrapID id) {
        this.id = id;
    }

    public void loadData(String dataFilePath){
        int protonCharacter=0;
        int[][][] protonMatrix= new int[100][100][100];
        int m,n,o;
        try (BufferedReader br = Files.newBufferedReader(Paths.get(dataFilePath))) {
            for (int i = 0; i < 1000000; i++) {
                protonCharacter = br.read();
                m = i/10000;
                n = (i/100)%100;
                o = i%100;
                protonMatrix[m][n][o]=protonCharacter;
            }
            protons[protonCounter]=new Proton(protonCounter+1);
            protons[protonCounter].setStructure(protonMatrix);
            protonCounter++;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void release(){
    }

    public Proton getProton() {
        if(protonCounter<=0) return null;
        return protons[--protonCounter];
    }


    public Ring getRing(){
        return ring;
    }

    public void setRing(Ring ring) {
        this.ring = ring;
    }


}
