package day03.code;

import utils.Solvable;

import java.util.*;

public class Challenge implements Solvable {

    private final int wordSize;
    private final List<int[]> binaries;
    private final int[] bitCountByColumn;

    public Challenge(List<int[]> binaries) {
        this.binaries = binaries;
        this.wordSize = binaries.get(0).length;
        this.bitCountByColumn = countBitsByColumn(binaries);
    }

    @Override
    public long solvePart1() {
        int gammaRate, epsilonRate, threshold = binaries.size() / 2;

        gammaRate = Arrays.stream(bitCountByColumn)
                .map(count -> Boolean.compare(count > threshold, Boolean.FALSE))
                .reduce(0, (acc, cur) -> acc << 1 | cur);

        epsilonRate = gammaRate ^ (1 << wordSize) - 1;

        return (long) gammaRate * epsilonRate;
    }

    @Override
    public long solvePart2() {
        int oxy = computeRating(1, 0);
        int co2 = computeRating(0, 1);

        return (long) oxy * co2;
    }

    private int computeRating(int forMajorityOnes, int forMajorityZeroes) {
        List<int[]> reducer = new ArrayList<>(binaries);
        int[] bitCounts = Arrays.copyOf(bitCountByColumn, bitCountByColumn.length);

        for (int i = 0; i < wordSize && reducer.size() > 1; i++) {
            int keep = bitCounts[i] >= reducer.size() - bitCounts[i] ? forMajorityOnes : forMajorityZeroes;

            for (Iterator<int[]> it = reducer.iterator(); it.hasNext(); ) {
                int[] binary = it.next();
                if (binary[i] == keep) continue;

                for (int j = i; j < wordSize; j++)
                    bitCounts[j] -= binary[j];

                it.remove();
            }
        }

        return Arrays.stream(reducer.get(0)).reduce(0, (acc, cur) -> acc << 1 | cur);
    }

    private int[] countBitsByColumn(List<int[]> binaries) {
        int[] countsByCol = new int[wordSize];

        for (int[] binary : binaries)
            for (int col = 0; col < wordSize; col++)
                countsByCol[col] += binary[col];

        return countsByCol;
    }
}
