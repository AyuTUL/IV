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
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 5, 5));

        // Exercise dropdown
        formPanel.add(new JLabel("Exercise:"));
        exerciseBox = new JComboBox<>();
        loadExercises();
        formPanel.add(exerciseBox);

        // Sets field
        formPanel.add(new JLabel("Sets:"));
        setsField = new JTextField();
        formPanel.add(setsField);

        // Reps field
        formPanel.add(new JLabel("Reps:"));
        repsField = new JTextField();
        formPanel.add(repsField);

        // Weight field
        formPanel.add(new JLabel("Weight (kg):"));
        weightField = new JTextField();
        formPanel.add(weightField);

        JButton saveButton = new JButton("Save Workout");
        saveButton.addActionListener(this::saveWorkout);

        // Table
        model = new DefaultTableModel(new String[]{"ID", "Exercise", "Sets", "Reps", "Weight"}, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        // Layout
        setLayout(new BorderLayout());
        add(formPanel, BorderLayout.NORTH);
        add(saveButton, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);

        loadWorkouts();
    }

    private void loadExercises() {
        List<Exercise> exercises = exerciseDao.findAll();
        for (Exercise ex : exercises) {
            exerciseBox.addItem(ex);
        }
    }

    private void saveWorkout(ActionEvent evt) {
        try {
            Exercise ex = (Exercise) exerciseBox.getSelectedItem();
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
                        w.getExercise().getName(),
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
