package org.personal.workoutlogger.ui.windows;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import org.personal.workoutlogger.model.BodyMetric;
import org.personal.workoutlogger.model.User;
import org.personal.workoutlogger.dao.BodyMetricDao;
import org.personal.workoutlogger.dao.impl.BodyMetricDaoImpl;

public class BodyMetricWindow extends JFrame {
    private final User user;
    private final BodyMetricDao dao = new BodyMetricDaoImpl();
    private final JTable table = new JTable(new DefaultTableModel(new Object[]{"ID","Date","Weight","BodyFat","Height","Age"}, 0));

    public BodyMetricWindow(User u) {
        super("Body Metrics");
        this.user = u;
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

        buttons.add(addBtn);
        buttons.add(editBtn);
        buttons.add(delBtn);
        buttons.add(exitBtn);
        add(buttons, BorderLayout.SOUTH);
    }

    private void loadData() {
        DefaultTableModel m = (DefaultTableModel) table.getModel();
        m.setRowCount(0);
        for (BodyMetric bm : dao.findByUser(user.getId())) {
            m.addRow(new Object[]{bm.getId(), bm.getDate(), bm.getWeight(), bm.getBodyFat(), bm.getHeight(), bm.getAge()});
        }
    }

    private void onAdd() {
        JTextField weight = new JTextField();
        JTextField bodyFat = new JTextField();
        JTextField height = new JTextField();
        JTextField age = new JTextField();
        JTextField date = new JTextField(LocalDate.now().toString());
        int ok = JOptionPane.showConfirmDialog(this, new Object[]{
                "Weight (kg):", weight,
                "Body Fat (% or blank):", bodyFat,
                "Height (cm):", height,
                "Age:", age,
                "Date (YYYY-MM-DD):", date
        }, "Add Body Metric", JOptionPane.OK_CANCEL_OPTION);
        if (ok == JOptionPane.OK_OPTION) {
            BodyMetric bm = new BodyMetric();
            bm.setUserId(user.getId());
            bm.setWeight(Double.parseDouble(weight.getText()));
            String bf = bodyFat.getText().trim();
            bm.setBodyFat(bf.isEmpty() ? null : Double.valueOf(bf));
            bm.setHeight(Double.parseDouble(height.getText()));
            bm.setAge(Integer.parseInt(age.getText()));
            bm.setDate(LocalDate.parse(date.getText().trim()));
            dao.add(bm);
            loadData();
        }
    }

    private void onEdit() {
        int row = table.getSelectedRow();
        if (row < 0) { JOptionPane.showMessageDialog(this, "Select a row"); return; }
        int id = (int) table.getValueAt(row, 0);
        Double weightV = toDouble(table.getValueAt(row, 2));
        Double bodyFatV = toDouble(table.getValueAt(row, 3));
        Double heightV = toDouble(table.getValueAt(row, 4));
        Integer ageV = toInt(table.getValueAt(row, 5));
        String dateV = String.valueOf(table.getValueAt(row, 1));

        JTextField weight = new JTextField(weightV==null? "" : String.valueOf(weightV));
        JTextField bodyFat = new JTextField(bodyFatV==null? "" : String.valueOf(bodyFatV));
        JTextField height = new JTextField(heightV==null? "" : String.valueOf(heightV));
        JTextField age = new JTextField(ageV==null? "" : String.valueOf(ageV));
        JTextField date = new JTextField(dateV);

        int ok = JOptionPane.showConfirmDialog(this, new Object[]{
                "Weight (kg):", weight,
                "Body Fat (% or blank):", bodyFat,
                "Height (cm):", height,
                "Age:", age,
                "Date (YYYY-MM-DD):", date
        }, "Edit Body Metric", JOptionPane.OK_CANCEL_OPTION);

        if (ok == JOptionPane.OK_OPTION) {
            BodyMetric bm = new BodyMetric();
            bm.setId(id);
            bm.setUserId(user.getId());
            bm.setWeight(Double.parseDouble(weight.getText()));
            String bf = bodyFat.getText().trim();
            bm.setBodyFat(bf.isEmpty() ? null : Double.valueOf(bf));
            bm.setHeight(Double.parseDouble(height.getText()));
            bm.setAge(Integer.parseInt(age.getText()));
            bm.setDate(LocalDate.parse(date.getText().trim()));
            dao.update(bm);
            loadData();
        }
    }

    private void onDelete() {
        int row = table.getSelectedRow();
        if (row < 0) { JOptionPane.showMessageDialog(this, "Select a row"); return; }
        int id = (int) table.getValueAt(row, 0);
        if (JOptionPane.showConfirmDialog(this, "Delete selected record?", "Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            dao.delete(id);
            loadData();
        }
    }

    private Double toDouble(Object v) { try { return v == null ? null : Double.valueOf(String.valueOf(v)); } catch(Exception e){ return null; } }
    private Integer toInt(Object v) { try { return v == null ? null : Integer.valueOf(String.valueOf(v)); } catch(Exception e){ return null; } }
}
