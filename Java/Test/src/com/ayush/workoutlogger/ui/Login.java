package com.ayush.workoutlogger.ui;

import javax.swing.*;
import java.awt.*;
import com.ayush.workoutlogger.dao.UserDao;
import com.ayush.workoutlogger.dao.impl.UserDaoListImpl;
import com.ayush.workoutlogger.model.User;

public class Login extends JFrame {
    private final JTextField txtUser = new JTextField(15);
    private final JPasswordField txtPass = new JPasswordField(15);
    private final JButton btnLogin = new JButton("Login");

    private final UserDao userDao = new UserDaoListImpl(); // will be replaced with DB impl when available

    public Login() {
        super("WorkoutLogger - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(360, 180);
        setLocationRelativeTo(null);

        JPanel p = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5,5,5,5);
        c.gridx = 0; c.gridy = 0; c.anchor = GridBagConstraints.LINE_END; p.add(new JLabel("Username:"), c);
        c.gridx = 1; c.anchor = GridBagConstraints.LINE_START; p.add(txtUser, c);
        c.gridx = 0; c.gridy = 1; c.anchor = GridBagConstraints.LINE_END; p.add(new JLabel("Password:"), c);
        c.gridx = 1; c.anchor = GridBagConstraints.LINE_START; p.add(txtPass, c);
        c.gridx = 1; c.gridy = 2; c.anchor = GridBagConstraints.CENTER; p.add(btnLogin, c);

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
        User user = userDao.findByUsername(u);
        if (user != null && p.equals(user.getPassword())) {
            JOptionPane.showMessageDialog(this, "Welcome " + user.getUsername() + " (" + user.getRole() + ")");
            new Dashboard(user).setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid credentials.");
        }
    }
}
