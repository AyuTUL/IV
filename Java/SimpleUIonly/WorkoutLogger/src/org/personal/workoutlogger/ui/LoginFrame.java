package org.personal.workoutlogger.ui;

import javax.swing.*;
import java.awt.*;
import org.personal.workoutlogger.dao.UserDao;
import org.personal.workoutlogger.dao.impl.UserDaoImpl;
import org.personal.workoutlogger.model.User;
import org.personal.workoutlogger.ui.dash.TrainerDashboard;
import org.personal.workoutlogger.ui.dash.TraineeDashboard;
import org.personal.workoutlogger.ui.DashboardFrame;

public class LoginFrame extends JFrame {
    private final JTextField tfUser = new JTextField(15);
    private final JPasswordField pfPass = new JPasswordField(15);

    // Role selection under password
    private final JRadioButton rbTrainer = new JRadioButton("Trainer");
    private final JRadioButton rbTrainee = new JRadioButton("Trainee", true);
    private final ButtonGroup roleGroup = new ButtonGroup();

    private final JButton btnLogin = new JButton("Log In");
    private final JButton btnSignup = new JButton("Sign Up");

    private final UserDao userDao = new UserDaoImpl();

    public LoginFrame() {
        super("Workout Logger - Login");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 260);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 8, 6, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Username
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1;
        panel.add(tfUser, gbc);

        // Password
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        panel.add(pfPass, gbc);

        // Role (below password)
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Role:"), gbc);
        JPanel rolePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        roleGroup.add(rbTrainer);
        roleGroup.add(rbTrainee);
        rolePanel.add(rbTrainer);
        rolePanel.add(rbTrainee);
        gbc.gridx = 1;
        panel.add(rolePanel, gbc);

        // Buttons row
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, 16, 0));
        buttons.add(btnSignup);
        buttons.add(btnLogin);
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        panel.add(buttons, gbc);

        add(panel);

        // Actions
        btnLogin.addActionListener(e -> doLogin());
        btnSignup.addActionListener(e -> openSignupDialog());
    }

    private void doLogin() {
        String username = tfUser.getText().trim();
        String password = new String(pfPass.getPassword());
        String role = rbTrainer.isSelected() ? "TRAINER" : "TRAINEE";

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter username and password.", "Missing info", JOptionPane.WARNING_MESSAGE);
            return;
        }

        User user = userDao.login(username, password, role);
        if (user != null) {
            JOptionPane.showMessageDialog(this, "Login successful as " + (role.equals("TRAINER") ? "Trainer" : "Trainee"));
            if ("TRAINER".equals(role)) {
                new DashboardFrame(user).setVisible(true);
            } else {
                new DashboardFrame(user).setVisible(true);
            }
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid credentials or role.", "Login failed", JOptionPane.ERROR_MESSAGE);
        }
        pfPass.setText("");
    }

    private void openSignupDialog() {
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JPasswordField confirmField = new JPasswordField();
        JRadioButton trainer = new JRadioButton("Trainer");
        JRadioButton trainee = new JRadioButton("Trainee", true);
        ButtonGroup g = new ButtonGroup();
        g.add(trainer); g.add(trainee);

        JPanel p = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5,5,5,5);
        c.fill = GridBagConstraints.HORIZONTAL;

        c.gridx=0; c.gridy=0; p.add(new JLabel("Username:"), c);
        c.gridx=1; p.add(usernameField, c);
        c.gridx=0; c.gridy=1; p.add(new JLabel("Password:"), c);
        c.gridx=1; p.add(passwordField, c);
        c.gridx=0; c.gridy=2; p.add(new JLabel("Confirm:"), c);
        c.gridx=1; p.add(confirmField, c);
        c.gridx=0; c.gridy=3; p.add(new JLabel("Role:"), c);
        JPanel rp = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        rp.add(trainer); rp.add(trainee);
        c.gridx=1; p.add(rp, c);

        int res = JOptionPane.showConfirmDialog(this, p, "Sign Up", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (res == JOptionPane.OK_OPTION) {
            String u = usernameField.getText().trim();
            String pw = new String(passwordField.getPassword());
            String cpw = new String(confirmField.getPassword());
            String role = trainer.isSelected() ? "TRAINER" : "TRAINEE";

            if (u.isEmpty() || pw.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all fields.", "Missing info", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (!pw.equals(cpw)) {
                JOptionPane.showMessageDialog(this, "Passwords do not match.", "Mismatch", JOptionPane.WARNING_MESSAGE);
                return;
            }
            try {
                boolean ok = userDao.createUser(u, pw, role);
                if (ok) {
                    JOptionPane.showMessageDialog(this, "Account created! You can now log in.");
                    tfUser.setText(u);
                    pfPass.setText("");
                    rbTrainee.setSelected("TRAINEE".equals(role));
                    rbTrainer.setSelected("TRAINER".equals(role));
                } else {
                    JOptionPane.showMessageDialog(this, "Username already exists.", "Sign up failed", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Sign up failed", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}
