package com.ayush.workoutlogger.ui;

import com.ayush.workoutlogger.model.User;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Dashboard extends JFrame {

    private JButton btnLogWorkout;
    private JButton btnSessions;
    private JButton btnBodyMetrics;
    private JButton btnReports;
    private JButton btnExportCsv;
    private JButton btnExit;

    public Dashboard() {
        setTitle("🏋️ Workout Logger Dashboard");
        setSize(700, 460);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel grid = new JPanel(new GridLayout(3, 2, 16, 16));
        grid.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        btnLogWorkout = new JButton("Log Workout");
        btnSessions = new JButton("Sessions");
        btnBodyMetrics = new JButton("Body Metrics");
        btnReports = new JButton("Reports");
        btnExportCsv = new JButton("Export CSV");
        btnExit = new JButton("Exit");

        JButton[] arr = {btnLogWorkout, btnSessions, btnBodyMetrics, btnReports, btnExportCsv, btnExit};
        for (JButton b : arr) {
            b.setFocusPainted(false);
            b.setBackground(new Color(60, 120, 200));
            b.setForeground(Color.WHITE);
            b.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
            grid.add(b);
        }

        add(grid);

        btnLogWorkout.addActionListener(this::openLogWorkout);
        btnSessions.addActionListener(e -> openSessions());
        btnBodyMetrics.addActionListener(e -> openBodyMetrics());
        btnReports.addActionListener(e -> openReports());
        btnExportCsv.addActionListener(e -> openExport());
        btnExit.addActionListener(e -> dispose());
    }

    Dashboard(User user) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    void openLogWorkout(ActionEvent e) {
        new WorkoutLogger().setVisible(true);
    }

    private void openSessions() {
        new Sessions().setVisible(true);
    }

    private void openBodyMetrics() {
        new BodyMetrics().setVisible(true);
    }

    private void openReports() {
        new Reports().setVisible(true);
    }

    private void openExport() {
        new ExportCSV().setVisible(true);
    }
}
