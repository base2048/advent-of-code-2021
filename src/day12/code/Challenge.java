package day12.code;

import utils.*;

import java.util.*;

import static utils.Part.*;

public class Challenge implements Solvable {

    private final Map<String, List<String>> caveToNeighbors;

    public Challenge(List<String[]> connections) {
        this.caveToNeighbors = buildUpMaze(connections);
    }

    @Override
    public long solvePart1() {
        return findPaths(PART1).size();
    }

    @Override
    public long solvePart2() {
        return findPaths(PART2).size();
    }

    private List<Path> findPaths(Part part) {
        List<Path> paths = new ArrayList<>();
        Deque<Path> scraper = new ArrayDeque<>();
        scraper.addLast(new Path("start"));

        while (!scraper.isEmpty()) {
            Path path = scraper.removeFirst();

            for (String cave : caveToNeighbors.get(path.getLastCave())) {
                if (cave.equals("start")) continue;
                if (cave.equals("end")) {
                    paths.add(path.clone().addCave(cave));
                    continue;
                }

                if (part == PART1 && path.visitedSmallCaves.contains(cave)) continue;
                if (part == PART2 && path.visitedSmallCaves.contains(cave) && path.visitedSingleSmallCaveTwice)  continue;

                scraper.addLast(path.clone().addCave(cave));
            }
        }

        return paths;
    }

    private Map<String, List<String>> buildUpMaze(List<String[]> connections) {
        Map<String, List<String>> caveToNeighbors = new HashMap<>();

        for (String[] caves : connections) {
            String cave1 = caves[0];
            String cave2 = caves[1];

            caveToNeighbors.merge(cave1, new ArrayList<>(List.of(cave2)), (oldVal, newVal) -> {
                oldVal.addAll(newVal);
                return oldVal;
            });

            caveToNeighbors.merge(cave2, new ArrayList<>(List.of(cave1)), (oldVal, newVal) -> {
                oldVal.addAll(newVal);
                return oldVal;
            });
        }

        return caveToNeighbors;
    }
}
