package com.ayush.workoutlogger.dao.impl;

import com.ayush.workoutlogger.database.ConnectionFactory;
import java.sql.Connection;

public class DaoProvider {
    private static boolean dbAvailableChecked = false;
    private static boolean dbAvailable = false;

    public static boolean isDbAvailable() {
        if (!dbAvailableChecked) {
            Connection c = ConnectionFactory.getConnection();
            dbAvailable = (c != null);
            try { if (c != null) c.close(); } catch (Exception ignore) {}
            dbAvailableChecked = true;
        }
        return dbAvailable;
    }
}
