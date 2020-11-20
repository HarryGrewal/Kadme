package com.kadme.test.util;

import com.kadme.test.model.Line;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.util.Random;
import java.util.Set;

public class DrawLines extends JComponent {

    private Set<Line> lines;

    public void setLines(Set<Line> lines) {
        this.lines = lines;
    }

    @Override
    public void paint(Graphics g) {
        // Draw a simple line using the Graphics2D draw() method.
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(2f));
        Random r = new Random();

        for (Line line : lines) {
            // g2.setColor(new Color(r.nextInt(256),r.nextInt(256),r.nextInt(256)));
            g2.setColor(Color.BLUE);
            g2.draw(new Line2D.Double(line.getP1().getX(), line.getP1().getY(),
                    line.getP2().getX(), line.getP2().getY()));
        }

    }
}
