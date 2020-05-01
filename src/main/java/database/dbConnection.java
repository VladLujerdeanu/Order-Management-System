package database;

import java.sql.*;
import java.util.logging.Logger;

/**
 * The class which deals with the connection to the database and closing the database.
 */

public class dbConnection {
    private static final Logger LOGGER = Logger.getLogger(dbConnection.class.getName());
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DBURL = "jdbc:mysql://localhost:3306/order_management";
    private static final String USER = "root";
    private static final String PASS = "root";

    private static dbConnection singleInstance = new dbConnection();

    private dbConnection() {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method creates the connection to the database.
     * @return the connection to the database
     */
    private Connection createConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DBURL, USER, PASS);
        } catch (SQLException e) {
            System.out.println("Error while trying to connect to database");
            e.printStackTrace();
        }
        return conn;
    }


    /**
     * This method returns the connection to the database.
     * @return the connection to the database
     */
    public static Connection getConnection() {
        return singleInstance.createConnection();
    }

    /**
     * This method closes the connection given as parameter
     * @param conn the connection to be closed
     */
    public static void close(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.out.println("Error while closing the connection");
                e.printStackTrace();
            }
        }
    }

    /**
     * This method closes the statement given as parameter
     * @param statement the statement to be closed
     */
    public static void close(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                System.out.println("Error while closing the statement");
                e.printStackTrace();
            }
        }
    }

    /**
     * This method closes the ResultSet given as parameter
     * @param resultSet the ResultSet to be closed
     */
    public static void close(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                System.out.println("Error while closing the resultSet");
                e.printStackTrace();
            }
        }
    }
}
