package com.kadme.test.service.impl;

import com.kadme.test.model.Line;
import com.kadme.test.service.FindGroups;
import com.kadme.test.service.FindOrder;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class FindGroupsImpl implements FindGroups {
    FindOrder findOrder;

    @Override
    public Map<Integer, Set<Line>> findGroups(Set<Line> lines) {
        findOrder = new FindOrderImpl();

        // TODO
        Map<Integer, Set<Line>> groups = new HashMap<>();
        Set<Line> OrderedLines = findOrder.getLineOrderOfGroup(lines);
        groups.put(0, new LinkedHashSet<>());
        return groups;
    }
}
