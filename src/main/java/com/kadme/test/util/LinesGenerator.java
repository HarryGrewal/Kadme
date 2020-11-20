package com.kadme.test.util;

import com.kadme.test.model.Line;
import com.kadme.test.model.Point;

import java.util.HashSet;
import java.util.Set;

public class LinesGenerator {

    private PointsGenerator pointsGenerator = new PointsGenerator();

    public Set<Line> generateRandomLines(double minPointRange, double maxPointRange, int range) {
        Set<Line> lines = new HashSet<>();
        for (int i = 0; i < range; i++) {
            final Point p1 = pointsGenerator.generateRandomPoint(minPointRange, maxPointRange);
            final Point p2 = pointsGenerator.generateRandomPoint(minPointRange, maxPointRange);
            lines.add(new Line(p1, p2));
        }
        return lines;
    }
}
