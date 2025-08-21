package org.personal.workoutlogger.ui.windows;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.personal.workoutlogger.dao.WorkoutDao;
import org.personal.workoutlogger.dao.impl.WorkoutDaoImpl;
import org.personal.workoutlogger.dao.ExerciseDao;
import org.personal.workoutlogger.dao.impl.ExerciseDaoImpl;
import org.personal.workoutlogger.model.Exercise;
import org.personal.workoutlogger.model.User;
import org.personal.workoutlogger.model.Workout;
import org.personal.workoutlogger.model.WorkoutItem;
import org.personal.workoutlogger.ui.forms.WorkoutForm;

public class WorkoutWindow extends JFrame {

    private final User user;
    private final WorkoutDao dao = new WorkoutDaoImpl();

    private final JTable table = new JTable(
            new DefaultTableModel(new Object[]{"ID", "Name", "Date", "Notes"}, 0) {
                @Override public boolean isCellEditable(int row, int column) { return false; }
            }
    );

    private JComboBox<org.personal.workoutlogger.model.User> cmbUsers = null;

    public WorkoutWindow(User u) {
        super("Workouts");
        this.user = u;

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
        setSize(900, 520);
        setLocationRelativeTo(null);

        initUI();
        loadData();
    }

    private void initUI() {
        setLayout(new BorderLayout());

        table.setRowHeight(22);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setAutoCreateRowSorter(true);

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

        int id = (Integer) table.getValueAt(row, 0);
        String currentName = String.valueOf(table.getValueAt(row, 1));
        String currentDate = String.valueOf(table.getValueAt(row, 2));
        String currentNotes = String.valueOf(table.getValueAt(row, 3));

        JTextField nameF = new JTextField(currentName);
        JTextField dateF = new JTextField(currentDate);
        JTextField notesF = new JTextField(currentNotes);

        ItemsEditor editor = new ItemsEditor(id);

        Object[] message = {
                "Name:", nameF,
                "Date (YYYY-MM-DD):", dateF,
                "Notes:", notesF,
                new JLabel("Exercises:"), editor.getPanel()
        };

        int ok = JOptionPane.showConfirmDialog(
                this, message, "Edit Workout", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE
        );

        if (ok == JOptionPane.OK_OPTION) {
            try {
                LocalDate parsedDate = LocalDate.parse(dateF.getText().trim());

                Workout w = new Workout(
                        id,
                        getTargetUserId(),
                        parsedDate,
                        nameF.getText().trim(),
                        notesF.getText().trim()
                );
                boolean isTrainer = "TRAINER".equalsIgnoreCase(user.getRole());
                dao.updateWorkout(w, isTrainer);

                List<WorkoutItem> items = editor.collectItems(id);
                ((WorkoutDaoImpl) dao).replaceItemsForWorkout(id, items);

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

    private static class ItemsEditor {
        private final JPanel panel = new JPanel(new BorderLayout(5,5));
        private final JTable itemTable;
        private final DefaultTableModel itemModel;
        private final ExerciseDao exerciseDao = new ExerciseDaoImpl();
        private final WorkoutDao dao = new WorkoutDaoImpl();

        ItemsEditor(int workoutId) {
            itemModel = new DefaultTableModel(
                    new Object[]{"ExerciseId", "Exercise", "Sets", "Reps", "Weight", "Rest(sec)"}, 0
            ) {
                @Override public boolean isCellEditable(int row, int col) { return false; }
                @Override public Class<?> getColumnClass(int col) {
                    switch (col) {
                        case 0: return Integer.class;
                        case 2: return Integer.class;
                        case 3: return Integer.class;
                        case 4: return Double.class;
                        case 5: return Integer.class;
                        default: return String.class;
                    }
                }
            };
            itemTable = new JTable(itemModel);
            itemTable.setRowHeight(22);
            itemTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

            for (WorkoutItem wi : dao.getItemsByWorkout(workoutId)) {
                Exercise ex = safeGetExercise(wi.getExerciseId());
                itemModel.addRow(new Object[]{
                        wi.getExerciseId(),
                        ex != null ? ex.getName() : ("#" + wi.getExerciseId()),
                        wi.getSets(),
                        wi.getReps(),
                        wi.getWeightUsed(),
                        wi.getRestTime()
                });
            }

            JPanel btns = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JButton add = new JButton("Add");
            JButton edit = new JButton("Edit");
            JButton remove = new JButton("Remove");

            add.addActionListener(e -> onAdd());
            edit.addActionListener(e -> onEdit());
            remove.addActionListener(e -> onRemove());

            btns.add(add); btns.add(edit); btns.add(remove);

            panel.add(new JScrollPane(itemTable), BorderLayout.CENTER);
            panel.add(btns, BorderLayout.SOUTH);
            panel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        }

        JPanel getPanel() { return panel; }

        private Exercise safeGetExercise(int id) {
            try { return exerciseDao.get(id); } catch (Exception ex) { return null; }
        }

        private void onAdd() {
            ItemData data = ItemEditorDialog.show(panel, exerciseDao, null);
            if (data != null) {
                itemModel.addRow(new Object[]{
                        data.exerciseId, data.exerciseName, data.sets, data.reps, data.weight, data.rest
                });
            }
        }

        private void onEdit() {
            int r = itemTable.getSelectedRow();
            if (r < 0) {
                JOptionPane.showMessageDialog(panel, "Select an item to edit.");
                return;
            }
            ItemData initial = new ItemData(
                    ((Number) itemModel.getValueAt(r, 0)).intValue(),
                    String.valueOf(itemModel.getValueAt(r, 1)),
                    ((Number) itemModel.getValueAt(r, 2)).intValue(),
                    ((Number) itemModel.getValueAt(r, 3)).intValue(),
                    ((Number) itemModel.getValueAt(r, 4)).doubleValue(),
                    ((Number) itemModel.getValueAt(r, 5)).intValue()
            );
            ItemData data = ItemEditorDialog.show(panel, exerciseDao, initial);
            if (data != null) {
                itemModel.setValueAt(data.exerciseId, r, 0);
                itemModel.setValueAt(data.exerciseName, r, 1);
                itemModel.setValueAt(data.sets, r, 2);
                itemModel.setValueAt(data.reps, r, 3);
                itemModel.setValueAt(data.weight, r, 4);
                itemModel.setValueAt(data.rest, r, 5);
            }
        }

        private void onRemove() {
            int r = itemTable.getSelectedRow();
            if (r >= 0) itemModel.removeRow(r);
        }

        List<WorkoutItem> collectItems(int workoutId) {
            List<WorkoutItem> list = new ArrayList<>();
            for (int i = 0; i < itemModel.getRowCount(); i++) {
                int exId = ((Number) itemModel.getValueAt(i, 0)).intValue();
                int sets = ((Number) itemModel.getValueAt(i, 2)).intValue();
                int reps = ((Number) itemModel.getValueAt(i, 3)).intValue();
                double weight = ((Number) itemModel.getValueAt(i, 4)).doubleValue();
                int rest = ((Number) itemModel.getValueAt(i, 5)).intValue();
                list.add(new WorkoutItem(0, workoutId, exId, sets, reps, weight, rest));
            }
            return list;
        }
    }

    private static class ItemData {
        final int exerciseId; final String exerciseName; final int sets; final int reps; final double weight; final int rest;
        ItemData(int exerciseId, String exerciseName, int sets, int reps, double weight, int rest) {
            this.exerciseId = exerciseId; this.exerciseName = exerciseName; this.sets = sets; this.reps = reps; this.weight = weight; this.rest = rest;
        }
    }

    private static class ItemEditorDialog {
        static ItemData show(Component owner, ExerciseDao exerciseDao, ItemData initial) {
            List<Exercise> all = exerciseDao.getAll();
            JComboBox<Exercise> exCmb = new JComboBox<>(all.toArray(new Exercise[0]));
            exCmb.setRenderer(new DefaultListCellRenderer(){
                @Override public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                    Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                    if (value instanceof Exercise) {
                        setText(((Exercise) value).getName());
                    }
                    return c;
                }
            });

            JSpinner setsSp = new JSpinner(new SpinnerNumberModel(3, 1, 100, 1));
            JSpinner repsSp = new JSpinner(new SpinnerNumberModel(10, 1, 100, 1));
            JSpinner weightSp = new JSpinner(new SpinnerNumberModel(0.0, 0.0, 1000.0, 1.0));
            JSpinner restSp = new JSpinner(new SpinnerNumberModel(60, 0, 10000, 5));

            if (initial != null) {
                for (int i = 0; i < exCmb.getItemCount(); i++) {
                    if (exCmb.getItemAt(i).getId() == initial.exerciseId) {
                        exCmb.setSelectedIndex(i);
                        break;
                    }
                }
                setsSp.setValue(initial.sets);
                repsSp.setValue(initial.reps);
                weightSp.setValue(initial.weight);
                restSp.setValue(initial.rest);
            }

            JPanel p = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(4,4,4,4);
            gbc.anchor = GridBagConstraints.WEST;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.gridx = 0; gbc.gridy = 0; p.add(new JLabel("Exercise:"), gbc);
            gbc.gridx = 1; p.add(exCmb, gbc);
            gbc.gridx = 0; gbc.gridy = 1; p.add(new JLabel("Sets:"), gbc);
            gbc.gridx = 1; p.add(setsSp, gbc);
            gbc.gridx = 0; gbc.gridy = 2; p.add(new JLabel("Reps:"), gbc);
            gbc.gridx = 1; p.add(repsSp, gbc);
            gbc.gridx = 0; gbc.gridy = 3; p.add(new JLabel("Weight:"), gbc);
            gbc.gridx = 1; p.add(weightSp, gbc);
            gbc.gridx = 0; gbc.gridy = 4; p.add(new JLabel("Rest (sec):"), gbc);
            gbc.gridx = 1; p.add(restSp, gbc);

            int result = JOptionPane.showConfirmDialog(owner, p, "Workout Item", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                Exercise chosen = (Exercise) exCmb.getSelectedItem();
                int exerciseId = chosen != null ? chosen.getId() : 0;
                String exerciseName = chosen != null ? chosen.getName() : "";
                int sets = ((Number) setsSp.getValue()).intValue();
                int reps = ((Number) repsSp.getValue()).intValue();
                double weight = ((Number) weightSp.getValue()).doubleValue();
                int rest = ((Number) restSp.getValue()).intValue();
                return new ItemData(exerciseId, exerciseName, sets, reps, weight, rest);
            }
            return null;
        }
    }
}
