package day06.code;

import utils.AbstractParser;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collector;

public class Parser extends AbstractParser {

    @Override @SuppressWarnings("unchecked")
    protected <T> T parse(InputStream is) {
        try (Scanner scn = new Scanner(is, StandardCharsets.UTF_8)) {
            return (T) scn.useDelimiter(",").tokens().map(Integer::valueOf).collect(toTimers());
        }
    }

    private static Collector<Integer, ?, long[]> toTimers() {
        class Timers {

            final long[] timers = new long[9];

            void add(int timer) {
                timers[timer]++;
            }

            Timers combine(Timers other) {
                for (int i = 0; i < timers.length; i++)
                    timers[i] += other.timers[i];
                return this;
            }

            long[] finish() {
                return timers;
            }
        }

        return Collector.of(Timers::new, Timers::add, Timers::combine, Timers::finish, Collector.Characteristics.UNORDERED);
    }
}
