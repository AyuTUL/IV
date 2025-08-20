package com.ayush.workoutlogger.dao;

import com.ayush.workoutlogger.model.Workouts;
import java.sql.SQLException;
import java.util.List;

public interface WorkoutsDao {
    void add(Workouts w) throws SQLException, ClassNotFoundException;
    List<Workouts> findAll() throws SQLException, ClassNotFoundException;
    void delete(int id) throws SQLException, ClassNotFoundException;
    List<Workouts> findByUser(int userId) throws SQLException, ClassNotFoundException;
}
