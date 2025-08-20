package com.ayush.workoutlogger.ui;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

import com.ayush.workoutlogger.dao.BodyMetricDao;
import com.ayush.workoutlogger.dao.ExerciseDao;
import com.ayush.workoutlogger.dao.UserDao;
import com.ayush.workoutlogger.dao.WorkoutSessionDao;
import com.ayush.workoutlogger.dao.impl.DaoProvider;
import com.ayush.workoutlogger.model.BodyMetric;
import com.ayush.workoutlogger.model.User;
import com.ayush.workoutlogger.model.WorkoutSession;

public class Reports extends JFrame {
    private final ExerciseDao exerciseDao = DaoProvider.getExerciseDao();
    private final WorkoutSessionDao sessionDao = DaoProvider.getWorkoutSessionDao();
    private final BodyMetricDao metricDao = DaoProvider.getBodyMetricDao();
    private final UserDao userDao = DaoProvider.getUserDao();

    private final JLabel lblExercises = new JLabel("0");
    private final JLabel lblSessions30 = new JLabel("0");
    private final JLabel lblMetrics30 = new JLabel("0");

    public Reports() {
        super("Reports");
        setSize(500, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initUI();
        loadData();
    }

    private void initUI() {
        setLayout(new GridLayout(3, 2, 10, 10));
        add(new JLabel("Total Exercises:")); add(lblExercises);
        add(new JLabel("Sessions (last 30 days):")); add(lblSessions30);
        add(new JLabel("Body metrics entries (last 30 days):")); add(lblMetrics30);
    }

    private void loadData() {
        try {
            int exCount = exerciseDao.findAll().size();
            lblExercises.setText(String.valueOf(exCount));

            LocalDate to = LocalDate.now();
            LocalDate from = to.minusDays(30);

            int sessions = 0, metrics = 0;
            List<User> users = userDao.findAll();
            for (User u : users) {
                List<WorkoutSession> ss = sessionDao.findByUserAndDateRange(u.getId(), from, to);
                sessions += ss.size();
                List<BodyMetric> ms = metricDao.findByUserAndDateRange(u.getId(), from, to);
                metrics += ms.size();
            }
            lblSessions30.setText(String.valueOf(sessions));
            lblMetrics30.setText(String.valueOf(metrics));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Failed to load reports: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
