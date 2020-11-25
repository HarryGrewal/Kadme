package com.kadme.test.util;

import com.kadme.test.model.Line;
import com.kadme.test.model.Point;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


class GenerateRandomLines {

    public static Set<Line> generateRandomLines(double minPointRange, double maxPointRange, int range) {
        Set<Line> lines = new HashSet<>();
        for (int i = 0; i < range; i++) {
            final Point p1 = GenerateRandomPoints.generateRandomPoint(minPointRange, maxPointRange);
            final Point p2 = GenerateRandomPoints.generateRandomPoint(minPointRange, maxPointRange);
            lines.add(new Line(p1, p2));
        }
        return lines;
    }

    static class GenerateRandomPoints {
        static Point generateRandomPoint(double minPointRange, double maxPointRange) {
            double x = GenerateRandomCoordinate.randomCoordinate(minPointRange, maxPointRange);
            double y = GenerateRandomCoordinate.randomCoordinate(minPointRange, maxPointRange);
            return new Point(x, y);
        }

        static List<Point> generateRandomPoints(double minPointRange, double maxPointRange, int numberOfObjects) {
            List<Point> pointList = new ArrayList<>();
            for (int i = 0; i < numberOfObjects; i++) {
                double x = GenerateRandomCoordinate.randomCoordinate(minPointRange, maxPointRange);
                double y = GenerateRandomCoordinate.randomCoordinate(minPointRange, maxPointRange);
                pointList.add(new Point(x, y));
            }
            return pointList;
        }

        static class GenerateRandomCoordinate {
            static double randomCoordinate(double minPointRange, double maxPointRange) {

                // This will Create A Random Number in between  Min And Max.
                double x = (Math.random() * ((maxPointRange - minPointRange) + 1)) + minPointRange;
                // Creates Answer To The Nearest 100 th, You Can Modify This To Change How It Rounds. ex. 5.36
                return round(x, 3);
            }

            static double round(double value, int places) {
                if (places < 0) throw new IllegalArgumentException();

                BigDecimal bd = BigDecimal.valueOf(value);
                bd = bd.setScale(places, RoundingMode.HALF_UP);
                return bd.doubleValue();
            }

        }
    }
}
