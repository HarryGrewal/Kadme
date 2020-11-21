package com.kadme.test.util;

import com.kadme.test.model.Point;

import java.util.ArrayList;
import java.util.List;

import static com.kadme.test.util.RandomCoordinate.randomCoordinate;

public class PointsGenerator {



    public Point generateRandomPoint(double minPointRange, double maxPointRange) {
        double x = randomCoordinate(minPointRange, maxPointRange);
        double y = randomCoordinate(minPointRange, maxPointRange);
        return new Point(x, y);
    }

    public List<Point> generateRandomPoints(double minPointRange, double maxPointRange, int numberOfObjects) {
        List<Point> pointList = new ArrayList<>();
        for (int i = 0; i < numberOfObjects; i++) {
            double x = randomCoordinate(minPointRange, maxPointRange);
            double y = randomCoordinate(minPointRange, maxPointRange);
            pointList.add(new Point(x, y));
        }
        return pointList;
    }


}
