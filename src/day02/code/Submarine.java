package day02.code;

import utils.Part;

import java.util.List;
import java.util.function.IntConsumer;

import static utils.Part.*;

public class Submarine {

    private long horizontalPos, depth, aim;
    private IntConsumer tuneUp, tuneDown, tuneForward;

    Submarine runCommands(List<Command> commands) {
        horizontalPos = depth = aim = 0;

        for (Command command : commands)
            switch (command.instr) {
                case UP -> tuneUp.accept(command.arg);
                case DOWN -> tuneDown.accept(command.arg);
                case FORWARD -> tuneForward.accept(command.arg);
            }

        return this;
    }

    Submarine setChallengePart(Part part) {
        tuneUp = part == PART1 ? arg -> depth -= arg : arg -> aim -= arg;
        tuneDown = part == PART1 ? arg -> depth += arg : arg -> aim += arg;
        tuneForward = part == PART1 ? arg -> horizontalPos += arg : arg -> { horizontalPos += arg; depth += aim * arg; };

        return this;
    }

    long getPositionHash() {
        return horizontalPos * depth;
    }
}
