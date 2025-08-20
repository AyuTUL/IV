package com.ayush.workoutlogger.ui;

import com.ayush.workoutlogger.dao.ExerciseDao;
import com.ayush.workoutlogger.dao.db.ExerciseDaoDbImpl;
import com.ayush.workoutlogger.model.Exercise;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class ExerciseManager extends JFrame {
    private JTable table;
    private ExerciseDao exerciseDao;
    private DefaultTableModel model;

    public ExerciseManager() {
        super("Exercises");
        setSize(700, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        exerciseDao = new ExerciseDaoDbImpl();
        initUI();
        loadExercises();
    }

    private void initUI() {
        setLayout(new BorderLayout());

        // Table
        String[] columnNames = {"ID", "Name", "Muscle Group"};
        model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel();
        JButton addBtn = new JButton("Add");
        JButton editBtn = new JButton("Edit");
        JButton deleteBtn = new JButton("Delete");
        buttonPanel.add(addBtn);
        buttonPanel.add(editBtn);
        buttonPanel.add(deleteBtn);
        add(buttonPanel, BorderLayout.SOUTH);

        // Event handlers
        addBtn.addActionListener(this::addExercise);
        editBtn.addActionListener(this::editExercise);
        deleteBtn.addActionListener(this::deleteExercise);
    }

    private void loadExercises() {
        model.setRowCount(0); // Clear table
        List<Exercise> exercises = exerciseDao.findAll();
        for (Exercise ex : exercises) {
            model.addRow(new Object[]{ex.getId(), ex.getName(), ex.getMuscleGroup()});
        }
    }

    private void addExercise(ActionEvent e) {
        JTextField nameField = new JTextField();
        JTextField muscleField = new JTextField();
        Object[] fields = {"Name:", nameField, "Muscle Group:", muscleField};

        int option = JOptionPane.showConfirmDialog(this, fields, "Add Exercise", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            Exercise ex = new Exercise();
            ex.setName(nameField.getText());
            ex.setMuscleGroup(muscleField.getText());
            exerciseDao.create(ex);
            loadExercises();
        }
    }

    private void editExercise(ActionEvent e) {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select an exercise to edit.");
            return;
        }

        int id = (int) model.getValueAt(row, 0);
        String currentName = (String) model.getValueAt(row, 1);
        String currentMuscle = (String) model.getValueAt(row, 2);

        JTextField nameField = new JTextField(currentName);
        JTextField muscleField = new JTextField(currentMuscle);
        Object[] fields = {"Name:", nameField, "Muscle Group:", muscleField};

        int option = JOptionPane.showConfirmDialog(this, fields, "Edit Exercise", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            Exercise ex = new Exercise();
            ex.setId(id);
            ex.setName(nameField.getText());
            ex.setMuscleGroup(muscleField.getText());
            exerciseDao.update(ex);
            loadExercises();
        }
    }

    private void deleteExercise(ActionEvent e) {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select an exercise to delete.");
            return;
        }

        int id = (int) model.getValueAt(row, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure?", "Delete Exercise", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            exerciseDao.delete(id);
            loadExercises();
        }
    }
}
