package pts.database;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
    private static final String DB_URL = "jdbc:sqlite:pomona_transit.db";
    private static Connection connection = null;

    /**
     * Get or create database connection
     */
    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(DB_URL);
                // CRITICAL: Enable foreign key constraints for SQLite
                Statement stmt = connection.createStatement();
                stmt.execute("PRAGMA foreign_keys = ON;");
                stmt.close();
                System.out.println("Connected to SQLite database successfully!");
            }
        } catch (SQLException e) {
            System.err.println("Error connecting to database: " + e.getMessage());
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * Initialize database with schema and test data
     */
    public static void initializeDatabase() {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            // Read and execute schema.sql
            InputStream is = DatabaseConnection.class.getClassLoader()
                    .getResourceAsStream("schema.sql");

            if (is == null) {
                // If not in resources, try reading from project root
                java.io.File schemaFile = new java.io.File("schema.sql");
                if (schemaFile.exists()) {
                    is = new java.io.FileInputStream(schemaFile);
                } else {
                    System.err.println("schema.sql not found!");
                    return;
                }
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sql = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                // Skip comments and empty lines
                if (line.trim().startsWith("--") || line.trim().isEmpty()) {
                    continue;
                }
                sql.append(line).append("\n");
            }

            // Split by semicolon and execute each statement
            String[] statements = sql.toString().split(";");
            for (String statement : statements) {
                if (!statement.trim().isEmpty()) {
                    stmt.execute(statement.trim());
                }
            }

            System.out.println("Database initialized successfully!");
            reader.close();

        } catch (Exception e) {
            System.err.println("Error initializing database: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Close database connection
     */
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            System.err.println("Error closing database: " + e.getMessage());
        }
    }
}
