package org.personal.workoutlogger.dao.impl;

import org.personal.workoutlogger.dao.BodyMetricDao;
import org.personal.workoutlogger.model.BodyMetric;
import org.personal.workoutlogger.connection.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BodyMetricDaoImpl implements BodyMetricDao {

    @Override
    public void add(BodyMetric bm) {
        if (bm.getWeight() <= 0) throw new IllegalArgumentException("Weight must be positive");
        if (bm.getAge() <= 0) throw new IllegalArgumentException("Age must be positive");
        String sql = "INSERT INTO body_metrics(user_id, weight, body_fat, height, age, date) VALUES(?,?,?,?,?,?)";
        try (Connection c = ConnectionFactory.getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setInt(1, bm.getUserId());
            p.setDouble(2, bm.getWeight());
            if (bm.getBodyFat() == null) p.setNull(3, Types.DOUBLE); else p.setDouble(3, bm.getBodyFat());
            p.setDouble(4, bm.getHeight());
            p.setInt(5, bm.getAge());
            p.setDate(6, Date.valueOf(bm.getDate()));
            p.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(BodyMetric bm) {
        if (bm.getWeight() <= 0) throw new IllegalArgumentException("Weight must be positive");
        if (bm.getAge() <= 0) throw new IllegalArgumentException("Age must be positive");
        String sql = "UPDATE body_metrics SET user_id=?, weight=?, body_fat=?, height=?, age=?, date=? WHERE id=?";
        try (Connection c = ConnectionFactory.getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setInt(1, bm.getUserId());
            p.setDouble(2, bm.getWeight());
            if (bm.getBodyFat() == null) p.setNull(3, Types.DOUBLE); else p.setDouble(3, bm.getBodyFat());
            p.setDouble(4, bm.getHeight());
            p.setInt(5, bm.getAge());
            p.setDate(6, Date.valueOf(bm.getDate()));
            p.setInt(7, bm.getId());
            p.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM body_metrics WHERE id=?";
        try (Connection c = ConnectionFactory.getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setInt(1, id);
            p.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<BodyMetric> findByUser(int uid) {
        String sql = "SELECT * FROM body_metrics WHERE user_id=? ORDER BY date DESC";
        List<BodyMetric> list = new ArrayList<>();
        try (Connection c = ConnectionFactory.getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setInt(1, uid);
            try (ResultSet r = p.executeQuery()) {
                while (r.next()) {
                    Double bodyFat = (r.getObject("body_fat") == null) ? null : r.getDouble("body_fat");
                    BodyMetric bm = new BodyMetric(
                            r.getInt("id"),
                            r.getInt("user_id"),
                            r.getDouble("weight"),
                            bodyFat,
                            r.getDouble("height"),
                            r.getInt("age"),
                            r.getDate("date").toLocalDate()
                    );
                    list.add(bm);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
