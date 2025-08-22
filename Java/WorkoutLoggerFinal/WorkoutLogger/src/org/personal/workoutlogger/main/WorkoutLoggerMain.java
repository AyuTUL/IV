package org.personal.workoutlogger.main;

import javax.swing.SwingUtilities;
import org.personal.workoutlogger.ui.LoginFrame;

public class WorkoutLoggerMain {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}
