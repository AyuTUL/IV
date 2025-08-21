package org.personal.workoutlogger.ui.windows;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;

import org.personal.workoutlogger.dao.WorkoutDao;
import org.personal.workoutlogger.dao.impl.WorkoutDaoImpl;
import org.personal.workoutlogger.model.User;
import org.personal.workoutlogger.model.Workout;
import org.personal.workoutlogger.ui.forms.WorkoutForm;

public class WorkoutWindow extends JFrame {

    private final User user;
    private final WorkoutDao dao = new WorkoutDaoImpl();

    // Table with all four columns we actually use
    private final JTable table = new JTable(
            new DefaultTableModel(new Object[]{"ID", "Name", "Date", "Notes"}, 0) {
                @Override public boolean isCellEditable(int row, int column) { return false; }
            }
    );

    // Only initialized if the logged in user is a trainer
    private JComboBox<org.personal.workoutlogger.model.User> cmbUsers = null;

    public WorkoutWindow(User u) {
        super("Workouts");
        this.user = u;

        // If trainer, allow selecting which user's workouts to view
        if ("TRAINER".equalsIgnoreCase(user.getRole())) {
            cmbUsers = new JComboBox<>();
            for (org.personal.workoutlogger.model.User uu
                    : new org.personal.workoutlogger.dao.impl.UserDaoImpl().getAllUsers()) {
                cmbUsers.addItem(uu);
            }
            cmbUsers.addActionListener(e -> loadData());

            JPanel north = new JPanel(new FlowLayout(FlowLayout.LEFT));
            north.add(new JLabel("Select user:"));
            north.add(cmbUsers);
            add(north, BorderLayout.NORTH);
        }

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(800, 450);
        setLocationRelativeTo(null);

        initUI();
        loadData();
    }

    private void initUI() {
        setLayout(new BorderLayout());
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton btnView = new JButton("View");
        JButton addBtn = new JButton("Add");
        JButton editBtn = new JButton("Edit");
        JButton delBtn = new JButton("Delete");
        JButton exitBtn = new JButton("Exit");

        btnView.addActionListener(e -> onView());
        addBtn.addActionListener(e -> onAdd());
        editBtn.addActionListener(e -> onEdit());
        delBtn.addActionListener(e -> onDelete());
        exitBtn.addActionListener(e -> dispose());

        buttons.add(btnView);
        buttons.add(addBtn);
        buttons.add(editBtn);
        buttons.add(delBtn);
        buttons.add(exitBtn);

        add(buttons, BorderLayout.SOUTH);
    }

    private int getTargetUserId() {
        if (cmbUsers != null && cmbUsers.getSelectedItem() != null) {
            return ((org.personal.workoutlogger.model.User) cmbUsers.getSelectedItem()).getId();
        }
        return user.getId();
    }

    private void loadData() {
        int targetUserId = getTargetUserId();

        DefaultTableModel m = (DefaultTableModel) table.getModel();
        m.setRowCount(0);

        for (Workout w : dao.getWorkoutsByUser(targetUserId)) {
            // Match the table columns: ID, Name, Date, Notes
            m.addRow(new Object[]{w.getId(), w.getName(), w.getDate(), w.getNotes()});
        }
    }

    private void onAdd() {
        WorkoutForm dlg = new WorkoutForm(this, user);
        dlg.setModal(true);
        dlg.setVisible(true);
        loadData();
    }

    private void onEdit() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Select a row to edit.");
            return;
        }

        // Read existing values from the table model (aligned to columns)
        int id = (Integer) table.getValueAt(row, 0);
        String currentName = String.valueOf(table.getValueAt(row, 1));
        String currentDate = String.valueOf(table.getValueAt(row, 2));
        String currentNotes = String.valueOf(table.getValueAt(row, 3));

        // Prefill fields
        JTextField nameF = new JTextField(currentName);
        JTextField dateF = new JTextField(currentDate);      // expects YYYY-MM-DD
        JTextField notesF = new JTextField(currentNotes);

        int ok = JOptionPane.showConfirmDialog(
                this,
                new Object[]{"Name:", nameF, "Date (YYYY-MM-DD):", dateF, "Notes:", notesF},
                "Edit Workout",
                JOptionPane.OK_CANCEL_OPTION
        );

        if (ok == JOptionPane.OK_OPTION) {
            try {
                LocalDate parsedDate = LocalDate.parse(dateF.getText().trim());

                Workout w = new Workout(
                        id,
                        getTargetUserId(),                 // if trainer, acts on selected user
                        parsedDate,
                        nameF.getText().trim(),
                        notesF.getText().trim()
                );

                boolean isTrainer = "TRAINER".equalsIgnoreCase(user.getRole());
                dao.updateWorkout(w, isTrainer);
                loadData();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error updating workout: " + ex.getMessage());
            }
        }
    }

    private void onDelete() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Select a row to delete.");
            return;
        }

        int id = (Integer) table.getValueAt(row, 0);

        if (JOptionPane.showConfirmDialog(this, "Delete selected workout?", "Confirm",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

            boolean isTrainer = "TRAINER".equalsIgnoreCase(user.getRole());
            dao.deleteWorkout(id, user.getId(), isTrainer);
            loadData();
        }
    }

    private void onView() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Select a workout to view.");
            return;
        }
        int id = (Integer) table.getValueAt(row, 0);
        new org.personal.workoutlogger.ui.forms.WorkoutViewDialog(
                this, id, ((org.personal.workoutlogger.dao.impl.WorkoutDaoImpl) dao)
        ).setVisible(true);
        loadData();
    }
}
