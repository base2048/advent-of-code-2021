package day04.code;

import utils.AbstractParser;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Parser extends AbstractParser {

    static class Data {
        final List<Integer> numbers;
        final List<int[][]> boards;

        Data(List<Integer> numbers, List<int[][]> boards) {
            this.numbers = numbers;
            this.boards = boards;
        }
    }

    @Override @SuppressWarnings("unchecked")
    protected <T> T parse(InputStream is) {
        try (Scanner scn = new Scanner(is, StandardCharsets.UTF_8)) {

            List<Integer> numbers = Arrays.stream(scn.nextLine().split(",")).map(Integer::parseInt).toList();
            List<int[][]> boards = new ArrayList<>();

            while (scn.hasNextLine()) {
                scn.nextLine();

                int[][] board = new int[6][6];
                for (int i = 0; i < 5; i++)
                    board[i] = Arrays.stream((scn.nextLine().trim() + " 0").split("\\s+"))
                            .mapToInt(Integer::parseInt)
                            .toArray();

                board[5] = new int[]{0, 0, 0, 0, 0, 0};
                boards.add(board);
            }

            return (T) new Data(numbers, boards);
        }
    }
}
