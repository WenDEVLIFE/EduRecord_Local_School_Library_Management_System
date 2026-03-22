package com.mycompany.edurecord_local_school_library_management_system.repositories;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Utility class for managing MySQL database connections.
 * 
 * @author Antigravity
 */
public class DatabaseConnection {

    // Default connection parameters - Update these as needed
    private static final String URL = "jdbc:mysql://localhost:3306/edurecord_db";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    /**
     * Establishes a connection to the MySQL database.
     * 
     * @return Connection object
     * @throws SQLException if connection fails
     */
    public static Connection getConnection() throws SQLException {
        try {
            // Explicitly load the driver (good practice in some environments)
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL Driver not found", e);
        }
    }

    /**
     * Utility method to close connection safely.
     * 
     * @param connection the connection to close
     */
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }
    }
}
