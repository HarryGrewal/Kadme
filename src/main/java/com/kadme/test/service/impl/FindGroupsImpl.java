package com.kadme.test.service.impl;

import com.kadme.test.model.Line;
import com.kadme.test.service.FindGroups;
import com.kadme.test.service.FindOrder;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class FindGroupsImpl implements FindGroups {
    private FindOrder findOrder;

    @Override
    public Map<Integer, Set<Line>> findGroups(Set<Line> lines) {
        findOrder = new FindOrderImpl();

        // TODO find groups or single group
        Map<Integer, Set<Line>> groups = new HashMap<>();
        // if more than 1 group
        Set<Line> lineOrderOfGroupInGroups = findOrder.getLineOrderOfGroupInGroups(lines);

        //if only one group
        Set<Line> lineOrderOfSingleGroup = findOrder.getLineOrderOfSingleGroup(lines);

        groups.put(0, new LinkedHashSet<>());
        return groups;
    }
}
