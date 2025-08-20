package com.ayush.workoutlogger.ui;

import javax.swing.*;
import java.awt.*;

public class ExportCSV extends JFrame {
    public ExportCSV() {
        setTitle("Export CSV");
        setSize(700, 480);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(16,16,16,16));
        panel.add(new JLabel("Export CSV - coming soon", SwingConstants.CENTER), BorderLayout.CENTER);
        add(panel);
    }
}
