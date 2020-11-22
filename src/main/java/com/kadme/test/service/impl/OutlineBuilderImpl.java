package com.kadme.test.service.impl;

import com.kadme.test.model.Line;
import com.kadme.test.model.Point;
import com.kadme.test.model.Polygon;
import com.kadme.test.service.FindGroups;
import com.kadme.test.service.FindOrder;
import com.kadme.test.service.OutlineBuilder;
import com.kadme.test.util.DrawComponent;
import com.kadme.test.util.PointsGenerator;

import java.util.List;
import java.util.Set;

import static com.kadme.test.util.OutlineBuilderConstants.*;

public class OutlineBuilderImpl implements OutlineBuilder {

    private FindGroups findGroups = new FindGroupsImpl();
    private FindOrder findOrder = new FindOrderImpl();

    @Override
    public Polygon buildOutline(Set<Line> lines) {

        //ToDO
        List<Point> randomPoints = new PointsGenerator().generateRandomPoints(MIN_VALUE_FOR_POINT_GENERATION,
                MAX_VALUE_FOR_POINT_GENERATION, POINT_RANGE);

        new DrawComponent(lines, randomPoints).draw();
        return new Polygon(randomPoints);
    }
}
