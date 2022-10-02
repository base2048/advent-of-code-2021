package day06.code;

import utils.Solvable;

import java.util.Arrays;

public class Challenge implements Solvable {

    private final long[] timers;

    public Challenge(long[] timers) {
        this.timers = timers;
    }

    @Override
    public long solvePart1() {
        return Arrays.stream(populate(Arrays.copyOfRange(timers, 0, timers.length), 80))
                .sum();
    }

    @Override
    public long solvePart2() {
        return Arrays.stream(populate(Arrays.copyOfRange(timers, 0, timers.length), 256))
                .sum();
    }

    private long[] populate(long[] timers, int cycles) {
        for (int i = 0; i < cycles; i++) {
            long birthing = timers[0];

            System.arraycopy(timers,1, timers, 0, timers.length - 1);

            timers[6] += birthing;
            timers[8] = birthing;
        }
        return timers;
    }
}
