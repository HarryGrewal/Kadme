package com.kadme.test.service;

import com.kadme.test.exception.EmptyLineException;
import com.kadme.test.model.Line;
import com.kadme.test.model.Polygon;

import java.util.Set;

public interface OutlineBuilder {

    Polygon buildOutline(Set<Line> lines) throws EmptyLineException;
}
