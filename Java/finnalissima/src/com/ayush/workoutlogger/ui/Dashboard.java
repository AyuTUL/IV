package com.ayush.workoutlogger.ui;

import com.ayush.workoutlogger.model.User;

import javax.swing.*;
import java.awt.*;

public class Dashboard extends JFrame {

    private final User loggedInUser;

    public Dashboard(User user) {
        super("Dashboard");
        this.loggedInUser = user;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        initUI();
    }

    private void initUI() {
        JPanel panel = new JPanel(new GridLayout(5, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton manageExerciseBtn = new JButton("Manage Exercise");
        JButton logWorkoutBtn = new JButton("Log Workout");
        JButton manageSessionBtn = new JButton("Manage Session");
        JButton bodyMetricsBtn = new JButton("Body Metrics");
        JButton exitBtn = new JButton("Exit");

        panel.add(manageExerciseBtn);
        panel.add(logWorkoutBtn);
        panel.add(manageSessionBtn);
        panel.add(bodyMetricsBtn);
        panel.add(exitBtn);

        // navigation
        manageExerciseBtn.addActionListener(e -> new ExerciseManager().setVisible(true));
        logWorkoutBtn.addActionListener(e -> new LogWorkout(loggedInUser).setVisible(true));
        manageSessionBtn.addActionListener(e -> new Sessions(loggedInUser).setVisible(true));
        bodyMetricsBtn.addActionListener(e -> new BodyMetrics(loggedInUser).setVisible(true));
        exitBtn.addActionListener(e -> System.exit(0));

        setContentPane(panel);
    }
}
