package com.kadme.test.service.impl;

import com.kadme.test.model.Line;
import com.kadme.test.model.Point;
import com.kadme.test.model.Polygon;
import com.kadme.test.service.OutlineBuilder;
import com.kadme.test.util.ByAngleComparator;
import com.kadme.test.util.CheckIfIntersect;
import com.kadme.test.util.DrawComponent;
import com.kadme.test.util.FindIntersectingPoint;
import org.apache.log4j.Logger;

import java.util.*;

import static com.kadme.test.util.OutlineBuilderConstants.INVALID_POINT;


public class OutlineBuilderImpl implements OutlineBuilder {
    private final static Logger logger = Logger.getLogger(OutlineBuilderImpl.class);
    private final CheckIfIntersect checkIfIntersect = new CheckIfIntersect();
    private final FindIntersectingPoint findIntersectingPoint = new FindIntersectingPoint();

    @Override
    public Polygon buildOutline(Set<Line> inputLines) {

        logger.info("Set<Line>  size is " + inputLines.size() + "\n");

        Map<Line, HashSet<Line>> nonIntersectingLinesMap = new HashMap<>();
        Map<Line, HashSet<Line>> intersectingLinesMap = new HashMap<>();

        //Create two maps; line as key and set of non intersecting & intersecting lines as respective values
        inputLines.forEach(baseLine -> categorizeIntersectingNonIntersectingLines
                (baseLine, inputLines, nonIntersectingLinesMap, intersectingLinesMap));

        //Sanitize groups, remove empty sets
        sanitizeGroupMap(nonIntersectingLinesMap);
        sanitizeGroupMap(intersectingLinesMap);

        //Identify groups of non intersecting and intersecting lines
        Set<HashSet<Line>> nonIntersectingGroup = groupCategorizedLines(nonIntersectingLinesMap.entrySet());
        Set<HashSet<Line>> intersectingGroup = groupCategorizedLines(intersectingLinesMap.entrySet());

        logger.info("\n NonIntersectingGroup size is " + nonIntersectingGroup.size() + "\n" + nonIntersectingGroup);
        logger.info("\n IntersectingGroup size is " + intersectingGroup.size() + "\n" + intersectingGroup);

        //Figure out from this group of non intersecting lines, group boundaries and
        // calculate their intersection point and figure out the order of the lines within the group.
        //Once we get the intersection point simply travel via first group to intersection point and
        // then to other points in the group and then to intersection point and to third group and so on
        //By travel I meant include points in Polygon class
        //Verify all the points are included in polygon

        // Logic below did'nt work out as thought it would!
    /*
                 //Ready for Red-Black action!
        nonIntersectingGroup.forEach(this::sortLines);
        Set<Point> finalListOfPoints = new LinkedHashSet<>();
        List<Line> intersectionLine = new ArrayList<>();
        nonIntersectingGroup.forEach(setOfLine -> {
                    Stream<Line> sorted = new TreeSet<>(setOfLine).stream()
                            .sorted(Collections.reverseOrder(new ByAngleComparator().byAngleComparator(centroid(setOfLine))));

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
        );*/


        List<Line> firstLine = new ArrayList<>();
        List<Line> secondLine = new ArrayList<>();
        List<Point> intersectionPoints = new ArrayList<>();

        nonIntersectingGroup.forEach(setOfLine -> {

            List<Point> pointsWithinGroup = new ArrayList<>();
            setOfLine.forEach(line -> pointsWithinGroup
                    .add(line.getP1().getX() < line.getP2().getX() ? line.getP1() : line.getP2()));

            logger.info("\npointsWithinGroup List filled with nonIntersectingGroup.forEach.setOfLine.line  size is " + pointsWithinGroup.size());
            logger.info("\npointsWithinGroup List before sorting is " + "\n" + pointsWithinGroup);

            Point center = findCenter(pointsWithinGroup);
            pointsWithinGroup.sort(Collections.reverseOrder(new ByAngleComparator().compareByAngle(center)));

            Point firstPoint = pointsWithinGroup.get(0);
            Point lastPoint = pointsWithinGroup.get(pointsWithinGroup.size() - 1);

            logger.info("\npointsWithinGroup List after sorting is " + "\n" + pointsWithinGroup);
            logger.info("\nFirst Point is " + firstPoint);
            logger.info("\nLast Point is " + lastPoint);

            setOfLine.forEach(line -> {
                Point pointX = line.getP1().getX() < line.getP2().getX() ? line.getP1() : line.getP2();

                logger.debug("\nFor PointX  " + pointX + " line.getP1().getX(): " + line.getP1().getX() + "<"
                        + " line.getP2().getX() " + "?" + "line.getP1() : " + line.getP1() + "line.getP2() " + line.getP2());

                if (pointX.equals(firstPoint))
                    firstLine.add(line);

                logger.debug("\nPointX " + pointX + " is equal to first point " + firstPoint + "and added to\n first line " + firstLine);

                if (pointX.equals(lastPoint))
                    secondLine.add(line);

                logger.debug("\nPointX " + pointX + " is equal to last point " + firstPoint + "and added to\n second line " + firstLine);
            });
        });

        for (int i = 0; i < firstLine.size(); i++) {
            Point intersectionPoint = findIntersectingPoint.findIntersectionPoint(firstLine.get(i), secondLine.get(i));

            logger.debug("\nFirst Line " + i + " position " + firstLine.get(i) + " line");
            logger.debug("\nSecond Line " + i + " position " + secondLine.get(i) + " line");
            logger.debug("\nIntersectionPoint returns " + findIntersectingPoint.findIntersectionPoint(firstLine.get(i), secondLine.get(i)));

            if (!intersectionPoint.equals(INVALID_POINT)) {
                if (!intersectionPoints.contains(intersectionPoint))
                    intersectionPoints.add(intersectionPoint);
            }

            logger.info("\n IntersectionPoints list size is " + intersectionPoints.size() + "\n" + intersectionPoints);
        }

        List<Point> polygonPoints = new ArrayList<>(intersectionPoints);

        inputLines.forEach(line -> {
            polygonPoints.add(line.getP1());
            polygonPoints.add(line.getP2());
        });

        logger.info("\n Final Polygon Points size is  " + polygonPoints.size() + "\n" + polygonPoints);

        //Draw Component
        new DrawComponent(inputLines, polygonPoints).draw();
        return new Polygon(polygonPoints);
    }

    private Point findCenter(List<Point> pointsWithinGroup) {
        double centroidX = 0, centroidY = 0;

        for (Point point : pointsWithinGroup) {
            centroidX += point.getX();
            centroidY += point.getY();
        }
        final Point point = new Point(centroidX / pointsWithinGroup.size(), centroidY / pointsWithinGroup.size());

        logger.debug("\nPointsWithinGroup in findCenter " + "\n" + pointsWithinGroup);
        logger.debug("\nCenter is " + point);

        return point;
    }

    private void categorizeIntersectingNonIntersectingLines(Line baseLine, Set<Line> lines,
                                                            Map<Line, HashSet<Line>> nonIntersectingLinesMap,
                                                            Map<Line, HashSet<Line>> intersectingLinesMap) {

        /*
        Example 1, Lines set trace for LinesMap
        Set of lines -> l1, l2, l3, l4, l5, l6 ,l7 ,l8, l9 ;
        non-intersecting groups -> {l1, l2} {l3, l4, l5} {l6, l7, l8, l9}
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
                if (findIntersectingPoint.ifIntersect(baseLine, line)) {
                    intersectingLineSet.add(line);

                    logger.debug("\nLine added to IntersectingLineSet " + "\n" + line + " \nbase line is : " + baseLine);
                } else {
                    nonIntersectingLineSet.add(line);

                    logger.debug("\nLine added to NonIntersectingLineSet " + "\n" + line + " \nbase line is : " + baseLine);
                }
            }
        });

        nonIntersectingLinesMap.put(baseLine, nonIntersectingLineSet);

        logger.info("\nNonIntersectingLinesMap size is " + nonIntersectingLinesMap.size());
        logger.info("\nNonIntersectingLinesMap" + "\n" + nonIntersectingLinesMap);

        intersectingLinesMap.put(baseLine, intersectingLineSet);

        logger.info("\nIntersectingLinesMap size is " + intersectingLinesMap.size());
        logger.info("\nIntersectingLinesMap" + "\n" + intersectingLinesMap);
    }


    private Set<HashSet<Line>> groupCategorizedLines(Set<Map.Entry<Line, HashSet<Line>>> entries) {

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
        logger.info("\ngroupCategorizedLines(Set<Map.Entry<Line, HashSet<Line>>> entries) input size " + entries.size() + "\n" + entries);
        Set<HashSet<Line>> group = new HashSet<>();
        entries.forEach(entry -> {
            HashSet<Line> lineSet = new HashSet<>();
            lineSet.add(entry.getKey());
            lineSet.addAll(entry.getValue());
            group.add(lineSet);
        });
        logger.info("\nGroup after categorizing size " + group.size() + "\n" + group);

        return group;
    }

    private void sanitizeGroupMap(Map<Line, HashSet<Line>> groupMap) {
        logger.info("\nGroupMap before sanitizing size" + groupMap.size() + "\n" + groupMap);
        groupMap.entrySet().removeIf(entry ->
                (entry.getValue().isEmpty()));
        logger.info("\nGroupMap after sanitizing size" + groupMap.size() + "\n" + groupMap);
    }

}
