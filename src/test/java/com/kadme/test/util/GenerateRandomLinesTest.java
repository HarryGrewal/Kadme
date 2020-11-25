package com.kadme.test.util;

import com.kadme.test.model.Line;
import com.kadme.test.model.Point;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

class GenerateRandomLinesTest {

    @Test
    void generateRandomLines() {
        final Set<Line> lines = GenerateRandomLines.generateRandomLines(OutlineBuilderConstants.MIN_VALUE_FOR_POINT_GENERATION,
                OutlineBuilderConstants.MAX_VALUE_FOR_POINT_GENERATION, OutlineBuilderConstants.LINE_RANGE);
        System.out.println(lines);
    }

    @Test
    void generateRandomPoint() {
        final Point point = GenerateRandomLines.GenerateRandomPoints.generateRandomPoint(100, 500);
        System.out.println(point);
    }

    @Test
    void generateRandomPoints() {
        final List<Point> points = GenerateRandomLines.GenerateRandomPoints.generateRandomPoints(OutlineBuilderConstants.MIN_VALUE_FOR_POINT_GENERATION,
                OutlineBuilderConstants.MAX_VALUE_FOR_POINT_GENERATION, OutlineBuilderConstants.POINT_RANGE);
        System.out.println(points);
    }

    @Test
    void randomCoordinate() {
        double coordinate = GenerateRandomLines.GenerateRandomPoints.GenerateRandomCoordinate.randomCoordinate(0, 10);
        System.out.println("Random coordinate generated in test is " + coordinate);
        Assertions.assertTrue(0 <= coordinate && coordinate <= 11, "coordinate not in set range");
    }


}
