package day17.code;

import utils.*;

public class Challenge implements Solvable {

    private final int xMin, xMax, yMin, yMax;

    public Challenge(Parser.Data data) {
        this.xMin = data.xMin;
        this.xMax = data.xMax;
        this.yMin = data.yMin;
        this.yMax = data.yMax;
    }

    @Override
    public long solvePart1() {
        int n = Math.abs(yMin) - 1;
        return ((long) n * n + n) / 2;
    }

    @Override
    public long solvePart2() {
        int minXVelocity = (int) Math.ceil((-1 + Math.sqrt(8 * xMin + 1)) / 2);
        int maxXVelocity = xMax;
        int minYVelocity = yMin;
        int maxYVelocity = Math.abs(yMin) - 1;

        long hits = 0;

        for (int xVel = minXVelocity; xVel <= maxXVelocity; xVel++)
            for (int yVel = minYVelocity; yVel <= maxYVelocity; yVel++)
                if (isHittingTarget(xVel, yVel)) hits++;

        return hits;
    }

    private boolean isHittingTarget(int xVel, int yVel) {
        for (int x = xVel, y = yVel;
             x <= xMax && y >= yMin;
             x += xVel > 0 ? --xVel : xVel, y += --yVel) {

            if (isInsideTargetArea(x, y)) return true;
        }

        return false;
    }

    private boolean isInsideTargetArea(int x, int y) {
        return x >= xMin && x <= xMax && y >= yMin && y <= yMax;
    }
}
