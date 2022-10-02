package day22.code;

import utils.AbstractParser;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.regex.*;

public class Parser extends AbstractParser {

    final Pattern cuboidPAT = Pattern.compile("^(on|off) x=(-?\\d+)\\.{2}(-?\\d+),y=(-?\\d+)\\.{2}(-?\\d+),z=(-?\\d+)\\.{2}(-?\\d+)$");

    @Override @SuppressWarnings("unchecked")
    protected <T> T parse(InputStream is) {
        try (Scanner scn = new Scanner(is, StandardCharsets.UTF_8)) {

            return (T) scn.useDelimiter("\\R").tokens()
                    .map(cuboidPAT::matcher)
                    .peek(Matcher::find)
                    .map(this::buildCuboid)
                    .toList();
        }
    }

    private Cuboid buildCuboid(Matcher matcher) {
        int state = matcher.group(1).equals("on") ? 1 : 0;
        int x1 = Integer.parseInt(matcher.group(2));
        int x2 = Integer.parseInt(matcher.group(3));
        int y1 = Integer.parseInt(matcher.group(4));
        int y2 = Integer.parseInt(matcher.group(5));
        int z1 = Integer.parseInt(matcher.group(6));
        int z2 = Integer.parseInt(matcher.group(7));

        return new Cuboid(state, x1, x2, y1, y2, z1, z2);
    }
}
