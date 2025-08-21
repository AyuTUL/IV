package org.personal.workoutlogger.ui.forms;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.personal.workoutlogger.dao.impl.ExerciseDaoImpl;
import org.personal.workoutlogger.dao.impl.WorkoutDaoImpl;
import org.personal.workoutlogger.model.Exercise;
import org.personal.workoutlogger.model.Workout;
import org.personal.workoutlogger.model.WorkoutItem;

public class EditWorkoutDialog extends JDialog {

    private final WorkoutDaoImpl dao;
    private final ExerciseDaoImpl exerciseDao = new ExerciseDaoImpl();
    private Workout workout;

    private final JTextField tfName = new JTextField(22);
    private final JTextField tfDate = new JTextField(12);
    private final JTextArea taNotes = new JTextArea(3, 32);

    private final DefaultTableModel itemsModel = new DefaultTableModel(
            new Object[]{"Exercise", "Sets", "Reps", "Weight", "Rest (sec)"}, 0) {
        @Override public boolean isCellEditable(int r,int c){ return false; }
        @Override public Class<?> getColumnClass(int c){
            switch (c) {
                case 0: return String.class;
                case 3: return Double.class;
                default: return Integer.class;
            }
        }
    };
    private final JTable itemsTable = new JTable(itemsModel);

    public EditWorkoutDialog(Frame owner, Workout workout, WorkoutDaoImpl dao) {
        super(owner, "Edit Workout", true);
        this.workout = workout;
        this.dao = dao;
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(true);
        setUndecorated(false);
        buildUI();
        loadData();
        pack();
        setLocationRelativeTo(owner);
    }

    private void buildUI() {
        taNotes.setLineWrap(true);
        taNotes.setWrapStyleWord(true);

        JPanel header = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6,6,6,6);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        int y=0;

        gbc.gridx=0; gbc.gridy=y; header.add(new JLabel("Name:"), gbc);
        gbc.gridx=1; header.add(tfName, gbc); y++;

        gbc.gridx=0; gbc.gridy=y; header.add(new JLabel("Date (YYYY-MM-DD):"), gbc);
        gbc.gridx=1; header.add(tfDate, gbc); y++;

        gbc.gridx=0; gbc.gridy=y; gbc.anchor = GridBagConstraints.NORTHWEST;
        header.add(new JLabel("Notes:"), gbc);
        gbc.gridx=1; gbc.weightx=1; gbc.fill = GridBagConstraints.BOTH;
        header.add(new JScrollPane(taNotes), gbc);

        itemsTable.setRowHeight(22);
        JScrollPane itemsPane = new JScrollPane(itemsTable);
        itemsPane.setPreferredSize(new Dimension(620, 240));

        JButton btnAdd = new JButton("Add Item");
        JButton btnEdit = new JButton("Edit Item");
        JButton btnRemove = new JButton("Remove");
        JPanel itemBtns = new JPanel(new FlowLayout(FlowLayout.LEFT));
        itemBtns.add(btnAdd);
        itemBtns.add(btnEdit);
        itemBtns.add(btnRemove);

        btnAdd.addActionListener(e -> addItemDialog());
        btnEdit.addActionListener(e -> editSelectedItemDialog());
        btnRemove.addActionListener(e -> {
            int r = itemsTable.getSelectedRow();
            if (r >= 0) itemsModel.removeRow(r);
        });

        JButton btnSave = new JButton("Save");
        JButton btnCancel = new JButton("Cancel");
        btnSave.addActionListener(e -> onSave());
        btnCancel.addActionListener(e -> dispose());
        JPanel south = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        south.add(btnSave); south.add(btnCancel);

        JPanel content = new JPanel(new BorderLayout(8,8));
        content.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        content.add(header, BorderLayout.NORTH);
        content.add(itemsPane, BorderLayout.CENTER);
        content.add(itemBtns, BorderLayout.WEST);
        content.add(south, BorderLayout.SOUTH);
        setContentPane(content);
    }

    private void loadData() {
        tfName.setText(workout.getName());
        tfDate.setText(workout.getDate().toString());
        taNotes.setText(workout.getNotes());
        itemsModel.setRowCount(0);
        for (WorkoutItem it : dao.getItemsByWorkout(workout.getId())) {
            itemsModel.addRow(new Object[]{
                    displayForExercise(it.getExerciseId()),
                    it.getSets(),
                    it.getReps(),
                    it.getWeightUsed(),
                    it.getRestTime()
            });
        }
    }

    private String displayForExercise(int exId) {
        Exercise ex = exerciseDao.get(exId);
        if (ex != null) {
            return ex.getName() + " (#" + ex.getId() + ")";
        }
        return "Exercise #" + exId;
    }

    private void addItemDialog() {
        JComboBox<Exercise> exCmb = new JComboBox<>();
        for (Exercise ex : exerciseDao.getAll()) exCmb.addItem(ex);

        JSpinner spSets = new JSpinner(new SpinnerNumberModel(3, 1, 20, 1));
        JSpinner spReps = new JSpinner(new SpinnerNumberModel(10, 1, 50, 1));
        JSpinner spWeight = new JSpinner(new SpinnerNumberModel(0.0, 0.0, 1000.0, 1.0));
        JSpinner spRest = new JSpinner(new SpinnerNumberModel(60, 0, 600, 5));

        JPanel p = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4,4,4,4);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int y=0;
        gbc.gridx=0; gbc.gridy=y; p.add(new JLabel("Exercise:"), gbc);
        gbc.gridx=1; p.add(exCmb, gbc); y++;
        gbc.gridx=0; gbc.gridy=y; p.add(new JLabel("Sets:"), gbc);
        gbc.gridx=1; p.add(spSets, gbc); y++;
        gbc.gridx=0; gbc.gridy=y; p.add(new JLabel("Reps:"), gbc);
        gbc.gridx=1; p.add(spReps, gbc); y++;
        gbc.gridx=0; gbc.gridy=y; p.add(new JLabel("Weight:"), gbc);
        gbc.gridx=1; p.add(spWeight, gbc); y++;
        gbc.gridx=0; gbc.gridy=y; p.add(new JLabel("Rest (sec):"), gbc);
        gbc.gridx=1; p.add(spRest, gbc);

        int ok = JOptionPane.showConfirmDialog(this, p, "Add Item", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (ok == JOptionPane.OK_OPTION) {
            Exercise ex = (Exercise) exCmb.getSelectedItem();
            String name = ex != null ? ex.getName() : "";
            int exId = ex != null ? ex.getId() : 0;
            int sets = ((Number) spSets.getValue()).intValue();
            int reps = ((Number) spReps.getValue()).intValue();
            double weight = ((Number) spWeight.getValue()).doubleValue();
            int rest = ((Number) spRest.getValue()).intValue();
            itemsModel.addRow(new Object[]{ name + " (#" + exId + ")", sets, reps, weight, rest });
        }
    }

    private void editSelectedItemDialog() {
        int row = itemsTable.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Select a row to edit");
            return;
        }
        String current = String.valueOf(itemsModel.getValueAt(row, 0));
        int currentId = parseExId(current);

        JComboBox<Exercise> exCmb = new JComboBox<>();
        int selectIndex = -1; int idx = 0;
        for (Exercise ex : exerciseDao.getAll()) {
            exCmb.addItem(ex);
            if (ex.getId() == currentId) selectIndex = idx;
            idx++;
        }
        if (selectIndex >= 0) exCmb.setSelectedIndex(selectIndex);

        JSpinner spSets = new JSpinner(new SpinnerNumberModel(((Number)itemsModel.getValueAt(row,1)).intValue(), 1, 20, 1));
        JSpinner spReps = new JSpinner(new SpinnerNumberModel(((Number)itemsModel.getValueAt(row,2)).intValue(), 1, 50, 1));
        JSpinner spWeight = new JSpinner(new SpinnerNumberModel(((Number)itemsModel.getValueAt(row,3)).doubleValue(), 0.0, 1000.0, 1.0));
        JSpinner spRest = new JSpinner(new SpinnerNumberModel(((Number)itemsModel.getValueAt(row,4)).intValue(), 0, 600, 5));

        JPanel p = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4,4,4,4);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int y=0;
        gbc.gridx=0; gbc.gridy=y; p.add(new JLabel("Exercise:"), gbc);
        gbc.gridx=1; p.add(exCmb, gbc); y++;
        gbc.gridx=0; gbc.gridy=y; p.add(new JLabel("Sets:"), gbc);
        gbc.gridx=1; p.add(spSets, gbc); y++;
        gbc.gridx=0; gbc.gridy=y; p.add(new JLabel("Reps:"), gbc);
        gbc.gridx=1; p.add(spReps, gbc); y++;
        gbc.gridx=0; gbc.gridy=y; p.add(new JLabel("Weight:"), gbc);
        gbc.gridx=1; p.add(spWeight, gbc); y++;
        gbc.gridx=0; gbc.gridy=y; p.add(new JLabel("Rest (sec):"), gbc);
        gbc.gridx=1; p.add(spRest, gbc);

        int ok = JOptionPane.showConfirmDialog(this, p, "Edit Item", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (ok == JOptionPane.OK_OPTION) {
            Exercise ex = (Exercise) exCmb.getSelectedItem();
            String name = ex != null ? ex.getName() : "";
            int exId = ex != null ? ex.getId() : 0;
            itemsModel.setValueAt(name + " (#" + exId + ")", row, 0);
            itemsModel.setValueAt(((Number) spSets.getValue()).intValue(), row, 1);
            itemsModel.setValueAt(((Number) spReps.getValue()).intValue(), row, 2);
            itemsModel.setValueAt(((Number) spWeight.getValue()).doubleValue(), row, 3);
            itemsModel.setValueAt(((Number) spRest.getValue()).intValue(), row, 4);
        }
    }

    private void onSave() {
        try {
            // Update workout header
            workout = new Workout(workout.getId(),
                                  workout.getUserId(),
                                  LocalDate.parse(tfDate.getText().trim()),
                                  tfName.getText().trim(),
                                  taNotes.getText().trim());
            dao.updateWorkout(workout, true); // role check done in window; trainer can edit anyone

            // Collect items
            List<WorkoutItem> items = new ArrayList<>();
            for (int r=0; r<itemsModel.getRowCount(); r++) {
                int exId = parseExId(String.valueOf(itemsModel.getValueAt(r, 0)));
                int sets = ((Number) itemsModel.getValueAt(r,1)).intValue();
                int reps = ((Number) itemsModel.getValueAt(r,2)).intValue();
                double weight = ((Number) itemsModel.getValueAt(r,3)).doubleValue();
                int rest = ((Number) itemsModel.getValueAt(r,4)).intValue();
                items.add(new WorkoutItem(0, workout.getId(), exId, sets, reps, weight, rest));
            }
            dao.replaceItemsForWorkout(workout.getId(), items);
            JOptionPane.showMessageDialog(this, "Workout updated");
            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Update failed", JOptionPane.ERROR_MESSAGE);
        }
    }

    private int parseExId(String display) {
        int idx = display.lastIndexOf("(#");
        if (idx >= 0) {
            int end = display.lastIndexOf(")");
            return Integer.parseInt(display.substring(idx+2, end));
        }
        try {
            return Integer.parseInt(display.replaceAll("[^0-9]", ""));
        } catch (Exception e) {
            return 0;
        }
    }
}