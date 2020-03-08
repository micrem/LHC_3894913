import Infrastructure.LHC.Block;
import Infrastructure.LHC.IExperiment;
import Infrastructure.LHC.Experiment;
import LHC_Streams.BlocksStreamProcessor;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestStreams {


    @Test
    void testStreams01() { //countWithHashtag
        IExperiment e = new Experiment();
        Block[] blocks = {new Block(true), new Block(true), new Block(true)};
        String structure0 = "a8345678##"; //once
        String structure1 = "a834567890";
        String structure2 = "X43456789X";
        blocks[0].setStructure(structure0);
        blocks[1].setStructure(structure1);
        blocks[2].setStructure(structure2);
        e.setBlocks(blocks);
        int hashCount = (new BlocksStreamProcessor(e)).countWithHashtag();
        assertEquals(1, hashCount);
    }

    @Test
    void testStreams02() { //blocksWithZ
        IExperiment e = new Experiment();
        Block[] blocks = {new Block(true), new Block(true), new Block(true)};
        String structure0 = "a83456789z";//should appear only once
        String structure1 = "a83456789z";
        String structure2 = "X43456789X";//should not appear
        blocks[0].setStructure(structure0);
        blocks[1].setStructure(structure1);
        blocks[2].setStructure(structure2);
        e.setBlocks(blocks);
        List<Block> list = (new BlocksStreamProcessor(e)).blocksWithZ();

        assertEquals(1, list.size());
        assertEquals(list.get(0), blocks[0]);
        assertFalse(list.contains(blocks[1]));
        assertFalse(list.contains(blocks[2]));
    }

    @Test
    void testStreams03() { //blocksWithA9Zordered
        IExperiment e = new Experiment();
        Block[] blocks = {new Block(true), new Block(true), new Block(true), new Block(true)};
        String structure0 = "a83456789z";//should appear last
        String structure1 = "a23456789z";//should appear first
        String structure2 = "X43456789X";//should not appear
        String structure3 = "a23456789z";//duplicate
        blocks[0].setStructure(structure0);
        blocks[1].setStructure(structure1);
        blocks[2].setStructure(structure2);
        blocks[3].setStructure(structure3);
        e.setBlocks(blocks);
        List<Block> list = (new BlocksStreamProcessor(e)).blocksWithA9Zordered();

        assertEquals(2, list.size());
        assertEquals(list.get(0), blocks[1]);
        assertEquals(list.get(1), blocks[0]);
        assertFalse(list.contains(blocks[2]));
    }

    @Test
    void testStreams04() {
        IExperiment e = new Experiment();
        Block[] blocks = {new Block(true), new Block(true), new Block(true), new Block(true)};
        String structure0 = "ab00000000";//group0
        String structure1 = "00000000ab";//group8
        String structure2 = "00000000ab";//duplicate discarded
        String structure3 = "11111111ab";//group8
        blocks[0].setStructure(structure0);
        blocks[1].setStructure(structure1);
        blocks[2].setStructure(structure2);
        blocks[3].setStructure(structure3);
        e.setBlocks(blocks);
        Map<Integer, List<Block>> mapAB = (new BlocksStreamProcessor(e)).containsAB();

        assertEquals(2, mapAB.size());
        assertEquals( mapAB.get(0).get(0), blocks[0] );
        assertTrue( mapAB.get(8).containsAll(Arrays.asList(blocks[1],blocks[3])) );
    }

    @Test
    void testStreams05() {
        IExperiment e = new Experiment();
        Block[] blocks = {new Block(true), new Block(true), new Block(true), new Block(true)};
        String structure0 = "abc0000000";//true
        String structure1 = "abc0000000";//duplicate
        String structure2 = "00000000ab";//false
        String structure3 = "c1111111ab";//true
        blocks[0].setStructure(structure0);
        blocks[1].setStructure(structure1);
        blocks[2].setStructure(structure2);
        blocks[3].setStructure(structure3);
        e.setBlocks(blocks);
        Map<Boolean, List<Block>> mapABC = (new BlocksStreamProcessor(e)).distinctPartitionABC();

        assertEquals(2, mapABC.size());
        assertEquals( mapABC.get(false).get(0), blocks[2] );
        assertTrue( mapABC.get(true).containsAll(Arrays.asList(blocks[0],blocks[3])) );
    }
}
