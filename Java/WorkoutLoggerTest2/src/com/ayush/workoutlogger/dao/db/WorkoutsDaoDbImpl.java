package com.ayush.workoutlogger.dao.db;

import com.ayush.workoutlogger.dao.WorkoutsDao;
import com.ayush.workoutlogger.model.Exercise;
import com.ayush.workoutlogger.model.Workouts;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.ayush.workoutlogger.connection.ConnectionFactory.getConnection;

public class WorkoutsDaoDbImpl implements WorkoutsDao {

    @Override
    public void add(Workouts w) throws SQLException {
        String sql = "INSERT INTO workout_sets (session_id, exercise_id, sets, reps, weight) VALUES (?, ?, ?, ?, ?)";
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, w.getSessionId());
            ps.setInt(2, w.getExercise().getId());
            ps.setInt(3, w.getSets());
            ps.setInt(4, w.getReps());
            ps.setDouble(5, w.getWeight());
            ps.executeUpdate();
        } catch (Exception ex) {
            throw new SQLException("Failed to insert workout set", ex);
        }
    }

    @Override
    public List<Workouts> findAll() throws SQLException {
        List<Workouts> list = new ArrayList<>();
        String sql = "SELECT ws.id, ws.session_id, ws.exercise_id, ws.sets, ws.reps, ws.weight, e.name " +
                     "FROM workout_sets ws JOIN exercises e ON ws.exercise_id = e.id " +
                     "ORDER BY ws.id DESC";
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Exercise ex = new Exercise();
                ex.setId(rs.getInt("exercise_id"));
                ex.setName(rs.getString("name"));

                Workouts w = new Workouts();
                w.setId(rs.getInt("id"));
                w.setSessionId(rs.getInt("session_id"));
                w.setExercise(ex);
                w.setSets(rs.getInt("sets"));
                w.setReps(rs.getInt("reps"));
                w.setWeight(rs.getDouble("weight"));
                list.add(w);
            }
        } catch (Exception ex) {
            throw new SQLException("Failed to fetch workout sets", ex);
        }
        return list;
    }
}
