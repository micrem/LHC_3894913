package PersistanceLayer;

import Infrastructure.LHC.Block;
import Infrastructure.LHC.Experiment;

import java.util.List;

public interface IPersistanceLayer {
    void setupConnection();

    void createTables();

    void insert(Experiment experiment);

    void dropTableExperiments();

    void dropTableBlocks();

    void insert(Block block);

    Experiment getExperiment(int index);

    void shutdown();

    List<Experiment> getExperiments();
}
