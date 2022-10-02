package day14.code;

import utils.AbstractParser;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

public class Parser extends AbstractParser {

    static class Data {
        final String template;
        final Map<String, String> insertions;

        Data(String template, Map<String, String> insertions) {
            this.template = template;
            this.insertions = insertions;
        }
    }

    @Override @SuppressWarnings("unchecked")
    protected <T> T parse(InputStream is) {
        try (Scanner scn = new Scanner(is, StandardCharsets.UTF_8)) {

            String template = scn.nextLine();
            scn.nextLine();

            Map<String, String> insertions = scn.useDelimiter("\\R").tokens()
                    .map(ins -> ins.split(" -> "))
                    .collect(Collectors.toMap(ins -> ins[0], ins -> ins[1]));

            return (T) new Data(template, insertions);
        }
    }
}
