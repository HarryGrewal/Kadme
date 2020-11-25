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

        //Line of thoughts
        /*From the nonIntersectingGroup -> take head of every line -> find center -> sort clockwise
         nonIntersectingGroup -> take tail of every line -> find center -> sort anticlockwise
         Fill polygon -> connect sorted heads of nonIntersectingGroup until last head
         Find intersection (I) of last line of first group with first line of second group
         connect last head of first group with intersection point (I) -> connect (I) with head of second group
         Do like wise with tail*/


        List<Line> firstLine = new ArrayList<>();
        List<Line> secondLine = new ArrayList<>();
        List<Point> intersectionPoints = new ArrayList<>();
        List<Point> polygonPoints = new ArrayList<>();

        nonIntersectingGroup.forEach(setOfLinesInOneGroup -> {

            List<List<Point>> headTailWithinOneGroup = new ArrayList<>();
            setOfLinesInOneGroup.forEach(line ->
            {
                //create a list of list -> head point and tail point of a line relative to progressive X
                final double x1 = line.getP1().getX();
                final double y1 = line.getP1().getY();
                final double x2 = line.getP2().getX();
                final double y2 = line.getP2().getY();
                Point head = null, tail = null;
                //corner case: avoid a point
                if (!(x1 == x2 && y1 == y2)) {
                    if (x1 == x2) {
                        double minY = Double.min(y1, y2);
                        double maxY = Double.max(y1, y2);
                        head = new Point(x1, minY);
                        tail = new Point(x1, maxY);
                    } else if (Double.min(x1, x2) == x1) {
                        head = new Point(x1, y1);
                        tail = new Point(x2, y2);
                    } else {
                        head = new Point(x2, y2);
                        tail = new Point(x1, y1);
                    }
                }
                headTailWithinOneGroup.add(Arrays.asList(head, tail));
            });

            List<Point> headArrayList = getHeadOrTailArrayList(headTailWithinOneGroup, "HEAD");
            List<Point> tailArrayList = getHeadOrTailArrayList(headTailWithinOneGroup, "TAIL");
            logger.info("\nHeadArrayList before sorting size is " + "\n" + headArrayList);
            logger.info("\nTailArrayList before sorting size is " + "\n" + tailArrayList);

            Point headCenter = findCenter(headArrayList);
            Point tailCenter = findCenter(tailArrayList);
            logger.info("\nHead Center is " + headCenter);
            logger.info("\nTail Center is " + tailCenter);

            headArrayList.sort(new ByAngleComparator().compareByAngle(headCenter));
            tailArrayList.sort(new ByAngleComparator().compareByAngle(tailCenter));
            logger.info("\nHeadArrayList after sorting is " + "\n" + headArrayList);
            logger.info("\nTailArrayList after sorting is " + "\n" + tailArrayList);

            Point firstHeadPoint = headArrayList.get(0);
            Point lastHeadPoint = headArrayList.get(headArrayList.size() - 1);
            Point firstTailPoint = tailArrayList.get(0);
            Point lastTailPoint = tailArrayList.get(tailArrayList.size() - 1);

            logger.info("\nFirst Head Point is " + firstHeadPoint);
            logger.info("\nLast  Head Point is " + lastHeadPoint);
            logger.info("\nFirst Tail Point is " + firstTailPoint);
            logger.info("\nLast  Tail Point is " + lastTailPoint);
            logger.info("\nLast  Tail Point is " + lastTailPoint);


            if (nonIntersectingGroup.size() == 1) {
                for (int i = headArrayList.size(); i-- > 0; ) {
                    polygonPoints.add(headArrayList.get(i));
                }
                for (Point point : tailArrayList) {
                    polygonPoints.add(point);
                }
            }

            /*setOfLinesInOneGroup.forEach(line -> {
                Point pointX = line.getP1().getX() < line.getP2().getX() ? line.getP1() : line.getP2();
                if (pointX.equals(firstHeadPoint))
                    firstLine.add(line);
                if (pointX.equals(lastHeadPoint))
                    secondLine.add(line);
            });*/
        });

        /*for (int i = 0; i < firstLine.size(); i++) {
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

        List<Point> polygonPoints = new ArrayList<>(intersectionPoints);*/

       /* inputLines.forEach(line -> {
            polygonPoints.add(line.getP1());
            polygonPoints.add(line.getP2());
        });*/

        logger.info("\n Final Polygon Points size is  " + polygonPoints.size() + "\n" + polygonPoints);

        //Draw Component
        new DrawComponent(inputLines, polygonPoints).draw();
        return new Polygon(polygonPoints);
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

    private Point findCenter(List<Point> relativePointsWithinGroup) {
        double centroidX = 0, centroidY = 0;

        for (Point point : relativePointsWithinGroup) {
            centroidX += point.getX();
            centroidY += point.getY();
        }
        return new Point(centroidX / relativePointsWithinGroup.size(), centroidY / relativePointsWithinGroup.size());
    }

    private List<Point> getHeadOrTailArrayList(List<List<Point>> headTailWithinGroup, String relativeTo) {
        List<Point> relativePointsWithinGroup = new ArrayList<>();
        if (relativeTo.equals("HEAD")) {
            headTailWithinGroup.forEach(headTailList -> {
                relativePointsWithinGroup.add(headTailList.get(0));
            });
        }
        if (relativeTo.equals("TAIL")) {
            headTailWithinGroup.forEach(headTailList -> {
                relativePointsWithinGroup.add(headTailList.get(1));
            });
        }
        return relativePointsWithinGroup;
    }


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
}
