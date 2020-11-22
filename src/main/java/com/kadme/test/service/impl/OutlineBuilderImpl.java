package com.kadme.test.service.impl;

import com.kadme.test.model.Line;
import com.kadme.test.model.Point;
import com.kadme.test.model.Polygon;
import com.kadme.test.service.OutlineBuilder;
import com.kadme.test.util.DrawComponent;
import com.kadme.test.util.GenerateRandomPoints;

import java.util.List;
import java.util.Set;

import static com.kadme.test.util.OutlineBuilderConstants.*;

public class OutlineBuilderImpl implements OutlineBuilder {


    @Override
    public Polygon buildOutline(Set<Line> lines) {

        //ToDO
        List<Point> randomPoints = new GenerateRandomPoints().generateRandomPoints(MIN_VALUE_FOR_POINT_GENERATION,
                MAX_VALUE_FOR_POINT_GENERATION, POINT_RANGE);

        new DrawComponent(lines, randomPoints).draw();
        return new Polygon(randomPoints);
    }
}
