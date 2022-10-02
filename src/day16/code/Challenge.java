package day16.code;

import utils.*;

public class Challenge implements Solvable {

    private final Packet packet;

    public Challenge(String transmission) {
        this.packet = new Decoder(transmission).decode();
    }

    @Override
    public long solvePart1() {
        return packet.sumVersionNumbers();
    }

    @Override
    public long solvePart2() {
        return packet.evaluate();
    }
}
