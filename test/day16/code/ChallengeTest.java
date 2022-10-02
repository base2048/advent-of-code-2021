package day16.code;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ChallengeTest {

    @Order(10)
    @DisplayName("Part1")
    @ParameterizedTest(name = "{index}")
    @MethodSource("dataSupplier")
    void solvePart1(String data, int expected) {
        InputStream bais = new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8));
        assertEquals(expected, new Challenge(new Parser().parse(bais)).solvePart1());
    }

    @Order(20)
    @DisplayName("Part2")
    @ParameterizedTest(name = "{index}")
    @CsvSource(value = {
            "C200B40A82, 3", "04005AC33890, 54", "880086C3E88112, 7", "CE00C43D881120, 9",
            "D8005AC2A8F0, 1", "F600BC2D8F, 0", "9C005AC2F8F0, 0", "9C0141080250320F1802104A08, 1"})
    void solvePart2(String data, int expected) {
        InputStream bais = new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8));
        assertEquals(expected, new Challenge(new Parser().parse(bais)).solvePart2());
    }

    static Stream<Arguments> dataSupplier() {
        return Stream.of(
          Arguments.of("8A004A801A8002F478", 16),
          Arguments.of("620080001611562C8802118E34", 12),
          Arguments.of("C0015000016115A2E0802F182340", 23),
          Arguments.of("A0016C880162017C3686B18A3D4780", 31)
        );
    }
}
