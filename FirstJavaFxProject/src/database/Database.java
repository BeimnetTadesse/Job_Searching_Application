package database;

import java.sql.Connection;
import java.sql.DriverManager;

public class Database {
    private static final String DB_URL = "jdbc:sqlite:jobSearch.db";

    public static Connection connect() {
        try {
            Class.forName("org.sqlite.JDBC");
            return DriverManager.getConnection(DB_URL);
        } catch (Exception e) {
            System.out.println("Database connection error: " + e.getMessage());
            return null;
        }
    }
}
