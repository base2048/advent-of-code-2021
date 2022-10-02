package day11.code;

import utils.AbstractParser;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Parser extends AbstractParser {

    @Override @SuppressWarnings("unchecked")
    protected <T> T parse(InputStream is) {
        try (Scanner scn = new Scanner(is, StandardCharsets.UTF_8)) {
            return (T) scn.useDelimiter("\\R").tokens().map(this::parseLine).toArray(int[][]::new);
        }
    }

    private int[] parseLine(String line) {
        return line.chars().map(ch -> ch - '0').toArray();
    }
}
