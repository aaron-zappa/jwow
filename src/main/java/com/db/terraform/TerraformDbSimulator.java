package com.db.terraform;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import com.db.terraform.data.Customer;
import com.db.sqlite.SQLiteConnector;
public class TerraformDbSimulator {

    private SQLiteConnector dbConnector;
    private String dbFilePath;

    public TerraformDbSimulator(String dbFilePath) {
        this.dbFilePath = dbFilePath;
        this.dbConnector = new SQLiteConnector();
    }

    public void connect() {
        dbConnector.connect(dbFilePath);
    }

    public void closeConnection() {
        dbConnector.closeConnection();
    }

    // Method to create tables
    public void createTables() {

        if (!dbConnector.isConnected()) { 
            System.err.println("Database connection is not open. Cannot create tables.");
            return;
        }
    
        try (Connection conn = dbConnector.getConnection();
             Statement stmt = conn.createStatement()) {

            // Simulate a simplified 'resources' table
            String createResourcesTable = "CREATE TABLE IF NOT EXISTS resources (\n" +
                    "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "    resource_type TEXT NOT NULL,\n" +
                    "    resource_name TEXT NOT NULL,\n" +
                    "    provider TEXT,\n" +
                    "    state TEXT,\n" +
                    "    UNIQUE (resource_type, resource_name)\n" +
                    ");";
            stmt.execute(createResourcesTable);
            System.out.println("Created 'resources' table.");

            // Simulate a simplified 'outputs' table
            String createOutputsTable = "CREATE TABLE IF NOT EXISTS outputs (\n" +
                    "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "    output_name TEXT NOT NULL,\n" +
                    "    value TEXT,\n" +
                    "    sensitive INTEGER,\n" + // 0 for false, 1 for true
                    "    UNIQUE (output_name)\n" +
                    ");";
            stmt.execute(createOutputsTable);
            System.out.println("Created 'outputs' table.");

            // Simulate a simplified 'state_lock' table
            String createStateLockTable = "CREATE TABLE IF NOT EXISTS state_lock (\n" +
                    "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "    lock_id TEXT NOT NULL UNIQUE,\n" +
                    "    info TEXT,\n" +
                    "    created_at DATETIME DEFAULT CURRENT_TIMESTAMP\n" +
                    ");";
            stmt.execute(createStateLockTable);
            System.out.println("Created 'state_lock' table.");

            // Create customer table
            String createCustomerTable = "CREATE TABLE IF NOT EXISTS customer (\n" +
                    "    customer_id INTEGER PRIMARY KEY,\n" +
                    "    name TEXT,\n" +
                    "    address TEXT,\n" +
                    "    phone TEXT,\n" +
                    "    email TEXT\n" +
                    ");";
            stmt.execute(createCustomerTable);
            System.out.println("Created 'customer' table.");

            // Create agent table
            String createAgentTable = "CREATE TABLE IF NOT EXISTS agent (\n" +
                    "    agent_id INTEGER PRIMARY KEY,\n" +
                    "    name TEXT,\n" +
                    "    branch TEXT\n" +
                    ");";
            stmt.execute(createAgentTable);
            System.out.println("Created 'agent' table.");

            // Create product table
            String createProductTable = "CREATE TABLE IF NOT EXISTS product (\n" +
                    "    product_id INTEGER PRIMARY KEY,\n" +
                    "    name TEXT,\n" +
                    "    type TEXT,\n" +
                    "    price REAL\n" +
                    ");";
            stmt.execute(createProductTable);
            System.out.println("Created 'product' table.");

            // Create contract table
            String createContractTable = "CREATE TABLE IF NOT EXISTS contract (\n" +
                    "    contract_id INTEGER PRIMARY KEY,\n" +
                    "    customer_id INTEGER,\n" +
                    "    agent_id INTEGER,\n" +
                    "    start_date TEXT,\n" +
                    "    end_date TEXT,\n" +
                    "    FOREIGN KEY (customer_id) REFERENCES customer(customer_id),\n" +
                    "    FOREIGN KEY (agent_id) REFERENCES agent(agent_id)\n" +
                    ");";
            stmt.execute(createContractTable);
            System.out.println("Created 'contract' table.");

            // Create order table
            String createOrderTable = "CREATE TABLE IF NOT EXISTS \"order\" (\n" +
                    "    order_id INTEGER PRIMARY KEY,\n" +
                    "    customer_id INTEGER,\n" +
                    "    order_date TEXT,\n" +
                    "    total_amount REAL,\n" +
                    "    FOREIGN KEY (customer_id) REFERENCES customer(customer_id)\n" +
                    ");";
            stmt.execute(createOrderTable);
            System.out.println("Created 'order' table.");

        } catch (SQLException e) {
            System.err.println("Error creating tables: " + e.getMessage());
        }
    }

    // Method to insert sample data into the customer table
    public void insertSampleCustomers() {
        if (!dbConnector.isConnected()) {
 System.err.println("Database connection is not open. Cannot insert sample customers.");
            return;
        }
    
        try (Connection conn = dbConnector.getConnection();
             Statement stmt = conn.createStatement()) {

            String[] names = {"Alice Smith", "Bob Johnson", "Charlie Brown", "David Davis", "Eve Williams",
                              "Frank Miller", "Grace Wilson", "Henry Moore", "Ivy Taylor", "Jack Anderson",
                              "Kelly Thomas", "Liam Jackson", "Mia White", "Noah Harris", "Olivia Martin",
                              "Peter Thompson", "Quinn Garcia", "Ryan Martinez", "Sophia Robinson", "Thomas Clark",
                              "Ursula Rodriguez", "Victor Lewis", "Wendy Lee", "Xander Walker", "Yara Hall",
                              "Zachary Allen", "Ava Young", "Benjamin Hernandez", "Chloe King", "Daniel Wright"};

            String[] addresses = {"123 Main St", "456 Oak Ave", "789 Pine Ln", "101 Maple Dr", "202 Elm Ct",
                                  "303 Birch Rd", "404 Cedar Blvd", "505 Spruce Way", "606 Fir Path", "707 Willow Cv",
                                  "808 Poplar Cir", "909 Redwood Sq", "111 Cherry Ln", "222 Apple Dr", "333 Berry Ct",
                                  "444 Grape Rd", "555 Melon Blvd", "666 Peach Way", "777 Plum Path", "888 Orange Cv",
                                  "999 Lemon Cir", "121 Lime Sq", "232 Mango Ln", "343 Pear Dr", "454 Fig Ct",
                                  "565 Date Rd", "676 Kiwi Blvd", "787 Plum Way", "898 Apricot Path", "919 Nectarine Cv"};

            for (int i = 0; i < 30; i++) {
                Customer customer = new Customer(i + 1, names[i], addresses[i],
                                                 String.format("555-01%02d", i),
                                                 names[i].toLowerCase().replace(" ", ".") + "@example.com");
                String insertSql = String.format("INSERT INTO customer (customer_id, name, address, phone, email) VALUES (%d, '%s', '%s', '%s', '%s');",
                                                 customer.getCustomer_id(), customer.getName(), customer.getAddress(), customer.getPhone(), customer.getEmail());
                stmt.executeUpdate(insertSql);
            }            
            System.out.println("Inserted 30 sample customers.");

        } catch (SQLException e) {
            System.err.println("Error inserting sample customers: " + e.getMessage());
        }
    }
    // Main method to run the simulation
    public static void main(String[] args) {
        String dbFile = "terraform_simulator.db"; // Specify your desired database file name
        TerraformDbSimulator simulator = new TerraformDbSimulator(dbFile);
    
 simulator.connect();
        if (simulator.dbConnector.isConnected()) {
            System.out.println("Connected to the database.");
            simulator.createTables();
            simulator.closeConnection();
            System.out.println("Database connection closed.");
        } else {
            System.err.println("Failed to connect to the database.");
        }
    }
}