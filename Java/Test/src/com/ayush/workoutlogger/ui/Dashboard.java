package com.ayush.workoutlogger.ui;

import javax.swing.*;
import java.awt.*;
import com.ayush.workoutlogger.model.User;

public class Dashboard extends JFrame {
    private final User currentUser;
    public Dashboard(User user) {
        super("WorkoutLogger - Dashboard");
        this.currentUser = user;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(2,3,10,10));
        panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        panel.add(new JButton("Exercises"));
        panel.add(new JButton("Log Workout"));
        panel.add(new JButton("Sessions"));
        panel.add(new JButton("Body Metrics"));
        panel.add(new JButton("Reports"));
        panel.add(new JButton("Export CSV"));

        setContentPane(panel);
    }
}
