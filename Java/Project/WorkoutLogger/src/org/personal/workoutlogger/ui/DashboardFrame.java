
package org.personal.workoutlogger.ui;

import javax.swing.*;
import java.awt.*;
import org.personal.workoutlogger.model.User;
import org.personal.workoutlogger.ui.windows.BodyMetricWindow;
import org.personal.workoutlogger.ui.windows.ExerciseWindow;
import org.personal.workoutlogger.ui.windows.UserInfoWindow;
import org.personal.workoutlogger.ui.windows.WorkoutWindow;

public class DashboardFrame extends JFrame {
    private final User currentUser;

    private ImageIcon loadImage() {
        try {
            String imageName = "TRAINER".equalsIgnoreCase(currentUser.getRole()) ? "trainerimg.png" : "traineeimg.png";
            ImageIcon icon = new ImageIcon(imageName);
            Image img = icon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
            return new ImageIcon(img);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public DashboardFrame(User user) {
        super("Workout Logger - Dashboard");
        this.currentUser = user;
        GlobalUISettings.setupFrame(this, "Workout Logger - Dashboard");
        setSize(800, 600);
        initUI();
    }

    private void initUI() {
        // Create main content panel
        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBackground(GlobalUISettings.WINDOW_BG);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Create left panel for image and user info
        JPanel leftPanel = new JPanel(new GridBagLayout());
        leftPanel.setBackground(GlobalUISettings.WINDOW_BG);
        leftPanel.setPreferredSize(new Dimension(200, 0));

        // Create a vertical panel to hold image and text
        JPanel verticalPanel = new JPanel();
        verticalPanel.setLayout(new BoxLayout(verticalPanel, BoxLayout.Y_AXIS));
        verticalPanel.setBackground(GlobalUISettings.WINDOW_BG);

        // Add image
        ImageIcon icon = loadImage();
        if (icon != null) {
            JLabel imageLabel = new JLabel(icon);
            imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            verticalPanel.add(imageLabel);
            verticalPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Add space between image and text
        }

        // Add user info below the image
        JLabel roleLabel = new JLabel("<html><center>" +
                currentUser.getRole() + "<br>" + currentUser.getUsername() +
                "</center></html>");
        roleLabel.setForeground(Color.BLACK);
        roleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        roleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        roleLabel.setFont(new Font(roleLabel.getFont().getName(), Font.BOLD, 16));
        verticalPanel.add(roleLabel);

        // Center the vertical panel in the left panel
        leftPanel.add(verticalPanel);

        // Create center panel for buttons
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(GlobalUISettings.WINDOW_BG);
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(10, 10, 10, 10);
        gc.fill = GridBagConstraints.BOTH;
        gc.weightx = 1.0;
        gc.weighty = 1.0;

        JButton btnBody = GlobalUISettings.createStyledButton("Body Metrics");
        JButton btnExercise = GlobalUISettings.createStyledButton("Exercises");
        JButton btnUser = GlobalUISettings.createStyledButton("User Info");
        JButton btnWorkout = GlobalUISettings.createStyledButton("Workouts");
        JButton btnLogout = GlobalUISettings.createStyledButton("Logout");

        btnBody.addActionListener(e -> new BodyMetricWindow(currentUser).setVisible(true));
        btnExercise.addActionListener(e -> new ExerciseWindow().setVisible(true));
        btnUser.addActionListener(e -> new UserInfoWindow(currentUser).setVisible(true));
        btnWorkout.addActionListener(e -> new WorkoutWindow(currentUser).setVisible(true));
        btnLogout.addActionListener(e -> {
            this.dispose();
            new LoginFrame().setVisible(true);
        });

        gc.gridx = 0;
        gc.gridy = 0;
        centerPanel.add(btnBody, gc);
        gc.gridx = 1;
        gc.gridy = 0;
        centerPanel.add(btnExercise, gc);
        gc.gridx = 0;
        gc.gridy = 1;
        centerPanel.add(btnUser, gc);
        gc.gridx = 1;
        gc.gridy = 1;
        centerPanel.add(btnWorkout, gc);

        // Create a panel for logout button with GridBagLayout
        JPanel southPanel = new JPanel(new GridBagLayout());
        southPanel.setBackground(GlobalUISettings.WINDOW_BG);

        // Position the logout button in the center
        GridBagConstraints logoutGc = new GridBagConstraints();
        logoutGc.gridx = 0;
        logoutGc.gridy = 0;
        logoutGc.insets = new Insets(10, 0, 10, 0); // Add some vertical padding
        southPanel.add(btnLogout, logoutGc);

        // Add all panels to the main panel
        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(southPanel, BorderLayout.SOUTH);

        // Set the main panel as the content pane
        setContentPane(mainPanel);
    }

}
