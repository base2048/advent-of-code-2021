package day11.code;

import utils.Solvable;

import java.util.*;

public class Challenge implements Solvable {

    private final int[][] initialGrid;
    private int[][] workingGrid;

    public Challenge(int[][] initialGrid) {
        this.initialGrid = initialGrid;
    }

    @Override
    public long solvePart1() {
        resetWorkingGrid();

        int steps = 100;
        int flashCtr = 0;

        for (int step = 0; step < steps; step++)
            flashCtr += flash(energizeDumbos());

        return flashCtr;
    }

    @Override
    public long solvePart2() {
        resetWorkingGrid();

        int dumboCounts = workingGrid.length * workingGrid[0].length;
        int step = 0;

        for (int flashCounts = 0; flashCounts != dumboCounts; step++)
            flashCounts = flash(energizeDumbos());

        return step;
    }

    private Deque<Integer> energizeDumbos() {
        Deque<Integer> pendingToFlash = new ArrayDeque<>();

        for (int row = 0; row < workingGrid.length; row++)
            for (int col = 0; col < workingGrid[0].length; col++)
                if (++workingGrid[row][col] == 10)
                    pendingToFlash.add((row << 4) | col);

        return pendingToFlash;
    }

    private int flash(Deque<Integer> pendingToFlash) {
        if (pendingToFlash.isEmpty()) return 0;

        int dumbo = pendingToFlash.removeLast();
        int row = dumbo >>> 4;
        int col = dumbo & 0xF;

        workingGrid[row][col] = 0;

        for (int r = row - 1; r <= row + 1; r++) {
            for (int c = col - 1; c <= col + 1; c++) {
                if (!isInBounds(r, c) || workingGrid[r][c] == 0) continue;
                if (++workingGrid[r][c] == 10) pendingToFlash.addLast((r << 4) | c);
            }
        }

        return 1 + flash(pendingToFlash);
    }

    private boolean isInBounds(int row, int col) {
        return row >= 0 && row < workingGrid.length && col >= 0 && col < workingGrid[0].length;
    }

    private void resetWorkingGrid() {
        workingGrid = Arrays.stream(initialGrid).map(int[]::clone).toArray(int[][]::new);
    }
}
