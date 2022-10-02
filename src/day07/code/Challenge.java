package day07.code;

import utils.Solvable;

import java.util.*;
import java.util.stream.Collectors;

public class Challenge implements Solvable {

    private final List<Integer> positions;

    public Challenge(List<Integer> positions) {
        this.positions = positions;
    }

    @Override
    public long solvePart1() {
        positions.sort(Comparator.naturalOrder());
        int median = positions.get(positions.size() / 2);

        return positions.stream().mapToInt(pos -> Math.abs(pos - median)).sum();
    }

    @Override
    public long solvePart2() {
        double average = positions.stream().collect(Collectors.averagingInt(pos -> pos));

        int meanCeil = (int) Math.ceil(average);
        int meanFloor = (int) Math.floor(average);

        int fuel1 = 0;
        int fuel2 = 0;

        for (int pos : positions) {
            int steps = Math.abs(pos - meanCeil);
            fuel1 += steps * (steps + 1) / 2;
        }

        for (int pos : positions) {
            int steps = Math.abs(pos - meanFloor);
            fuel2 += steps * (steps + 1) / 2;
        }

        return Math.min(fuel1, fuel2);
    }
}
