package org.personal.workoutmgmt.main;

import org.personal.workoutmgmt.ui.Login;

public class WorkoutLoggerMain {
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login().setVisible(true);
            }
        });
    }
}
