package day08.code;

import utils.AbstractParser;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Parser extends AbstractParser {

    @Override @SuppressWarnings("unchecked")
    protected <T> T parse(InputStream is) {
        try (Scanner scn = new Scanner(is, StandardCharsets.UTF_8)) {
            return (T) scn.useDelimiter("\\R").tokens().map(this::parseDisplay).toList();
        }
    }

    private Display parseDisplay(String display) {
        String[] tokens = display.split("\\s\\|\\s");

        List<Digit> samples = Arrays.stream(tokens[0].split("\\s"))
                .map(this::parseDigitToSegmentMap)
                .map(Digit::new)
                .toList();

        List<Digit> outputs = Arrays.stream(tokens[1].split("\\s"))
                .map(this::parseDigitToSegmentMap)
                .map(Digit::new)
                .toList();

        return new Display(samples, outputs);
    }

    private int parseDigitToSegmentMap(String digit) {
        return digit.chars().reduce(0, (segments, segment) -> segments |= 1 << segment - 'a');
    }
}
