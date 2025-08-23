
package org.personal.workoutlogger.ui;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class UIStyle {
    public static Font getFont() {
        return FONT;
    }

    public static final Color BG = new Color(242, 244, 247);
    public static final Color PANEL_BG = new Color(230, 233, 240);
    public static final Color BORDER = new Color(200, 210, 230);
    public static final Color BUTTON_BG = new Color(220, 225, 235);
    public static final Color BUTTON_FG = new Color(40, 40, 40);
    public static final Color BUTTON_BG_HOVER = new Color(200, 210, 230);
    public static final Color TABLE_HEADER_BG = new Color(220, 225, 235);
    public static final Color TABLE_ROW_ALT = new Color(250, 250, 252);
    public static final Font FONT = new Font("San Francisco", Font.PLAIN, 16);
    public static final Font FONT_BOLD = new Font("San Francisco", Font.BOLD, 16);

    public static void apply(JFrame frame) {
        frame.getContentPane().setBackground(BG);
        setUIFont(FONT);
        setLookAndFeel();
    }

    public static void apply(JDialog dialog) {
        dialog.getContentPane().setBackground(BG);
        setUIFont(FONT);
        setLookAndFeel();
    }

    public static void stylePanel(JPanel panel) {
        panel.setBackground(PANEL_BG);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER, 1),
                BorderFactory.createEmptyBorder(12, 12, 12, 12)));
    }

    public static JButton styledButton(String text) {
        JButton btn = new JButton(text);
        btn.setFocusPainted(false);
        btn.setBackground(BUTTON_BG);
        btn.setForeground(BUTTON_FG);
        btn.setFont(FONT_BOLD);
        btn.setBorder(BorderFactory.createCompoundBorder(
                new RoundedBorder(14),
                BorderFactory.createEmptyBorder(6, 18, 6, 18)));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(BUTTON_BG_HOVER);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(BUTTON_BG);
            }
        });
        return btn;
    }

    public static void styleTable(JTable table) {
        JTableHeader header = table.getTableHeader();
        header.setBackground(TABLE_HEADER_BG);
        header.setFont(FONT_BOLD);
        header.setBorder(BorderFactory.createLineBorder(BORDER, 1));
        table.setFont(FONT);
        table.setRowHeight(28);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setSelectionBackground(new Color(180, 200, 230));
        table.setSelectionForeground(Color.BLACK);
        // Alternate row color
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object v, boolean isSelected, boolean hasFocus,
                    int row, int col) {
                Component c = super.getTableCellRendererComponent(t, v, isSelected, hasFocus, row, col);
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : TABLE_ROW_ALT);
                }
                return c;
            }
        });
    }

    public static void styleTextField(JTextField tf) {
        tf.setFont(FONT);
        tf.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER, 1),
                BorderFactory.createEmptyBorder(4, 8, 4, 8)));
        tf.setBackground(Color.WHITE);
    }

    public static void styleTextArea(JTextArea ta) {
        ta.setFont(FONT);
        ta.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER, 1),
                BorderFactory.createEmptyBorder(6, 8, 6, 8)));
        ta.setBackground(Color.WHITE);
    }

    public static void setLookAndFeel() {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    return;
                }
            }
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }
    }

    public static void setUIFont(Font f) {
        java.util.Enumeration<Object> keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof javax.swing.plaf.FontUIResource) {
                UIManager.put(key, new javax.swing.plaf.FontUIResource(f));
            }
        }
    }
}
