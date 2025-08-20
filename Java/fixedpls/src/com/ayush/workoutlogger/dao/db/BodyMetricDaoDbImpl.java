package com.ayush.workoutlogger.dao.db;

import com.ayush.workoutlogger.dao.BodyMetricDao;
import com.ayush.workoutlogger.model.BodyMetric;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import static com.ayush.workoutlogger.connection.ConnectionFactory.getConnection;

public class BodyMetricDaoDbImpl implements BodyMetricDao {

    @Override
    public BodyMetric create(BodyMetric b) {
        String sql = "INSERT INTO body_metrics(user_id, metric_date, weight_kg, body_fat) VALUES(?,?,?,?)";
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, b.getUserId());
            ps.setDate(2, Date.valueOf(b.getDate()));
            ps.setDouble(3, b.getWeightKg());
            ps.setDouble(4, b.getBodyFat());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) b.setId(rs.getInt(1));
            }
            return b;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void update(BodyMetric b) {
        String sql = "UPDATE body_metrics SET user_id=?, metric_date=?, weight_kg=?, body_fat=? WHERE id=?";
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, b.getUserId());
            ps.setDate(2, Date.valueOf(b.getDate()));
            ps.setDouble(3, b.getWeightKg());
            ps.setDouble(4, b.getBodyFat());
            ps.setInt(5, b.getId());
            ps.executeUpdate();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM body_metrics WHERE id=?";
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<BodyMetric> findByUserAndDateRange(int userId, LocalDate from, LocalDate to) {
        StringBuilder sb = new StringBuilder("SELECT id, user_id, metric_date, weight_kg, body_fat FROM body_metrics WHERE user_id=?");
        if (from != null) sb.append(" AND metric_date >= ?");
        if (to != null) sb.append(" AND metric_date <= ?");
        sb.append(" ORDER BY metric_date DESC");
        List<BodyMetric> out = new ArrayList<>();
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sb.toString())) {
            int idx = 1;
            ps.setInt(idx++, userId);
            if (from != null) ps.setDate(idx++, Date.valueOf(from));
            if (to != null) ps.setDate(idx++, Date.valueOf(to));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    BodyMetric b = new BodyMetric();
                    b.setId(rs.getInt("id"));
                    b.setUserId(rs.getInt("user_id"));
                    b.setDate(rs.getDate("metric_date").toLocalDate());
                    b.setWeightKg(rs.getDouble("weight_kg"));
                    b.setBodyFat(rs.getDouble("body_fat"));
                    out.add(b);
                }
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return out;
    }
}
