package com.kadme.test.service.impl;

import com.kadme.test.model.Line;
import com.kadme.test.service.FindGroups;
import com.kadme.test.service.FindIntersections;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class FindGroupsImpl implements FindGroups {

    private FindIntersections findIntersections = new FindIntersectionsImpl();

    @Override
    public Map<Integer, Set<Line>> findGroups(Set<Line> lines) {
        Map<Integer, Set<Line>> groupMap = new HashMap<>();
        //TODO
        return null;
    }

}

