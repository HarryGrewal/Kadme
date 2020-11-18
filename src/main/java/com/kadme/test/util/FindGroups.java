package com.kadme.test.util;

import com.kadme.test.Line;

import java.util.Map;
import java.util.Set;

public interface FindGroups {
    Map<Integer, Set<Line>> findGroups(Set<Line> lines);
}
