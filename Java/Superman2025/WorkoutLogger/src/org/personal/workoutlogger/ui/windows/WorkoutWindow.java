package org.personal.workoutlogger.ui.windows;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import org.personal.workoutlogger.model.User;
import org.personal.workoutlogger.model.Workout;
import org.personal.workoutlogger.dao.WorkoutDao;
import org.personal.workoutlogger.dao.impl.WorkoutDaoImpl;
import org.personal.workoutlogger.ui.forms.WorkoutForm;

public class WorkoutWindow extends JFrame {
    private final User user;
    private final WorkoutDao dao = new WorkoutDaoImpl();
    private final JTable table = new JTable(new DefaultTableModel(new Object[]{"ID","Date","Notes"},0));
  private javax.swing.JComboBox<org.personal.workoutlogger.model.User> cmbUsers = null;

    public WorkoutWindow(User u) {
        super("Workouts");
        this.user = u;
    if ("TRAINER".equalsIgnoreCase(user.getRole())) {
        cmbUsers = new javax.swing.JComboBox<>();
        // load users
        for (org.personal.workoutlogger.model.User uu : new org.personal.workoutlogger.dao.impl.UserDaoImpl().getAllUsers()) {
            cmbUsers.addItem(uu);
        }
        cmbUsers.addActionListener(e -> loadData());
        JPanel north = new JPanel(new FlowLayout(FlowLayout.LEFT)); north.add(new JLabel("Select user:")); north.add(cmbUsers);
        add(north, BorderLayout.NORTH);
    }

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(700, 400);
        setLocationRelativeTo(null);
        initUI();
        loadData();
    }

    private void initUI() {
        setLayout(new BorderLayout());
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel buttons = new JPanel();
        JButton addBtn = new JButton("Add");
        JButton editBtn = new JButton("Edit");
        JButton delBtn = new JButton("Delete");
        JButton exitBtn = new JButton("Exit");

        addBtn.addActionListener(e -> onAdd());
        editBtn.addActionListener(e -> onEdit());
        delBtn.addActionListener(e -> onDelete());
        exitBtn.addActionListener(e -> dispose());

        buttons.add(addBtn); buttons.add(editBtn); buttons.add(delBtn); buttons.add(exitBtn);
        add(buttons, BorderLayout.SOUTH);
    }

    private void loadData() {
        DefaultTableModel m = (DefaultTableModel) table.getModel();
        m.setRowCount(0);
        for (Workout w : dao.getWorkoutsByUser(user.getId())) {
            m.addRow(new Object[]{w.getId(), w.getDate(), w.getNotes()});
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
        if (row < 0) { JOptionPane.showMessageDialog(this, "Select a row"); return; }
        int id = (int) table.getValueAt(row, 0);
        String date = String.valueOf(table.getValueAt(row, 1));
        String notes = String.valueOf(table.getValueAt(row, 2));

        JTextField dateF = new JTextField(date);
        JTextField notesF = new JTextField(notes);

        int ok = JOptionPane.showConfirmDialog(this, new Object[]{"Date (YYYY-MM-DD):", dateF, "Notes:", notesF}, "Edit Workout", JOptionPane.OK_CANCEL_OPTION);
        if (ok == JOptionPane.OK_OPTION) {
            // NOTE: WorkoutDao has no update method in current design. We would need to add it.
            try {
            org.personal.workoutlogger.model.Workout w = new org.personal.workoutlogger.model.Workout(id, user.getId(), java.time.LocalDate.parse(dateF.getText().trim()), notesF.getText().trim());
            dao.updateWorkout(w, "TRAINER".equalsIgnoreCase(user.getRole()));
            loadData();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
        }
    }

    private void onDelete() {
        int row = table.getSelectedRow();
        if (row < 0) { JOptionPane.showMessageDialog(this, "Select a row"); return; }
        int id = (int) table.getValueAt(row, 0);
        if (JOptionPane.showConfirmDialog(this, "Delete selected workout?", "Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            dao.deleteWorkout(id, user.getId(), false);
            loadData();
        }
    }
}
