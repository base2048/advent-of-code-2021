package day16.code;

import java.util.*;

class Packet {
    final int version;
    final int typeId;

    long literal = 0;
    List<Packet> payload = new ArrayList<>();

    Packet(int version, int typeId, long literal) {
        this.version = version;
        this.typeId = typeId;
        this.literal = literal;
    }

    Packet(int version, int typeId, List<Packet> payload) {
        this.version = version;
        this.typeId = typeId;
        this.payload = payload;
    }

    long sumVersionNumbers() {
        return version + payload.stream().mapToLong(Packet::sumVersionNumbers).sum();
    }

    long evaluate() {
        return switch (typeId) {
            case 0 -> payload.stream().mapToLong(Packet::evaluate).sum();
            case 1 -> payload.stream().map(Packet::evaluate).reduce((prod, eval) -> prod *= eval).orElse(0L);
            case 2 -> payload.stream().mapToLong(Packet::evaluate).min().orElse(0);
            case 3 -> payload.stream().mapToLong(Packet::evaluate).max().orElse(0);
            case 4 -> literal;
            case 5 -> payload.get(0).evaluate() > payload.get(1).evaluate() ? 1 : 0;
            case 6 -> payload.get(0).evaluate() < payload.get(1).evaluate() ? 1 : 0;
            case 7 -> payload.get(0).evaluate() == payload.get(1).evaluate() ? 1 : 0;
            default -> 0;
        };
    }
}
