package day25.code;

import utils.AbstractParser;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collector;

public class Parser extends AbstractParser {

    @Override @SuppressWarnings("unchecked")
    protected <T> T parse(InputStream is) {

        try (Scanner scn = new Scanner(is, StandardCharsets.UTF_8)) {
            List<byte[]> rows = scn.useDelimiter("\\R").tokens().map(this::parseRow).toList();

            byte[][] grid  = new byte[rows.size()][];
            for (int i = 0; i < rows.size(); i++)
                grid[i] = rows.get(i);

            return (T) grid ;
        }
    }

    private byte[] parseRow(String row) {
        return row.chars()
                .mapToObj(ch -> (byte) ch)
                .collect(Collector.of(
                        ByteArrayOutputStream::new,
                        ByteArrayOutputStream::write,
                        (baos1, baos2) -> {
                            try { baos2.writeTo(baos1); return baos1; }
                            catch (IOException e) { e.printStackTrace(); return null; }
                        },
                        ByteArrayOutputStream::toByteArray));
    }
}
