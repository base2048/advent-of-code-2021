package day20.code;

import utils.Solvable;

import java.util.*;

public class Challenge implements Solvable {

    private final int[] algorithm;
    private final int[][] viewport; /* [upper left x, y][lower right x, y] */
    private final Set<Integer> lightPixels;

    public Challenge(Parser.Data data) {
        this.algorithm = data.algorithm;
        this.viewport = data.viewport;
        this.lightPixels = data.lightPixels;
    }

    @Override
    public long solvePart1() {
        return enhanceImage(new HashSet<>(lightPixels), 2).size();
    }

    @Override
    public long solvePart2() {
        return enhanceImage(new HashSet<>(lightPixels), 50).size();
    }

    private Set<Integer> enhanceImage(Set<Integer> lightPixels, int cycles) {
        int background = 0;
        boolean toggleBackground = algorithm[0] == 1 && algorithm[algorithm.length - 1] == 0;

        for (int i = 0; i < cycles; i++) {
            Set<Integer> newState = new HashSet<>(lightPixels.size());  /* approximate guess of size */

            for (int x = viewport[0][0] - 1; x < viewport[1][0] + 1; x++) {
                for (int y = viewport[0][1] - 1; y < viewport[1][1] + 1; y++) {
                    int binary = 0;

                    for (int l = y - 1; l <= y + 1; l++) {
                        for (int k = x - 1; k <= x + 1; k++) {

                            int bit = isWithinViewport(k, l)
                                    ? lightPixels.contains(k << 16 | l) ? 1 : 0
                                    : background;

                            binary = binary << 1 | bit;
                        }
                    }

                    if (algorithm[binary] == 1)
                        newState.add(x << 16 | y);
                }
            }

            if (toggleBackground) background ^= 1;

            viewport[0][0]--; viewport[0][1]--;
            viewport[1][0]++; viewport[1][1]++;

            lightPixels = newState;
        }

        /* To print image uncomment next line. */
        /* printImage(lightPixels); */

        return lightPixels;
    }

    private boolean isWithinViewport(int x, int y) {
        return x >= viewport[0][0] && x < viewport[1][0] && y >= viewport[0][1] && y < viewport[1][1];
    }

    private void printImage(Set<Integer> lightPixels) {
        for (int y = viewport[0][1]; y < viewport[1][1]; y++) {
            for (int x = viewport[0][0]; x < viewport[1][0]; x++)
                System.out.print(lightPixels.contains(x << 16 | y & 0xFFFF) ? "#" : ".");

            System.out.println();
        }
    }
}
