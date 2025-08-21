package org.personal.workoutlogger.ui.forms;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;

import org.personal.workoutlogger.dao.impl.ExerciseDaoImpl;
import org.personal.workoutlogger.dao.impl.WorkoutDaoImpl;
import org.personal.workoutlogger.model.Exercise;
import org.personal.workoutlogger.model.User;
import org.personal.workoutlogger.model.Workout;
import org.personal.workoutlogger.model.WorkoutItem;

public class WorkoutForm extends JDialog {

    private final User user;
    private final WorkoutDaoImpl workoutDao = new WorkoutDaoImpl();
    private final ExerciseDaoImpl exerciseDao = new ExerciseDaoImpl();

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

    public WorkoutForm(Frame owner, User user) {
        super(owner, "Add Workout", true);
        this.user = user;
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(true);
        setUndecorated(false);
        buildUI();
        pack();
        setLocationRelativeTo(owner);
        tfDate.setText(LocalDate.now().toString());
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
        itemsPane.setPreferredSize(new Dimension(620, 220));

        JButton btnAddItem = new JButton("Add Item");
        JButton btnRemove = new JButton("Remove");
        JPanel itemBtns = new JPanel(new FlowLayout(FlowLayout.LEFT));
        itemBtns.add(btnAddItem);
        itemBtns.add(btnRemove);

        btnAddItem.addActionListener(e -> addItemDialog());
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

    private void onSave() {
        try {
            LocalDate d = LocalDate.parse(tfDate.getText().trim());
            Workout w = new Workout(0, user.getId(), d, tfName.getText().trim(), taNotes.getText().trim());
            int wid = workoutDao.createWorkout(w);
            for (int r=0; r<itemsModel.getRowCount(); r++) {
                String col0 = String.valueOf(itemsModel.getValueAt(r, 0));
                int exId = parseExId(col0);
                int sets = ((Number) itemsModel.getValueAt(r,1)).intValue();
                int reps = ((Number) itemsModel.getValueAt(r,2)).intValue();
                double weight = ((Number) itemsModel.getValueAt(r,3)).doubleValue();
                int rest = ((Number) itemsModel.getValueAt(r,4)).intValue();
                WorkoutItem it = new WorkoutItem(0, wid, exId, sets, reps, weight, rest);
                workoutDao.addWorkoutItem(it);
            }
            JOptionPane.showMessageDialog(this, "Workout saved");
            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Save failed", JOptionPane.ERROR_MESSAGE);
        }
    }

    private int parseExId(String display) {
        int idx = display.lastIndexOf("(#");
        if (idx >= 0) {
            int end = display.lastIndexOf(")");
            return Integer.parseInt(display.substring(idx+2, end));
        }
        return 0;
    }
}