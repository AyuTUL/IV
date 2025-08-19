package com.ayush.workoutlogger;

import com.ayush.workoutlogger.ui.Login;
import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {
        System.out.println("Workout Logger running!");
        SwingUtilities.invokeLater(() -> new Login().setVisible(true));
    }
}
