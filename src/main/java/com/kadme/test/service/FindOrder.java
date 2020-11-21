package com.kadme.test.service;

import com.kadme.test.model.Line;

import java.util.LinkedHashSet;
import java.util.Set;

public interface FindOrder {
    LinkedHashSet<Line> getLineOrderOfGroupInGroups(Set<Line> lines);

    LinkedHashSet<Line> getLineOrderOfSingleGroup(Set<Line> lines);
}
