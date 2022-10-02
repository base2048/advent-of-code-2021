package day04.code;

import utils.Solvable;

import java.util.*;
import java.util.stream.Collectors;

public class Challenge implements Solvable {

    private final int STILL_ALIVE = -1;
    private final List<Integer> numbers;
    private final List<int[][]> boards;

    public Challenge(Parser.Data preset) {
        this.numbers = preset.numbers;
        this.boards = preset.boards;
    }

    @Override
    public long solvePart1() {
        List<int[][]> runningBoards = copyBoards(boards);

        for (int number : numbers) {
            runBoards(runningBoards, number);
            int result = checkBoards(runningBoards);
            if (result != STILL_ALIVE) return (long) number * result;
        }

        return -1;
    }

    @Override
    public long solvePart2() {
        List<int[][]> runningBoards = copyBoards(boards);

        for (int number : numbers) {
            runBoards(runningBoards, number);
            int result = checkBoards(runningBoards);
            if (runningBoards.isEmpty()) return (long) number * result;
        }

        return -1;
    }

    private void runBoards(List<int[][]> boards, int num) {
        for (int[][] board : boards)
            for (int i = 0; i < 5; i++)
                for (int j = 0; j < 5; j++)
                    if (board[i][j] == num) {
                        board[i][5]++;
                        board[5][j]++;
                        board[i][j] = -1;
                    }
    }

    private int checkBoards(List<int[][]> boards) {
        int score = STILL_ALIVE;

        check: for (Iterator<int[][]> it = boards.iterator(); it.hasNext(); ) {

            int[][] board = it.next();

            for (int row = 0; row < 5; row++)
                if (board[row][5] == 5) {
                    score = computeScore(board);
                    it.remove();
                    continue check;
                }

            for (int col = 0; col < 5; col++)
                if (board[5][col] == 5) {
                    score = computeScore(board);
                    it.remove();
                    continue check;
                }
        }

        return score;
    }

    private int computeScore(int[][] board) {
        return Arrays.stream(board, 0, 5)
                .flatMapToInt(row -> Arrays.stream(row, 0, 5))
                .map(num -> Math.max(0, num))
                .sum();
    }

    private List<int[][]> copyBoards(List<int[][]> boards) {
        return boards.stream()
                .map(board -> Arrays.stream(board).map(int[]::clone).toArray(int[][]::new))
                .collect(Collectors.toList());
    }
}
