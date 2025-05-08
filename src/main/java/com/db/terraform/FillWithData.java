package com.db.terraform;

import com.db.sqlite.SQLiteConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.Random;
import java.util.UUID;

public class FillWithData {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Usage: java FillWithData <amount>");
            return;
        }

        int amount;
        try {
            amount = Integer.parseInt(args[0]);
            if (amount <= 0) {
                System.err.println("Amount must be a positive integer.");
                return;
            }
        } catch (NumberFormatException e) {
            System.err.println("Invalid amount. Please provide an integer.");
            return;
        }

        SQLiteConnector connector = new SQLiteConnector();
        connector.connect("terraform_simulator.db");
        Connection conn = connector.getConnection();

        if (conn == null) {
            System.err.println("Failed to connect to the database.");
            return;
        }

        System.out.println("Reading existing data from the 'customer' table:");
        try (java.sql.Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM customer")) {
            
            // Print header
            int columnCount = rs.getMetaData().getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                System.out.print(rs.getMetaData().getColumnName(i) + "\t\t");
            }
            System.out.println();
            
            // Print data
            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    System.out.print(rs.getString(i) + "\t\t");
                }
                System.out.println();
            }
            System.out.println("Finished reading existing data.");

        } catch (SQLException e) {
            System.err.println("Error reading data: " + e.getMessage());
        }

        System.out.println("\nConnected to the database. Attempting to insert " + amount + " customers.");

 System.out.println("Connected to the database. Inserting a single test customer.");

        String sql = "INSERT INTO customer(customer_id, name, address, phone, email) VALUES(?,?,?,?,?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                 String customerId = UUID.randomUUID().toString();
                String name = "Customer_" + UUID.randomUUID().toString().substring(0, 8);
                String email = name.toLowerCase() + "@example.com";
                String address = "123 Test St";

                pstmt.setString(1, customerId); // customer_id as String
                pstmt.setString(2, name);
                pstmt.setString(3, address);
                pstmt.setString(4, "555-123-4567"); // Hardcoded phone number
                pstmt.setString(5, email);

                pstmt.executeUpdate();
            System.out.println("Successfully inserted a test customer.");

        } catch (SQLException e) {
            System.err.println("Error inserting data: " + e.getMessage());
        } finally {
            connector.closeConnection();
            System.out.println("Database connection closed.");
        }
    }
}