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
import org.personal.workoutlogger.ui.forms.WorkoutViewDialog;
import org.personal.workoutlogger.ui.forms.EditWorkoutDialog;
import org.personal.workoutlogger.ui.GlobalUISettings;

public class WorkoutWindow extends JFrame {

    private final User user;
    private final WorkoutDao dao = new WorkoutDaoImpl();
    private final JTable table = new JTable(new DefaultTableModel(new Object[] { "ID", "Name", "Date", "Notes" }, 0));
    private javax.swing.JComboBox<org.personal.workoutlogger.model.User> cmbUsers = null;

    public WorkoutWindow(User u) {
        super();
        GlobalUISettings.setupFrame(this, "Workouts");
        this.user = u;
        setUndecorated(false);
        setSize(820, 480);
        initUI();
        loadData();
    }

    private void initUI() {
        setLayout(new BorderLayout());

        if ("TRAINER".equalsIgnoreCase(user.getRole())) {
            cmbUsers = new javax.swing.JComboBox<>();
            for (org.personal.workoutlogger.model.User uu : new org.personal.workoutlogger.dao.impl.UserDaoImpl()
                    .getAllUsers()) {
                cmbUsers.addItem(uu);
            }
            cmbUsers.addActionListener(e -> loadData());
            JPanel north = new JPanel(new FlowLayout(FlowLayout.LEFT));
            north.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
            north.add(new JLabel("Select user:"));
            north.add(cmbUsers);
            add(north, BorderLayout.NORTH);
        }

        table.setRowHeight(22);
        table.setFillsViewportHeight(true);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttons.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        JButton addBtn = GlobalUISettings.createStyledButton("Add");
        JButton viewBtn = GlobalUISettings.createStyledButton("View");
        JButton editBtn = GlobalUISettings.createStyledButton("Edit");
        JButton delBtn = GlobalUISettings.createStyledButton("Delete");
        JButton exitBtn = GlobalUISettings.createStyledButton("Close");

        addBtn.addActionListener(e -> onAdd());
        viewBtn.addActionListener(e -> onView());
        editBtn.addActionListener(e -> onEdit());
        delBtn.addActionListener(e -> onDelete());
        exitBtn.addActionListener(e -> dispose());

        buttons.add(addBtn);
        buttons.add(viewBtn);
        buttons.add(editBtn);
        buttons.add(delBtn);
        buttons.add(exitBtn);
        add(buttons, BorderLayout.SOUTH);
    }

    private int getSelectedUserId() {
        if ("TRAINER".equalsIgnoreCase(user.getRole()) && cmbUsers != null && cmbUsers.getSelectedItem() != null) {
            return ((org.personal.workoutlogger.model.User) cmbUsers.getSelectedItem()).getId();
        }
        return user.getId();
    }

    private void loadData() {
        DefaultTableModel m = (DefaultTableModel) table.getModel();
        m.setRowCount(0);
        int uid = getSelectedUserId();
        for (Workout w : dao.getWorkoutsByUser(uid)) {
            m.addRow(new Object[] { w.getId(), w.getName(), w.getDate(), w.getNotes() });
        }
    }

    private void onAdd() {
        WorkoutForm dlg = new WorkoutForm(this, user);
        dlg.setModal(true);
        dlg.setVisible(true);
        loadData();
    }

    private void onView() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Select a workout");
            return;
        }
        int id = (int) table.getValueAt(row, 0);
        String name = String.valueOf(table.getValueAt(row, 1));
        LocalDate date = LocalDate.parse(String.valueOf(table.getValueAt(row, 2)));
        String notes = String.valueOf(table.getValueAt(row, 3));
        Workout w = new Workout(id, getSelectedUserId(), date, name, notes);
        new WorkoutViewDialog(this, w, (WorkoutDaoImpl) dao).setVisible(true);
    }

    private void onEdit() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Select a workout");
            return;
        }
        int id = (int) table.getValueAt(row, 0);
        String name = String.valueOf(table.getValueAt(row, 1));
        LocalDate date = LocalDate.parse(String.valueOf(table.getValueAt(row, 2)));
        String notes = String.valueOf(table.getValueAt(row, 3));
        Workout w = new Workout(id, getSelectedUserId(), date, name, notes);
        EditWorkoutDialog dlg = new EditWorkoutDialog(this, w, (WorkoutDaoImpl) dao);
        dlg.setModal(true);
        dlg.setVisible(true);
        loadData();
    }

    private void onDelete() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Select a row");
            return;
        }
        int id = (int) table.getValueAt(row, 0);
        if (JOptionPane.showConfirmDialog(this, "Delete selected workout?", "Confirm",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            dao.deleteWorkout(id, user.getId(), "TRAINER".equalsIgnoreCase(user.getRole()));
            loadData();
        }
    }
}