
package com.ayush.workoutlogger.ui;

import com.ayush.workoutlogger.dao.ExerciseDao;
import com.ayush.workoutlogger.dao.impl.DaoProvider;
import com.ayush.workoutlogger.model.Exercise;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class ExerciseManager extends JFrame {
    private final ExerciseDao exerciseDao = DaoProvider.getExerciseDao();
    private final DefaultTableModel model = new DefaultTableModel(new Object[]{"ID","Name","Muscle Group"}, 0);
    private final JTable table = new JTable(model);

    public ExerciseManager() {
        super("Exercises");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(560, 420);
        setLocationRelativeTo(null);
        initUI();
        loadExercises();
    }

    private void initUI() {
        setLayout(new BorderLayout());
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton addBtn = new JButton("Add");
        JButton editBtn = new JButton("Edit");
        JButton delBtn = new JButton("Delete");
        bottom.add(addBtn); bottom.add(editBtn); bottom.add(delBtn);
        add(bottom, BorderLayout.SOUTH);

        addBtn.addActionListener(this::addExercise);
        editBtn.addActionListener(this::editExercise);
        delBtn.addActionListener(this::deleteExercise);
    }

    private void loadExercises() {
        model.setRowCount(0);
        List<Exercise> list = exerciseDao.findAll();
        for (Exercise e : list) {
            model.addRow(new Object[]{ e.getId(), e.getName(), e.getMuscleGroup() });
        }
    }

    private void addExercise(ActionEvent e) {
        JTextField name = new JTextField();
        JTextField group = new JTextField();
        Object[] fields = {"Name:", name, "Muscle Group:", group};
        if (JOptionPane.showConfirmDialog(this, fields, "Add Exercise", JOptionPane.OK_CANCEL_OPTION)==JOptionPane.OK_OPTION) {
            try {
                Exercise ex = new Exercise();
                ex.setName(name.getText().trim());
                ex.setMuscleGroup(group.getText().trim());
                exerciseDao.create(ex);
                loadExercises();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Failed to add: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void editExercise(ActionEvent e) {
        int row = table.getSelectedRow();
        if (row==-1) { JOptionPane.showMessageDialog(this, "Select a row."); return; }
        int id = (Integer) model.getValueAt(row, 0);
        JTextField name = new JTextField(model.getValueAt(row,1).toString());
        JTextField group = new JTextField(model.getValueAt(row,2).toString());
        Object[] fields = {"Name:", name, "Muscle Group:", group};
        if (JOptionPane.showConfirmDialog(this, fields, "Edit Exercise", JOptionPane.OK_CANCEL_OPTION)==JOptionPane.OK_OPTION) {
            try {
                Exercise ex = new Exercise();
                ex.setId(id);
                ex.setName(name.getText().trim());
                ex.setMuscleGroup(group.getText().trim());
                exerciseDao.update(ex);
                loadExercises();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Failed to edit: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void deleteExercise(ActionEvent e) {
        int row = table.getSelectedRow();
        if (row==-1) { JOptionPane.showMessageDialog(this, "Select a row."); return; }
        int id = (Integer) model.getValueAt(row, 0);
        if (JOptionPane.showConfirmDialog(this, "Delete selected exercise?", "Confirm", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION) {
            try {
                exerciseDao.delete(id);
                loadExercises();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Failed to delete: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
