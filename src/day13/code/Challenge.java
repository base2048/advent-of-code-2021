package day13.code;

import utils.*;

import java.util.List;

public class Challenge implements Solvable {

    private final Sheet sheet;
    private final List<Instruction> instructions;

    public Challenge(Parser.Data data) {
        this.sheet = data.sheet;
        this.instructions = data.instructions;
    }

    @Override
    public long solvePart1() {
        Instruction instr = instructions.get(0);

        switch (instr.command) {
            case FOLD_X -> sheet.foldX(instr.pivot);
            case FOLD_Y -> sheet.foldY(instr.pivot);
        }

        return sheet.countDots();
    }

    @Override
    public long solvePart2() {
        for (Instruction instr : instructions)
            switch (instr.command) {
                case FOLD_X -> sheet.foldX(instr.pivot);
                case FOLD_Y -> sheet.foldY(instr.pivot);
            }

        sheet.printDots();
        return 0;
    }
}
