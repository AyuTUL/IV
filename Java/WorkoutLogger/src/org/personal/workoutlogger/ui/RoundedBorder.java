package org.personal.workoutlogger.ui;

import javax.swing.border.AbstractBorder;
import java.awt.*;
import javax.swing.*;

public class RoundedBorder extends AbstractBorder {
    private final int radius;

    public RoundedBorder(int r) {
        radius = r;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        g.setColor(new Color(180, 200, 230));
        g.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(radius + 1, radius + 1, radius + 1, radius + 1);
    }

    @Override
    public Insets getBorderInsets(Component c, Insets insets) {
        insets.left = insets.right = insets.top = insets.bottom = radius + 1;
        return insets;
    }
}
