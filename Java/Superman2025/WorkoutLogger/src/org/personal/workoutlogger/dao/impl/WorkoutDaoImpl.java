package org.personal.workoutlogger.dao.impl;

import org.personal.workoutlogger.dao.WorkoutDao;
import org.personal.workoutlogger.model.Workout;
import org.personal.workoutlogger.model.WorkoutItem;
import org.personal.workoutlogger.connection.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WorkoutDaoImpl implements WorkoutDao {

    @Override
    public int createWorkout(Workout w) {
        String sql = "INSERT INTO workouts(user_id,date,notes) VALUES(?,?,?)";
        try (Connection c = ConnectionFactory.getConnection(); PreparedStatement p = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            p.setInt(1, w.getUserId());
            p.setDate(2, Date.valueOf(w.getDate()));
            p.setString(3, w.getNotes());
            p.executeUpdate();
            try (ResultSet g = p.getGeneratedKeys()) {
                if (g.next()) {
                    return g.getInt(1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public void addWorkoutItem(WorkoutItem i) {
        String sql = "INSERT INTO workout_items(workout_id,exercise_id,sets,reps,weight_used,rest_time) VALUES(?,?,?,?,?,?)";
        try (Connection c = ConnectionFactory.getConnection(); PreparedStatement p = c.prepareStatement(sql)) {
            p.setInt(1, i.getWorkoutId());
            p.setInt(2, i.getExerciseId());
            p.setInt(3, i.getSets());
            p.setInt(4, i.getReps());
            p.setDouble(5, i.getWeightUsed());
            p.setInt(6, i.getRestTime());
            p.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Workout> getWorkoutsByUser(int uid) {
        String sql = "SELECT * FROM workouts WHERE user_id=? ORDER BY date DESC";
        List<Workout> list = new ArrayList<>();
        try (Connection c = ConnectionFactory.getConnection(); PreparedStatement p = c.prepareStatement(sql)) {
            p.setInt(1, uid);
            try (ResultSet r = p.executeQuery()) {
                while (r.next()) {
                    list.add(new Workout(
                            r.getInt("id"),
                            r.getInt("user_id"),
                            r.getDate("date").toLocalDate(),
                            r.getString("notes")
                    ));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<WorkoutItem> getItemsByWorkout(int wid) {
        String sql = "SELECT * FROM workout_items WHERE workout_id=?";
        List<WorkoutItem> list = new ArrayList<>();
        try (Connection c = ConnectionFactory.getConnection(); PreparedStatement p = c.prepareStatement(sql)) {
            p.setInt(1, wid);
            try (ResultSet r = p.executeQuery()) {
                while (r.next()) {
                    list.add(new WorkoutItem(
                            r.getInt("id"),
                            r.getInt("workout_id"),
                            r.getInt("exercise_id"),
                            r.getInt("sets"),
                            r.getInt("reps"),
                            r.getDouble("weight_used"),
                            r.getInt("rest_time")
                    ));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void updateWorkout(Workout w, boolean isTrainer) {
        String sql = "UPDATE workouts SET date=?, notes=? WHERE id=? AND user_id=?";
        try (Connection c = ConnectionFactory.getConnection(); PreparedStatement p = c.prepareStatement(sql)) {
            p.setDate(1, Date.valueOf(w.getDate()));
            p.setString(2, w.getNotes());
            p.setInt(3, w.getId());
            p.setInt(4, w.getUserId());
            p.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteWorkout(int wid, int requesterUserId, boolean isTrainer) {
        String ownerSql = "SELECT user_id FROM workouts WHERE id=?";
        try (Connection c = ConnectionFactory.getConnection()) {
            int owner = -1;
            try (PreparedStatement p = c.prepareStatement(ownerSql)) {
                p.setInt(1, wid);
                try (ResultSet r = p.executeQuery()) {
                    if (r.next()) {
                        owner = r.getInt(1);
                    }
                }
            }
            if (owner == -1) {
                return;
            }
            if (!isTrainer && owner != requesterUserId) {
                return;
            }

            try (PreparedStatement p1 = c.prepareStatement("DELETE FROM workout_items WHERE workout_id=?"); PreparedStatement p2 = c.prepareStatement("DELETE FROM workouts WHERE id=?")) {
                p1.setInt(1, wid);
                p1.executeUpdate();
                p2.setInt(1, wid);
                p2.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int createWorkoutWithItems(Workout w, java.util.List<WorkoutItem> items) {
        String sql = "INSERT INTO workouts(user_id,date,notes) VALUES(?,?,?)";
        String itemSql = "INSERT INTO workout_items(workout_id,exercise_id,sets,reps,weight_used,rest_time) VALUES(?,?,?,?,?,?)";
        try (Connection c = ConnectionFactory.getConnection()) {
            try {
                c.setAutoCommit(false);
                try (PreparedStatement p = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                    p.setInt(1, w.getUserId());
                    p.setDate(2, java.sql.Date.valueOf(w.getDate()));
                    p.setString(3, w.getNotes());
                    p.executeUpdate();
                    int wid = -1;
                    try (ResultSet g = p.getGeneratedKeys()) {
                        if (g.next()) {
                            wid = g.getInt(1);
                        }
                    }
                    if (wid > 0 && items != null && !items.isEmpty()) {
                        try (PreparedStatement pi = c.prepareStatement(itemSql)) {
                            for (WorkoutItem it : items) {
                                pi.setInt(1, wid);
                                pi.setInt(2, it.getExerciseId());
                                pi.setInt(3, it.getSets());
                                pi.setInt(4, it.getReps());
                                pi.setDouble(5, it.getWeightUsed());
                                pi.setInt(6, it.getRestTime());
                                pi.addBatch();
                            }
                            pi.executeBatch();
                        }
                    }
                    c.commit();
                    return wid;
                }
            } catch (Exception ex) {
                try {
                    c.rollback();
                } catch (Exception r) {
                    r.printStackTrace();
                }
                ex.printStackTrace();
            } finally {
                try {
                    c.setAutoCommit(true);
                } catch (Exception e) {
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

}
