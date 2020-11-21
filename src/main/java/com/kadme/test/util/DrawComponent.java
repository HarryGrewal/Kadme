package com.kadme.test.util;

import com.kadme.test.model.Line;
import com.kadme.test.model.Point;

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


    public DrawComponent(Set<Line> lines, List<Point> points) {
        this.lines = lines;
        this.points = points;
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
            g2d.setStroke(new BasicStroke(2f, BasicStroke.CAP_BUTT,
                    BasicStroke.JOIN_MITER, 1.0f, dashingPattern1, 2.0f));
            g2d.setColor(Color.BLUE);

            for (int i = 0; i < points.size() - 1; i++) {
                Point p1 = points.get(i);
                Point p2 = points.get(++i);
                g2d.draw(new Line2D.Double(p1.getX(), p1.getY(),
                        p2.getX(), p2.getY()));
            }
        }
        repaint();
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawComponent(g);
    }

    private void clearLines() {
        lines.clear();
        repaint();
    }

    public void draw() {
        JFrame jFrame = new JFrame("Shortest Polygon Demo");
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setPreferredSize(new Dimension(550, 550));
        jFrame.getContentPane().add(this, BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel();
        JButton drawEx1Button = new JButton("Example 1");
        JButton drawLinesButton = new JButton("Random Lines");
        JButton drawPolygonButton = new JButton("Draw Polygon");
        JButton clearButton = new JButton("Clear");
        buttonsPanel.add(drawEx1Button);
        buttonsPanel.add(drawLinesButton);
        buttonsPanel.add(drawPolygonButton);
        buttonsPanel.add(clearButton);
        jFrame.getContentPane().add(buttonsPanel, BorderLayout.SOUTH);

        //Draw new Lines button
        drawLinesButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                lines = new LinesGenerator().generateRandomLines(MIN_VALUE_FOR_POINT_GENERATION,
                        MAX_VALUE_FOR_POINT_GENERATION, LINE_RANGE);
                componentType = LINE;
                repaint();
            }
        });

        //Draw Polygon button
        drawPolygonButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                componentType = POLYGON;
                repaint();
            }
        });

        //Draw Example 1 button
        drawEx1Button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                //todo
                repaint();
            }
        });

        //Clear Button
        clearButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                clearLines();
            }
        });

        jFrame.pack();
        jFrame.setVisible(true);
    }


}


