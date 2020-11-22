package com.kadme.test.util;

import com.kadme.test.model.Line;
import com.kadme.test.model.Point;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.kadme.test.util.OutlineBuilderConstants.*;

class DrawComponentTest {

    @Test
    void draw() throws InterruptedException {

        final Set<Line> randomLines = new LinesGenerator().generateRandomLines(MIN_VALUE_FOR_POINT_GENERATION,
                MAX_VALUE_FOR_POINT_GENERATION, LINE_RANGE);
        final List<Point> randomPoints = new PointsGenerator().generateRandomPoints(OutlineBuilderConstants.MIN_VALUE_FOR_POINT_GENERATION,
                OutlineBuilderConstants.MAX_VALUE_FOR_POINT_GENERATION, OutlineBuilderConstants.POINT_RANGE);
        DrawComponent drawComponent = new DrawComponent(randomLines, randomPoints);
        drawComponent.draw();

        Thread.sleep(1000);
    }

    @Test
    void drawExample1() throws InterruptedException {
        final Set<Line> lines = new HashSet<>();
        Line l2 = new Line(new Point(200, 30), new Point(30, 200));
        Line l3 = new Line(new Point(50, 150), new Point(250, 350));
        Line l4 = new Line(new Point(250, 350), new Point(350, 250));
        Line l5 = new Line(new Point(350, 250), new Point(150, 50));
        Line l6 = new Line(new Point(150, 50), new Point(50, 150));
        lines.add(l2);
        lines.add(l3);
        lines.add(l4);
        lines.add(l5);
        lines.add(l6);
        DrawComponent drawComponent = new DrawComponent(lines);
        drawComponent.draw();

        Thread.sleep(1000);
    }
}

