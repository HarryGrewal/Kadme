package com.kadme.test.util;

import com.kadme.test.model.Point;

import java.util.Comparator;

public class ByAngleComparator {

    private static double angleTheta(
            double x0, double y0, double x1, double y1) {
        double dx = x1 - x0;
        double dy = y1 - y0;
        final double v = Math.atan2(dy, dx);

        System.out.println("\nangelTheta from comparator returns " + v);
        return v;


    }

    public Comparator<Point> compareByAngle(
            Point center) {
        final double centerX = center.getX();
        final double centerY = center.getY();
        return (p0, p1) -> {

            double angle0 = angleTheta(
                    centerX, centerY, p0.getX(), p0.getY());
            double angle1 = angleTheta(
                    centerX, centerY, p1.getX(), p1.getY());

            final int compare = Double.compare(angle0, angle1);

            System.out.println("\nCompare by angle returns " + compare + " angle0 = " + angle0 + " angle1 = " + angle1);
            return compare;
        };
    }

}
