package com.ayush.workoutlogger.ui;

import com.ayush.workoutlogger.dao.ExerciseDao;
import com.ayush.workoutlogger.dao.WorkoutsDao;
import com.ayush.workoutlogger.dao.db.ExerciseDaoDbImpl;
import com.ayush.workoutlogger.dao.db.WorkoutsDaoDbImpl;
import com.ayush.workoutlogger.model.Exercise;
import com.ayush.workoutlogger.model.User;
import com.ayush.workoutlogger.model.Workouts;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Dashboard extends JFrame {

    private final User loggedInUser;

    private JComboBox<Exercise> exerciseCombo;
    private JSpinner setsSpinner;
    private JSpinner repsSpinner;
    private JSpinner weightSpinner;
    private JButton saveWorkoutBtn;

    public Dashboard(User user) {
        super("Log Workout");
        this.loggedInUser = user;

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 300);
        setLocationRelativeTo(null);

        initUI();
    }

    private void initUI() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Load exercises
        ExerciseDao exerciseDao = new ExerciseDaoDbImpl();
        List<Exercise> exercises;
        try {
            exercises = exerciseDao.findAll();
        } catch (Exception e) {
            exercises = List.of();
            e.printStackTrace();
        }

        exerciseCombo = new JComboBox<>(exercises.toArray(new Exercise[0]));
        setsSpinner = new JSpinner(new SpinnerNumberModel(3, 1, 20, 1));
        repsSpinner = new JSpinner(new SpinnerNumberModel(10, 1, 100, 1));
        weightSpinner = new JSpinner(new SpinnerNumberModel(0.0, 0.0, 500.0, 2.5));
        saveWorkoutBtn = new JButton("💾 Save Workout");

        Font font = new Font("Segoe UI", Font.PLAIN, 14);
        exerciseCombo.setFont(font);
        setsSpinner.setFont(font);
        repsSpinner.setFont(font);
        weightSpinner.setFont(font);
        saveWorkoutBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));

        // Layout
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Exercise:"), gbc);
        gbc.gridx = 1;
        panel.add(exerciseCombo, gbc);

        gbc.gridx = 0; gbc.gridy++;
        panel.add(new JLabel("Sets:"), gbc);
        gbc.gridx = 1;
        panel.add(setsSpinner, gbc);

        gbc.gridx = 0; gbc.gridy++;
        panel.add(new JLabel("Reps:"), gbc);
        gbc.gridx = 1;
        panel.add(repsSpinner, gbc);

        gbc.gridx = 0; gbc.gridy++;
        panel.add(new JLabel("Weight (kg):"), gbc);
        gbc.gridx = 1;
        panel.add(weightSpinner, gbc);

        gbc.gridx = 1; gbc.gridy++;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(saveWorkoutBtn, gbc);

        saveWorkoutBtn.addActionListener(e -> saveWorkout());

        setContentPane(panel);
    }

    private void saveWorkout() {
        try {
            Exercise selected = (Exercise) exerciseCombo.getSelectedItem();
            int sets = (Integer) setsSpinner.getValue();
            int reps = (Integer) repsSpinner.getValue();
            double weight = (Double) weightSpinner.getValue();

            Workouts workout = new Workouts();
            workout.setId(loggedInUser.getId()); // associate with current user
            workout.setExercise(selected);
            workout.setSets(sets);
            workout.setReps(reps);
            workout.setWeight(weight);

            WorkoutsDao workoutsDao = new WorkoutsDaoDbImpl();
            workoutsDao.add(workout);

            JOptionPane.showMessageDialog(this, "✅ Workout saved!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "❌ Error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
