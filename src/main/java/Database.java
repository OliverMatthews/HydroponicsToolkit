import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Tools for connecting to and managing the database.
 *
 * @since 0.1.0
 * @version 0.1.0
 */
public class Database {

    /**
     * Connection to the database.
     */
    private static Connection databaseConnection = null;
    /**
     * URL for accessing the database.
     */
    private static final String url = "jdbc:postgresql://localhost:5432/";
    /**
     * Username for accessing the database.
     */
    private static final String username = "hydroponics_toolkit";
    /**
     * Password for accessing the database.
     */
    private static final String password = "12345";
    /**
     * Logging tool used to record specific points in program execution.
     */
    private static final Logger log = Logger.getLogger(Database.class.getName());

    public static void main(String[] args) {
        try {
            prepareLogging();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + "\n" + e.getMessage());
            log.warning("Logger could not be setup, no logging will occur.");
        }

        try {
            setupDatabase(DatabaseType.PROD);
        } catch (SQLException | FileNotFoundException | ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + "\n" + e.getMessage());
            log.warning("Exception was thrown whilst setting up the database.");

            try {
                databaseConnection.close();
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
                System.err.println(sqlException.getClass().getName() + "\n" + sqlException.getMessage());
                log.warning("Database connection was not closed properly.");
            }
        }
    }

    private static void setupDatabase(DatabaseType databaseType) throws SQLException, ClassNotFoundException, FileNotFoundException {

        connectDatabase(DatabaseType.DEFAULT);
        runSqlFile(databaseType.getHeaderFilepath());
        databaseConnection.close();

        connectDatabase(databaseType);
        runSqlFile(databaseType.getDdlFilepath());
        databaseConnection.close();

        log.info("Database " + databaseType.getDatabaseName() + " was set up.");
    }

    public static void setDatabaseConnection(Connection databaseConnection) {
        Database.databaseConnection = databaseConnection;
    }

    /**
     * Prepares the logger to track points in program execution.
     *
     * @throws IOException Thrown if the log file could not be accessed/written to.
     * @since 0.1.0
     */
    public static void prepareLogging() throws IOException {
        log.setLevel(Level.INFO);
        FileHandler fh = new FileHandler("./.logs/logFile.log", true);

        log.addHandler(fh);
    }

    /**
     * Establishes a connection to a database.
     * @param databaseType {@link DatabaseType} indicating the type of the database to be connected to.
     * @throws SQLException Thrown if the SQL used to connect to the database was invalid.
     * @throws ClassNotFoundException Thrown if org.postgresql.Driver was inaccessible.
     * @since 0.1.0
     */
    public static void connectDatabase(DatabaseType databaseType) throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        setDatabaseConnection(DriverManager.getConnection(url + databaseType.getDatabaseName(), username, password));
    }

    /**
     * Inserts data to the database.
     * @param sqlStatement SQL statement to be carried out.
     * @return {@link ResultSet} of keys generated from the insert statement.
     * @throws SQLException Thrown if the given SQL statement was invalid.
     * @since 0.1.0
     */
    public static ResultSet executeInsert(String sqlStatement) throws SQLException {
        log.fine("Executing insert: " + sqlStatement);

        PreparedStatement preparedStatement = databaseConnection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.executeUpdate();

        return preparedStatement.getGeneratedKeys();
    }

    /**
     * Reads all statements from a specified SQL file and returns them in an ArrayList.
     * @param filepath Relative or absolute filepath to the SQL file.
     * @return {@link ArrayList} of SQL statements stored as Strings.
     * @throws FileNotFoundException Thrown if the specified SQL file could not be found.
     * @since 0.1.0
     */
    private static ArrayList<String> readSqlFromFile(String filepath) throws FileNotFoundException {
        log.finer("Reading in SQL statements from file (" + filepath + ")");

        ArrayList<String> statements = new ArrayList<>();
        Scanner read = new Scanner(new File(filepath));
        read.useDelimiter(";");

        while (read.hasNext()) {
            statements.add(read.next());
        }

        return statements;
    }

    /**
     * Runs all SQL statements in an SQL file.
     * @param filepath Relative or absolute filepath to the SQL file.
     * @throws FileNotFoundException Thrown if the specified SQL file could not be found.
     * @throws SQLException Thrown if a statement in the file was invalid.
     * @since 0.1.0
     */
    public static void runSqlFile(String filepath) throws FileNotFoundException, SQLException {
        ArrayList<String> statements = readSqlFromFile(filepath);

        for (String statement : statements) {
            executeUpdate(statement + ";");
        }
    }

    /**
     * Executes an update on the database.
     * @param sqlStatement SQL statement to be carried out.
     * @throws SQLException Thrown if the given SQL statement was invalid.
     * @since 0.1.0
     */
    public static void executeUpdate(String sqlStatement) throws SQLException {
        log.fine("Executing update: " + sqlStatement);

        PreparedStatement preparedStatement = databaseConnection.prepareStatement(sqlStatement);
        preparedStatement.executeUpdate();
    }

    /**
     * Executes a query on the database.
     * @param sqlStatement SQL statement to be carried out.
     * @return {@link ResultSet} of results from the query.
     * @throws SQLException Thrown if the given SQL statement was invalid.
     * @since 0.1.0
     */
    public static ResultSet executeQuery(String sqlStatement) throws SQLException {
        log.fine("Executing query: " + sqlStatement);

        PreparedStatement preparedStatement = databaseConnection.prepareStatement(sqlStatement);

        return preparedStatement.executeQuery();
    }

}
