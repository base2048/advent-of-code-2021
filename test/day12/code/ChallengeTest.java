package day12.code;

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
    void solvePart1(String data, int expectedPart1, int expectedPart2) {
        InputStream bais = new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8));
        assertEquals(expectedPart1, new Challenge(new Parser().parse(bais)).solvePart1());
    }

    @Order(20)
    @DisplayName("Part2")
    @ParameterizedTest(name = "{index}")
    @MethodSource("dataSupplier")
    void solvePart2(String data, int expectedPart1, int expectedPart2) {
        InputStream bais = new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8));
        assertEquals(expectedPart2, new Challenge(new Parser().parse(bais)).solvePart2());
    }

    static Stream<Arguments> dataSupplier() {
        final String data1 = """
                start-A
                start-b
                A-c
                A-b
                b-d
                A-end
                b-end
                """;

        final String data2 = """
                dc-end
                HN-start
                start-kj
                dc-start
                dc-HN
                LN-dc
                HN-end
                kj-sa
                kj-HN
                kj-dc
                """;

        final String data3 = """
                fs-end
                he-DX
                fs-he
                start-DX
                pj-DX
                end-zg
                zg-sl
                zg-pj
                pj-he
                RW-he
                fs-DX
                pj-RW
                zg-RW
                start-pj
                he-WI
                zg-he
                pj-fs
                start-RW        
                """;

        return Stream.of(
                Arguments.of(data1, 10, 36),
                Arguments.of(data2, 19, 103),
                Arguments.of(data3, 226, 3509));
    }
}
