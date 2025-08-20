package com.ayush.workoutlogger.dao.db;

import com.ayush.workoutlogger.dao.ExerciseDao;
import com.ayush.workoutlogger.model.Exercise;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import static com.ayush.workoutlogger.connection.ConnectionFactory.getConnection;

public class ExerciseDaoDbImpl implements ExerciseDao {

    @Override
    public Exercise create(Exercise e) {
        String sql = "INSERT INTO exercises(name, muscle_group) VALUES(?, ?)";
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, e.getName());
            ps.setString(2, e.getMuscleGroup());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) e.setId(rs.getInt(1));
            }
            return e;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void update(Exercise e) {
        String sql = "UPDATE exercises SET name=?, muscle_group=? WHERE id=?";
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, e.getName());
            ps.setString(2, e.getMuscleGroup());
            ps.setInt(3, e.getId());
            ps.executeUpdate();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM exercises WHERE id=?";
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public Exercise findById(int id) {
        String sql = "SELECT id, name, muscle_group FROM exercises WHERE id=?";
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Exercise e = new Exercise();
                    e.setId(rs.getInt("id"));
                    e.setName(rs.getString("name"));
                    e.setMuscleGroup(rs.getString("muscle_group"));
                    return e;
                }
                return null;
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<Exercise> findAll() {
        String sql = "SELECT id, name, muscle_group FROM exercises ORDER BY name";
        List<Exercise> list = new ArrayList<>();
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Exercise e = new Exercise();
                e.setId(rs.getInt("id"));
                e.setName(rs.getString("name"));
                e.setMuscleGroup(rs.getString("muscle_group"));
                list.add(e);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return list;
    }
}
