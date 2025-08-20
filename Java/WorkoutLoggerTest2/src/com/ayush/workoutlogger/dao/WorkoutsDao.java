package com.ayush.workoutlogger.dao;

import com.ayush.workoutlogger.model.Workouts;
import java.sql.SQLException;
import java.util.List;

public interface WorkoutsDao {
    void add(Workouts w) throws SQLException;
    List<Workouts> findAll() throws SQLException;
    // Optional convenience
    // List<Workouts> findBySession(int sessionId) throws SQLException;
}
