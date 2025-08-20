package com.ayush.workoutlogger.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDate;

public class Sessions extends JFrame {

    private final DefaultTableModel model = new DefaultTableModel(
            new Object[]{"ID", "User", "Date", "Workout", "Notes"}, 0
    );
    private final JTable table = new JTable(model);

    public Sessions() {
        super("Sessions");
        setSize(700, 480);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
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

        // Dummy action listeners for now
        btnRefresh.addActionListener(this::refresh);
        btnAdd.addActionListener(this::add);
        btnEdit.addActionListener(this::edit);
        btnDelete.addActionListener(this::delete);
    }

    private void refresh(ActionEvent e) {
        // TODO: implement database call
        JOptionPane.showMessageDialog(this, "Refresh not yet implemented.");
    }

    private void add(ActionEvent e) {
        // TODO: implement add session
        JOptionPane.showMessageDialog(this, "Add session not yet implemented.");
    }

    private void edit(ActionEvent e) {
        JOptionPane.showMessageDialog(this, "Edit session not yet implemented.");
    }

    private void delete(ActionEvent e) {
        JOptionPane.showMessageDialog(this, "Delete session not yet implemented.");
    }
}
