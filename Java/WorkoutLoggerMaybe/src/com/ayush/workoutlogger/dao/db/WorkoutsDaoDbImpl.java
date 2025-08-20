package com.ayush.workoutlogger.dao.db;

import com.ayush.workoutlogger.connection.ConnectionFactory;
import com.ayush.workoutlogger.dao.WorkoutsDao;
import com.ayush.workoutlogger.model.Exercise;
import com.ayush.workoutlogger.model.Workouts;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WorkoutsDaoDbImpl implements WorkoutsDao {

    @Override
    public void add(Workouts w) throws SQLException {
        final String sql = "INSERT INTO workouts (session_id, exercise_id, sets, reps, weight) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, w.getSessionId());
            ps.setInt(2, w.getExercise().getId());
            ps.setInt(3, w.getSets());
            ps.setInt(4, w.getReps());
            if (w.getWeight() != null) {
                ps.setDouble(5, w.getWeight());
            } else {
                ps.setNull(5, Types.DOUBLE);
            }
            ps.executeUpdate();
        } catch (ClassNotFoundException ex) {
            System.getLogger(WorkoutsDaoDbImpl.class.getName())
                  .log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }

    @Override
    public List<Workouts> findAll() throws SQLException {
        final String sql =
            "SELECT w.id, w.session_id, w.exercise_id, w.sets, w.reps, w.weight, " +
            "       e.name AS exercise_name, e.muscle_group " +
            "FROM workouts w " +
            "JOIN exercises e ON e.id = w.exercise_id " +
            "ORDER BY w.id DESC";

        List<Workouts> list = new ArrayList<>();
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Exercise ex = new Exercise();
                ex.setId(rs.getInt("exercise_id"));
                ex.setName(rs.getString("exercise_name"));
                ex.setMuscleGroup(rs.getString("muscle_group"));

                Workouts w = new Workouts();
                w.setId(rs.getInt("id"));
                w.setSessionId(rs.getInt("session_id"));
                w.setExercise(ex);
                w.setSets(rs.getInt("sets"));
                w.setReps(rs.getInt("reps"));
                double weight = rs.getDouble("weight");
                w.setWeight(rs.wasNull() ? null : weight);

                list.add(w);
            }
        } catch (ClassNotFoundException ex) {
            System.getLogger(WorkoutsDaoDbImpl.class.getName())
                  .log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return list;
    }

    @Override
    public void delete(int id) throws SQLException, ClassNotFoundException {
        final String sql = "DELETE FROM workouts WHERE id = ?";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }   // ✅ properly close delete()

    @Override
    public List<Workouts> findByUser(int userId) throws SQLException, ClassNotFoundException {
        final String sql =
            "SELECT w.id, w.session_id, w.exercise_id, w.sets, w.reps, w.weight, " +
            "       e.name AS exercise_name, e.muscle_group " +
            "FROM workouts w " +
            "JOIN sessions s ON s.id = w.session_id " +
            "JOIN exercises e ON e.id = w.exercise_id " +
            "WHERE s.user_id = ? " +
            "ORDER BY w.id DESC";

        List<Workouts> list = new ArrayList<>();
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Exercise ex = new Exercise();
                    ex.setId(rs.getInt("exercise_id"));
                    ex.setName(rs.getString("exercise_name"));
                    ex.setMuscleGroup(rs.getString("muscle_group"));

                    Workouts w = new Workouts();
                    w.setId(rs.getInt("id"));
                    w.setSessionId(rs.getInt("session_id"));
                    w.setExercise(ex);
                    w.setSets(rs.getInt("sets"));
                    w.setReps(rs.getInt("reps"));
                    double weight = rs.getDouble("weight");
                    w.setWeight(rs.wasNull() ? null : weight);

                    list.add(w);
                }
            }
        }
        return list;
    }
}
