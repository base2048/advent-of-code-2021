package day09.code;

import utils.Solvable;

import java.util.*;

public class Challenge implements Solvable {

    private final byte[][] heightmap;
    private final List<List<Byte>> basins;

    public Challenge(byte[][] heightmap) {
        this.heightmap = heightmap;
        this.basins = findBasins();
    }

    @Override
    public long solvePart1() {
        return basins.stream()
                .mapToInt(basin -> 1 + basin.stream().mapToInt(height -> (int) height).min().getAsInt())
                .sum();
    }

    @Override
    public long solvePart2() {
        return basins.stream()
                .map(List::size)
                .sorted(Comparator.reverseOrder())
                .limit(3)
                .reduce(1L, (acc, cur) -> acc * cur, (a, b) -> a);
    }

    private List<List<Byte>> findBasins() {
        List<List<Byte>> basins = new ArrayList<>();

        for (int row = 0; row < heightmap.length; row++) {
            for (int col = 0; col < heightmap[0].length; col++) {

                if (heightmap[row][col] == 9) continue;
                basins.add(scanBasin(row, col, new ArrayList<>()));
            }
        }
        return basins;
    }

    private List<Byte> scanBasin(int row, int col, List<Byte> basin) {
        if (!isInBounds(row, col) || heightmap[row][col] == 9) return Collections.emptyList();

        basin.add(heightmap[row][col]);
        heightmap[row][col] = 9;

        scanBasin(row, col - 1, basin);
        scanBasin(row, col + 1, basin);
        scanBasin(row - 1, col, basin);
        scanBasin(row + 1, col, basin);

        return basin;
    }

    private boolean isInBounds(int row, int col) {
        return row >= 0 && row < heightmap.length && col >= 0 && col < heightmap[0].length;
    }
}
