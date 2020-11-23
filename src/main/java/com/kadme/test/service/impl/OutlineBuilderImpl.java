package com.kadme.test.service.impl;

import com.kadme.test.model.Line;
import com.kadme.test.model.Point;
import com.kadme.test.model.Polygon;
import com.kadme.test.service.OutlineBuilder;
import com.kadme.test.util.CheckIfIntersect;
import com.kadme.test.util.DrawComponent;
import com.kadme.test.util.FindIntersectingPoint;

import java.util.*;
import java.util.stream.Stream;

import static com.kadme.test.util.ByAngleComparator.byAngleComparator;

public class OutlineBuilderImpl implements OutlineBuilder {

    private CheckIfIntersect checkIfIntersect = new CheckIfIntersect();
    private FindIntersectingPoint findIntersectingPoint = new FindIntersectingPoint();

    @Override
    public Polygon buildOutline(Set<Line> lines) {

        Map<Line, HashSet<Line>> nonIntersectingLinesMap = new HashMap<>();
        Map<Line, HashSet<Line>> intersectingLinesMap = new HashMap<>();

        //Create two maps; line as key and set of non intersecting & intersecting lines as respective values
        lines.forEach(baseLine -> categorizeIntersectingNonIntersectingLines
                (baseLine, lines, nonIntersectingLinesMap, intersectingLinesMap));

        //Sanitize groups, remove empty sets
        sanitizeGroupMap(nonIntersectingLinesMap);
        sanitizeGroupMap(intersectingLinesMap);

        //Identify groups of non intersecting and intersecting lines
        Set<LinkedHashSet<Line>> nonIntersectingGroup = groupCategorizedLines(nonIntersectingLinesMap.entrySet());
        Set<LinkedHashSet<Line>> intersectingGroup = groupCategorizedLines(intersectingLinesMap.entrySet());


        //Figure out from this group of non intersecting lines, group boundaries and
        // calculate their intersection point and figure out the order of the lines within the group.
        //Once we get the intersection point simply travel via first group to intersection point and
        // then to other points in the group and then to intersection point and to third group and so on
        //By travel I meant include points in Polygon class
        //Verify all the points are included in polygon

        //Ready for Red-Black action!
//        nonIntersectingGroup.forEach(this::sortLines);

        Set<Point> finalListOfPoints = new LinkedHashSet<>();
        List<Line> intersectionLine = new ArrayList<>();
        nonIntersectingGroup.forEach(setOfLine -> {
                    Stream<Line> sorted = new TreeSet<>(setOfLine).stream()
                            .sorted(Collections.reverseOrder(byAngleComparator(centroid(setOfLine))));

                    intersectionLine.add(sorted.parallel().findFirst().isPresent() ?
                            sorted.parallel().findFirst().get() : null);
                    intersectionLine.add(sorted.parallel().skip(setOfLine.size() - 1).findFirst().isPresent() ?
                            sorted.parallel().skip(setOfLine.size() - 1).findFirst().get() : null);
                }
        );

        nonIntersectingGroup.forEach(setOfLine -> {
                    setOfLine.forEach(line -> finalListOfPoints.add(line.getP1()));
                    int i = 0;
                    finalListOfPoints.add(findIntersectingPoint
                            .findIntersectionPoint(intersectionLine.get(++i),
                                    intersectionLine.get(++i)));
                }
        );

        //finalListOfPoints.add(e)


        System.out.println("Set<Line>  size is " + lines.size() + "\n");
        System.out.println("\n nonIntersectingGroup size is " + nonIntersectingGroup.size() + "\n" + nonIntersectingGroup);
        System.out.println("\n intersectingGroup size is " + intersectingGroup.size() + "\n" + intersectingGroup);
        System.out.println("\n Intersecting lines size is  " + intersectionLine.size() + "\n" + intersectionLine);
        System.out.println("\n Final list of points size is " + finalListOfPoints.size() + "\n" + finalListOfPoints);
        //Draw Component
        new DrawComponent(lines).draw();
        return new Polygon(null);
    }

/*    private void sortLines(Set<Line> setOfLine) {
          List<Line> listOfLine = new ArrayList<>(setOfLine);
        listOfLine.sort(Collections.reverseOrder(byAngleComparator(centroid(setOfLine))));
    }*/

    private Point centroid(Set<Line> setOfLine) {
        double centroidX = 0, centroidY = 0;

        for (Line knot : setOfLine) {
            centroidX += knot.getP1().getX();
            centroidX += knot.getP2().getX();
            centroidY += knot.getP1().getY();
            centroidY += knot.getP2().getY();
        }
        return new Point(centroidX / (setOfLine.size() * 2), centroidY / (setOfLine.size() * 2));
    }

    private void categorizeIntersectingNonIntersectingLines(Line baseLine, Set<Line> lines,
                                                            Map<Line, HashSet<Line>> nonIntersectingLinesMap,
                                                            Map<Line, HashSet<Line>> intersectingLinesMap) {

        /*
        Example 1, Lines set trace for LinesMap
        Set of lines -> l1, l2, l3, l4, l5, l6 ,l7 ,l8, l9 ; non-intersecting groups -> {l1, l2} {l3, l4, l5} {l6, l7, l8, l9}
        for baseLine l1
        non-intersecting : l1 {l2}
        intersectingLineSet : l1 {l3, l4, l5, l6, l7, l8, l9}
        for baseLine l2
        non-intersecting : l2 {l1}
        intersectingLineSet : l2 {l3, l4, l5, l6, l7, l8, l9}
        for baseLine l3
        non-intersecting : l3 {l4, l5}
        intersectingLineSet : l3 {l6, l7, l8, l9, l1, l2}
        for baseLine l4
        non-intersecting : l4 {l3, l5}
        intersectingLineSet : l4 {l6, l7, l8, l9, l1, l2}
        ...
        * */
        HashSet<Line> nonIntersectingLineSet = new HashSet<>();
        HashSet<Line> intersectingLineSet = new HashSet<>();
        lines.forEach(line -> {
            if (baseLine != line) {
                if (checkIfIntersect.doIntersect(baseLine.getP1(), baseLine.getP2(),
                        line.getP1(), line.getP2())) {
                    intersectingLineSet.add(line);
                } else {
                    nonIntersectingLineSet.add(line);
                }
            }
        });

        nonIntersectingLinesMap.put(baseLine, nonIntersectingLineSet);
        intersectingLinesMap.put(baseLine, intersectingLineSet);
    }


    private Set<LinkedHashSet<Line>> groupCategorizedLines(Set<Map.Entry<Line, HashSet<Line>>> entries) {

        /*
         * Example 1, Trace group from non-intersecting LinesMap entrySet
         * at list location 0 for l1 -> {l1, l2}
         * at list location 1 for l2 -> {l1, l2}
         * at list location 2 for l3 -> {l3, l4, l5}
         * at list location 3 for l4 -> {l3, l4, l5}
         * ...
         * from intersecting LinesMap entrySet
         * at list location 0 for l1 -> {l1, l3, l4, l5, l6, l7, l8, l9}
         * at list location 1 for l2 -> {l2, l3, l4, l5, l6, l7, l8, l9}
         * at list location 2 for l3 -> {l1, l2, l3, l6, l7, l8, l9}
         * at list location 3 for l4 -> {l1, l2, l4, l6, l7, l8, l9}
         * ...
         * */

        Set<LinkedHashSet<Line>> group = new LinkedHashSet<>();
        entries.forEach(entry -> {
            LinkedHashSet<Line> lineSet = new LinkedHashSet<>();
            lineSet.add(entry.getKey());
            lineSet.addAll(entry.getValue());
            group.add(lineSet);
        });
        return group;
    }

    private void sanitizeGroupMap(Map<Line, HashSet<Line>> groupMap) {
        groupMap.entrySet().removeIf(entry ->
                (entry.getValue().isEmpty()));
    }

}
