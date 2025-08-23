package org.personal.workoutlogger.ui.dash;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.*;
import java.sql.*;
import org.personal.workoutlogger.model.User;
import org.personal.workoutlogger.connection.ConnectionFactory;
import org.personal.workoutlogger.ui.GlobalUISettings;

public class TrainerDashboard extends JFrame {
  private final User currentUser;
  private JTable table = new JTable(new DefaultTableModel(new Object[] { "Workout ID", "User", "Date", "Notes" }, 0));

  private ImageIcon loadImage() {
    try {
      ImageIcon icon = new ImageIcon("trainerimg.png");
      Image img = icon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
      return new ImageIcon(img);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  public TrainerDashboard(User u) {
    super();
    this.currentUser = u;
    GlobalUISettings.setupFrame(this, "Trainer Dashboard - View All Workouts");
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

    // Add trainer info
    JLabel welcomeLabel = new JLabel("<html><center>Trainer<br>" + currentUser.getUsername() + "</center></html>");
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

    JButton refresh = GlobalUISettings.createStyledButton("Refresh");
    refresh.addActionListener(e -> loadData());
    add(refresh, BorderLayout.SOUTH);
    loadData();
  }

  private void loadData() {
    DefaultTableModel m = (DefaultTableModel) table.getModel();
    m.setRowCount(0);
    String sql = "SELECT w.id, u.username, w.date, w.notes FROM workouts w JOIN users u ON w.user_id=u.id ORDER BY w.date DESC";
    try (Connection con = ConnectionFactory.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery()) {
      while (rs.next())
        m.addRow(new Object[] { rs.getInt(1), rs.getString(2), rs.getDate(3), rs.getString(4) });
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
