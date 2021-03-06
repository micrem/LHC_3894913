package PersistanceLayer;

import Infrastructure.LHC.Block;
import Infrastructure.LHC.Experiment;
import Infrastructure.LHC.IExperiment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public enum PersistanceLayerDB implements IPersistanceLayer {
    instance;
    private final String userDirectory = System.getProperty("user.dir");
    private final String fileSeparator = System.getProperty("file.separator");
    private final String dataDirectory = userDirectory + fileSeparator + "data" + fileSeparator;
    private final String databaseFile = dataDirectory + "ExperimentDatabase.db";
    private Connection connection;
    private String driverName = "jdbc:hsqldb:";
    private String username = "sa";
    private String password = "";

    public static void main(String... args) {
        PersistanceLayerDB db = PersistanceLayerDB.instance;
        db.setupConnection();
        db.shutdown();
    }

    @Override
    public void setupConnection() {
        try {
            Class.forName("org.hsqldb.jdbcDriver");
            String databaseURL = driverName + databaseFile;
            connection = DriverManager.getConnection(databaseURL, username, password);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void createTables() {
        //attempt to create database in case it doesn't exist yet
        dropTableExperiments();
        dropTableBlocks();
        createTableExperiments();
        createTableBlocks();
    }


    private void createTableExperiments() {

        // System.out.println("sqlStringBuilder : " + sqlStringBuilder.toString());
        String sqlStringBuilder = "CREATE TABLE Experiments ( " +
                "uuid VARCHAR(256) NOT NULL" + "," +
                "dateTimeStamp VARCHAR(256) NOT NULL" + "," +
                "isHiggsBosonFound BOOLEAN NOT NULL" + "," +
                "higgsBlockID VARCHAR(256)" + "," +
                "scope VARCHAR(256) NOT NULL" + "," +
                "proton01ID INTEGER DEFAULT 0" + "," +
                "proton02ID INTEGER DEFAULT 0" + "," +
                "PRIMARY KEY (uuid)" +
                " )";
        update(sqlStringBuilder);
    }

    @Override
    public void insert(IExperiment experiment) {
        StringBuilder sqlStringStringBuilder = new StringBuilder();
        sqlStringStringBuilder.append("INSERT INTO Experiments values ('" +
                experiment.getUuid() + "','" +
                experiment.getDateTimeStamp() + "'," +
                experiment.isHiggsBosonFound() + ",'" +
                experiment.getHiggsBlockID() + "','" +
                experiment.getScope() + "'," +
                experiment.getProton01ID() + "," +
                experiment.getProton02ID() +
                ")");
        update(sqlStringStringBuilder.toString());
    }

    @Override
    public IExperiment getExperiment(int index) {
        try {
            String sqlStatement = "SELECT * from Experiments";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlStatement);

            if (!resultSet.absolute(index)) return null; //attempt to move to resultLine given by index
            IExperiment retrievedExperiment = getExperimentFromResult(resultSet);

            resultSet.close();
            return retrievedExperiment;
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
        }
        return null;
    }

    private IExperiment getExperimentFromResult(ResultSet resultSet) throws SQLException {
        IExperiment retrievedExperiment = new Experiment();
        retrievedExperiment.setUuid(resultSet.getString("uuid"));
        retrievedExperiment.setDateTimeStamp(resultSet.getString("dateTimeStamp"));
        retrievedExperiment.setHiggsBosonFound(resultSet.getBoolean("isHiggsBosonFound"));
        retrievedExperiment.setHiggsBlockID(resultSet.getString("higgsBlockID"));
        retrievedExperiment.setProton01ID(resultSet.getInt("proton01ID"));
        retrievedExperiment.setProton02ID(resultSet.getInt("proton02ID"));
        retrievedExperiment.setScope(resultSet.getString("scope"));
        return retrievedExperiment;
    }

    @Override
    public void dropTableExperiments() {
        StringBuilder sqlStringBuilder = new StringBuilder();
        sqlStringBuilder.append("DROP TABLE Experiments");
        // System.out.println("sqlStringBuilder : " + sqlStringBuilder.toString());
        update(sqlStringBuilder.toString());
    }

    @Override
    public void dropTableBlocks() {
        StringBuilder sqlStringBuilder = new StringBuilder();
        sqlStringBuilder.append("DROP TABLE Blocks");
        // System.out.println("sqlStringBuilder : " + sqlStringBuilder.toString());
        update(sqlStringBuilder.toString());
    }

    private void createTableBlocks() {

        StringBuilder sqlStringBuilder = new StringBuilder();
        sqlStringBuilder.append("CREATE TABLE Blocks ( ");
        sqlStringBuilder.append("uuid VARCHAR(256) NOT NULL").append(",");
        sqlStringBuilder.append("structure VARCHAR(256) NOT NULL").append(",");
        sqlStringBuilder.append("IExperiment VARCHAR(256) NOT NULL").append(",");
        sqlStringBuilder.append("PRIMARY KEY (uuid)");
        sqlStringBuilder.append(" )");
        // System.out.println("sqlStringBuilder : " + sqlStringBuilder.toString());

        update(sqlStringBuilder.toString());
    }

    @Override
    public void insert(Block block) {
        if (block == null) {
            return;
        }
        StringBuilder sqlStringStringBuilder = new StringBuilder();
        sqlStringStringBuilder.append("INSERT INTO blocks values ('" +
                block.getUuid() + "','" +
                block.getStructure() + "','" +
                block.getExperimentUUID() +
                "')");
        update(sqlStringStringBuilder.toString());
    }

    private synchronized void update(String sqlStatement) {
        try {
            Statement statement = connection.createStatement();
            int result = statement.executeUpdate(sqlStatement);

            if (result == -1) {
                System.out.println("error executing " + sqlStatement);
            }
            statement.close();
        } catch (SQLException sqle) {
            System.out.println("SQL error: " + sqle.getMessage());
        }
    }

    private void executeSQLStatement(String sqlStatement, int numberOfColumns) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlStatement);

            while (resultSet.next()) {
                if (numberOfColumns == 1) {
                    System.out.println(resultSet.getString(1));
                } else if (numberOfColumns == 2) {
                    System.out.println(resultSet.getString(1) + " - " + resultSet.getString(2));
                }
            }

            resultSet.close();
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
        }
    }

    @Override
    public void shutdown() {
        // System.out.println("--- shutdown");
        try {
            Statement statement = connection.createStatement();
            statement.execute("SHUTDOWN");
            connection.close();
            // System.out.println("isClosed : " + connection.isClosed());
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
        }
    }

    @Override
    public List<IExperiment> getExperiments() {
        List<IExperiment> retrievedExperiments = new ArrayList<>();
        try {
            String sqlStatement = "SELECT * from Experiments ORDER BY proton01ID";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlStatement);
            while (resultSet.next()) {
                IExperiment retrievedExperiment = getExperimentFromResult(resultSet);
                if (retrievedExperiment != null) {
                    retrievedExperiments.add(retrievedExperiment);
                }
            }
            resultSet.close();
            for (IExperiment exp : retrievedExperiments) {
                Block[] blocks = getBlocksForExperiment(exp);
                exp.setBlocks(blocks);
            }
            return retrievedExperiments;
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
        }
        return retrievedExperiments;
    }

    private Block[] getBlocksForExperiment(IExperiment experiment) {
        Block[] blocks = new Block[200000];
        int index = 0;
        try {
            String expUUID = experiment.getUuid();
            String sqlStatement = "SELECT * from BLOCKS where BLOCKS.EXPERIMENT='" + expUUID + "'";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlStatement);
            while (resultSet.next()) {
                Block block = new Block(false);
                block.setExperimentUUID(expUUID);
                block.setStructure(resultSet.getString("Structure"));
                block.setUuid(resultSet.getString("UUID"));
                blocks[index++] = block;
            }
            if (index < 200000)
                System.out.println("  Only loaded " + index + " of 200000 blocks for experiment " + expUUID + "!");
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
        }
        return blocks;
    }
}