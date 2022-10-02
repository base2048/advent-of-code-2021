package day02.code;

import utils.AbstractParser;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Parser extends AbstractParser {

    @Override @SuppressWarnings("unchecked")
    protected <T> T parse(InputStream is) {
        try (Scanner scn = new Scanner(is, StandardCharsets.UTF_8)) {
            return (T) scn.useDelimiter("\\R").tokens().map(this::parseCommand).toList();
        }
    }

    private Command parseCommand(String command) {
        String[] tokens = command.split(" ");
        return new Command(Command.Instruction.valueOf(tokens[0].toUpperCase()), Integer.parseInt(tokens[1]));
    }
}
