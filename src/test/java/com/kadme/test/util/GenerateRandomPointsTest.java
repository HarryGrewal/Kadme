package com.kadme.test.util;

import com.kadme.test.model.Point;
import org.junit.jupiter.api.Test;

import java.util.List;

class GenerateRandomPointsTest {

    private GenerateRandomPoints generateRandomPoints = new GenerateRandomPoints();

    @Test
    void generateRandomPoint() {
        final Point point = generateRandomPoints.generateRandomPoint(100, 500);
        System.out.println(point);
    }

    @Test
    void generateRandomPoints() {
        final List<Point> points = generateRandomPoints.generateRandomPoints(OutlineBuilderConstants.MIN_VALUE_FOR_POINT_GENERATION,
                OutlineBuilderConstants.MAX_VALUE_FOR_POINT_GENERATION, OutlineBuilderConstants.POINT_RANGE);
        System.out.println(points);
    }
}
