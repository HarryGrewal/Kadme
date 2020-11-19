package com.kadme.test.util.impl;

import com.kadme.test.Line;
import com.kadme.test.Point;
import com.kadme.test.util.FindGroups;
import com.kadme.test.util.LinesGenerator;
import com.kadme.test.util.MyConstants;
import com.kadme.test.util.PointsGenerator;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

class FindGroupsImplTest {

    LinesGenerator linesGenerator = new LinesGenerator();
    PointsGenerator pointsGenerator = new PointsGenerator();

    @Test
    void generateCoordinates() {
        final Set<Line> lines = linesGenerator.generateRandomLines(MyConstants.MIN_VALUE_FOR_POINT_GENERATION,
                MyConstants.MAX_VALUE_FOR_POINT_GENERATION, MyConstants.LINE_RANGE);

        final List<Point> points = pointsGenerator.generateRandomPoints(MyConstants.MIN_VALUE_FOR_POINT_GENERATION,
                MyConstants.MAX_VALUE_FOR_POINT_GENERATION, MyConstants.POINT_RANGE);
        System.out.println(lines);
        System.out.println(points);
    }

    @Test
    void findGroups() {
        //TODO
        FindGroups findGroups = new FindGroupsImpl();

    }
}
