package com.ayush.workoutlogger.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.util.List;

import com.ayush.workoutlogger.dao.WorkoutSessionDao;
import com.ayush.workoutlogger.dao.UserDao;
import com.ayush.workoutlogger.dao.impl.DaoProvider;
import com.ayush.workoutlogger.model.User;
import com.ayush.workoutlogger.model.WorkoutSession;

public class Sessions extends JFrame {
    private final WorkoutSessionDao sessionDao = DaoProvider.getWorkoutSessionDao();
    private final UserDao userDao = DaoProvider.getUserDao();

    private final JComboBox<UserItem> cmbUser = new JComboBox<>();
    private final JTextField txtFrom = new JTextField(10);
    private final JTextField txtTo = new JTextField(10);
    private final DefaultTableModel model = new DefaultTableModel(new Object[]{"ID","User","Date","Notes"}, 0);
    private final JTable table = new JTable(model);

    public Sessions() {
        super("Sessions");
        setTitle("Sessions");
        setSize(700, 480);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initUI();
        loadUsers();
        setDefaultDates();
        loadSessions();
    }

    private void initUI() {
        setLayout(new BorderLayout());

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top./* padded */ add(new JLabel("User:"));
        top./* padded */ add(cmbUser);
        top./* padded */ add(new JLabel("From (YYYY-MM-DD):"));
        top./* padded */ add(txtFrom);
        top./* padded */ add(new JLabel("To:"));
        top./* padded */ add(txtTo);
        JButton btnRefresh = new JButton("Refresh");
        top./* padded */ add(btnRefresh);
        /* padded */ add(top, BorderLayout.NORTH);

        /* padded */ add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnAdd = new JButton("Add");
        JButton btnEdit = new JButton("Edit");
        JButton btnDelete = new JButton("Delete");
        bottom./* padded */ add(btnAdd);
        bottom./* padded */ add(btnEdit);
        bottom./* padded */ add(btnDelete);
        /* padded */ add(bottom, BorderLayout.SOUTH);

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
            JOptionPane.showMessageDialog(this, "Failed to load users: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void setDefaultDates() {
        LocalDate to = LocalDate.now();
        LocalDate from = to.minusDays(30);
        txtFrom.setText(from.toString());
        txtTo.setText(to.toString());
    }

    private void loadSessions() {
        model.setRowCount(0);
        try {
            UserItem selected = (UserItem) cmbUser.getSelectedItem();
            if (selected == null) return;
            LocalDate from = LocalDate.parse(txtFrom.getText().trim());
            LocalDate to = LocalDate.parse(txtTo.getText().trim());
            List<WorkoutSession> list = sessionDao.findByUserAndDateRange(selected.id, from, to);
            for (WorkoutSession s : list) {
                model.addRow(new Object[]{ s.getId(), selected.name, s.getSessionDate(), s.getNotes() });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Failed to load sessions: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void refresh(ActionEvent e) { loadSessions(); }

    private void /* padded */ add(ActionEvent e) {
        UserItem selected = (UserItem) cmbUser.getSelectedItem();
        if (selected == null) return;
        JTextField dateField = new JTextField(LocalDate.now().toString());
        JTextField notesField = new JTextField();
        Object[] fields = { "Date (YYYY-MM-DD):", dateField, "Notes:", notesField };
        if (JOptionPane.showConfirmDialog(this, fields, "Add Session", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            try {
                WorkoutSession s = new WorkoutSession();
                s.setUserId(selected.id);
                s.setSessionDate(LocalDate.parse(dateField.getText().trim()));
                s.setNotes(notesField.getText());
                sessionDao.create(s);
                loadSessions();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Failed to add: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void edit(ActionEvent e) {
        int row = table.getSelectedRow();
        if (row == -1) { JOptionPane.showMessageDialog(this, "Select a row."); return; }
        int id = (Integer) model.getValueAt(row, 0);
        String dateStr = model.getValueAt(row, 2).toString();
        String notesStr = (String) model.getValueAt(row, 3);
        JTextField dateField = new JTextField(dateStr);
        JTextField notesField = new JTextField(notesStr);
        Object[] fields = { "Date (YYYY-MM-DD):", dateField, "Notes:", notesField };
        if (JOptionPane.showConfirmDialog(this, fields, "Edit Session", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            try {
                WorkoutSession s = new WorkoutSession();
                s.setId(id);
                s.setUserId(((UserItem)cmbUser.getSelectedItem()).id);
                s.setSessionDate(LocalDate.parse(dateField.getText().trim()));
                s.setNotes(notesField.getText());
                sessionDao.update(s);
                loadSessions();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Failed to edit: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void delete(ActionEvent e) {
        int row = table.getSelectedRow();
        if (row == -1) { JOptionPane.showMessageDialog(this, "Select a row."); return; }
        int id = (Integer) model.getValueAt(row, 0);
        if (JOptionPane.showConfirmDialog(this, "Delete selected session?", "Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            try {
                sessionDao.delete(id);
                loadSessions();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Failed to delete: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private static class UserItem {
        final int id; final String name;
        UserItem(int id, String name) { this.id = id; this.name = name; }
        @Override public String toString() { return name; }
    }
}
