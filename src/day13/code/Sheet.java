package day13.code;

import java.util.*;
import java.util.stream.Collectors;

import static utils.ANSI.*;

public class Sheet {

    private int width, height;
    private Map<Integer, Set<Integer>> dotsByRow;

    Sheet(int width, int height, Map<Integer, Set<Integer>> dotsByRow) {
        this.width = width;
        this.height = height;
        this.dotsByRow = dotsByRow;
    }

    void foldX(int pivot) {
        Map<Integer, Set<Integer>> temp = new HashMap<>();

        for (Map.Entry<Integer, Set<Integer>> entry : dotsByRow.entrySet())
            temp.put(
                    entry.getKey(),
                    entry.getValue().stream()
                            .map(col -> col > pivot ? pivot * 2 - col : col)
                            .collect(Collectors.toSet()));

        dotsByRow = temp;
        width = pivot - 1;
    }

    void foldY(int pivot) {
        Map<Integer, Set<Integer>> temp = new HashMap<>();

        for (Map.Entry<Integer, Set<Integer>> entry : dotsByRow.entrySet())
            temp.merge(
                    entry.getKey() > pivot ? pivot * 2 - entry.getKey() : entry.getKey(),
                    entry.getValue(),
                    (oldVal, newVal) -> { oldVal.addAll(newVal); return oldVal; });

        dotsByRow = temp;
        height = pivot - 1;
    }

    long countDots() {
        return dotsByRow.values().stream().mapToLong(Set::size).sum();
    }

    void printDots() {
        for (int row = 0; row <= height; row++) {
            if (!dotsByRow.containsKey(row)) continue;  /* unit test hack */

            StringBuilder sb = new StringBuilder().append(ANSI_BOLD);
            for (int col = 0; col <= width; col++)
                sb.append(dotsByRow.get(row).contains(col) ? '#' : ' ');

            System.out.println(sb.append(ANSI_NORMAL));
        }
    }

    void printDotsDev() {
        String emptyLine = new String(new char[width + 1]).replace('\u0000', '.');

        for (int row = 0; row <= height; row++) {
            if (!dotsByRow.containsKey(row)) {
                System.out.println(emptyLine);
                continue;
            }

            StringBuilder sb = new StringBuilder(width + 1);
            for (int col = 0; col <= width; col++)
                sb.append(dotsByRow.get(row).contains(col) ? '#' : '.');

            System.out.println(sb);
        }
    }
}
