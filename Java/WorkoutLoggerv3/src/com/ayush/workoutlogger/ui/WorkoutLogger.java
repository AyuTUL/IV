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
    private JTextField setsField, repsField, weightField;
    private JTable table;
    private DefaultTableModel model;

    private final WorkoutsDao workoutsDao = new WorkoutsDaoDbImpl();
    private final ExerciseDao exerciseDao = new ExerciseDaoDbImpl();

    public WorkoutLogger() {
        setTitle("Log Workout");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Row 0 - Exercise
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Exercise:"), gbc);
        gbc.gridx = 1;
        exerciseBox = new JComboBox<>();
        loadExercises();
        formPanel.add(exerciseBox, gbc);

        // Row 1 - Sets
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Sets:"), gbc);
        gbc.gridx = 1;
        setsField = new JTextField();
        formPanel.add(setsField, gbc);

        // Row 2 - Reps
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Reps:"), gbc);
        gbc.gridx = 1;
        repsField = new JTextField();
        formPanel.add(repsField, gbc);

        // Row 3 - Weight
        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("Weight (kg):"), gbc);
        gbc.gridx = 1;
        weightField = new JTextField();
        formPanel.add(weightField, gbc);

        // Save button row
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        JButton saveButton = new JButton("💾 Save Workout");
        saveButton.setFont(new Font("Arial", Font.BOLD, 14));
        saveButton.addActionListener(this::saveWorkout);
        formPanel.add(saveButton, gbc);

        // Table
        model = new DefaultTableModel(new String[]{"ID", "Exercise", "Sets", "Reps", "Weight"}, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        // Layout
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
                JOptionPane.showMessageDialog(this, "Please select an exercise.");
                return;
            }

            int sets = Integer.parseInt(setsField.getText());
            int reps = Integer.parseInt(repsField.getText());
            double weight = Double.parseDouble(weightField.getText());

            Workouts w = new Workouts();
            w.setSessionId(1); // TODO: later connect with actual session
            w.setExercise(ex);
            w.setSets(sets);
            w.setReps(reps);
            w.setWeight(weight);

            workoutsDao.add(w);
            JOptionPane.showMessageDialog(this, "Workout saved!");
            loadWorkouts();

            setsField.setText("");
            repsField.setText("");
            weightField.setText("");
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers for sets, reps, and weight.");
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
                        w.getExercise() != null ? w.getExercise().getName() : "Unknown",
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
