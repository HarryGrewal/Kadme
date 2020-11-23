package com.kadme.test.util;

import com.kadme.test.model.Line;
import com.kadme.test.model.Point;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

class DrawComponentTest {

    @Test
    void draw() throws InterruptedException {

       /* final Set<Line> randomLines = new GenerateRandomLines().generateRandomLines(MIN_VALUE_FOR_POINT_GENERATION,
                MAX_VALUE_FOR_POINT_GENERATION, LINE_RANGE);
        final List<Point> randomPoints = new GenerateRandomPoints().generateRandomPoints(OutlineBuilderConstants.MIN_VALUE_FOR_POINT_GENERATION,
                OutlineBuilderConstants.MAX_VALUE_FOR_POINT_GENERATION, OutlineBuilderConstants.POINT_RANGE);*/


        final Set<Line> lines = new HashSet<>();
        Line l1 = new Line(new Point(100, 50), new Point(350, 450));
        Line l2 = new Line(new Point(150, 50), new Point(400, 450));
        Line l3 = new Line(new Point(200, 50), new Point(450, 450));
        Line l4 = new Line(new Point(50, 250), new Point(550, 275));
        Line l5 = new Line(new Point(50, 300), new Point(600, 325));
        Line l6 = new Line(new Point(500, 50), new Point(50, 500));
        Line l7 = new Line(new Point(550, 50), new Point(100, 550));
        Line l8 = new Line(new Point(600, 50), new Point(150, 600));

        lines.add(l1);
        lines.add(l2);
        lines.add(l3);
        lines.add(l4);
        lines.add(l5);
        lines.add(l6);
        lines.add(l7);
        lines.add(l8);

        DrawComponent drawComponent = new DrawComponent(lines);
        drawComponent.draw();

        Thread.sleep(10000);
    }

}

