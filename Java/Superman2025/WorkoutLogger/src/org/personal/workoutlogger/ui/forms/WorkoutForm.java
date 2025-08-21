package org.personal.workoutlogger.ui.forms;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import org.personal.workoutlogger.model.*;
import org.personal.workoutlogger.dao.*;
import org.personal.workoutlogger.dao.impl.*;

public class WorkoutForm extends JDialog {

    private final User user;
    private final WorkoutDao workoutDao = new WorkoutDaoImpl();
    private final ExerciseDao exerciseDao = new ExerciseDaoImpl();

    private JTextField tfDate = new JTextField(10);
    private JTextArea taNotes = new JTextArea(3, 30);
    private JComboBox<ComboItem> cbExercise = new JComboBox<>();
    private JSpinner spSets = new JSpinner(new SpinnerNumberModel(3, 1, 20, 1));
    private JSpinner spReps = new JSpinner(new SpinnerNumberModel(10, 1, 50, 1));
    private JSpinner spWeight = new JSpinner(new SpinnerNumberModel(0.0, 0.0, 1000.0, 2.5));
    private JSpinner spRest = new JSpinner(new SpinnerNumberModel(60, 0, 600, 5));

    // Table for workout items
    private javax.swing.table.DefaultTableModel itemsModel;
    private JTable itemsTable;

    public WorkoutForm(Frame owner, User user) {
        super(owner, "Add Workout", true);
        this.user = user;
        setSize(600, 400);
        setLocationRelativeTo(owner);

        JPanel p = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4, 4, 4, 4);
        c.anchor = GridBagConstraints.WEST;
        int y = 0;

        c.gridx = 0;
        c.gridy = y;
        p.add(new JLabel("Date (YYYY-MM-DD):"), c);
        c.gridx = 1;
        p.add(tfDate, c);
        y++;

        c.gridx = 0;
        c.gridy = y;
        p.add(new JLabel("Notes:"), c);
        c.gridx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        p.add(new JScrollPane(taNotes), c);
        y++;

        c.fill = GridBagConstraints.NONE;
        c.gridx = 0;
        c.gridy = y;
        p.add(new JLabel("Exercise:"), c);
        c.gridx = 1;
        p.add(cbExercise, c);
        y++;

        c.gridx = 0;
        c.gridy = y;
        p.add(new JLabel("Sets:"), c);
        c.gridx = 1;
        p.add(spSets, c);
        y++;

        c.gridx = 0;
        c.gridy = y;
        p.add(new JLabel("Reps:"), c);
        c.gridx = 1;
        p.add(spReps, c);
        y++;

        c.gridx = 0;
        c.gridy = y;
        p.add(new JLabel("Weight (kg):"), c);
        c.gridx = 1;
        p.add(spWeight, c);
        y++;

        c.gridx = 0;
        c.gridy = y;
        p.add(new JLabel("Rest (sec):"), c);
        c.gridx = 1;
        p.add(spRest, c);
        y++;

        JButton btnSave = new JButton("Save Workout");
        btnSave.addActionListener(e -> saveWorkout());
        c.gridx = 0;
        c.gridy = y;
        c.gridwidth = 2;
        p.add(btnSave, c);

        setContentPane(p);

        loadExercises();
        tfDate.setText(LocalDate.now().toString());

        // Items table
        itemsModel = new javax.swing.table.DefaultTableModel(
                new Object[]{"ExerciseId", "Exercise", "Sets", "Reps", "Weight", "Rest"}, 0);
        itemsTable = new JTable(itemsModel);

        JButton btnAddItem = new JButton("Add Item");
        btnAddItem.addActionListener(e -> {
            ComboItem ex = (ComboItem) cbExercise.getSelectedItem();
            if (ex == null) {
                JOptionPane.showMessageDialog(this, "Select an exercise first");
                return;
            }
            int sets = (Integer) spSets.getValue();
            int reps = (Integer) spReps.getValue();
            double weight = (Double) spWeight.getValue();
            int rest = (Integer) spRest.getValue();
            itemsModel.addRow(new Object[]{ex.id, ex.label, sets, reps, weight, rest});
        });

        JButton btnRemoveItem = new JButton("Remove Selected Item");
        btnRemoveItem.addActionListener(e -> {
            int r = itemsTable.getSelectedRow();
            if (r >= 0) {
                itemsModel.removeRow(r);
            } else {
                JOptionPane.showMessageDialog(this, "Select an item");
            }
        });

        JPanel itemPanel = new JPanel(new BorderLayout());
        JPanel buttons = new JPanel();
        buttons.add(btnAddItem);
        buttons.add(btnRemoveItem);
        itemPanel.add(buttons, BorderLayout.NORTH);
        itemPanel.add(new JScrollPane(itemsTable), BorderLayout.CENTER);

        // Add item panel to main form
        c.gridx = 0;
        c.gridy = ++y;
        c.gridwidth = 2;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;
        c.weighty = 1.0;
        p.add(itemPanel, c);
    }

    private void loadExercises() {
        for (Exercise ex : exerciseDao.getAll()) {
            cbExercise.addItem(new ComboItem(ex.getId(), ex.getName()));
        }
    }

    private void saveWorkout() {
    try {
        Workout workout = new Workout();
        workout.setUserId(user.getId());
        workout.setDate(LocalDate.parse(tfDate.getText()));
        workout.setNotes(taNotes.getText());

        java.util.List<WorkoutItem> items = new java.util.ArrayList<>();

        for (int i = 0; i < itemsModel.getRowCount(); i++) {
            int exId = (Integer) itemsModel.getValueAt(i, 0);
            int sets = (Integer) itemsModel.getValueAt(i, 2);
            int reps = (Integer) itemsModel.getValueAt(i, 3);
            double weight = (Double) itemsModel.getValueAt(i, 4);
            int rest = (Integer) itemsModel.getValueAt(i, 5);

            WorkoutItem item = new WorkoutItem();
            item.setExerciseId(exId);
            item.setSets(sets);
            item.setReps(reps);
            item.setWeightUsed(weight);
            item.setRestTime(rest);

            items.add(item);
        }

        workoutDao.createWorkoutWithItems(workout, items);

        JOptionPane.showMessageDialog(this, "Workout saved");
        dispose();
    } catch (Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error saving workout: " + ex.getMessage());
    }
}


    // Simple holder for combo box
    private static class ComboItem {
        int id;
        String label;

        ComboItem(int id, String label) {
            this.id = id;
            this.label = label;
        }

        @Override
        public String toString() {
            return label;
        }
    }
}
