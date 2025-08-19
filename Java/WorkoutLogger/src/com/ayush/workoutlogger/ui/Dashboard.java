package com.ayush.workoutlogger.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.List;

import com.ayush.workoutlogger.model.User;
import com.ayush.workoutlogger.model.Exercise;
import com.ayush.workoutlogger.dao.ExerciseDao;
import com.ayush.workoutlogger.dao.impl.DaoProvider; 

public class Dashboard extends JFrame {
    private final User currentUser;
    private final JButton btnExercises = new JButton("Exercises");
    private final JButton btnLogWorkout = new JButton("Log Workout");
    private final JButton btnSessions = new JButton("Sessions");
    private final JButton btnBodyMetrics = new JButton("Body Metrics");
    private final JButton btnReports = new JButton("Reports");
    private final JButton btnExportCsv = new JButton("Export CSV");

    public Dashboard(User user) {
        super("WorkoutLogger - Dashboard");
        this.currentUser = user;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 420);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(2, 3, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.add(btnExercises);
        panel.add(btnLogWorkout);
        panel.add(btnSessions);
        panel.add(btnBodyMetrics);
        panel.add(btnReports);
        panel.add(btnExportCsv);

        // Wire actions
        btnExercises.addActionListener(this::openExercises);
        btnLogWorkout.addActionListener(this::openLogWorkout);
        btnSessions.addActionListener(e -> new Sessions().setVisible(true));
        btnBodyMetrics.addActionListener(e -> new BodyMetrics().setVisible(true));
        btnReports.addActionListener(e -> new Reports().setVisible(true));
        btnExportCsv.addActionListener(this::exportExercisesCsv);

        setContentPane(panel);
    }

    private void openExercises(ActionEvent e) {
        new ExerciseManager().setVisible(true);
    }

    private void openLogWorkout(ActionEvent e) {
        // TODO: implement a dedicated LogWorkout screen later
        JOptionPane.showMessageDialog(this,
                "Open 'Log Workout' screen (to be implemented).",
                "Log Workout",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void exportExercisesCsv(ActionEvent e) {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Export Exercises to CSV");
        if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            java.io.File f = chooser.getSelectedFile();
            if (!f.getName().toLowerCase().endsWith(".csv")) {
                f = new java.io.File(f.getParentFile(), f.getName() + ".csv");
            }
            try (PrintWriter out = new PrintWriter(new FileWriter(f))) {
                ExerciseDao dao = DaoProvider.getExerciseDao();
                List<Exercise> all = dao.findAll();
                out.println("id,name,muscleGroup");
                for (Exercise x : all) {
                    out.printf("%d,%s,%s%n", 
                        x.getId(), escape(x.getName()), escape(x.getMuscleGroup()));
                }
                JOptionPane.showMessageDialog(this,
                        "Exported " + all.size() + " exercises to " + f.getAbsolutePath());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Failed to export: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private String escape(String s) {
        if (s == null) return "";
        if (s.contains(",") || s.contains("\"")) {
            return '"' + s.replace("\"", "\"\"") + '"';
        }
        return s;
    }
}
