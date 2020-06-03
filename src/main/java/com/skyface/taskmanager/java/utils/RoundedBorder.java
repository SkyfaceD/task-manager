package com.skyface.taskmanager.java.utils;

import javax.swing.border.Border;
import java.awt.*;

public class RoundedBorder implements Border {
    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D graphics = (Graphics2D) g;
        graphics.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );
        graphics.setStroke(new BasicStroke(2));
        graphics.setColor(Color.RED);

        assert c != null;

        graphics.drawRoundRect(0, 0, c.getWidth(), c.getHeight(), 16, 16);
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(24, 24, 24, 24);
    }

    @Override
    public boolean isBorderOpaque() {
        return false;
    }
}