package com.kadme.test.service;

import com.kadme.test.model.Line;

import java.util.Map;
import java.util.Set;

public interface FindGroups {
    Map<Integer, Set<Line>> findGroups(Set<Line> lines);
}
