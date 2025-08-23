package org.personal.workoutlogger.ui.dash;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.*;
import java.util.*;
import org.personal.workoutlogger.model.*;
import org.personal.workoutlogger.dao.*;
import org.personal.workoutlogger.dao.impl.*;
import org.personal.workoutlogger.ui.forms.WorkoutForm;
import org.personal.workoutlogger.ui.GlobalUISettings;

public class TraineeDashboard extends JFrame {
  private final User currentUser;
  private final WorkoutDao workoutDao = new WorkoutDaoImpl();
  private JTable table = new JTable(new DefaultTableModel(new Object[] { "ID", "Date", "Notes" }, 0));

  private ImageIcon loadImage() {
    try {
      ImageIcon icon = new ImageIcon(getClass().getResource("traineeimg.png"));
      Image img = icon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
      return new ImageIcon(img);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  public TraineeDashboard(User u) {
    super();
    this.currentUser = u;
    GlobalUISettings.setupFrame(this, "Trainee Dashboard - My Workouts");
    setSize(900, 400);

    // Create left panel for image
    JPanel imagePanel = new JPanel(new BorderLayout());
    imagePanel.setBackground(GlobalUISettings.WINDOW_BG);
    imagePanel.setPreferredSize(new Dimension(200, 0));
    imagePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

    // Add image
    ImageIcon icon = loadImage();
    if (icon != null) {
      JLabel imageLabel = new JLabel(icon);
      imagePanel.add(imageLabel, BorderLayout.NORTH);
    }

    // Add welcome text
    JLabel welcomeLabel = new JLabel("<html><center>Welcome<br>" + currentUser.getUsername() + "</center></html>");
    welcomeLabel.setForeground(Color.WHITE);
    welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
    welcomeLabel.setFont(new Font(welcomeLabel.getFont().getName(), Font.BOLD, 16));
    imagePanel.add(welcomeLabel, BorderLayout.CENTER);

    // Add main content
    JPanel contentPanel = new JPanel(new BorderLayout());
    contentPanel.setBackground(GlobalUISettings.WINDOW_BG);
    contentPanel.add(new JScrollPane(table), BorderLayout.CENTER);

    // Add panels to frame
    add(imagePanel, BorderLayout.WEST);
    add(contentPanel, BorderLayout.CENTER);

    JPanel south = new JPanel();
    south.setBackground(GlobalUISettings.WINDOW_BG);
    JButton refresh = GlobalUISettings.createStyledButton("Refresh");
    JButton add = GlobalUISettings.createStyledButton("Add Workout");
    JButton del = GlobalUISettings.createStyledButton("Delete Selected");
    south.add(refresh);
    south.add(add);
    south.add(del);
    add(south, BorderLayout.SOUTH);
    table.setBackground(Color.WHITE);
    refresh.addActionListener(e -> loadData());
    add.addActionListener(e -> new WorkoutForm(this, currentUser).setVisible(true));
    del.addActionListener(e -> deleteSelected());
    loadData();
  }

  public void loadData() {
    DefaultTableModel m = (DefaultTableModel) table.getModel();
    m.setRowCount(0);
    for (Workout w : workoutDao.getWorkoutsByUser(currentUser.getId()))
      m.addRow(new Object[] { w.getId(), w.getDate(), w.getNotes() });
  }

  private void deleteSelected() {
    int row = table.getSelectedRow();
    if (row < 0) {
      JOptionPane.showMessageDialog(this, "Select a row");
      return;
    }
    int id = (int) table.getValueAt(row, 0);
    workoutDao.deleteWorkout(id, currentUser.getId(), false);
    loadData();
  }
}
