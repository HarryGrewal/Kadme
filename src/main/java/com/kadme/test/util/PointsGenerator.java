package com.kadme.test.util;

import com.kadme.test.model.Point;

import java.util.ArrayList;
import java.util.List;

public class PointsGenerator {

    private final Random random = new Random();

    public Point generateRandomPoint(double minPointRange, double maxPointRange) {
        double x = random.randomCoordinate(minPointRange, maxPointRange);
        double y = random.randomCoordinate(minPointRange, maxPointRange);
        return new Point(x, y);
    }

    public List<Point> generateRandomPoints(double minPointRange, double maxPointRange, int numberOfObjects) {
        List<Point> pointList = new ArrayList<>();
        for (int i = 0; i < numberOfObjects; i++) {
            double x = random.randomCoordinate(minPointRange, maxPointRange);
            double y = random.randomCoordinate(minPointRange, maxPointRange);
            pointList.add(new Point(x, y));
        }
        return pointList;
    }


}
