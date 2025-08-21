package org.personal.workoutlogger.ui;

import javax.swing.*;
import java.awt.*;
import org.personal.workoutlogger.model.User;
import org.personal.workoutlogger.ui.windows.BodyMetricWindow;
import org.personal.workoutlogger.ui.windows.ExerciseWindow;
import org.personal.workoutlogger.ui.windows.UserInfoWindow;
import org.personal.workoutlogger.ui.windows.WorkoutWindow;

public class DashboardFrame extends JFrame {
    private final User currentUser;

    public DashboardFrame(User user) {
        super("Workout Logger - Dashboard");
        this.currentUser = user;
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500, 350);
        setLocationRelativeTo(null);
        initUI();
    }

    private void initUI() {
        JPanel panel = new JPanel(new GridLayout(2, 2, 12, 12));
        panel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        JButton btnBody = new JButton("Body Metrics");
        JButton btnExercise = new JButton("Exercises");
        JButton btnUser = new JButton("User Info");
        JButton btnWorkout = new JButton("Workouts");
        JButton btnExit = new JButton("Exit");

        btnBody.addActionListener(e -> new BodyMetricWindow(currentUser).setVisible(true));
        btnExercise.addActionListener(e -> new ExerciseWindow().setVisible(true));
        btnUser.addActionListener(e -> new UserInfoWindow(currentUser).setVisible(true));
        btnWorkout.addActionListener(e -> new WorkoutWindow(currentUser).setVisible(true));
        btnExit.addActionListener(e -> System.exit(0));

        panel.add(btnBody);
        panel.add(btnExercise);
        panel.add(btnUser);
        panel.add(btnWorkout);
        panel.add(btnExit);

        add(panel, BorderLayout.CENTER);
    }
}
