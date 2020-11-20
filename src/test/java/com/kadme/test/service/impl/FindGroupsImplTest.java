package com.kadme.test.service.impl;

import com.kadme.test.model.Line;
import com.kadme.test.model.Point;
import com.kadme.test.service.FindGroups;
import com.kadme.test.util.LinesGenerator;
import com.kadme.test.util.OutlineBuilderConstants;
import com.kadme.test.util.PointsGenerator;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

class FindGroupsImplTest {

    private LinesGenerator linesGenerator = new LinesGenerator();
    private PointsGenerator pointsGenerator = new PointsGenerator();

    @Test
    void generateCoordinates() {
        final Set<Line> lines = linesGenerator.generateRandomLines(OutlineBuilderConstants.MIN_VALUE_FOR_POINT_GENERATION,
                OutlineBuilderConstants.MAX_VALUE_FOR_POINT_GENERATION, OutlineBuilderConstants.LINE_RANGE);

        final List<Point> points = pointsGenerator.generateRandomPoints(OutlineBuilderConstants.MIN_VALUE_FOR_POINT_GENERATION,
                OutlineBuilderConstants.MAX_VALUE_FOR_POINT_GENERATION, OutlineBuilderConstants.POINT_RANGE);
        System.out.println(lines);
        System.out.println(points);
    }

    @Test
    void findGroups() {
        //TODO
        FindGroups findGroups = new FindGroupsImpl();

    }
}
