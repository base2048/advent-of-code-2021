package day13.code;

import utils.AbstractParser;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Parser extends AbstractParser {

    static class Data {
        final Sheet sheet;
        final List<Instruction> instructions;

        Data(Sheet sheet, List<Instruction> instructions) {
            this.sheet = sheet;
            this.instructions = instructions;
        }
    }

    @Override @SuppressWarnings("unchecked")
    protected <T> T parse(InputStream is) {

        try (Scanner scn = new Scanner(is, StandardCharsets.UTF_8)) {

            int width = 0;
            int height = 0;
            Map<Integer, Set<Integer>> dotsByRow = new HashMap<>();

            for (String line; scn.hasNextLine() && !(line = scn.nextLine()).isBlank(); ) {
                String[] dot = line.split(",");
                int col = Integer.parseInt(dot[0]);
                int row = Integer.parseInt(dot[1]);

                width = Math.max(col, width);
                height = Math.max(row, height);

                dotsByRow.computeIfAbsent(row, unused -> new HashSet<>()).add(col);
            }

            Sheet sheet = new Sheet(width, height, dotsByRow);
            List<Instruction> instructions = new ArrayList<>();

            while (scn.hasNextLine()) {
                String[] instr = scn.nextLine().split(" ")[2].split("=");
                instructions.add(new Instruction(
                        instr[0].equals("x") ? Instruction.Command.FOLD_X : Instruction.Command.FOLD_Y,
                        Integer.parseInt(instr[1])));
            }

            return (T) new Data(sheet, instructions);
        }
    }
}
