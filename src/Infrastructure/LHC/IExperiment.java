package Infrastructure.LHC;

public interface IExperiment {
    ExperimentScope getScope();

    void setScope(String scopeName);

    Block[] getBlocks();

    void setBlocks(Block[] blocks);

    String getUuid();

    void setUuid(String uuid);

    String getDateTimeStamp();

    void setDateTimeStamp(String dateTimeStamp);

    boolean isHiggsBosonFound();

    void setHiggsBosonFound(boolean higgsBosonFound);

    String getHiggsBlockID();

    void setHiggsBlockID(String higgsBlockID);

    int getProton01ID();

    void setProton01ID(int proton01ID);

    int getProton02ID();

    void setProton02ID(int proton02ID);
}
