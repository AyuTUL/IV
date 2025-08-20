package com.ayush.workoutlogger.ui;

import com.ayush.workoutlogger.dao.ExerciseDao;
import com.ayush.workoutlogger.dao.WorkoutsDao;
import com.ayush.workoutlogger.dao.db.ExerciseDaoDbImpl;
import com.ayush.workoutlogger.dao.db.WorkoutsDaoDbImpl;
import com.ayush.workoutlogger.model.Exercise;
import com.ayush.workoutlogger.model.Workouts;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.List;

public class WorkoutLogger extends JFrame {

    private JComboBox<Exercise> exerciseBox;
    private JSpinner setsField, repsField, weightField;
    private JTable table;
    private DefaultTableModel model;

    private final WorkoutsDao workoutsDao = new WorkoutsDaoDbImpl();
    private final ExerciseDao exerciseDao = new ExerciseDaoDbImpl();

    public WorkoutLogger() {
        setTitle("Log Workout");
        setSize(750, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        // Exercise
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0;
        formPanel.add(new JLabel("Exercise:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        exerciseBox = new JComboBox<>();
        loadExercises();
        formPanel.add(exerciseBox, gbc);

        // Sets
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        formPanel.add(new JLabel("Sets:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        setsField = new JSpinner(new SpinnerNumberModel(1, 1, 50, 1));
        formPanel.add(setsField, gbc);

        // Reps
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0;
        formPanel.add(new JLabel("Reps:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        repsField = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        formPanel.add(repsField, gbc);

        // Weight
        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0;
        formPanel.add(new JLabel("Weight (kg):"), gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        weightField = new JSpinner(new SpinnerNumberModel(0.0, 0.0, 1000.0, 0.5));
        formPanel.add(weightField, gbc);

        // Save button
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        JButton saveButton = new JButton("💾 Save Workout");
        saveButton.addActionListener(this::saveWorkout);
        formPanel.add(saveButton, gbc);

        // Table
        model = new DefaultTableModel(new String[]{"ID", "Exercise", "Sets", "Reps", "Weight"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        table = new JTable(model);
        table.setRowHeight(24);
        JScrollPane scrollPane = new JScrollPane(table);

        setLayout(new BorderLayout(10, 10));
        add(formPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        loadWorkouts();
    }

    private void loadExercises() {
        try {
            List<Exercise> exercises = exerciseDao.findAll();
            exerciseBox.removeAllItems();
            for (Exercise ex : exercises) {
                exerciseBox.addItem(ex);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Failed to load exercises: " + e.getMessage());
        }
    }

    private void saveWorkout(ActionEvent evt) {
        try {
            Exercise ex = (Exercise) exerciseBox.getSelectedItem();
            if (ex == null) {
                JOptionPane.showMessageDialog(this, "Please add an exercise first.");
                return;
            }
            int sets = (Integer) setsField.getValue();
            int reps = (Integer) repsField.getValue();
            double weight = (Double) weightField.getValue();

            Workouts w = new Workouts();
            w.setSessionId(1); // TODO: connect with current session selection
            w.setExercise(ex);
            w.setSets(sets);
            w.setReps(reps);
            w.setWeight(weight);

            workoutsDao.add(w);
            loadWorkouts();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void loadWorkouts() {
        try {
            model.setRowCount(0);
            List<Workouts> list = workoutsDao.findAll();
            for (Workouts w : list) {
                model.addRow(new Object[]{
                        w.getId(),
                        w.getExercise() != null ? w.getExercise().getName() : "",
                        w.getSets(),
                        w.getReps(),
                        w.getWeight()
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Failed to load workouts: " + e.getMessage());
        }
    }
}
