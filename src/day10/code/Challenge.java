package day10.code;

import utils.Solvable;

import java.util.*;
import java.util.stream.*;

public class Challenge implements Solvable {

    private final List<String> lines;

    private final int PART_1 = 0;
    private final int PART_2 = 1;

    private final Map<Integer, Integer> bracketPairs;
    private final Map<Integer, long[]> bracketToScores;

    public Challenge(List<String> lines) {
        this.lines = lines;

        this.bracketPairs = Stream.of("()", "[]", "{}", "<>")
                .collect(Collectors.toMap(pair -> (int) pair.charAt(0), pair -> (int) pair.charAt(1)));

        this.bracketToScores = Map.of(
                (int) ')', new long[]{3L, 1L},
                (int) ']', new long[]{57L, 2L},
                (int) '}', new long[]{1197L, 3L},
                (int) '>', new long[]{25137L, 4L}
        );
    }

    @Override
    public long solvePart1() {
        return lines.stream()
                .mapToLong(line -> getScore(line, PART_1))
                .sum();
    }

    @Override
    public long solvePart2() {
        long[] scores = lines.stream()
                .mapToLong(line -> getScore(line, PART_2))
                .filter(score -> score > 0)
                .sorted()
                .toArray();

        return scores[scores.length / 2];
    }

    public long getScore(String line, int part) {
        Deque<Integer> pendingClosingBrackets = new ArrayDeque<>();

        for (int ch : line.toCharArray()) {
            if (bracketPairs.containsKey(ch)) {
                pendingClosingBrackets.addFirst(bracketPairs.get(ch));
            } else {
                if (pendingClosingBrackets.removeFirst() == ch) continue;
                return part == PART_1 ? bracketToScores.get(ch)[PART_1] : 0;    /* line is corrupted */
            }
        }

        return part == PART_2   /* line is incomplete */
                ? pendingClosingBrackets.stream()
                    .map(b -> bracketToScores.get(b)[PART_2])
                    .reduce(0L, (acc, score) -> acc * 5 + score)
                : 0;
    }
}
