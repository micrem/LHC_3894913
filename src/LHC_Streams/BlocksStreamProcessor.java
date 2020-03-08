package LHC_Streams;

import Infrastructure.LHC.Block;
import Infrastructure.LHC.IExperiment;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BlocksStreamProcessor {
    static Map<Object, Boolean> staticDistinctMap = new ConcurrentHashMap<>();
    IExperiment experiment;
    Stream<Block> blockStream;

    public BlocksStreamProcessor(IExperiment experiment) {
        this.experiment = experiment;
        this.blockStream = Arrays.stream(experiment.getBlocks());
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
        staticDistinctMap = new ConcurrentHashMap<>();
        return t -> staticDistinctMap.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    public static void main(String[] args) {

    }

    //specificaion 01
    public int countWithHashtag() {
        staticDistinctMap = new ConcurrentHashMap<>();
        int countWithHashtag = blockStream.filter(b -> b.getStructure().contains("#"))
                //.distinct()
                .filter(distinctByKey(b -> b.getStructure()))
                .mapToInt(e -> 1).sum();
        return countWithHashtag;
    }

    //specification 02
    public List<Block> blocksWithZ() {
        staticDistinctMap = new ConcurrentHashMap<>();
        List<Block> list = blockStream.filter(b -> b.getStructure().endsWith("z"))
                //.distinct()
                .filter(distinctByKey(b -> b.getStructure()))
                .collect(Collectors.toList());
        return list;
    }

    //specification 03
    public List<Block> blocksWithA9Zordered() {
        staticDistinctMap = new ConcurrentHashMap<>();
        List<Block> list = blockStream
                .filter(b -> (b.getStructure().endsWith("z") && b.getStructure().startsWith("a") && b.getStructure().contains("9")))
                //.distinct()
                .filter(distinctByKey(b -> b.getStructure()))
                .sorted(Comparator.comparing(Block::getStructure))
                .collect(Collectors.toList());
        return list;
    }

    //specification 04
    public Map<Integer, List<Block>> containsAB() {
        staticDistinctMap = new ConcurrentHashMap<>();
        Function<Block, Integer> colAB = (Block b) -> b.getStructure().indexOf("ab");
        Predicate<Block> filterAB = (Block b) -> b.getStructure().contains("ab");
        Map<Integer, List<Block>> mapAB = blockStream.filter(filterAB)
                //.distinct()
                .filter(distinctByKey(b -> b.getStructure()))
                .collect(Collectors.groupingBy(colAB));
        return mapAB;
    }

    //specification 05
    public Map<Boolean, List<Block>> distinctPartitionABC() {
        staticDistinctMap = new ConcurrentHashMap<>();
        Predicate<Block> partABC = (Block b) -> Arrays.asList('a', 'b', 'c').contains(b.getStructure().charAt(0));

        Map<Boolean, List<Block>> mapABC = Arrays.stream(experiment.getBlocks())
                //.distinct()
                .filter(distinctByKey(b -> b.getStructure()))
                .collect(Collectors.partitioningBy(partABC));
        return mapABC;
    }

}
