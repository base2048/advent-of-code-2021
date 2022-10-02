package day24.code;

import utils.AbstractParser;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Parser extends AbstractParser {

    @Override @SuppressWarnings("unchecked")
    protected <T> T parse(InputStream is) {
        try (Scanner scn = new Scanner(is, StandardCharsets.UTF_8)) {

            String[] data = scn.useDelimiter("\\R").tokens().toArray(String[]::new);
            List<int[]> inputs = new ArrayList<>();

            final int BLOCK_SIZE = 18;
            final int BLOCK_REPEAT = 14;

            final int X_OFFSET = 5;
            final int Y_OFFSET = 15;

            for (int i = 0; i < BLOCK_REPEAT; i++) {
                int x = Integer.parseInt(data[i * BLOCK_SIZE + X_OFFSET].split(" ")[2]);
                int y = Integer.parseInt(data[i * BLOCK_SIZE + Y_OFFSET].split(" ")[2]);
                inputs.add(new int[]{x, y});
            }

            return (T) inputs;
        }
    }
}
