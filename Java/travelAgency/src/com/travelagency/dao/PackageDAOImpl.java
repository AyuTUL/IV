/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.travelagency.dao;

import javax.swing.JOptionPane;

import com.travelagency.connection.DBConnection;
import com.travelagency.model.PackageModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PackageDAOImpl implements PackageDAO {

    @Override

    public void addPackage(PackageModel pkg) {
        String sql = "INSERT INTO packages (name, duration, price) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, pkg.getName());
            ps.setString(2, pkg.getDuration());
            ps.setDouble(3, pkg.getPrice());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updatePackage(PackageModel pkg) {
        String sql = "UPDATE packages SET name=?, duration=?, price=? WHERE id=?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, pkg.getName());
            ps.setString(2, pkg.getDuration());
            ps.setDouble(3, pkg.getPrice());
            ps.setInt(4, pkg.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deletePackage(int id) {
        String sql = "DELETE FROM packages WHERE id=?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<PackageModel> getAllPackages() {
        List<PackageModel> list = new ArrayList<>();
        String sql = "SELECT * FROM packages";
        try (Connection conn = DBConnection.getConnection(); Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new PackageModel(
                        rs.getInt("id"),
                        rs.getString("name"),
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
