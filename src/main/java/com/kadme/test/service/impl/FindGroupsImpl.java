package com.kadme.test.service.impl;

import com.kadme.test.model.Line;
import com.kadme.test.service.FindGroups;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class FindGroupsImpl implements FindGroups {
    @Override
    public Map<Integer, Set<Line>> findGroups(Set<Line> lines) {
        // TODO
        return new TreeMap<>();
    }
}
