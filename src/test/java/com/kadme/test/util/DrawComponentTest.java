package com.kadme.test.util;

import com.kadme.test.model.Line;
import com.kadme.test.model.Point;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
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
        Line l1 = new Line(new Point(175, 50), new Point(180, 450));
        Line l2 = new Line(new Point(180, 50), new Point(185, 450));
        Line l3 = new Line(new Point(185, 50), new Point(190, 450));
        Line l6 = new Line(new Point(190, 50), new Point(195, 450));
        Line l7 = new Line(new Point(195, 50), new Point(195, 450));
        Line l8 = new Line(new Point(200, 50), new Point(200, 450));

        lines.add(l1);
        lines.add(l2);
        lines.add(l3);
        lines.add(l6);
        lines.add(l7);
        lines.add(l8);

        DrawComponent drawComponent = new DrawComponent(lines, new ArrayList<>());
        drawComponent.draw();

        Thread.sleep(1000);
    }

}

