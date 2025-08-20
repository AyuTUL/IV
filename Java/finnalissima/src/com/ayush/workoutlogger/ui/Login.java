package com.ayush.workoutlogger.ui;

import javax.swing.*;
import java.awt.*;
import com.ayush.workoutlogger.dao.UserDao;
import com.ayush.workoutlogger.dao.impl.DaoProvider;
import com.ayush.workoutlogger.model.User;

public class Login extends JFrame {
    private final JTextField txtUser = new JTextField(15);
    private final JPasswordField txtPass = new JPasswordField(15);
    private final JButton btnLogin = new JButton("Login");

    private final UserDao userDao = DaoProvider.getUserDao(); // auto-selects DB or in-memory

    public Login() {
        super("WorkoutLogger - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(360, 180);
        setLocationRelativeTo(null);

        JPanel p = new JPanel(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(5,5,5,5);
        gc.gridx = 0; gc.gridy = 0; gc.anchor = GridBagConstraints.EAST;
        p.add(new JLabel("Username:"), gc);
        gc.gridx = 1; gc.gridy = 0; gc.anchor = GridBagConstraints.WEST;
        p.add(txtUser, gc);
        gc.gridx = 0; gc.gridy = 1; gc.anchor = GridBagConstraints.EAST;
        p.add(new JLabel("Password:"), gc);
        gc.gridx = 1; gc.gridy = 1; gc.anchor = GridBagConstraints.WEST;
        p.add(txtPass, gc);
        gc.gridx = 1; gc.gridy = 2; gc.anchor = GridBagConstraints.CENTER;
        p.add(btnLogin, gc);

        btnLogin.addActionListener(e -> doLogin());

        setContentPane(p);
    }

    private void doLogin() {
        String u = txtUser.getText().trim();
        String p = new String(txtPass.getPassword());
        if (u.isEmpty() || p.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter username and password.");
            return;
        }
        try {
            User user = userDao.findByUsername(u);
            if (user != null && p.equals(user.getPassword())) {
                JOptionPane.showMessageDialog(this, "Welcome " + user.getUsername() + " (" + user.getRole() + ")");
                new Dashboard(user).setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Login failed: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
