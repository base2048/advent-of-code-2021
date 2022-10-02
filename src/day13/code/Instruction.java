package day13.code;

public class Instruction {

    enum Command { FOLD_X, FOLD_Y }

    final Command command;
    final int pivot;

    Instruction(Command command, int pivot) {
        this.command = command;
        this.pivot = pivot;
    }
}
