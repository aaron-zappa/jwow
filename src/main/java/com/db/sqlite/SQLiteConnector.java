package com.db.sqlite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class SQLiteConnector {

    private Connection connection;

    /**
     * Connects to a SQLite database file.
     *
     * @param dbFilePath The path to the SQLite database file.
     */
    public void connect(String dbFilePath) {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + dbFilePath);
            System.out.println("Connected to the database successfully.");
        } catch (ClassNotFoundException e) {
            System.err.println("SQLite JDBC driver not found: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Error connecting to the database: " + e.getMessage());
        }
    }

    public boolean isConnected() {
        return connection != null;
    }

    public Connection getConnection() {
        return connection;
    }

    /**
     * Executes an SQL query.
     *
     * @param sql The SQL query to execute.
     * @return A ResultSet containing the results of the query, or null if an error occurred or the query was not a SELECT statement.
     */
    public ResultSet executeQuery(String sql) {
        if (connection == null) {
            System.err.println("No database connection established.");
            return null;
        }
        try {
            Statement statement = connection.createStatement();
            if (sql.trim().toUpperCase().startsWith("SELECT")) {
                return statement.executeQuery(sql);
            } else {
                statement.executeUpdate(sql);
                System.out.println("SQL executed successfully.");
                return null;
            }
        } catch (SQLException e) {
            System.err.println("Error executing SQL query: " + e.getMessage());
            return null;
        }
    }

    /**
     * Closes the database connection.
     */
    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Database connection closed.");
            } catch (SQLException e) {
                System.err.println("Error closing the database connection: " + e.getMessage());
            }
        }
    }
}