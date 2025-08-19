package org.personal.workoutmgmt.ui;

import javax.swing.*;

public class Dashboard extends JFrame {
    public Dashboard() {
        setTitle("Workout Logger - Dashboard");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        JLabel label = new JLabel("Dashboard Placeholder");
        add(label);
    }
}
