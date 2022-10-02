package day22.code;

import java.util.*;

public class Cuboid {

    static final int ON = 1;
    static final int OFF = 0;
    static final int CORRECTION = -1;

    final int state, xMin, xMax, yMin, yMax, zMin, zMax;

    Cuboid(int state, int xMin, int xMax, int yMin, int yMax, int zMin, int zMax) {
        this.state = state;
        this.xMin = xMin;
        this.xMax = xMax;
        this.yMin = yMin;
        this.yMax = yMax;
        this.zMin = zMin;
        this.zMax = zMax;
    }

    long countCubes() {
        return (long) (xMax - xMin + 1) * (yMax - yMin + 1) * (zMax - zMin + 1);
    }

    Optional<Cuboid> sliceIntersection(Cuboid other) {
        int overlappingMinX = Math.max(this.xMin, other.xMin);
        int overlappingMaxX = Math.min(this.xMax, other.xMax);
        int overlappingMinY = Math.max(this.yMin, other.yMin);
        int overlappingMaxY = Math.min(this.yMax, other.yMax);
        int overlappingMinZ = Math.max(this.zMin, other.zMin);
        int overlappingMaxZ = Math.min(this.zMax, other.zMax);

        if (
                overlappingMaxX - overlappingMinX >= 0 &&
                overlappingMaxY - overlappingMinY >= 0 &&
                overlappingMaxZ - overlappingMinZ >= 0) return Optional.of(
                        new Cuboid(CORRECTION,
                                overlappingMinX, overlappingMaxX,
                                overlappingMinY, overlappingMaxY,
                                overlappingMinZ, overlappingMaxZ));

        return Optional.empty();
    }
}
