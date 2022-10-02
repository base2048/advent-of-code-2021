package day05.code;

import utils.AbstractParser;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.*;

public class Parser extends AbstractParser {

    private final Pattern coordsPAT = Pattern.compile("^(\\d+),(\\d+)\\D+?(\\d+),(\\d+)$");

    @Override @SuppressWarnings("unchecked")
    protected <T> T parse(InputStream is) {
        try (Scanner scn = new Scanner(is, StandardCharsets.UTF_8)) {
            return (T) scn.useDelimiter("\\R").tokens().map(this::parseEndpoints).toList();
        }
    }

    private int[][] parseEndpoints(String token) {
        Matcher matcher = coordsPAT.matcher(token);
        matcher.find();
        return new int[][]{
                { Integer.parseInt(matcher.group(1)),  Integer.parseInt(matcher.group(2)) },
                { Integer.parseInt(matcher.group(3)),  Integer.parseInt(matcher.group(4)) }};
    }
}
