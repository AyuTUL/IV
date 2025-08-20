package com.ayush.workoutlogger.ui;

import javax.swing.*;
import java.awt.*;

public class Reports extends JFrame {

    public Reports() {
        super("Reports");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());

        JLabel lbl = new JLabel("Reports feature coming soon...", SwingConstants.CENTER);
        lbl.setFont(new Font("Arial", Font.PLAIN, 16));
        add(lbl, BorderLayout.CENTER);
    }
}
