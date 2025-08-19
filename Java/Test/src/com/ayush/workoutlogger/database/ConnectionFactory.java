package com.ayush.workoutlogger.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    private static final String HOST = "localhost";
    private static final String PORT = "3306";
    private static final String DB   = "workout_db";
    private static final String USER = "root";
    private static final String PASS = ""; // no password

    public static Connection getConnection() {
        try {
            // MySQL 8+ driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DB + "?useSSL=false&serverTimezone=UTC";
            return DriverManager.getConnection(url, USER, PASS);
        } catch (ClassNotFoundException e) {
            System.err.println("[INFO] MySQL driver not found. Running in in-memory mode if available.");
            return null;
        } catch (SQLException e) {
            System.err.println("[INFO] Could not connect to database: " + e.getMessage());
            return null;
        }
    }
}
