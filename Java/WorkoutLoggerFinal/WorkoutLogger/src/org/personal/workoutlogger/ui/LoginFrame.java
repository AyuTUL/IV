package org.personal.workoutlogger.ui;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import org.personal.workoutlogger.dao.UserDao;
import org.personal.workoutlogger.dao.impl.UserDaoImpl;
import org.personal.workoutlogger.model.User;

public class LoginFrame extends JFrame {

    private final JTextField tfUser = new JTextField(16);
    private final JPasswordField pfPass = new JPasswordField(16);
    private final JRadioButton rbTrainee = new JRadioButton("Trainee", true);
    private final JRadioButton rbTrainer = new JRadioButton("Trainer", false);
    private final ButtonGroup roleGroup = new ButtonGroup();

    private final JButton btnLogin = new JButton("Log In");
    private final JButton btnSignup = new JButton("Sign Up");
    private final JButton btnExit = new JButton("Exit");

    private final UserDao userDao = new UserDaoImpl();

    public LoginFrame() {
        super("Workout Logger - Login");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(420, 300);
        setLocationRelativeTo(null);
        setResizable(true);

        JPanel form = new JPanel();
        form.setLayout(new GridBagLayout());
        form.setBorder(BorderFactory.createEmptyBorder(12,12,12,12));
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(8,8,8,8);
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.weightx = 0;
        gc.anchor = GridBagConstraints.EAST;
        gc.gridx = 0; gc.gridy = 0; gc.weightx = 0; gc.anchor = GridBagConstraints.EAST; form.add(new JLabel("Username"), gc);
        gc.gridx = 1; gc.weightx = 1.0; gc.anchor = GridBagConstraints.WEST; form.add(tfUser, gc);

        gc.gridx = 0; gc.gridy = 1; gc.weightx = 0; gc.anchor = GridBagConstraints.EAST; form.add(new JLabel("Password"), gc);
        gc.gridx = 1; gc.weightx = 1.0; gc.anchor = GridBagConstraints.WEST; form.add(pfPass, gc);

        roleGroup.add(rbTrainee);
        roleGroup.add(rbTrainer);
        JPanel roles = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        // moved label out for alignment
        
        roles.add(rbTrainee);
        roles.add(rbTrainer);

        gc.gridx = 0; gc.gridy = 2; gc.gridwidth = 1; gc.weightx = 0; gc.anchor = GridBagConstraints.EAST; form.add(new JLabel("Role"), gc);
        gc.gridx = 1; gc.gridwidth = 1; gc.weightx = 1.0; gc.anchor = GridBagConstraints.WEST; form.add(roles, gc);

        JPanel actionsTop = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 0));
        actionsTop.add(btnLogin);
        actionsTop.add(btnSignup);

        JPanel actions = new JPanel();
        actions.setLayout(new BoxLayout(actions, BoxLayout.Y_AXIS));
        actions.add(actionsTop);
        JPanel exitPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 6));
        exitPanel.add(btnExit);
        actions.add(exitPanel);

        add(form, BorderLayout.CENTER);
        add(actions, BorderLayout.SOUTH);
        getRootPane().setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        pack();
        setMinimumSize(new Dimension(420, getHeight()));

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
