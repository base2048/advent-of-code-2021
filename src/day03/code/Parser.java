package day03.code;

import utils.AbstractParser;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Parser extends AbstractParser {

    @Override @SuppressWarnings("unchecked")
    protected <T> T parse(InputStream is) {
        try (Scanner scn = new Scanner(is, StandardCharsets.UTF_8)) {

            return (T) scn.useDelimiter("\\R").tokens()
                    .map(token -> Arrays.stream(token.split(""))
                            .mapToInt(Integer::parseInt)
                            .toArray())
                    .toList();
        }
    }
}
