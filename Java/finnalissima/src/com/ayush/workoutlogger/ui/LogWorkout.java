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

public class LogWorkout extends JFrame {
    private final User loggedInUser;

    private JComboBox<Exercise> exerciseCombo;
    private JSpinner setsSpinner;
    private JSpinner repsSpinner;
    private JSpinner weightSpinner;
    private JButton saveWorkoutBtn;

    public LogWorkout(User user) {
        super("Log Workout");
        this.loggedInUser = user;

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
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

        // load exercises
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

        // layout
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
        Exercise selected = (Exercise) exerciseBox.getSelectedItem();
        int sets = Integer.parseInt(setsField.getText().trim());
        int reps = Integer.parseInt(repsField.getText().trim());
        double weight = Double.parseDouble(weightField.getText().trim());

        // 1. Auto-create a new workout session
        WorkoutSessionDao sessionDao = DaoProvider.getWorkoutSessionDao();
        WorkoutSession session = new WorkoutSession();
        session.setUserId(loggedInUser.getId());
        session.setDate(new java.sql.Date(System.currentTimeMillis())); // today
        int sessionId = sessionDao.create(session);   // ✅ create() for session

        // 2. Create workout linked to session
        Workout workout = new Workout();
        workout.setSessionId(sessionId);              // link to new session
        workout.setExercise(selected);
        workout.setSets(sets);
        workout.setReps(reps);
        workout.setWeight(weight);

        WorkoutsDao workoutsDao = DaoProvider.getWorkoutsDao();
        workoutsDao.add(workout);                     // ✅ add() for workout

        JOptionPane.showMessageDialog(this, "Workout logged!");
        dispose();
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
    }
}



}
