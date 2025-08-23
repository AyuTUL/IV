package org.personal.workoutlogger.ui;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class GlobalUISettings {
    private static final Font DEFAULT_FONT = new Font("Segoe UI", Font.PLAIN, 12);
    private static final Color BUTTON_BG = new Color(225, 225, 225); // Darker button color
    private static final Color BUTTON_HIGHLIGHT = new Color(210, 210, 210); // Even darker on hover
    private static final Color BUTTON_BORDER = new Color(200, 200, 200); // Darker border
    public static final Color WINDOW_BG = new Color(240, 240, 240); // Very light gray background
    private static final int BUTTON_RADIUS = 12; // More rounded corners
    private static final Insets BUTTON_MARGINS = new Insets(4, 12, 4, 12);

    static class RoundedBorder extends AbstractBorder {
        private final int radius;

        RoundedBorder(int radius) {
            this.radius = radius;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(BUTTON_BORDER);
            g2.draw(new RoundRectangle2D.Float(x, y, width - 1, height - 1, radius, radius));
            g2.dispose();
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(radius / 2, radius, radius / 2, radius);
        }

        @Override
        public Insets getBorderInsets(Component c, Insets insets) {
            insets.left = insets.right = radius;
            insets.top = insets.bottom = radius / 2;
            return insets;
        }
    }

    public static void setupGlobalUI() {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());

                    // Set global font
                    FontUIResource font = new FontUIResource(DEFAULT_FONT);
                    for (Object key : UIManager.getLookAndFeelDefaults().keySet()) {
                        if (key.toString().toLowerCase().contains("font")) {
                            UIManager.put(key, font);
                        }
                    }

                    // Button appearance
                    UIManager.put("Button.background", BUTTON_BG);
                    UIManager.put("Button.foreground", Color.BLACK);
                    UIManager.put("Label.foreground", Color.BLACK);
                    UIManager.put("TextField.foreground", Color.BLACK);
                    UIManager.put("TextArea.foreground", Color.BLACK);
                    UIManager.put("ComboBox.foreground", Color.BLACK);
                    UIManager.put("Table.foreground", Color.BLACK);
                    UIManager.put("TableHeader.foreground", Color.BLACK);
                    UIManager.put("Button.contentMargins", BUTTON_MARGINS);
                    UIManager.put("Button.focusPainted", false);

                    // Override Nimbus button painter
                    UIManager.getLookAndFeelDefaults().put("Button[Enabled].backgroundPainter",
                            (Painter<JComponent>) (g, c, w, h) -> {
                                Graphics2D g2 = (Graphics2D) g;
                                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                                // Create glassy background
                                GradientPaint gradient = new GradientPaint(
                                        0, 0, BUTTON_BG,
                                        0, h / 2, BUTTON_HIGHLIGHT,
                                        true);
                                g2.setPaint(gradient);
                                g2.fill(new RoundRectangle2D.Float(0, 0, w - 1, h - 1, BUTTON_RADIUS * 2,
                                        BUTTON_RADIUS * 2));

                                // Add slight highlight at top
                                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
                                g2.setColor(Color.WHITE);
                                g2.fillRect(0, 0, w, h / 4);

                                // Draw border
                                g2.setComposite(AlphaComposite.SrcOver);
                                g2.setColor(BUTTON_BORDER);
                                g2.setStroke(new BasicStroke(1f));
                                g2.draw(new RoundRectangle2D.Float(0, 0, w - 1, h - 1, BUTTON_RADIUS * 2,
                                        BUTTON_RADIUS * 2));
                            });

                    // Background colors
                    UIManager.put("Panel.background", WINDOW_BG);
                    UIManager.put("Frame.background", WINDOW_BG);
                    UIManager.put("control", WINDOW_BG);
                    UIManager.put("nimbusBase", WINDOW_BG);
                    UIManager.put("Table.background", WINDOW_BG);
                    UIManager.put("TableHeader.background", WINDOW_BG);
                    UIManager.put("ScrollPane.background", WINDOW_BG);

                    // Global component defaults
                    UIManager.put("Button.background", BUTTON_BG);
                    UIManager.put("Button.foreground", Color.BLACK);
                    UIManager.put("Button.font", font);
                    UIManager.put("Button.margin", BUTTON_MARGINS);
                    UIManager.put("Button.focusPainted", false);
                    UIManager.put("Button.defaultButtonFollowsFocus", false);
                    UIManager.put("Panel.background", WINDOW_BG);
                    UIManager.put("Frame.background", WINDOW_BG);

                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static JButton createStyledButton(String text) {
        return createStyledButton(text, null);
    }

    public static JButton createStyledButton(String text, String iconName) {
        JButton btn = new JButton(text);
        if (iconName != null) {
            btn.setIcon(Icons.getIcon(iconName, 16));
            btn.setHorizontalTextPosition(SwingConstants.RIGHT);
            btn.setIconTextGap(8);
        }
        btn.setFocusPainted(false);
        btn.setBackground(BUTTON_BG);
        btn.setForeground(Color.BLACK);
        btn.setFont(DEFAULT_FONT);
        btn.setBorder(BorderFactory.createCompoundBorder(
                new RoundedBorder(BUTTON_RADIUS),
                BorderFactory.createEmptyBorder(4, 12, 4, 12)));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        // Make buttons wider to accommodate text
        btn.setPreferredSize(new Dimension(120, 28));
        // Set minimum size to ensure text is always visible
        btn.setMinimumSize(new Dimension(100, 28));
        return btn;
    }

    public static void setupFrame(JFrame frame, String title) {
        frame.setTitle(title);
        frame.setDefaultCloseOperation(title.contains("Login") ? JFrame.EXIT_ON_CLOSE : JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(true);
        frame.getContentPane().setBackground(WINDOW_BG);
        frame.setLocationRelativeTo(null);
    }
}
