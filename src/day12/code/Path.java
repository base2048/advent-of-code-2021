package day12.code;

import java.util.*;

public class Path {

    final Deque<String> path = new ArrayDeque<>();
    final Set<String> visitedSmallCaves = new HashSet<>();
    boolean visitedSingleSmallCaveTwice = false;

    private Path(Path path) {
        this.path.addAll(path.path);
        this.visitedSmallCaves.addAll(path.visitedSmallCaves);
        this.visitedSingleSmallCaveTwice = path.visitedSingleSmallCaveTwice;
    }

    Path(String cave) {
        this.path.addLast(cave);
    }

    Path addCave(String cave) {
        path.addLast(cave);
        if (cave.equals(cave.toLowerCase()))
            visitedSingleSmallCaveTwice |= !visitedSmallCaves.add(cave);
        return this;
    }

    String getLastCave() {
        return path.getLast();
    }

    @Override @SuppressWarnings("all")
    protected Path clone() {
        return new Path(this);
    }
}
