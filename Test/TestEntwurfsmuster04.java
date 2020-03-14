import Infrastructure.LHC.Block;
import Infrastructure.LHC.Experiment;
import Infrastructure.LHC.IExperiment;
import LHC_Entwurfsmuster04.Compress;
import LHC_Entwurfsmuster04.Denoise;
import LHC_Entwurfsmuster04.ExperimentFileSave;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertTrue;


public class TestEntwurfsmuster04 {
    static IExperiment experiment = new Experiment();
    static Block[] blocks = {new Block(true), new Block(true), new Block(true), new Block(true)};
    static String structure0 = "bbb####aaa";
    static String structure1 = "abc0000aaa";
    static String structure2 = "c<c>c00###";
    static String structure3 = "c111111aaa";
    static List<String> expectedStructures = Arrays.asList("3b3a", "abc00003a", "3c00", "c1111113a");
    static IExperiment denoised;
    static IExperiment compressed;

    @BeforeAll
    public static void init() {
        blocks[0].setStructure(structure0);
        blocks[1].setStructure(structure1);
        blocks[2].setStructure(structure2);
        blocks[3].setStructure(structure3);
        experiment.setBlocks(blocks);
        denoised = new Denoise(experiment);
        compressed = new Compress(denoised);
        experiment.setProton01ID(2);
        experiment.setProton02ID(3);
    }

    @Test
    void testEntwurfsmuster() {
        assertTrue(Arrays.asList(compressed.getBlocks())
                .stream()
                .map(a -> a.getStructure())
                .collect(Collectors.toList())
                .containsAll(expectedStructures));
    }

    @Test
    void testEntwurfsmusterSave() {
        List<String> fileLines = new ArrayList<>();
        ExperimentFileSave.save(new Compress(new Denoise(experiment)));
        try (BufferedReader reader =
                     new BufferedReader(
                             new FileReader(
                                     new File(String.format("proton%02d_proton%02d.txt", experiment.getProton01ID(), experiment.getProton02ID()))
                             )
                     )
        ) {
            String line = reader.readLine();
            while (line != null) {
                fileLines.add(line);
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertTrue(fileLines.containsAll(expectedStructures));
    }
}
