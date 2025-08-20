package org.personal.workoutlogger.ui.forms;
import javax.swing.*; import java.awt.*; import java.time.LocalDate;
import org.personal.workoutlogger.model.*; import org.personal.workoutlogger.dao.*; import org.personal.workoutlogger.dao.impl.*;
public class WorkoutForm extends JDialog{
  private final User user; private final WorkoutDao workoutDao=new WorkoutDaoImpl(); private final ExerciseDao exerciseDao=new ExerciseDaoImpl();
  private JTextField tfDate=new JTextField(10); private JTextArea taNotes=new JTextArea(3,30);
  private JComboBox<ComboItem> cbExercise=new JComboBox<>(); private JSpinner spSets=new JSpinner(new SpinnerNumberModel(3,1,20,1));
  private JSpinner spReps=new JSpinner(new SpinnerNumberModel(10,1,50,1)); private JSpinner spWeight=new JSpinner(new SpinnerNumberModel(0.0,0.0,1000.0,2.5));
  private JSpinner spRest=new JSpinner(new SpinnerNumberModel(60,0,600,5));
  public WorkoutForm(Frame owner, User user){ super(owner,"Add Workout",true); this.user=user; setSize(600,400); setLocationRelativeTo(owner);
    JPanel p=new JPanel(new GridBagLayout()); GridBagConstraints c=new GridBagConstraints(); c.insets=new Insets(4,4,4,4); c.anchor=GridBagConstraints.WEST; int y=0;
    c.gridx=0;c.gridy=y;p.add(new JLabel("Date (YYYY-MM-DD):"),c); c.gridx=1;p.add(tfDate,c); y++;
    c.gridx=0;c.gridy=y;p.add(new JLabel("Notes:"),c); c.gridx=1;c.fill=GridBagConstraints.HORIZONTAL;p.add(new JScrollPane(taNotes),c); y++; c.fill=GridBagConstraints.NONE;
    c.gridx=0;c.gridy=y;p.add(new JLabel("Exercise:"),c); c.gridx=1;p.add(cbExercise,c); y++;
    c.gridx=0;c.gridy=y;p.add(new JLabel("Sets:"),c); c.gridx=1;p.add(spSets,c); y++;
    c.gridx=0;c.gridy=y;p.add(new JLabel("Reps:"),c); c.gridx=1;p.add(spReps,c); y++;
    c.gridx=0;c.gridy=y;p.add(new JLabel("Weight (kg):"),c); c.gridx=1;p.add(spWeight,c); y++;
    c.gridx=0;c.gridy=y;p.add(new JLabel("Rest (sec):"),c); c.gridx=1;p.add(spRest,c); y++;
    JButton btnSave=new JButton("Save Workout"); btnSave.addActionListener(e->saveWorkout()); c.gridx=0;c.gridy=y;c.gridwidth=2;p.add(btnSave,c);
    setContentPane(p); loadExercises(); tfDate.setText(LocalDate.now().toString());
  }
  private void loadExercises(){ cbExercise.removeAllItems(); for(Exercise ex: exerciseDao.getAll()) cbExercise.addItem(new ComboItem(ex.getId(), ex.getName())); }
  private void saveWorkout(){
    try{
      LocalDate d=LocalDate.parse(tfDate.getText().trim()); Workout w=new Workout(0,user.getId(),d,taNotes.getText().trim());
      int workoutId=workoutDao.createWorkout(w); ComboItem ex=(ComboItem)cbExercise.getSelectedItem();
      if(ex!=null && workoutId>0){ WorkoutItem item=new WorkoutItem(0,workoutId,ex.id,(Integer)spSets.getValue(),(Integer)spReps.getValue(),(Double)spWeight.getValue(),(Integer)spRest.getValue());
        workoutDao.addWorkoutItem(item); }
      JOptionPane.showMessageDialog(this,"Saved!"); dispose();
    }catch(Exception ex){ JOptionPane.showMessageDialog(this,"Error: "+ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE); }
  }
  static class ComboItem{ int id; String label; ComboItem(int id,String label){this.id=id;this.label=label;} public String toString(){return label;} }
}
