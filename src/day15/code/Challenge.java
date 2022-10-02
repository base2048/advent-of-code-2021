package day15.code;

import utils.*;

import java.util.*;

public class Challenge implements Solvable {

    private final int[][] riskLevels;
    private final int[][] deltas = new int[][]{{-1, 0}, {0, -1}, {1, 0}, {0, 1}};

    public Challenge(int[][] riskLevels) {
        this.riskLevels = riskLevels;
    }

    @Override
    public long solvePart1() {
        int[][] workingGrid = Arrays.stream(riskLevels).map(int[]::clone).toArray(int[][]::new);
        return danceLikeDijkstra(workingGrid);
    }

    @Override
    public long solvePart2() {
        int[][] workingGrid = enhanceGrid(riskLevels);
        return danceLikeDijkstra(workingGrid);
    }

    private long danceLikeDijkstra(int[][] costs) {
        int width = costs[0].length;
        int height = costs.length;

        int[][] distance = new int[height][width];
        for (int[] row : distance)
            Arrays.fill(row, Integer.MAX_VALUE);

        distance[0][0] = 0;

        Queue<Integer> queue = new PriorityQueue<>(width * height, Comparator.comparingInt(cell -> cell & (1 << 14) - 1));
        queue.add(0);

        while (!queue.isEmpty()) {

            int cell = queue.poll();
            int cur_x = cell >>> 23;
            int cur_y = cell >>> 14 & (1 << 9) - 1;

            for (int[] delta : deltas) {
                int adj_x = cur_x + delta[0];
                int adj_y = cur_y + delta[1];

                if (!isInBounds(adj_x, adj_y, width, height)) continue;
                if (distance[adj_y][adj_x] <= distance[cur_y][cur_x] + costs[adj_y][adj_x]) continue;

                if (distance[adj_y][adj_x] != Integer.MAX_VALUE)
                    queue.remove(adj_x << 23 | adj_y << 14 | distance[adj_y][adj_x]);

                distance[adj_y][adj_x] = distance[cur_y][cur_x] + costs[adj_y][adj_x];
                queue.add(adj_x << 23 | adj_y << 14 | distance[adj_y][adj_x]);
            }
        }

        return distance[height - 1][width - 1];
    }

    private int[][] enhanceGrid(int[][] grid) {
        int[][] workingGrid = new int[grid.length * 5][grid[0].length * 5];

        for (int i = 0; i < grid.length; i++)
            for (int j = 0; j < grid[0].length; j++)
                for (int k = 0; k < 5; k++)
                    for (int l = 0; l < 5; l++)
                        workingGrid[i + k * grid.length][j + l * grid[0].length] = (grid[i][j] + k + l - 1) % 9 + 1;

        return workingGrid;
    }

    private boolean isInBounds(int x, int y, int width, int height) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }
}
