package com.mycompany.cs321jarvis;

/**
 *
 * @author matthewdonoho
 */

import javax.swing.*;
import java.awt.*;

class roundedPanel extends JPanel {
    private Color backgroundColor;
    private int cornerRadius;

    public roundedPanel(Color bgColor, int radius) {
        this.backgroundColor = bgColor;
        this.cornerRadius = radius;
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Dimension arcs = new Dimension(cornerRadius, cornerRadius);
        int width = getWidth();
        int height = getHeight();
        Graphics2D graphics = (Graphics2D) g;
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        //Draws the rounded panel with borders.
        graphics.setColor(backgroundColor);
        graphics.fillRoundRect(0, 0, width - 1, height - 1, arcs.width, arcs.height); //paint background
    }

    @Override
    public Dimension getPreferredSize() {
        // The preferred size will be determined by the content (set in Jarvis class)
        return super.getPreferredSize();
    }
}