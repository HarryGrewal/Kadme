package com.kadme.test.util;

import com.kadme.test.model.Line;
import com.kadme.test.model.Point;
import com.kadme.test.service.impl.OutlineBuilderImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.util.List;
import java.util.Set;

import static com.kadme.test.util.OutlineBuilderConstants.*;

public class DrawComponent extends JComponent {

    private final List<Point> points;
    private Set<Line> lines;
    private String componentType;

    public DrawComponent(Set<Line> lines, List<Point> points) {
        this.lines = lines;
        this.points = points;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawComponent(g);
    }

    private void drawComponent(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        g2d.setStroke(new BasicStroke(3f));
        g2d.setColor(Color.BLACK);

        if (componentType != WEAR_MASK) {
            for (Line line : lines) {
                g2d.draw(new Line2D.Double(line.getP1().getX(), line.getP1().getY(),
                        line.getP2().getX(), line.getP2().getY()));
            }

            if (componentType == POLYGON) {
                float[] dashingPattern1 = {2f, 2f};
                g2d.setStroke(new BasicStroke(5f, BasicStroke.CAP_BUTT,
                        BasicStroke.JOIN_MITER, 1.0f, dashingPattern1, 2.0f));
                g2d.setColor(Color.RED);

                for (int i = 0; i < points.size() - 1; i++) {
                    Point p1 = points.get(i);
                    Point p2 = points.get(++i);
                    g2d.draw(new Line2D.Double(p1.getX(), p1.getY(),
                            p2.getX(), p2.getY()));
                }
              /*  g2d.draw(new Line2D.Double(points.get(points.size() - 1).getX(),
                        points.get(points.size() - 1).getY(),
                        points.get(0).getX(), points.get(0).getY()));*/
            }
        }

        if (componentType == WEAR_MASK) {
            g2d.setColor(Color.RED);
            g2d.fillOval(100, 100, 300, 300);
            g2d.setColor(Color.BLACK);
            g2d.fillOval(110, 130, 60, 60);
            g2d.fillOval(170, 130, 60, 60);
            g2d.fillOval(100, 210, 220, 120);
            g2d.setColor(Color.WHITE);
            g2d.fillRect(100, 210, 220, 60);
            g2d.fillOval(100, 220, 220, 80);
        }
        repaint();
    }


    public void draw() {
        JFrame jFrame = new JFrame("Shortest Polygon Demo");
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setPreferredSize(new Dimension(650, 650));
        jFrame.getContentPane().add(this, BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel();
        JButton drawEx1Button = new JButton("Example 1");
        JButton drawEx2Button = new JButton("Example 2");
        JButton drawEx3Button = new JButton("Example 3");
        JButton drawLinesButton = new JButton("Random Lines");
        JButton drawPolygonButton = new JButton("Draw Polygon");
        JButton wearMaskButton = new JButton("Wear Mask");
        buttonsPanel.add(drawEx1Button);
        buttonsPanel.add(drawEx2Button);
        buttonsPanel.add(drawEx3Button);
        buttonsPanel.add(drawLinesButton);
        buttonsPanel.add(drawPolygonButton);
        buttonsPanel.add(wearMaskButton);
        jFrame.getContentPane().add(buttonsPanel, BorderLayout.SOUTH);

        //Draw Example 1 button
        drawEx1Button.addActionListener(e -> {
            componentType = EXAMPLE_1;
            lines = EXAMPLE_1_SET;
            redraw(jFrame);
            repaint();
        });

        //Draw Example 2 button
        drawEx2Button.addActionListener(e -> {
            componentType = EXAMPLE_2;
            lines = EXAMPLE_2_SET;
            redraw(jFrame);
            repaint();
        });

        //Draw Example 3 button
        drawEx3Button.addActionListener(e -> {
            componentType = EXAMPLE_3;
            lines = EXAMPLE_3_SET;
            redraw(jFrame);
            repaint();
        });

        //Draw new Lines button
        drawLinesButton.addActionListener(e -> {
            componentType = LINE;
            lines = GenerateRandomLines.generateRandomLines(MIN_VALUE_FOR_POINT_GENERATION,
                    MAX_VALUE_FOR_POINT_GENERATION, LINE_RANGE);
            redraw(jFrame);
        });

        //Draw Polygon button
        drawPolygonButton.addActionListener(e -> {
            componentType = POLYGON;
            repaint();
        });

        //Draw Real Polygon :) button
        wearMaskButton.addActionListener(e -> {
            componentType = WEAR_MASK;
            buttonsPanel.remove(drawPolygonButton);
            repaint();
        });

        jFrame.pack();
        jFrame.setVisible(true);
    }

    private void redraw(JFrame jFrame) {
        jFrame.dispose();
        jFrame.setVisible(false);
        new OutlineBuilderImpl().buildOutline(lines);
    }

}


