package day24.code;

import utils.Solvable;

import java.util.*;

public class Challenge implements Solvable {

    private final List<int[]> inputs;

    public Challenge(List<int[]> inputs) {
        this.inputs = inputs;
    }

    @Override
    public long solvePart1() {
        return computeModelNumber(true);
    }

    @Override
    public long solvePart2() {
        return computeModelNumber(false);
    }

    private long computeModelNumber(boolean doMaximize) {
        final int BLOCK_REPEAT = 14;

        Deque<int[]> stack = new ArrayDeque<>();
        int[] modelNumber = new int[BLOCK_REPEAT];

        for (int i = 0; i < BLOCK_REPEAT; i++) {
            int x = inputs.get(i)[0];
            int y = inputs.get(i)[1];

            if (x > 0)
                stack.push(new int[]{y, i});

            if (x < 0) {
                int[] inc = stack.pop();
                int y_inc = inc[0];
                int y_inc_idx = inc[1];

                int x_dec = x;
                int bias = y_inc + x_dec;
                int w_inc;

                if (doMaximize) w_inc = bias <= 0 ? 9 : 9 - bias;
                else w_inc = y_inc + x_dec >= 0 ? 1 : 1 - bias;

                modelNumber[y_inc_idx] = w_inc;
                modelNumber[i] = w_inc + bias;
            }
        }

        long mnr = 0;
        for (int digit : modelNumber)
            mnr = mnr * 10 + digit;

        return mnr;
    }
}
