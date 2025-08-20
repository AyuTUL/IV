package com.ayush.workoutlogger.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.util.List;

import com.ayush.workoutlogger.dao.BodyMetricDao;
import com.ayush.workoutlogger.dao.UserDao;
import com.ayush.workoutlogger.dao.impl.DaoProvider;
import com.ayush.workoutlogger.model.BodyMetric;
import com.ayush.workoutlogger.model.User;

public class BodyMetrics extends JFrame {
    private final BodyMetricDao metricDao = DaoProvider.getBodyMetricDao();
    private final UserDao userDao = DaoProvider.getUserDao();

    private final JComboBox<UserItem> cmbUser = new JComboBox<>();
    private final JTextField txtFrom = new JTextField(10);
    private final JTextField txtTo = new JTextField(10);
    private final DefaultTableModel model = new DefaultTableModel(
            new Object[]{"ID", "User", "Date", "Weight (kg)", "Body Fat %", "Notes"}, 0
    );
    private final JTable table = new JTable(model);

    public BodyMetrics() {
        super("Body Metrics");
        setSize(700, 480);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initUI();
        loadUsers();
        setDefaultDates();
        loadMetrics();
    }

    private void initUI() {
        setLayout(new BorderLayout());

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.add(new JLabel("User:"));
        top.add(cmbUser);
        top.add(new JLabel("From (YYYY-MM-DD):"));
        top.add(txtFrom);
        top.add(new JLabel("To:"));
        top.add(txtTo);
        JButton btnRefresh = new JButton("Refresh");
        top.add(btnRefresh);
        add(top, BorderLayout.NORTH);

        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnAdd = new JButton("Add");
        JButton btnEdit = new JButton("Edit");
        JButton btnDelete = new JButton("Delete");
        bottom.add(btnAdd);
        bottom.add(btnEdit);
        bottom.add(btnDelete);
        add(bottom, BorderLayout.SOUTH);

        btnRefresh.addActionListener(this::refresh);
        btnAdd.addActionListener(this::add);
        btnEdit.addActionListener(this::edit);
        btnDelete.addActionListener(this::delete);
    }

    private void loadUsers() {
        try {
            cmbUser.removeAllItems();
            List<User> users = userDao.findAll();
            for (User u : users) {
                cmbUser.addItem(new UserItem(u.getId(), u.getUsername()));
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Failed to load users: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void setDefaultDates() {
        LocalDate to = LocalDate.now();
        LocalDate from = to.minusDays(30);
        txtFrom.setText(from.toString());
        txtTo.setText(to.toString());
    }

    private void loadMetrics() {
        model.setRowCount(0);
        try {
            UserItem selected = (UserItem) cmbUser.getSelectedItem();
            if (selected == null) return;
            LocalDate from = LocalDate.parse(txtFrom.getText().trim());
            LocalDate to = LocalDate.parse(txtTo.getText().trim());
            List<BodyMetric> list = metricDao.findByUserAndDateRange(selected.id, from, to);
            for (BodyMetric m : list) {
                model.addRow(new Object[]{
                        m.getId(), selected.name, m.getDate(),
                        m.getWeightKg(), m.getBodyFat(), m.getNotes()
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Failed to load metrics: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void refresh(ActionEvent e) { loadMetrics(); }

    private void add(ActionEvent e) {
        UserItem selected = (UserItem) cmbUser.getSelectedItem();
        if (selected == null) return;
        JTextField dateField = new JTextField(LocalDate.now().toString());
        JTextField weightField = new JTextField();
        JTextField fatField = new JTextField();
        JTextField notesField = new JTextField();
        Object[] fields = {
                "Date (YYYY-MM-DD):", dateField,
                "Weight (kg):", weightField,
                "Body Fat % (optional):", fatField,
                "Notes:", notesField
        };
        if (JOptionPane.showConfirmDialog(this, fields, "Add Body Metric",
                JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            try {
                BodyMetric m = new BodyMetric();
                m.setUserId(selected.id);
                m.setDate(LocalDate.parse(dateField.getText().trim()));
                m.setWeightKg(Double.parseDouble(weightField.getText().trim()));
                String fat = fatField.getText().trim();
                if (!fat.isEmpty()) m.setBodyFat(Double.parseDouble(fat));
                m.setNotes(notesField.getText());
                metricDao.create(m);
                loadMetrics();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Failed to add: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void edit(ActionEvent e) {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a row.");
            return;
        }
        int id = (Integer) model.getValueAt(row, 0);
        String dateStr = model.getValueAt(row, 2).toString();
        String weightStr = model.getValueAt(row, 3).toString();
        String fatStr = model.getValueAt(row, 4) == null ? "" : model.getValueAt(row, 4).toString();
        String notesStr = model.getValueAt(row, 5) == null ? "" : model.getValueAt(row, 5).toString();

        JTextField dateField = new JTextField(dateStr);
        JTextField weightField = new JTextField(weightStr);
        JTextField fatField = new JTextField(fatStr);
        JTextField notesField = new JTextField(notesStr);

        Object[] fields = {
                "Date (YYYY-MM-DD):", dateField,
                "Weight (kg):", weightField,
                "Body Fat % (optional):", fatField,
                "Notes:", notesField
        };

        if (JOptionPane.showConfirmDialog(this, fields, "Edit Body Metric",
                JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            try {
                BodyMetric m = new BodyMetric();
                m.setId(id);
                m.setUserId(((UserItem) cmbUser.getSelectedItem()).id);
                m.setDate(LocalDate.parse(dateField.getText().trim()));
                m.setWeightKg(Double.parseDouble(weightField.getText().trim()));
                String fat = fatField.getText().trim();
                m.setBodyFat(fat.isEmpty() ? null : Double.parseDouble(fat));
                m.setNotes(notesField.getText());
                metricDao.update(m);
                loadMetrics();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Failed to edit: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void delete(ActionEvent e) {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a row.");
            return;
        }
        int id = (Integer) model.getValueAt(row, 0);
        if (JOptionPane.showConfirmDialog(this, "Delete selected record?",
                "Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            try {
                metricDao.delete(id);
                loadMetrics();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Failed to delete: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private static class UserItem {
        final int id;
        final String name;

        UserItem(int id, String name) {
            this.id = id;
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
