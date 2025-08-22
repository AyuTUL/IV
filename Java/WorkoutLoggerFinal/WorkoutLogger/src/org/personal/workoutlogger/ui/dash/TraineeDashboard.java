package org.personal.workoutlogger.ui.dash;
import javax.swing.*; import java.awt.*; import javax.swing.table.*; import java.util.*;
import org.personal.workoutlogger.model.*; import org.personal.workoutlogger.dao.*; import org.personal.workoutlogger.dao.impl.*; 
import org.personal.workoutlogger.ui.forms.WorkoutForm;
public class TraineeDashboard extends JFrame{
  private final User currentUser; private final WorkoutDao workoutDao=new WorkoutDaoImpl();
  private JTable table=new JTable(new DefaultTableModel(new Object[]{"ID","Date","Notes"},0));
  public TraineeDashboard(User u){
    super("Trainee Dashboard - My Workouts");
        setResizable(true); this.currentUser=u; setDefaultCloseOperation(EXIT_ON_CLOSE); setSize(700,400); setLocationRelativeTo(null);
    add(new JScrollPane(table),BorderLayout.CENTER);
    JPanel south=new JPanel(); JButton refresh=new JButton("Refresh"); JButton add=new JButton("Add Workout"); JButton del=new JButton("Delete Selected");
    south.add(refresh); south.add(add); south.add(del); add(south,BorderLayout.SOUTH);
    refresh.addActionListener(e->loadData()); add.addActionListener(e->new WorkoutForm(this,currentUser).setVisible(true)); del.addActionListener(e->deleteSelected());
    loadData();
  }
  public void loadData(){
    DefaultTableModel m=(DefaultTableModel)table.getModel(); m.setRowCount(0);
    for(Workout w: workoutDao.getWorkoutsByUser(currentUser.getId())) m.addRow(new Object[]{w.getId(), w.getDate(), w.getNotes()});
  }
  private void deleteSelected(){
    int row=table.getSelectedRow(); if(row<0){ JOptionPane.showMessageDialog(this,"Select a row"); return; }
    int id=(int)table.getValueAt(row,0); workoutDao.deleteWorkout(id,currentUser.getId(),false); loadData();
  }
}
