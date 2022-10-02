package utils;

import java.lang.reflect.Constructor;
import java.util.concurrent.TimeUnit;

public class Timer {

    public static class Preparation {
        public final Solvable challenge;
        public final long duration;

        public Preparation(Solvable challenge, long duration) {
            this.challenge = challenge;
            this.duration = duration;
        }
    }

    public static class Summary {
        public final long result, duration;

        public Summary(long result, long duration) {
            this.result = result;
            this.duration = duration;
        }
    }

    public static Preparation prepareChallenge(Class<?> challenge, Class<?> parser) {
        try {
            Object data = ((AbstractParser) parser.getConstructor().newInstance()).fetchData();
            Constructor<?> constructor = challenge.getConstructors()[0];

            System.gc(); System.gc();

            long start = System.nanoTime();
            Solvable instance = (Solvable) constructor.newInstance(data);
            long end = System.nanoTime();
            long duration = TimeUnit.NANOSECONDS.toMillis(end - start);

            return new Preparation(instance, duration);

        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
            throw new RuntimeException("Santa needs vacation, seriously.");
        }
    }

    public static Summary solveChallenge(Solvable challenge, Part part) {
        System.gc(); System.gc();

        long start = System.nanoTime();
        long result = switch(part) {
            case PART1 -> challenge.solvePart1();
            case PART2 -> challenge.solvePart2();
        };
        long end = System.nanoTime();

        long duration = TimeUnit.NANOSECONDS.toMillis(end - start);
        return new Summary(result, duration);
    }

    private Timer() {}
}
