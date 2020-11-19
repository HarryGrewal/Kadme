package com.kadme.test.util;

import com.kadme.test.Line;
import com.kadme.test.Point;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;

class DrawLinesTest {

    LinesGenerator linesGenerator = new LinesGenerator();
    DrawLines drawLines = new DrawLines();

    @Test
    void paint() throws InterruptedException {
//        final Set<Line> lines = linesGenerator.generateRandomLines(MyConstants.MIN_VALUE_FOR_POINT_GENERATION,
//                MyConstants.MAX_VALUE_FOR_POINT_GENERATION, MyConstants.LINE_RANGE);
        final Set<Line> lines = new HashSet<>();
        Line l = new Line(new Point(0, 0), new Point(60, 90));
        Line L2 = new Line(new Point(25, 45), new Point(70, 99));
        lines.add(l);
        lines.add(L2);
        drawLines.setLines(lines);

        JFrame frame = new JFrame("Draw Lines");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(drawLines);
        frame.pack();
        frame.setSize(new Dimension(420, 440));
        frame.setVisible(true);
        Thread.sleep(10000000);
    }
}
