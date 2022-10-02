package day02.code;

import utils.*;

import java.util.List;

public class Challenge implements Solvable {

    private final Submarine submarine;
    private final List<Command> commands;

    public Challenge(List<Command> commands) {
        this.submarine = new Submarine();
        this.commands = commands;
    }

    @Override
    public long solvePart1() {
        return submarine.setChallengePart(Part.PART1).runCommands(commands).getPositionHash();
    }

    @Override
    public long solvePart2() {
        return submarine.setChallengePart(Part.PART2).runCommands(commands).getPositionHash();
    }
}
