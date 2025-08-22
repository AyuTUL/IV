package org.personal.workoutlogger.ui.forms;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;

import org.personal.workoutlogger.dao.impl.WorkoutDaoImpl;
import org.personal.workoutlogger.model.Workout;
import org.personal.workoutlogger.model.WorkoutItem;

public class WorkoutViewDialog extends JDialog {
    private final int workoutId;
    private final WorkoutDaoImpl dao;

    private final JTextField tfName = new JTextField(24);
    private final JTextField tfDate = new JTextField(12);
    private final JTextArea taNotes = new JTextArea(4, 36);

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

    public WorkoutViewDialog(Frame owner, Workout workout, WorkoutDaoImpl daoImpl) {
        super(owner, "Workout Details", true);
        this.workoutId = workout.getId();
        this.dao = daoImpl;
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(true);
        setUndecorated(false);
        buildUI();
        loadHeader(workout);
        loadItems();
        pack();
        setLocationRelativeTo(owner);
    }

    private void buildUI() {
        tfName.setEditable(false);
        tfDate.setEditable(false);
        taNotes.setEditable(false);
        taNotes.setLineWrap(true);
        taNotes.setWrapStyleWord(true);

        JPanel header = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6,6,6,6);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        int y = 0;

        gbc.gridx=0; gbc.gridy=y; header.add(new JLabel("Name:"), gbc);
        gbc.gridx=1; header.add(tfName, gbc); y++;

        gbc.gridx=0; gbc.gridy=y; header.add(new JLabel("Date:"), gbc);
        gbc.gridx=1; header.add(tfDate, gbc); y++;

        gbc.gridx=0; gbc.gridy=y; gbc.anchor = GridBagConstraints.NORTHWEST; header.add(new JLabel("Notes:"), gbc);
        gbc.gridx=1; gbc.weightx=1; gbc.fill = GridBagConstraints.BOTH;
        header.add(new JScrollPane(taNotes), gbc);

        itemsTable.setFillsViewportHeight(true);
        itemsTable.setRowHeight(22);
        JScrollPane tablePane = new JScrollPane(itemsTable);
        tablePane.setPreferredSize(new Dimension(640, 260));

        JPanel content = new JPanel(new BorderLayout(8,8));
        content.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        content.add(header, BorderLayout.NORTH);
        content.add(tablePane, BorderLayout.CENTER);

        JButton close = new JButton("Close");
        close.addActionListener(e -> dispose());
        JPanel south = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        south.add(close);
        content.add(south, BorderLayout.SOUTH);

        setContentPane(content);
    }

    private void loadHeader(Workout w) {
        tfName.setText(w.getName());
        tfDate.setText(w.getDate().format(DateTimeFormatter.ISO_LOCAL_DATE));
        taNotes.setText(w.getNotes());
    }

    private void loadItems() {
        itemsModel.setRowCount(0);
        for (WorkoutItem it : dao.getItemsByWorkout(workoutId)) {
            itemsModel.addRow(new Object[]{
                it.getExerciseName(),
                it.getSets(),
                it.getReps(),
                it.getWeightUsed(),
                it.getRestTime()
            });
        }
    }
}