package com.ayush.workoutlogger.dao.impl;

import com.ayush.workoutlogger.connection.ConnectionFactory;
import com.ayush.workoutlogger.dao.*;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Chooses DB-backed DAO implementations if a database connection is available,
 * otherwise falls back to in-memory List implementations.
 */
public class DaoProvider {
    private static boolean dbAvailableChecked = false;
    private static boolean dbAvailable = false;

    private static UserDao userDao;
    private static ExerciseDao exerciseDao;
    private static WorkoutSessionDao workoutSessionDao;
    private static BodyMetricDao bodyMetricDao;

    public static synchronized boolean isDbAvailable() {
        if (!dbAvailableChecked) {
            try (Connection c = ConnectionFactory.getConnection()) {
                dbAvailable = (c != null);
            } catch (Exception ex) {
                dbAvailable = false;
            }
            dbAvailableChecked = true;
        }
        return dbAvailable;
    }

    public static synchronized UserDao getUserDao() {
        if (userDao == null) {
            if (isDbAvailable()) {
                userDao = new com.ayush.workoutlogger.dao.db.UserDaoDbImpl();
            } else {
                userDao = new UserDaoListImpl();
            }
        }
        return userDao;
    }

    public static synchronized ExerciseDao getExerciseDao() {
        if (exerciseDao == null) {
            if (isDbAvailable()) {
                exerciseDao = new com.ayush.workoutlogger.dao.db.ExerciseDaoDbImpl();
            } else {
                exerciseDao = new ExerciseDaoListImpl();
            }
        }
        return exerciseDao;
    }

    public static synchronized WorkoutSessionDao getWorkoutSessionDao() {
        if (workoutSessionDao == null) {
            if (isDbAvailable()) {
                workoutSessionDao = new com.ayush.workoutlogger.dao.db.WorkoutSessionDaoDbImpl();
            } else {
                workoutSessionDao = new WorkoutSessionDaoListImpl();
            }
        }
        return workoutSessionDao;
    }

    public static synchronized BodyMetricDao getBodyMetricDao() {
        if (bodyMetricDao == null) {
            if (isDbAvailable()) {
                bodyMetricDao = new com.ayush.workoutlogger.dao.db.BodyMetricDaoDbImpl();
            } else {
                bodyMetricDao = new BodyMetricDaoListImpl();
            }
        }
        return bodyMetricDao;
    }
}
