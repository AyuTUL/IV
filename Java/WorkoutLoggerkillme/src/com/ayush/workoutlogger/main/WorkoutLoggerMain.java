package com.ayush.workoutlogger.main;

import com.ayush.workoutlogger.ui.Login;
import javax.swing.SwingUtilities;

public class WorkoutLoggerMain {

    public static void main (String[] args) {
        SwingUtilities.invokeLater(() -> new Login().setVisible(true));
    }
}
