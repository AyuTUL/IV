package org.personal.workoutlogger.ui;
import javax.swing.*; import java.awt.*; 
import org.personal.workoutlogger.dao.UserDao; import org.personal.workoutlogger.dao.impl.UserDaoImpl; 
import org.personal.workoutlogger.model.User; 
import org.personal.workoutlogger.ui.dash.TrainerDashboard; import org.personal.workoutlogger.ui.dash.TraineeDashboard;
public class LoginFrame extends JFrame{
  private JTextField tfUser=new JTextField(15); private JPasswordField pfPass=new JPasswordField(15);
  private JButton btnLogin=new JButton("Login"); private JButton btnRegister=new JButton("Register as Trainee");
  private final UserDao userDao=new UserDaoImpl();
  public LoginFrame(){
    super("Workout Logger - Login"); setDefaultCloseOperation(EXIT_ON_CLOSE); setSize(380,200); setLocationRelativeTo(null);
    JPanel p=new JPanel(new GridBagLayout()); GridBagConstraints c=new GridBagConstraints(); c.insets=new Insets(4,4,4,4);
    c.gridx=0;c.gridy=0;p.add(new JLabel("Username:"),c); c.gridx=1;p.add(tfUser,c);
    c.gridx=0;c.gridy=1;p.add(new JLabel("Password:"),c); c.gridx=1;p.add(pfPass,c);
    c.gridx=0;c.gridy=2;c.gridwidth=2; JPanel btns=new JPanel(); btns.add(btnLogin); btns.add(btnRegister); p.add(btns,c);
    btnLogin.addActionListener(e->doLogin()); btnRegister.addActionListener(e->doRegister()); setContentPane(p);
  }
  private void doLogin(){
    String u=tfUser.getText().trim(); String p=new String(pfPass.getPassword()); User user=userDao.login(u,p);
    if(user==null){ JOptionPane.showMessageDialog(this,"Invalid credentials","Error",JOptionPane.ERROR_MESSAGE); return; }
    JOptionPane.showMessageDialog(this,"Welcome "+user.getUsername()+" ("+user.getRole()+")");
    SwingUtilities.invokeLater(()->{ dispose(); if("TRAINER".equalsIgnoreCase(user.getRole())) new TrainerDashboard(user).setVisible(true); else new TraineeDashboard(user).setVisible(true); });
  }
  private void doRegister(){
    String u=tfUser.getText().trim(); String p=new String(pfPass.getPassword());
    if(u.isEmpty()||p.isEmpty()){ JOptionPane.showMessageDialog(this,"Enter username & password to register"); return; }
    boolean ok=userDao.register(u,p,"TRAINEE"); JOptionPane.showMessageDialog(this, ok? "Registered! Now log in.":"Username already exists.");
  }
}
