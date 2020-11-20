package com.kadme.test.service.impl;

import com.kadme.test.model.Line;
import com.kadme.test.model.Polygon;
import com.kadme.test.service.FindGroups;
import com.kadme.test.service.FindIntersections;
import com.kadme.test.service.FindOrder;
import com.kadme.test.service.OutlineBuilder;

import java.util.Set;

public class OutlineBuilderImpl implements OutlineBuilder {

    FindIntersections findIntersections;
    FindGroups findGroups;
    FindOrder findOrder;

    @Override
    public Polygon buildOutline(Set<Line> lines) {

        findIntersections = new FindIntersectionsImpl();
        findGroups = new FindGroupsImpl();
        findOrder = new FindOrderImpl();

        //ToDO

        return null;
    }
}
