package day22.code;

import utils.Solvable;

import java.util.*;

public class Challenge implements Solvable {

    private final List<Cuboid> cuboids;

    public Challenge(List<Cuboid> cuboids) {
        this.cuboids = cuboids;
    }

    @Override
    public long solvePart1() {
        List<Cuboid> initialization = cuboids.stream()
                .filter(c -> c.xMin >= -50 && c.xMax <= 50)
                .filter(c -> c.yMin >= -50 && c.yMax <= 50)
                .filter(c -> c.zMin >= -50 && c.zMax <= 50)
                .toList();

        return rebootCore(initialization);
    }

    @Override
    public long solvePart2() {
        return rebootCore(cuboids);
    }

    private long rebootCore(List<Cuboid> cuboids) {
        long onCounts = 0;
        List<Cuboid> processed = new ArrayList<>(cuboids.size());

        for (Cuboid cuboid : cuboids) {
            if (cuboid.state == Cuboid.ON)
                onCounts += cuboid.countCubes();

            onCounts -= computeOverlapCorrection(cuboid, processed);
            processed.add(cuboid);
        }

        return onCounts;
    }

    private long computeOverlapCorrection(Cuboid cuboid, List<Cuboid> processed) {
        long offCounts = 0;

        for (int i = processed.size() - 1; i >= 0; i--) {
            Optional<Cuboid> correction = cuboid.sliceIntersection(processed.get(i));
            if (correction.isEmpty()) continue;

            offCounts += processed.get(i).state == Cuboid.ON ? correction.get().countCubes() : 0;
            offCounts -= computeOverlapCorrection(correction.get(), processed.subList(0, i));
        }

        return offCounts;
    }
}
