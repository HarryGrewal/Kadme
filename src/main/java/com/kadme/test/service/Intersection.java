package com.kadme.test.service;

import com.kadme.test.model.Line;
import com.kadme.test.model.Point;

public interface Intersection {
    Point findIntersectionPoint(Line L1, Line L2);
}
