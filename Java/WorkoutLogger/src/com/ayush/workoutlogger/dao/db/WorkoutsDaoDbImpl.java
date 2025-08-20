package com.ayush.workoutlogger.dao.db;

import com.ayush.workoutlogger.dao.WorkoutsDao;
import com.ayush.workoutlogger.model.Exercise;
import com.ayush.workoutlogger.model.Workouts;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WorkoutsDaoDbImpl implements WorkoutSetDao {

    private Connection getConnection() throws SQLException {
        return DbConnection.getInstance().getConnection();
    }

    @Override
    public void add(Workouts set) throws SQLException {
        String sql = "INSERT INTO workout_sets (session_id, exercise_id, sets, reps, weight) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, set.getSessionId());
            stmt.setInt(2, set.getExercise().getId());
            stmt.setInt(3, set.getSets());
            stmt.setInt(4, set.getReps());
            stmt.setDouble(5, set.getWeight());
            stmt.executeUpdate();
        }
    }

    @Override
    public List<Workouts> findAll() throws SQLException {
        List<Workouts> list = new ArrayList<>();
        String sql = "SELECT ws.id, ws.session_id, ws.exercise_id, ws.sets, ws.reps, ws.weight, e.name " +
                     "FROM workout_sets ws JOIN exercises e ON ws.exercise_id = e.id";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Exercise ex = new Exercise();
                ex.setId(rs.getInt("exercise_id"));
                ex.setName(rs.getString("name"));

                Workouts set = new Workouts();
                set.setId(rs.getInt("id"));
                set.setSessionId(rs.getInt("session_id"));
                set.setExercise(ex);
                set.setSets(rs.getInt("sets"));
                set.setReps(rs.getInt("reps"));
                set.setWeight(rs.getDouble("weight"));

                list.add(set);
            }
        }
        return list;
    }
}
