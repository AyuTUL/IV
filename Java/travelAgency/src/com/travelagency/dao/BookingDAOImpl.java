/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.travelagency.dao;

import com.travelagency.connection.DBConnection;
import com.travelagency.model.BookingModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingDAOImpl implements BookingDAO {

    @Override
    public void bookPackage(String userName, String phone, int packageId) {
        String insertUser = "INSERT INTO users (name, phone) VALUES (?, ?) ON DUPLICATE KEY UPDATE name = VALUES(name)";
        String insertBooking = "INSERT INTO bookings (user_id, package_id) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);

            // Insert or update user
            int userId = -1;
            try (PreparedStatement ps = conn.prepareStatement(insertUser, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, userName);
                ps.setString(2, phone);
                ps.executeUpdate();

                try (PreparedStatement getId = conn.prepareStatement("SELECT user_id FROM users WHERE phone = ?")) {
                    getId.setString(1, phone);
                    ResultSet rs = getId.executeQuery();
                    if (rs.next()) userId = rs.getInt("user_id");
                }
            }

            // Insert booking
            try (PreparedStatement ps = conn.prepareStatement(insertBooking)) {
                ps.setInt(1, userId);
                ps.setInt(2, packageId);
                ps.executeUpdate();
            }

            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void cancelBooking(int bookingId) {
        String sql = "DELETE FROM bookings WHERE booking_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, bookingId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<BookingModel> getUserBookings(String phone) {
        List<BookingModel> list = new ArrayList<>();
        String sql = "SELECT b.booking_id, u.user_id, u.name, u.phone, p.id, p.name AS package_name, p.duration, p.price " +
                     "FROM bookings b " +
                     "JOIN users u ON b.user_id = u.user_id " +
                     "JOIN packages p ON b.package_id = p.id " +
                     "WHERE u.phone = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, phone);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new BookingModel(
                        rs.getInt("booking_id"),
                        rs.getInt("user_id"),
                        rs.getString("name"),
                        rs.getString("phone"),
                        rs.getInt("id"),
                        rs.getString("package_name"),
                        rs.getString("duration"),
                        rs.getDouble("price")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<BookingModel> getAllBookings() {
        List<BookingModel> list = new ArrayList<>();
        String sql = "SELECT b.booking_id, u.user_id, u.name, u.phone, p.id, p.name AS package_name, p.duration, p.price " +
                     "FROM bookings b " +
                     "JOIN users u ON b.user_id = u.user_id " +
                     "JOIN packages p ON b.package_id = p.id";
        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new BookingModel(
                        rs.getInt("booking_id"),
                        rs.getInt("user_id"),
                        rs.getString("name"),
                        rs.getString("phone"),
                        rs.getInt("id"),
                        rs.getString("package_name"),
                        rs.getString("duration"),
                        rs.getDouble("price")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
