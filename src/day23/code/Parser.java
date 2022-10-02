package day23.code;

import utils.AbstractParser;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.stream.Collector;

public class Parser extends AbstractParser {

    @Override @SuppressWarnings("unchecked")
    protected <T> T parse(InputStream is) {
        try (Scanner scn = new Scanner(is, StandardCharsets.UTF_8)) {

            int[] positions = scn.useDelimiter("\\R").tokens()
                    .skip(2)
                    .limit(2)
                    .map(this::parseRow)
                    .collect(toPositions());

            return (T) positions;
        }
    }

    private int[] parseRow(String row) {
        return row.trim().chars().filter(ch -> ch != '#').map(ch -> ch - 'A' + 1).toArray();
    }

    private Collector<int[], ?, int[]> toPositions() {
        class Positions {

            final int HALLWAY_SIZE = 11;
            final int ROOM_SIZE = 4;
            final int ROOM_COUNT = 4;

            final int[] positions = new int[ROOM_COUNT * ROOM_SIZE + HALLWAY_SIZE];
            int roomSpot = 0;

            void place(int[] types) {
                for (int room = 0; room < 4; room++)
                    positions[HALLWAY_SIZE + room * ROOM_SIZE + roomSpot] = types[room];

                roomSpot++;
            }

            Positions combine(Positions other) {
                return this;
            }

            int[] finish() {
                return positions;
            }
        }

        return Collector.of(Positions::new, Positions::place, Positions::combine, Positions::finish, Collector.Characteristics.UNORDERED);
    }
}
