package day17.code;

import utils.AbstractParser;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.*;

public class Parser extends AbstractParser {

    static class Data {
        int xMin, xMax, yMin, yMax;

        public Data(int xMin, int xMax, int yMin, int yMax) {
            this.xMin = xMin;
            this.xMax = xMax;
            this.yMin = yMin;
            this.yMax = yMax;
        }
    }

    @Override @SuppressWarnings("unchecked")
    protected <T> T parse(InputStream is) {
        try (Scanner scn = new Scanner(is, StandardCharsets.UTF_8)) {
            String data = scn.useDelimiter("\\Z").next();

            Pattern pattern = Pattern.compile("^target area: x=([-\\d]*)\\.{2}([-\\d]*), y=([-\\d]*)\\.{2}([-\\d]*)$");
            Matcher matcher = pattern.matcher(data);
            matcher.find();

            return (T) new Data(
                    Integer.parseInt(matcher.group(1)),
                    Integer.parseInt(matcher.group(2)),
                    Integer.parseInt(matcher.group(3)),
                    Integer.parseInt(matcher.group(4)));
        }
    }
}
