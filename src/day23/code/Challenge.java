package day23.code;

import utils.Solvable;

import java.util.*;

public class Challenge implements Solvable {

    /*
     *   Positions Array:
     *
     *   Side Wing       Side Wing
     *   ┌↓ ↓     Hallway   ↓ ↓ ─                     Rooms                     ┐
     *    0|1|2|3|4|5|6|7|8|9|10║11|12|13|14║15|16|17|18║19|20|21|22║23|24|25|26
     *   └    ↑   ↑   ↑   ↑     ─   Room 0      Room 1      Room 2      Room 3  ┘
     *      Entrance Positions
     *        (= No Parking)
     */

    private final int HALLWAY_SIZE = 11;
    private final int SIDE_WING_SIZE = 2;
    private final int ROOM_SIZE = 4;

    private final int EMPTY = 0;
    private final int AMBER = 1;
    private final int BRONZE = 2;
    private final int COPPER = 3;
    private final int DESERT = 4;
    private final int OFFSET = 1;

    private int POD_COUNT;
    private final int TYPE_COUNT = 4;
    private final int ROOM_COUNT = 4;
    private final int ONE_STEP = 1;
    private final int DISCARD = -1;

    private final int[] COSTS = {0, 1, 10, 100, 1000};
    private final boolean[] NO_PARKING = {false, false, true, false, true, false, true, false, true, false, false};

    private final int[] initialPositions;

    public Challenge(int[] initialPositions) {
        this.initialPositions = initialPositions;
    }

    @Override
    public long solvePart1() {
        POD_COUNT = 8;
        int[] targetPositions = {
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                AMBER, AMBER, 0, 0,
                BRONZE, BRONZE, 0, 0,
                COPPER, COPPER, 0, 0,
                DESERT, DESERT, 0, 0};

        State start = new State(initialPositions, 0);
        State target = new State(targetPositions, 0);

        return playAmongTheStars(start, target);
    }

    @Override
    public long solvePart2() {
        POD_COUNT = 16;

        /* Relocate amphipods. */
        int fromRoomSpot = 1;
        int toRoomSpot = 3;
        int[] positions = Arrays.copyOf(this.initialPositions, this.initialPositions.length);
        for (int room = 0; room < 4; room++)
            positions[HALLWAY_SIZE + room * ROOM_SIZE + toRoomSpot] = positions[HALLWAY_SIZE + room * ROOM_SIZE + fromRoomSpot];

        /* Inject amphipods. */
        int[] extraPods = {
                DESERT, COPPER, BRONZE, AMBER,
                DESERT, BRONZE, AMBER, COPPER};

        for (int room = 0; room < 4; room++)
            for (int spot = 1; spot <= 2; spot++)
                positions[HALLWAY_SIZE + room * ROOM_SIZE + spot] = extraPods[room + (spot - 1) * ROOM_SIZE];

        State start = new State(positions, 0);

        int[] targetPositions = {
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                AMBER, AMBER, AMBER, AMBER,
                BRONZE, BRONZE, BRONZE, BRONZE,
                COPPER, COPPER, COPPER, COPPER,
                DESERT, DESERT, DESERT, DESERT};

        State target = new State(targetPositions, 0);

        return playAmongTheStars(start, target);
    }

    private long playAmongTheStars(State start, State target) {
        final Queue<State> open = new PriorityQueue<>();
        final Map<State, Integer> closed = new HashMap<>();

        open.offer(start);
        int minCost = Integer.MAX_VALUE;

        while (!open.isEmpty() && open.peek().cost < minCost) {
            State current = open.poll();

            /* Scan hallway. */
            for (int hallwayPos = 0; hallwayPos < HALLWAY_SIZE; hallwayPos++) {
                if (NO_PARKING[hallwayPos]) continue;

                int type = current.positions[hallwayPos];
                if (type == EMPTY) continue;

                /* Find target room. */
                int targetRoomType = type - OFFSET;
                /* x2 because the entries to the 4 rooms branch off every second spot. */
                int entrancePos = SIDE_WING_SIZE + 2 * targetRoomType;

                /* Homecoming possible? */
                if (!isHallwayFree(hallwayPos, entrancePos, current.positions)) continue;
                int nextRoomPos = findRoomPosition(type, current.positions);
                if (nextRoomPos == -1) continue;

                /* Moving from hallway to room. */
                State nextState = move(hallwayPos, nextRoomPos, type, current);

                if (closed.getOrDefault(nextState, Integer.MAX_VALUE) > closed.merge(nextState, nextState.cost, Math::min))
                    open.offer(nextState);

                /* We can't break here. Maybe there are other states
                   with lower costs waiting to be processed. */
                if (nextState.equals(target))
                    minCost = Math.min(minCost, nextState.cost);
            }

            /* Scan rooms. */
            for (int roomsPos = HALLWAY_SIZE; roomsPos < current.positions.length; roomsPos++) {
                int type = current.positions[roomsPos];
                if (type == EMPTY) continue;
                if (canAmphipodStay(type, roomsPos, current.positions)) continue;

                int[] nextHallwayPositions = findHallwayPositions(roomsPos, current.positions);

                /* Moving from room to each possible hallway position. */
                for (int nextHallwayPos : nextHallwayPositions) {
                    if (nextHallwayPos == DISCARD) continue;

                    /* Moving from room to hallway. */
                    State nextState = move(roomsPos, nextHallwayPos, type, current);

                    if (closed.getOrDefault(nextState, Integer.MAX_VALUE) > closed.merge(nextState, nextState.cost, Math::min))
                        open.offer(nextState);
                }
            }
        }

        return minCost;
    }

    private int findRoomPosition(int podType, int[] positions) {
        int targetRoomType = podType - OFFSET;
        int targetRoomFirstSpotPos = HALLWAY_SIZE + targetRoomType * ROOM_SIZE;
        int podsPerRoom = POD_COUNT / ROOM_COUNT;

        int nextRoomPos = -1;
        for (int roomPos = targetRoomFirstSpotPos + podsPerRoom - 1; roomPos >= targetRoomFirstSpotPos; roomPos--) {
            if (positions[roomPos] == podType) continue;
            if (positions[roomPos] == EMPTY)
                nextRoomPos = roomPos;

            break;
        }

        return nextRoomPos;
    }

    private int[] findHallwayPositions(int roomsPos, int[] positions) {
        int roomType = (roomsPos - HALLWAY_SIZE) / 4;

        /* Is any amphipod blocking the way to the hallway? */
        int roomFirstSpotIndex = HALLWAY_SIZE + roomType * ROOM_SIZE;
        for (int roomPos = roomsPos - 1; roomPos >= roomFirstSpotIndex; roomPos--)
            if (positions[roomPos] != EMPTY) return new int[0];

        int[] hallwayPositions = new int[HALLWAY_SIZE];
        Arrays.fill(hallwayPositions, DISCARD);

        /* Check path for each hallway position to entrance spot. */
        int entrancePos = SIDE_WING_SIZE + 2 * roomType;
        for (int hallwayPos = 0; hallwayPos < HALLWAY_SIZE; hallwayPos++)
            if (!NO_PARKING[hallwayPos] && isHallwayFree(entrancePos, hallwayPos, positions))
                hallwayPositions[hallwayPos] = hallwayPos;

        return hallwayPositions;
    }

    private boolean isHallwayFree(int fromPos, int toPos, int[] positions) {
        /* Exclude fromPos since we assume this tile either being empty
           or taken by the amphipod which wants to move. */
        fromPos += fromPos - toPos > 0 ? -1 : +1;

        int from = Math.min(fromPos, toPos);
        int to = Math.max(fromPos, toPos);

        for (int pos = from; pos <= to; pos++)
            if (positions[pos] != EMPTY) return false;

        return true;
    }

    private boolean canAmphipodStay(int podType, int roomsPos, int[] positions) {
        int roomType = (roomsPos - HALLWAY_SIZE) / 4;

        /* Is the amphipod in its home room? */
        if (roomType != podType - OFFSET) return false;

        /* Is the amphipod blocking others who are not on their proper spot? */
        int podsPerRoom = POD_COUNT / TYPE_COUNT;
        for (int roomPos = roomsPos + 1; roomPos < HALLWAY_SIZE + roomType * ROOM_SIZE + podsPerRoom; roomPos++)
            if (positions[roomPos] - OFFSET != roomType) return false;

        return true;
    }

    private State move(int fromPos, int toPos, int type, State current) {
        int[] nextPositions = getNextPositions(fromPos, toPos, type, current.positions);
        int cost = calculateCosts(type, fromPos, toPos);

        return new State(nextPositions, current.cost + cost);
    }

    private int[] getNextPositions(int clearPos, int setPos, int type, int[] positions) {
        int[] nextPositions = Arrays.copyOf(positions, positions.length);
        nextPositions[clearPos] = 0;
        nextPositions[setPos] = type;
        return nextPositions;
    }

    private int calculateCosts(int podType, int fromPos, int toPos) {
        int hallwayPos = Math.min(fromPos, toPos);
        int roomsPos = Math.max(fromPos, toPos);
        int roomType = (roomsPos - HALLWAY_SIZE) / 4;
        /* x2 because the entries to the 4 rooms branch off every second spot. */
        int entrancePos = SIDE_WING_SIZE + 2 * roomType;
        int roomFirstSpotPos = HALLWAY_SIZE + roomType * ROOM_SIZE;

        int hallwaySteps = Math.abs(hallwayPos - entrancePos);
        int roomSteps = roomsPos - roomFirstSpotPos;
        int steps = hallwaySteps + roomSteps + ONE_STEP;

        return steps * COSTS[podType];
    }
}
