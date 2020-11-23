package com.kadme.test.util;

import com.kadme.test.model.Line;
import com.kadme.test.model.Point;
import com.kadme.test.service.impl.OutlineBuilderImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import java.util.List;
import java.util.Set;

import static com.kadme.test.util.OutlineBuilderConstants.*;

public class DrawComponent extends JComponent {

    private Set<Line> lines;
    private List<Point> points;
    private String componentType;

    public DrawComponent(Set<Line> lines) {
        this.lines = lines;
    }

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

        for (Line line : lines) {
            g2d.draw(new Line2D.Double(line.getP1().getX(), line.getP1().getY(),
                    line.getP2().getX(), line.getP2().getY()));
        }

        if (componentType == POLYGON) {
            float[] dashingPattern1 = {2f, 2f};
            g2d.setStroke(new BasicStroke(5f, BasicStroke.CAP_BUTT,
                    BasicStroke.JOIN_MITER, 1.0f, dashingPattern1, 2.0f));
            g2d.setColor(Color.RED);

            /*for (int i = 0; i < points.size() - 1; i++) {
                Point p1 = points.get(i);
                Point p2 = points.get(++i);
                g2d.draw(new Line2D.Double(p1.getX(), p1.getY(),
                        p2.getX(), p2.getY()));
            }*/
            for (Line line : lines) {
                g2d.draw(new Line2D.Double(line.getP1().getX(), line.getP1().getY(),
                        line.getP2().getX(), line.getP2().getY()));
            }
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
        JButton clearButton = new JButton("Clear");
        buttonsPanel.add(drawEx1Button);
        buttonsPanel.add(drawEx2Button);
        buttonsPanel.add(drawEx3Button);
        buttonsPanel.add(drawLinesButton);
        buttonsPanel.add(drawPolygonButton);
        buttonsPanel.add(clearButton);
        jFrame.getContentPane().add(buttonsPanel, BorderLayout.SOUTH);

        //Draw Example 1 button
        drawEx1Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                componentType = EXAMPLE_1;
                lines = EXAMPLE_1_SET;
                repaint();
            }
        });

        //Draw Example 2 button
        drawEx2Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                componentType = EXAMPLE_2;
                lines = EXAMPLE_2_SET;
                repaint();
            }
        });

        //Draw Example 3 button
        drawEx3Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                componentType = EXAMPLE_3;
                lines = EXAMPLE_3_SET;
                repaint();
            }
        });

        //Draw new Lines button
        drawLinesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                componentType = LINE;
                lines = new GenerateRandomLines().generateRandomLines(MIN_VALUE_FOR_POINT_GENERATION,
                        MAX_VALUE_FOR_POINT_GENERATION, LINE_RANGE);
                jFrame.dispose();
                jFrame.setVisible(false);
                new OutlineBuilderImpl().buildOutline(lines);
            }
        });

        //Draw Polygon button
        drawPolygonButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                componentType = POLYGON;
                points = new GenerateRandomPoints().generateRandomPoints(MIN_VALUE_FOR_POINT_GENERATION,
                        MAX_VALUE_FOR_POINT_GENERATION, POINT_RANGE);

                repaint();
            }
        });

        //Clear Button
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clear();
            }
        });

        jFrame.pack();
        jFrame.setVisible(true);
    }

    private void clear() {
        lines.clear();
//        points.clear();
        repaint();
    }

}


