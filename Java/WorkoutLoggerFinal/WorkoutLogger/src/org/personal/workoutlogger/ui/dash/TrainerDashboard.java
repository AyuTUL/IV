package org.personal.workoutlogger.ui.dash;
import javax.swing.*; import java.awt.*; import javax.swing.table.*; import java.sql.*;
import org.personal.workoutlogger.model.User; import org.personal.workoutlogger.connection.ConnectionFactory;
public class TrainerDashboard extends JFrame{
  private final User currentUser; private JTable table=new JTable(new DefaultTableModel(new Object[]{"Workout ID","User","Date","Notes"},0));
  public TrainerDashboard(User u){
    super("Trainer Dashboard - View All Workouts");
        setResizable(true); this.currentUser=u; setDefaultCloseOperation(EXIT_ON_CLOSE); setSize(700,400); setLocationRelativeTo(null);
    add(new JScrollPane(table),BorderLayout.CENTER); JButton refresh=new JButton("Refresh"); refresh.addActionListener(e->loadData()); add(refresh,BorderLayout.SOUTH); loadData();
  }
  private void loadData(){
    DefaultTableModel m=(DefaultTableModel)table.getModel(); m.setRowCount(0);
    String sql="SELECT w.id, u.username, w.date, w.notes FROM workouts w JOIN users u ON w.user_id=u.id ORDER BY w.date DESC";
    try(Connection con=ConnectionFactory.getConnection(); PreparedStatement ps=con.prepareStatement(sql); ResultSet rs=ps.executeQuery()){
      while(rs.next()) m.addRow(new Object[]{rs.getInt(1), rs.getString(2), rs.getDate(3), rs.getString(4)});
    }catch(Exception e){e.printStackTrace();}
  }
}
