package com.db.sqlite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class CreatorForSQLiteDatabaseForTDM {

    /**
     * Creates an empty SQLite database file at the specified path.
     *
     * @param dbFilePath The path where the database file should be created.
     */
    public void createDatabase(String dbFilePath) {
        Connection conn = null;
        try {
            // Load the SQLite JDBC driver
            Class.forName("org.sqlite.JDBC");

            // Create a connection to the database. If the file doesn't exist, it will be created.
            conn = DriverManager.getConnection("jdbc:sqlite:" + dbFilePath);

            System.out.println("Database created successfully at " + dbFilePath);

        } catch (ClassNotFoundException e) {
            System.err.println("SQLite JDBC driver not found: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Error creating the database: " + e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.err.println("Error closing the database connection: " + ex.getMessage());
            }
        }
    }
}