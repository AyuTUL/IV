package org.personal.workoutlogger.ui.windows;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import org.personal.workoutlogger.model.Exercise;
import org.personal.workoutlogger.dao.ExerciseDao;
import org.personal.workoutlogger.dao.impl.ExerciseDaoImpl;

public class ExerciseWindow extends JFrame {

    private final ExerciseDao dao = new ExerciseDaoImpl();
    private final JTable table = new JTable(new DefaultTableModel(new Object[]{"ID", "Name", "Muscle Group"}, 0));

    public ExerciseWindow() {
        super("Exercises");
        setResizable(true);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(600, 400);
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

        buttons.add(addBtn);
        buttons.add(editBtn);
        buttons.add(delBtn);
        buttons.add(exitBtn);
        add(buttons, BorderLayout.SOUTH);
    }

    private void loadData() {
        DefaultTableModel m = (DefaultTableModel) table.getModel();
        m.setRowCount(0);
        for (Exercise ex : dao.getAll()) {
            m.addRow(new Object[]{ex.getId(), ex.getName(), ex.getMuscleGroup()});
        }
    }

    private void onAdd() {
        JTextField name = new JTextField();
        JTextField group = new JTextField();
        int ok = JOptionPane.showConfirmDialog(this, new Object[]{"Name:", name, "Muscle Group:", group}, "Add Exercise", JOptionPane.OK_CANCEL_OPTION);
        if (ok == JOptionPane.OK_OPTION) {
            Exercise ex = new Exercise(0, name.getText().trim(), group.getText().trim());
            dao.add(ex);
            loadData();
        }
    }

    private void onEdit() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Select a row");
            return;
        }
        int id = (int) table.getValueAt(row, 0);
        JTextField name = new JTextField(String.valueOf(table.getValueAt(row, 1)));
        JTextField group = new JTextField(String.valueOf(table.getValueAt(row, 2)));
        int ok = JOptionPane.showConfirmDialog(this, new Object[]{"Name:", name, "Muscle Group:", group}, "Edit Exercise", JOptionPane.OK_CANCEL_OPTION);
        if (ok == JOptionPane.OK_OPTION) {
            Exercise ex = new Exercise(id, name.getText().trim(), group.getText().trim());
            dao.update(ex);
            loadData();
        }
    }

    private void onDelete() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Select a row");
            return;
        }
        int id = (int) table.getValueAt(row, 0);
        if (JOptionPane.showConfirmDialog(this, "Delete selected exercise?", "Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            dao.delete(id);
            loadData();
        }
    }
}
