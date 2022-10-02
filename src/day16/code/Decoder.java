package day16.code;

import java.io.*;
import java.math.BigInteger;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Decoder {

    private int cursor = 0;

    private final InputStream bais;
    private final InputStreamReader isr;
    private final CharBuffer cb = CharBuffer.allocate(16);

    Decoder(String transmission) {
        String binary = convertToBinary(transmission);

        this.bais = new ByteArrayInputStream(binary.getBytes(StandardCharsets.UTF_8));
        this.isr = new InputStreamReader(bais);
    }

    Packet decode() {
        try (bais; isr) { return decodePacket(); }
        catch (IOException ignored) { return null; }
    }

    private Packet decodePacket() {
        int version = parseNext(3);
        int typeId = parseNext(3);

        if (typeId == 4) return new Packet(version, typeId, extractLiteral(0));

        List<Packet> payload = new ArrayList<>();
        int lengthTypeId = parseNext(1);

        if (lengthTypeId == 0) {
            int packetsLength = parseNext(15);
            int target = cursor + packetsLength;

            while (cursor < target)
                payload.add(decodePacket());
        }

        if (lengthTypeId != 0) {
            int packetsCount = parseNext(11);

            while (payload.size() < packetsCount)
                payload.add(decodePacket());
        }

        return new Packet(version, typeId, payload);
    }

    private int parseNext(int length) {
        try { cursor += isr.read(cb.limit(length)); }
        catch (IOException ignored) {}

        return Integer.parseInt(cb.flip().toString(), 2);
    }

    private long extractLiteral(long literal) {
        if (parseNext(1) == 0) return literal << 4 | parseNext(4);

        return extractLiteral(literal << 4 | parseNext(4));
    }

    private String convertToBinary(String hex) {
        String format = "%" + hex.length() * 4 + "s";
        String binary = new BigInteger(hex, 16).toString(2);

        return String.format(format, binary).replace(' ', '0');
    }
}
