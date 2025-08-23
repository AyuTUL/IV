
package org.personal.workoutlogger.ui;

import javax.swing.*;
import java.awt.*;
import org.personal.workoutlogger.dao.UserDao;
import org.personal.workoutlogger.dao.impl.UserDaoImpl;
import org.personal.workoutlogger.model.User;

public class LoginFrame extends JFrame {

    private final JTextField tfUser = new JTextField(16);
    private final JPasswordField pfPass = new JPasswordField(16);
    private final JRadioButton rbTrainee = new JRadioButton("Trainee", true);
    private final JRadioButton rbTrainer = new JRadioButton("Trainer", false);
    private final JButton btnLogin = GlobalUISettings.createStyledButton("Log In");
    private final JButton btnSignup = GlobalUISettings.createStyledButton("Sign Up");
    private final JButton btnExit = GlobalUISettings.createStyledButton("Exit");
    private final ButtonGroup roleGroup = new ButtonGroup();

    private final UserDao userDao = new UserDaoImpl();

    private ImageIcon loadImage() {
        try {
            ImageIcon icon = new ImageIcon("loginimg.png");
            Image img = icon.getImage().getScaledInstance(250, 250, Image.SCALE_SMOOTH);
            return new ImageIcon(img);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public LoginFrame() {
        GlobalUISettings.setupFrame(this, "Workout Logger - Login");
        setSize(640, 400);

        // Create left panel for image
        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.setBackground(GlobalUISettings.WINDOW_BG);
        imagePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Add image
        ImageIcon icon = loadImage();
        if (icon != null) {
            JLabel imageLabel = new JLabel(icon);
            imagePanel.add(imageLabel, BorderLayout.CENTER);
        }

        // Create right panel for form
        JPanel formWrapper = new JPanel(new GridBagLayout());
        formWrapper.setBackground(GlobalUISettings.WINDOW_BG);
        JPanel form = new JPanel();
        form.setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(10, 10, 10, 10);
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.weightx = 0;
        gc.anchor = GridBagConstraints.EAST;
        gc.gridx = 0;
        gc.gridy = 0;
        gc.weightx = 0;
        gc.anchor = GridBagConstraints.EAST;
        form.add(new JLabel("Username"), gc);
        gc.gridx = 1;
        gc.weightx = 1.0;
        gc.anchor = GridBagConstraints.WEST;
        form.add(tfUser, gc);

        gc.gridx = 0;
        gc.gridy = 1;
        gc.weightx = 0;
        gc.anchor = GridBagConstraints.EAST;
        form.add(new JLabel("Password"), gc);
        gc.gridx = 1;
        gc.weightx = 1.0;
        gc.anchor = GridBagConstraints.WEST;
        form.add(pfPass, gc);

        roleGroup.add(rbTrainee);
        roleGroup.add(rbTrainer);
        JPanel roles = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        roles.setOpaque(false);
        roles.add(rbTrainee);
        roles.add(rbTrainer);

        gc.gridx = 0;
        gc.gridy = 2;
        gc.gridwidth = 1;
        gc.weightx = 0;
        gc.anchor = GridBagConstraints.EAST;
        form.add(new JLabel("Role"), gc);
        gc.gridx = 1;
        gc.gridwidth = 1;
        gc.weightx = 1.0;
        gc.anchor = GridBagConstraints.WEST;
        form.add(roles, gc);

        JPanel buttons = new JPanel();
        buttons.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.GRAY));
        buttons.add(btnLogin);
        buttons.add(btnSignup);
        buttons.add(btnExit);

        formWrapper.add(form);

        // Main content panel with image and form
        JPanel content = new JPanel(new GridLayout(1, 2, 20, 0));
        content.setBackground(GlobalUISettings.WINDOW_BG);
        content.add(imagePanel);
        content.add(formWrapper);

        add(content, BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);
        getRootPane().setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(640, 400));

        // Events
        btnExit.addActionListener(e -> System.exit(0));
        btnLogin.addActionListener(e -> onLogin());
        btnSignup.addActionListener(e -> onSignup());
        getRootPane().setDefaultButton(btnLogin);
    }

    private void onLogin() {
        String username = tfUser.getText().trim();
        String password = new String(pfPass.getPassword());
        String role = rbTrainer.isSelected() ? "TRAINER" : "TRAINEE";
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter username and password.");
            return;
        }
        try {
            User u = userDao.login(username, password, role);
            if (u == null) {
                JOptionPane.showMessageDialog(this, "Invalid credentials or role.");
                return;
            }
            this.dispose();
            new DashboardFrame(u).setVisible(true);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Login error: " + ex.getMessage());
        }
    }

    private void onSignup() {
        String username = tfUser.getText().trim();
        String password = new String(pfPass.getPassword());
        String role = rbTrainer.isSelected() ? "TRAINER" : "TRAINEE";
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter username and password.");
            return;
        }
        try {
            boolean ok = userDao.createUser(username, password, role);
            JOptionPane.showMessageDialog(this, ok ? "Account created." : "Username already exists.");
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Sign up error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}
