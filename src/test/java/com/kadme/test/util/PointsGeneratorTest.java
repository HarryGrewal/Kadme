package com.kadme.test.util;

import com.kadme.test.model.Point;
import org.junit.jupiter.api.Test;

import java.util.List;

class PointsGeneratorTest {

    private PointsGenerator pointsGenerator = new PointsGenerator();

    @Test
    void generateRandomPoint() {
        final Point point = pointsGenerator.generateRandomPoint(100, 500);
        System.out.println(point);
    }

    @Test
    void generateRandomPoints() {
        final List<Point> points = pointsGenerator.generateRandomPoints(OutlineBuilderConstants.MIN_VALUE_FOR_POINT_GENERATION,
                OutlineBuilderConstants.MAX_VALUE_FOR_POINT_GENERATION, OutlineBuilderConstants.POINT_RANGE);
        System.out.println(points);
    }
}
