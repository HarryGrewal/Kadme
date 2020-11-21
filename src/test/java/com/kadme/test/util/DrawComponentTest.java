package com.kadme.test.util;

import com.kadme.test.model.Line;
import com.kadme.test.model.Point;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static com.kadme.test.util.OutlineBuilderConstants.*;

class DrawComponentTest {

    @Test
    void draw() throws InterruptedException {
        //        final Set<Line> lines = new HashSet<>();
//        Line l = new Line(new Point(30, 30 ), new Point(200, 30));
//        Line L2 = new Line(new Point(200, 30 ), new Point(30, 200));
//        Line L3 = new Line(new Point(30, 200), new Point(200, 200));
//        Line L4 = new Line(new Point(200, 200), new Point(30, 30));
//        Line L5 = new Line(new Point(76, 150), new Point(220, 49));
//        Line L6 = new Line(new Point(0, 0), new Point(60, 90));
//        Line L7 = new Line(new Point(120, 50), new Point(360, 50));
//        Line L8 = new Line(new Point(59.2d, 99.8d), new Point(419.1d, 99.8d));
//        lines.add(l);
//        lines.add(L2);
//        lines.add(L3);
//        lines.add(L4);
//        lines.add(L5);
//        lines.add(L6);
//        lines.add(L7);
//        lines.add(L8);

        final Set<Line> randomLines = new LinesGenerator().generateRandomLines(MIN_VALUE_FOR_POINT_GENERATION,
                MAX_VALUE_FOR_POINT_GENERATION, LINE_RANGE);
        final List<Point> randomPoints = new PointsGenerator().generateRandomPoints(OutlineBuilderConstants.MIN_VALUE_FOR_POINT_GENERATION,
                OutlineBuilderConstants.MAX_VALUE_FOR_POINT_GENERATION, OutlineBuilderConstants.POINT_RANGE);
        DrawComponent drawComponent = new DrawComponent(randomLines, randomPoints);
        drawComponent.draw();

        Thread.sleep(1000);
    }
}

