package PersistanceLayer;

import Infrastructure.LHC.Block;
import Infrastructure.LHC.IExperiment;

import java.util.List;

public interface IPersistanceLayer {
    void setupConnection();

    void createTables();

    void insert(IExperiment experiment);

    void dropTableExperiments();

    void dropTableBlocks();

    void insert(Block block);

    IExperiment getExperiment(int index);

    void shutdown();

    List<IExperiment> getExperiments();
}
