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
    private JComboBox<Exercise> exerciseDropdown;
    private JTextField setsField, repsField, weightField;
    private JButton saveButton;
    private JTable workoutTable;
    private DefaultTableModel tableModel;

    private ExerciseDao exerciseDao = new ExerciseDaoDbImpl();
    private WorkoutsDao workoutsDao = new WorkoutsDaoDbImpl();

    public WorkoutLogger() {
        setTitle("Log Workout");
        setSize(600, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        initUI();
        loadExercises();
        loadWorkouts();
    }

    private void initUI() {
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));

        exerciseDropdown = new JComboBox<>();
        setsField = new JTextField();
        repsField = new JTextField();
        weightField = new JTextField();
        saveButton = new JButton("Save Workout");

        formPanel.add(new JLabel("Exercise:"));
        formPanel.add(exerciseDropdown);
        formPanel.add(new JLabel("Sets:"));
        formPanel.add(setsField);
        formPanel.add(new JLabel("Reps:"));
        formPanel.add(repsField);
        formPanel.add(new JLabel("Weight:"));
        formPanel.add(weightField);
        formPanel.add(new JLabel(""));
        formPanel.add(saveButton);

        // Table
        tableModel = new DefaultTableModel(new Object[]{"ID", "Exercise", "Sets", "Reps", "Weight"}, 0);
        workoutTable = new JTable(tableModel);

        saveButton.addActionListener(this::saveWorkout);

        add(formPanel, BorderLayout.NORTH);
        add(new JScrollPane(workoutTable), BorderLayout.CENTER);
    }

    private void loadExercises() {
        try {
            List<Exercise> exercises = exerciseDao.findAll();
            for (Exercise ex : exercises) {
                exerciseDropdown.addItem(ex);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading exercises: " + e.getMessage());
        }
    }

    private void loadWorkouts() {
        try {
            tableModel.setRowCount(0);
            for (Workouts w : workoutsDao.findAll()) {
                tableModel.addRow(new Object[]{
                        w.getId(),
                        w.getExercise().getName(),
                        w.getSets(),
                        w.getReps(),
                        w.getWeight()
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading workouts: " + e.getMessage());
        }
    }

    private void saveWorkout(ActionEvent e) {
        try {
            Exercise exercise = (Exercise) exerciseDropdown.getSelectedItem();
            int sets = Integer.parseInt(setsField.getText());
            int reps = Integer.parseInt(repsField.getText());
            double weight = Double.parseDouble(weightField.getText());

            Workouts w = new Workouts();
            w.setExercise(exercise);
            w.setSets(sets);
            w.setReps(reps);
            w.setWeight(weight);

            workoutsDao.add(w);
            loadWorkouts();

            setsField.setText("");
            repsField.setText("");
            weightField.setText("");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error saving workout: " + ex.getMessage());
        }
    }
}
