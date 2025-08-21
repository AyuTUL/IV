package org.personal.workoutlogger.ui.windows;

import javax.swing.*;
import java.awt.*;
import org.personal.workoutlogger.model.User;
import org.personal.workoutlogger.dao.UserDao;
import org.personal.workoutlogger.dao.impl.UserDaoImpl;

public class UserInfoWindow extends JFrame {
    private final User user;
    private final UserDao userDao = new UserDaoImpl();

    private final JTextField tfUsername = new JTextField();
    private final JComboBox<String> cbRole = new JComboBox<>(new String[]{"TRAINEE","TRAINER"});
    private final JPasswordField pfPassword = new JPasswordField();

    public UserInfoWindow(User u) {
        super("User Info");
        this.user = u;
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(400, 250);
        setLocationRelativeTo(null);
        initUI();
        loadData();
    }

    private void initUI() {
        setLayout(new BorderLayout());
        JPanel form = new JPanel(new GridLayout(0,2,8,8));
        form.setBorder(BorderFactory.createEmptyBorder(16,16,16,16));

        form.add(new JLabel("Username:")); form.add(tfUsername);
        form.add(new JLabel("Role:")); form.add(cbRole);
        form.add(new JLabel("New Password:")); form.add(pfPassword);

        JPanel buttons = new JPanel();
        JButton addBtn = new JButton("Add");
        JButton editBtn = new JButton("Edit");
        JButton delBtn = new JButton("Delete");
        JButton exitBtn = new JButton("Exit");

        // For demo, Add creates a user with given fields; Edit updates only password/role for current user.
        addBtn.addActionListener(e -> onAdd());
        editBtn.addActionListener(e -> onEdit());
        delBtn.addActionListener(e -> onDelete());
        exitBtn.addActionListener(e -> dispose());

        add(form, BorderLayout.CENTER);
        JPanel south = new JPanel();
        south.add(addBtn); south.add(editBtn); south.add(delBtn); south.add(exitBtn);
        add(south, BorderLayout.SOUTH);
    }

    private void loadData() {
        tfUsername.setText(user.getUsername());
        cbRole.setSelectedItem(user.getRole());
    }

    private void onAdd() {
        String username = tfUsername.getText().trim();
        String role = String.valueOf(cbRole.getSelectedItem());
        String pass = new String(pfPassword.getPassword());
        if (username.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Username and password required.");
            return;
        }
        try {
            boolean ok = userDao.createUser(username, pass, role);
            JOptionPane.showMessageDialog(this, ok ? "User created." : "User already exists.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void onEdit() {
        // In this simplified example, allow changing the password/role for the logged-in user via createUser logic isn't ideal.
        // A robust implementation would add an explicit update method in UserDao. Here we'll just display a message.
        try {
            String username = tfUsername.getText().trim();
            String role = String.valueOf(cbRole.getSelectedItem());
            String pass = new String(pfPassword.getPassword());
            if (username.isEmpty() || pass.isEmpty()) { JOptionPane.showMessageDialog(this,"Username and password required."); return; }
            boolean ok = userDao.updateUser(user.getId(), username, pass, role);
            if (ok) { JOptionPane.showMessageDialog(this,"Updated."); user.setUsername(username); user.setRole(role); }
            else { JOptionPane.showMessageDialog(this,"No changes made."); }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void onDelete() {
        int confirm = JOptionPane.showConfirmDialog(this, "Delete your user account? This cannot be undone.", "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                boolean ok = userDao.deleteUser(user.getId());
                JOptionPane.showMessageDialog(this, ok ? "User deleted. Please restart the application." : "Delete failed.");
                if (ok) dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        }
    }
}
