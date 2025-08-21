package org.personal.workoutlogger.dao.impl;

import org.personal.workoutlogger.dao.UserDao;
import org.personal.workoutlogger.model.User;
import org.personal.workoutlogger.connection.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.security.MessageDigest;

public class UserDaoImpl implements UserDao {

    private static String sha256Hex(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(input.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException("SHA-256 not available", e);
        }
    }

    @Override
    public User login(String username, String password, String role) {
        String sql = "SELECT id, username, password_hash, role FROM users WHERE username=? AND password_hash=? AND role=?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, sha256Hex(password));
            stmt.setString(3, role);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setId(rs.getInt("id"));
                    user.setUsername(rs.getString("username"));
                    user.setPassword(rs.getString("password_hash"));
                    user.setRole(rs.getString("role"));
                    return user;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean createUser(String username, String password, String role) throws Exception {
        String checkSql = "SELECT COUNT(*) FROM users WHERE username=?";
        String insertSql = "INSERT INTO users (username, password_hash, role) VALUES (?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement chk = conn.prepareStatement(checkSql)) {
            chk.setString(1, username);
            try (ResultSet crs = chk.executeQuery()) {
                if (crs.next() && crs.getInt(1) > 0) {
                    return false;
                }
            }
            try (PreparedStatement ins = conn.prepareStatement(insertSql)) {
                ins.setString(1, username);
                ins.setString(2, sha256Hex(password));
                ins.setString(3, role);
                ins.executeUpdate();
                return true;
            }
        }
    }

    @Override
    public boolean updateUser(int id, String newUsername, String newPassword, String role) throws Exception {
        String sql = "UPDATE users SET username=?, password_hash=?, role=? WHERE id=?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, newUsername);
            ps.setString(2, sha256Hex(newPassword));
            ps.setString(3, role);
            ps.setInt(4, id);
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean deleteUser(int id) throws Exception {
        String sql = "DELETE FROM users WHERE id=?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }
    
    @Override
    public java.util.List<User> getAllUsers() {
        java.util.List<User> list = new java.util.ArrayList<>();
        String sql = "SELECT id, username, role FROM users ORDER BY username";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                User u = new User();
                u.setId(rs.getInt("id"));
                u.setUsername(rs.getString("username"));
                u.setRole(rs.getString("role"));
                list.add(u);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
