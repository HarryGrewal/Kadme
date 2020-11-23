package com.kadme.test.util;

import com.kadme.test.model.Line;
import com.kadme.test.model.Point;

import java.util.Comparator;

public class ByAngleComparator {

    public static Comparator<Line> byAngleComparator(
            Point center) {
        final double centerX = center.getX();
        final double centerY = center.getY();
        return (l0, l1) -> {
            Point p0, p1;
            p0 = l0.getP1().getX() < l0.getP2().getX() ? l0.getP1() : l0.getP2();
            p1 = l1.getP1().getX() < l1.getP2().getX() ? l1.getP1() : l1.getP2();
            double angle0 = angleToX(
                    centerX, centerY, p0.getX(), p0.getY());
            double angle1 = angleToX(
                    centerX, centerY, p1.getX(), p1.getY());
            return Double.compare(angle0, angle1);
        };
    }

    private static double angleToX(
            double x0, double y0, double x1, double y1) {
        double dx = x1 - x0;
        double dy = y1 - y0;
        return Math.atan2(dy, dx);
    }

}
