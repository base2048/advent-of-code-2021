package day14.code;

import utils.*;

import java.nio.CharBuffer;
import java.util.*;
import java.util.stream.Collectors;

public class Challenge implements Solvable {

    private final String template;
    private final Map<String, String> insertions;

    public Challenge(Parser.Data data) {
        this.template = data.template;
        this.insertions = data.insertions;
    }

    @Override
    public long solvePart1() {
        int steps = 10;
        String polymer = this.template;

        for (int i = 0; i < steps; i++) {
            StringBuilder sb = new StringBuilder(polymer.length() * 2 - 1);
            CharBuffer cs = CharBuffer.wrap(polymer);

            for (int j = 0; j < polymer.length() - 1; j++)
                sb.append(cs.get()).append(insertions.get(polymer.substring(j, j + 2)));

            polymer = sb.append(cs.get()).toString();
        }

        long[] minMax = polymer.chars()
                .boxed()
                .collect(Collectors.groupingBy(ch -> ch, Collectors.counting()))
                .values().stream()
                .reduce(new long[]{Long.MAX_VALUE, 0}, (mm, cur) -> {
                    mm[0] = Math.min(cur, mm[0]);
                    mm[1] = Math.max(cur, mm[1]);
                    return mm;
                }, (aloha, beloha) -> aloha);

        return minMax[1] - minMax[0];
    }

    @Override
    public long solvePart2() {
        int steps = 40;
        long[] counts = new long[26];

        Map<Integer, Integer> insertions = convertToHashes(this.insertions);
        int[] template = this.template.chars().map(ch -> ch - 'A').peek(ch -> counts[ch]++).toArray();

        Map<Integer, Long> currentState = new HashMap<>();

        for (int i = 0; i < template.length - 1; i++)
            currentState.merge(template[i] << 8 | template[i + 1], 1L, Long::sum);

        for (int i = 0; i < steps; i++) {
            Map<Integer, Long> nextState = new HashMap<>();

            for (Map.Entry<Integer, Long> entry : currentState.entrySet()) {
                int left = entry.getKey() >>> 8;
                int right = entry.getKey() & 0xFF;
                int insert = insertions.get(entry.getKey());

                counts[insert] += entry.getValue();

                nextState.merge(left << 8 | insert, entry.getValue(), Long::sum);
                nextState.merge(insert << 8 | right, entry.getValue(), Long::sum);
            }

            currentState = nextState;
        }

        long min = Long.MAX_VALUE;
        long max = 0;

        for (long count : counts) {
            if (count == 0) continue;
            max = Math.max(count, max);
            min = Math.min(count, min);
        }

        return max - min;
    }

    private Map<Integer, Integer> convertToHashes(Map<String, String> insertions) {
        return insertions.entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> entry.getKey().chars().map(ch -> ch - 'A').reduce(0, (hash, ch) -> hash << 8 | ch),
                        entry -> entry.getValue().charAt(0) - 'A'));
    }
}
