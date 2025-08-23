package org.personal.workoutlogger.main;

import javax.swing.*;
import org.personal.workoutlogger.ui.GlobalUISettings;
import org.personal.workoutlogger.ui.LoginFrame;

public class WorkoutLoggerMain {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GlobalUISettings.setupGlobalUI();
            new LoginFrame().setVisible(true);
        });
    }
}
