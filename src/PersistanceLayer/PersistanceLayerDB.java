package PersistanceLayer;

import java.sql.*;

public enum PersistanceLayerDB implements IPersistanceLayer{
    instance;
    private final String userDirectory = System.getProperty("user.dir");
    private final String fileSeparator = System.getProperty("file.separator");
    private final String dataDirectory = userDirectory + fileSeparator + "data" + fileSeparator;
    private final String databaseFile = dataDirectory + "datastore.db";
    private Connection connection;
    private String driverName = "jdbc:hsqldb:";
    private String username = "sa";
    private String password = "";

//    public static void main(String... args) {
//        PersistanceLayerDB application = PersistanceLayerDB.instance;
//
//        application.setupConnection();
//        application.dropTableCustomers();
//        application.createTableCustomers();
//
//        application.insert(new Customer(1, "A"));
//        application.insert(new Customer(2, "C"));
//        application.insert(new Customer(3, "B"));
//        application.insert(new Customer(4, "D"));
//        application.insert(new Customer(5, "E"));
//
//        application.select();
//
//        application.shutdown();
//    }


    PersistanceLayerDB() {
        //if DB doesnt exist, create DB
        createTableEmployee();
        createTableIDCard();
        createTableEployee();
        createTableEployee();
    }

    public void setupConnection() {
        System.out.println("--- setupConnection");

        try {
            Class.forName("org.hsqldb.jdbcDriver");
            String databaseURL = driverName + databaseFile;
            connection = DriverManager.getConnection(databaseURL, username, password);
            System.out.println("connection : " + connection);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public synchronized void update(String sqlStatement) {
        try {
            Statement statement = connection.createStatement();
            int result = statement.executeUpdate(sqlStatement);

            if (result == -1) {
                System.out.println("error executing " + sqlStatement);
            }

            statement.close();
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
        }
    }

    public void dropTableCustomers() {
        System.out.println("--- dropTableCustomer");

        StringBuilder sqlStringBuilder = new StringBuilder();
        sqlStringBuilder.append("DROP TABLE customer");
        System.out.println("sqlStringBuilder : " + sqlStringBuilder.toString());

        update(sqlStringBuilder.toString());
    }

    public void createTableCustomers() {
        System.out.println("--- createTableCustomers");

        StringBuilder sqlStringBuilder = new StringBuilder();
        sqlStringBuilder.append("CREATE TABLE customer ( ");
        sqlStringBuilder.append("id INTEGER NOT NULL").append(",");
        sqlStringBuilder.append("name VARCHAR(256) NOT NULL").append(",");
        sqlStringBuilder.append("PRIMARY KEY (id)");
        sqlStringBuilder.append(" )");
        System.out.println("sqlStringBuilder : " + sqlStringBuilder.toString());

        update(sqlStringBuilder.toString());
    }

    public void insert(Customer customer) {
        StringBuilder sqlStringStringBuilder = new StringBuilder();
        sqlStringStringBuilder.append("INSERT INTO customer values (" + customer.getID() + ",'" + customer.getName() + "')");
        update(sqlStringStringBuilder.toString());
    }

    public void executeSQLStatement(String sqlStatement, int numberOfColumns) {
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

    public void select() {
        executeSQLStatement("SELECT * FROM customer order by name", 2);
        System.out.println();
    }

    public void shutdown() {
        System.out.println("--- shutdown");

        try {
            Statement statement = connection.createStatement();
            statement.execute("SHUTDOWN");
            connection.close();
            System.out.println("isClosed : " + connection.isClosed());
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
        }
    }
}