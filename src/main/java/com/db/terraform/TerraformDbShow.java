package com.db.terraform;

import com.db.sqlite.SQLiteConnector;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class TerraformDbShow {

    private static final String DB_FILE_PATH = "terraform_simulator.db";
    private static final String OUTPUT_FILE_PATH = "terraform_data_export.txt";

    public static void main(String[] args) {
        SQLiteConnector dbConnector = new SQLiteConnector();
        PrintWriter writer = null;

        try {
            dbConnector.connect(DB_FILE_PATH);

            if (dbConnector.isConnected()) {
                System.out.println("Connected to the database. Exporting data...");

                writer = new PrintWriter(new FileWriter(OUTPUT_FILE_PATH));

                String[] tables = {"resources", "outputs", "state_lock", "customer", "agent", "product", "contract", "\"order\""};

                try (Connection conn = dbConnector.getConnection();
                     Statement stmt = conn.createStatement()) {

                    for (String table : tables) {
                        writer.println("--- Data from table: " + table + " ---");
                        String sql = "SELECT * FROM " + table;
                        ResultSet rs = stmt.executeQuery(sql);
                        printResultSet(rs, writer);
                        writer.println(); // Add a blank line between tables
                    }

                } catch (SQLException e) {
                    System.err.println("Error reading data from database: " + e.getMessage());
                }

                System.out.println("Data exported to " + OUTPUT_FILE_PATH);

            } else {
                System.err.println("Failed to connect to the database. Cannot export data.");
            }
        } catch (IOException e) {
            System.err.println("Error writing to output file: " + e.getMessage());
        } finally {
            if (writer != null) {
                writer.close();
            }
            dbConnector.closeConnection();
            System.out.println("Database connection closed.");
        }
    }

    private static void printResultSet(ResultSet rs, PrintWriter writer) throws SQLException {
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnCount = rsmd.getColumnCount();

        // Print header
        for (int i = 1; i <= columnCount; i++) {
            writer.printf("%-20s", rsmd.getColumnName(i));
        }
        writer.println();
        for (int i = 1; i <= columnCount; i++) {
            writer.printf("%-20s", "--------------------");
        }
        writer.println();

        // Print rows
        while (rs.next()) {
            for (int i = 1; i <= columnCount; i++) {
                writer.printf("%-20s", rs.getString(i));
            }
            writer.println();
        }
    }
}