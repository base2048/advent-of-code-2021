package day23.code;

import java.util.Arrays;

class State implements Comparable<State> {

    final int cost;
    final int[] positions;

    State(int[] positions, int cost) {
        this.positions = positions;
        this.cost = cost;
    }

    @Override
    public int compareTo(State other) {
        return Long.compare(this.cost, other.cost);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        State state = (State) o;

        return Arrays.equals(positions, state.positions);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(positions);
    }
}
