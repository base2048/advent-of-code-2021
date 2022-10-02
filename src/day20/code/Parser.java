package day20.code;

import utils.AbstractParser;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Parser extends AbstractParser {

    static class Data {
        int[] algorithm;
        int[][] viewport;
        Set<Integer> lightPixels;

        Data(int[] algorithm, Set<Integer> lightPixels, int[][] viewport) {
            this.algorithm = algorithm;
            this.lightPixels = lightPixels;
            this.viewport = viewport;
        }
    }

    @Override @SuppressWarnings("unchecked")
    protected <T> T parse(InputStream is) {

        try (Reader reader = new InputStreamReader(is, StandardCharsets.UTF_8);
            LineNumberReader lnr = new LineNumberReader(reader)) {

            int[] algorithm = lnr.readLine().chars().map(ch -> ch == '#' ? 1 : 0).toArray();

            lnr.readLine();
            lnr.setLineNumber(-1);

            Set<Integer> lightPixels = new HashSet<>();
            int offset = 32767;
            int width = 0;
            int height = 0;

            for (String line; (line = lnr.readLine()) != null; ) {

                int[] row = line.chars().map(ch -> ch == '#' ? 1 : 0).toArray();
                int y = lnr.getLineNumber();

                for (int x = 0; x < row.length; x++) {
                    if (row[x] == 1)
                        lightPixels.add(x - row.length / 2 + offset << 16 | y - row.length / 2 + offset);
                        /* We try to center the grid, also on the y-axis.
                           Since we don't know what the final height of the grid will be,
                           we use the width for y-axis centering as well, assuming the grid is square. */
                }

                width = row.length;
                height = y + 1;
            }

            return (T) new Data(algorithm, lightPixels, new int[][]{
                    {offset - width / 2, offset - width / 2},                       /* upper left corner */
                    {offset - width / 2 + width, offset - width / 2 + height}});    /* lower right corner */

        } catch (IOException e) {
            e.printStackTrace(); return null;
        }
    }
}
