package com.ayush.workoutlogger.dao.db;

import com.ayush.workoutlogger.dao.WorkoutSessionDao;
import com.ayush.workoutlogger.model.WorkoutSession;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import static com.ayush.workoutlogger.connection.ConnectionFactory.getConnection;

public class WorkoutSessionDaoDbImpl implements WorkoutSessionDao {

    @Override
    public WorkoutSession create(WorkoutSession s) {
        String sql = "INSERT INTO workout_sessions(user_id, session_date, notes) VALUES(?,?,?)";
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, s.getUserId());
            ps.setDate(2, Date.valueOf(s.getSessionDate()));
            ps.setString(3, s.getNotes());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) s.setId(rs.getInt(1));
            }
            return s;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void update(WorkoutSession s) {
        String sql = "UPDATE workout_sessions SET user_id=?, session_date=?, notes=? WHERE id=?";
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, s.getUserId());
            ps.setDate(2, Date.valueOf(s.getSessionDate()));
            ps.setString(3, s.getNotes());
            ps.setInt(4, s.getId());
            ps.executeUpdate();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM workout_sessions WHERE id=?";
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public WorkoutSession findById(int id) {
        String sql = "SELECT id, user_id, session_date, notes FROM workout_sessions WHERE id=?";
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    WorkoutSession s = new WorkoutSession();
                    s.setId(rs.getInt("id"));
                    s.setUserId(rs.getInt("user_id"));
                    s.setSessionDate(rs.getDate("session_date").toLocalDate());
                    s.setNotes(rs.getString("notes"));
                    return s;
                }
                return null;
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<WorkoutSession> findByUserAndDateRange(int userId, LocalDate from, LocalDate to) {
        StringBuilder sb = new StringBuilder("SELECT id, user_id, session_date, notes FROM workout_sessions WHERE user_id=?");
        if (from != null) sb.append(" AND session_date >= ?");
        if (to != null) sb.append(" AND session_date <= ?");
        sb.append(" ORDER BY session_date DESC");
        List<WorkoutSession> out = new ArrayList<>();
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sb.toString())) {
            int idx = 1;
            ps.setInt(idx++, userId);
            if (from != null) ps.setDate(idx++, Date.valueOf(from));
            if (to != null) ps.setDate(idx++, Date.valueOf(to));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    WorkoutSession s = new WorkoutSession();
                    s.setId(rs.getInt("id"));
                    s.setUserId(rs.getInt("user_id"));
                    s.setSessionDate(rs.getDate("session_date").toLocalDate());
                    s.setNotes(rs.getString("notes"));
                    out.add(s);
                }
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return out;
    }
}
