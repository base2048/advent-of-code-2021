package day25.code;

import utils.Solvable;

public class Challenge implements Solvable {

    private final int width, height;
    private final byte[][] grid;

    public Challenge(byte[][] grid) {
        this.grid = grid;
        this.width = grid[0].length;
        this.height = grid.length;
    }

    @Override
    public long solvePart1() {
        final byte EAST = '>';
        final byte SOUTH = 'v';
        final byte EMPTY = '.';

        int steps = 0;
        boolean hasMoved = true;

        while (hasMoved) {
            hasMoved = false;
            steps++;

            for (byte[] row : grid) {
                boolean isFirstPosInRowEmpty = row[0] == EMPTY;
                boolean skip = false;

                for (int col = 0; col < width - 1; col++) {
                    if (skip) { skip = false; continue; }

                    if (row[col] == EAST && row[col + 1] == EMPTY) {
                        row[col] = EMPTY;
                        row[col + 1] = EAST;
                        hasMoved = true;
                        skip = true;
                    }
                }

                if (row[width - 1] == EAST && isFirstPosInRowEmpty && !skip) {
                    row[width - 1] = EMPTY;
                    row[0] = EAST;
                    hasMoved = true;
                }
            }

            for (int col = 0; col < width; col++) {
                boolean isFirstPosInColEmpty = grid[0][col] == EMPTY;
                boolean skip = false;

                for (int row = 0; row < height - 1; row++) {
                    if (skip) { skip = false; continue; }

                    if (grid[row][col] == SOUTH && grid[row + 1][col] == EMPTY) {
                        grid[row][col] = EMPTY;
                        grid[row + 1][col] = SOUTH;
                        hasMoved = true;
                        skip = true;
                    }
                }

                if (grid[height - 1][col] == SOUTH && isFirstPosInColEmpty && !skip) {
                    grid[height - 1][col] = EMPTY;
                    grid[0][col] = SOUTH;
                    hasMoved = true;
                }
            }
        }

        return steps;
    }

    @Override
    public long solvePart2() {
        return 24;
    }
}
