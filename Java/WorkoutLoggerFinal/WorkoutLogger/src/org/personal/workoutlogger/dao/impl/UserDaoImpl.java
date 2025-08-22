package org.personal.workoutlogger.dao.impl;

import org.personal.workoutlogger.dao.UserDao;
import org.personal.workoutlogger.model.User;
import org.personal.workoutlogger.connection.ConnectionFactory;

import java.sql.*;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {

    private static String sha256Hex(String input) {
        if (input == null) return null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(input.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) sb.append(String.format("%02x", b));
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User login(String username, String password, String role) {
        String sql = "SELECT id, username, password_hash, role FROM users WHERE username=? AND role=?";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, role);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;
                String dbHash = rs.getString("password_hash");
                String inHash = sha256Hex(password);
                if (dbHash != null && dbHash.equalsIgnoreCase(inHash)) {
                    User u = new User();
                    u.setId(rs.getInt("id"));
                    u.setUsername(rs.getString("username"));
                    u.setRole(rs.getString("role"));
                    return u;
                }
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean createUser(String username, String password, String role) throws Exception {
        String check = "SELECT COUNT(*) FROM users WHERE username=?";
        String insert = "INSERT INTO users(username, password_hash, role) VALUES(?,?,?)";
        try (Connection con = ConnectionFactory.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement(check)) {
                ps.setString(1, username);
                try (ResultSet rs = ps.executeQuery()) {
                    rs.next();
                    if (rs.getInt(1) > 0) return false;
                }
            }
            try (PreparedStatement ps = con.prepareStatement(insert)) {
                ps.setString(1, username);
                ps.setString(2, sha256Hex(password));
                ps.setString(3, role);
                return ps.executeUpdate() == 1;
            }
        }
    }

    @Override
    public boolean updateUser(int id, String newUsername, String newPassword, String role) throws Exception {
        // Build dynamic SQL to keep password if null/empty
        boolean changePassword = newPassword != null && !newPassword.isEmpty();
        String sql = "UPDATE users SET username=?, role=?" + (changePassword ? ", password_hash=?" : "") + " WHERE id=?";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            int idx = 1;
            ps.setString(idx++, newUsername);
            ps.setString(idx++, role);
            if (changePassword) ps.setString(idx++, sha256Hex(newPassword));
            ps.setInt(idx, id);
            return ps.executeUpdate() == 1;
        }
    }

    @Override
    public boolean deleteUser(int id) throws Exception {
        String sql = "DELETE FROM users WHERE id=?";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() == 1;
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        String sql = "SELECT id, username, role FROM users ORDER BY username ASC";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
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
