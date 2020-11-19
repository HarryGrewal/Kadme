package com.kadme.test.util;

import com.kadme.test.Point;

import java.util.ArrayList;
import java.util.List;

public class PointsGenerator {

    private Double random(double minPointRange, double maxPointRange) {

        // This will Create A Random Number in between  Min And Max.
        double x = (Math.random() * ((maxPointRange - minPointRange) + 1)) + minPointRange;
        // Creates Answer To The Nearest 100 th, You Can Modify This To Change How It Rounds.
        return Math.round(x * 100.0) / 100.0;
    }

    public Point generateRandomPoint(double minPointRange, double maxPointRange) {
        double x = random(minPointRange, maxPointRange);
        double y = random(minPointRange, maxPointRange);
        return new Point(x, y);
    }

    public List<Point> generateRandomPoints(double minPointRange, double maxPointRange, int numberOfObjects) {
        List<Point> pointList = new ArrayList<>();
        for (int i = 0; i < numberOfObjects; i++) {
            double x = random(minPointRange, maxPointRange);
            double y = random(minPointRange, maxPointRange);
            pointList.add(new Point(x, y));
        }
        return pointList;
    }


}
