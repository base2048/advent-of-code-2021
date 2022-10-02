package day01.code;

import utils.Solvable;

import java.util.List;
import java.util.stream.IntStream;

public class Challenge implements Solvable {

    private final List<Integer> measurements;

    public Challenge(List<Integer> measurements) {
        this.measurements = measurements;
    }

    @Override
    public long solvePart1() {
        return IntStream.range(1, measurements.size())
                .filter(i -> measurements.get(i) > measurements.get(i - 1))
                .count();
    }

    @Override
    public long solvePart2() {
        /* Since sliding windows overlap it is only necessary to compare those parts which do not overlap.
           In this case, the first value of the first window with the last value of the second window. */

        return IntStream.range(3, measurements.size())
                .filter(i -> measurements.get(i) > measurements.get(i - 3))
                .count();
    }
}
