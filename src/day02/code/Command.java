package day02.code;

public class Command {

    enum Instruction { UP, DOWN, FORWARD }

    final Instruction instr;
    final int arg;

    Command(Instruction instr, int arg) {
        this.instr = instr;
        this.arg = arg;
    }
}
