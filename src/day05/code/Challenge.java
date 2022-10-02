package day05.code;

import utils.Solvable;

import java.util.List;
import java.util.stream.*;

public class Challenge implements Solvable {

    private final List<int[][]> endPoints;
    private final boolean INCLUDE_DIAGONALS = true;

    public Challenge(List<int[][]> endPoints) {
        this.endPoints = endPoints;
    }

    @Override
    public long solvePart1() {
        return endPoints.stream()
                .flatMap(eps -> interpolateEndpoints(eps, !INCLUDE_DIAGONALS))
                .collect(Collectors.groupingBy($ -> $, Collectors.counting()))
                .values()
                .stream()
                .filter(ctr -> ctr > 1)
                .count();
    }

    @Override
    public long solvePart2() {
        return endPoints.stream()
                .flatMap(eps -> interpolateEndpoints(eps, INCLUDE_DIAGONALS))
                .collect(Collectors.groupingBy($ -> $, Collectors.counting()))
                .values()
                .stream()
                .filter(ctr -> ctr > 1)
                .count();
    }

    private Stream<String> interpolateEndpoints(int[][] endPoints, boolean includeDiagonals) {
        int x1 = endPoints[0][0], y1 = endPoints[0][1];
        int x2 = endPoints[1][0], y2 = endPoints[1][1];

        if (x1 == x2) {     /* would cover single points as well */
            int fromY = Math.min(y1, y2);
            int toY = Math.max(y1, y2);
            return IntStream.rangeClosed(fromY, toY).mapToObj(y -> x1 + "," + y);
        }

        if (y1 == y2) {
            int fromX = Math.min(x1, x2);
            int toX = Math.max(x1, x2);
            return IntStream.rangeClosed(fromX, toX).mapToObj(x -> x + "," + y1);
        }

        if (!includeDiagonals) return Stream.empty();

        /* According to the description, all remaining pairs of endpoints form diagonals. */

        int signumX = Integer.signum(x2 - x1);
        int signumY = Integer.signum(y2 - y1);

        int[] startingPoint = endPoints[0];
        int endPointX = endPoints[1][0];

        return Stream.iterate(startingPoint, point -> point[0] != endPointX + signumX, point -> {
            point[0] += signumX;
            point[1] += signumY;
            return point;
        }).map(point -> point[0] + "," + point[1]);
    }
}
