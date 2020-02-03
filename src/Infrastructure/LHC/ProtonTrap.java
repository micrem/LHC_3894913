package Infrastructure.LHC;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProtonTrap {

    private Ring ring;
    private Proton[] protons = new Proton[50];
    private ProtonTrapID id;
    int protonCounter=0;

    public ProtonTrap(ProtonTrapID id) {
        this.id = id;
    }

    public void loadData(String dataFilePath){
        List<String> list = new ArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(Paths.get(dataFilePath))) {

            //br returns as stream and convert it into a List
            list = br.lines().collect(Collectors.toList());

        } catch (IOException e) {
            e.printStackTrace();
        }
        int[][][] protonStruct= new int[100][100][100];
        for (int i = 0; i < 1000000; i++) {
            protonStruct[i/10000][(i/100)%100][i%10000]=list.iterator().next().charAt(i);
        }
        protons[protonCounter++].setStructure(protonStruct);

    }

    public void release(){
        //send proton to ring
    }

    public Proton getProton() {
        if(protonCounter<=0) return null;
        return protons[protonCounter--];
    }


    public Ring getRing() {
        return ring;
    }

    public void setRing(Ring ring) {
        this.ring = ring;
    }

    public static void main(String[] args) {

    }

}
