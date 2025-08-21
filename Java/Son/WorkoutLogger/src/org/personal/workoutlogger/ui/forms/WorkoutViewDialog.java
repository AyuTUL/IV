package org.personal.workoutlogger.ui.forms;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.personal.workoutlogger.dao.WorkoutDao;
import org.personal.workoutlogger.dao.impl.WorkoutDaoImpl;
import org.personal.workoutlogger.model.Workout;
import org.personal.workoutlogger.model.WorkoutItem;

public class WorkoutViewDialog extends JDialog {
    private final int workoutId;
    private final WorkoutDao dao;
    private JTextField tfName = new JTextField(20);
    private JTextField tfDate = new JTextField(10);
    private JTextArea taNotes = new JTextArea(3, 30);
    private JTable itemsTable;
    private DefaultTableModel itemsModel;

    public WorkoutViewDialog(Frame owner, int workoutId, WorkoutDaoImpl daoImpl) {
        super(owner, "View Workout", true);
        this.workoutId = workoutId;
        this.dao = daoImpl;
        setSize(700, 500);
        setLocationRelativeTo(owner);
        buildUI();
        loadData();
    }

    private void buildUI() {
        JPanel top = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4,4,4,4);
        c.anchor = GridBagConstraints.WEST;
        int y=0;

        c.gridx=0; c.gridy=y; top.add(new JLabel("Name:"), c);
        c.gridx=1; top.add(tfName, c); y++;

        c.gridx=0; c.gridy=y; top.add(new JLabel("Date (YYYY-MM-DD):"), c);
        c.gridx=1; top.add(tfDate, c); y++;

        c.gridx=0; c.gridy=y; top.add(new JLabel("Notes:"), c);
        c.gridx=1; c.fill = GridBagConstraints.HORIZONTAL;
        top.add(new JScrollPane(taNotes), c);

        itemsModel = new DefaultTableModel(new Object[]{"ItemID","ExerciseID","Sets","Reps","Weight","Rest"}, 0) {
            @Override public boolean isCellEditable(int r,int c){ return c!=0; }
            @Override public Class<?> getColumnClass(int c){ return (c==4)?Double.class:Integer.class; }
        };
        itemsTable = new JTable(itemsModel);

        JPanel buttons = new JPanel();
        JButton btnSave = new JButton("Save");
        JButton btnClose = new JButton("Close");
        buttons.add(btnSave); buttons.add(btnClose);

        btnSave.addActionListener(e -> onSave());
        btnClose.addActionListener(e -> dispose());

        JPanel main = new JPanel(new BorderLayout());
        main.add(top, BorderLayout.NORTH);
        main.add(new JScrollPane(itemsTable), BorderLayout.CENTER);
        main.add(buttons, BorderLayout.SOUTH);
        setContentPane(main);
    }

    private void loadData() {
        // Load header
        List<Workout> list = dao.getWorkoutsByUser(-1); // not ideal; we'll fetch item via items method and infer header from first match if impl supports get by id
        // Since interface lacks get-by-id, we try to read items and leave header fields as-is.
        // Safer approach: we won't attempt to reload header from DB here.

        // Load items
        itemsModel.setRowCount(0);
        for (WorkoutItem it : dao.getItemsByWorkout(workoutId)) {
            itemsModel.addRow(new Object[]{
                it.getId(), it.getExerciseId(), it.getSets(), it.getReps(), it.getWeightUsed(), it.getRestTime()
            });
        }
    }

    private void onSave() {
        try {
            // Update header (name/date/notes) if dao supports updateWorkout
            Workout w = new Workout();
            w.setId(workoutId);
            w.setName(tfName.getText());
            w.setDate(LocalDate.parse(tfDate.getText()));
            w.setNotes(taNotes.getText());
            dao.updateWorkout(w, false);

            // Replace items using impl helper
            if (dao instanceof WorkoutDaoImpl) {
                ((WorkoutDaoImpl)dao).replaceItemsForWorkout(workoutId, collectItems());
            }
            JOptionPane.showMessageDialog(this, "Saved.");
            dispose();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: "+ex.getMessage());
        }
    }

    private List<WorkoutItem> collectItems(){
        List<WorkoutItem> items = new ArrayList<>();
        for (int i=0;i<itemsModel.getRowCount();i++){
            WorkoutItem it = new WorkoutItem();
            Object idObj = itemsModel.getValueAt(i,0);
            if (idObj instanceof Integer){ it.setId((Integer)idObj); }
            it.setWorkoutId(workoutId);
            it.setExerciseId((Integer)itemsModel.getValueAt(i,1));
            it.setSets((Integer)itemsModel.getValueAt(i,2));
            it.setReps((Integer)itemsModel.getValueAt(i,3));
            it.setWeightUsed(((Number)itemsModel.getValueAt(i,4)).doubleValue());
            it.setRestTime((Integer)itemsModel.getValueAt(i,5));
            items.add(it);
        }
        return items;
    }
}
