package day11.code;

import org.junit.jupiter.api.*;

import java.io.*;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ChallengeTest {

    final String data = """
            5483143223
            2745854711
            5264556173
            6141336146
            6357385478
            4167524645
            2176841721
            6882881134
            4846848554
            5283751526
            """;

    final InputStream bais = new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8));
    final Challenge challenge = new Challenge(new Parser().parse(bais));

    @Test
    @Order(10)
    void solvePart1() {
        assertEquals(1656, challenge.solvePart1());
    }

    @Test
    @Order(20)
    void solvePart2() {
        assertEquals(195, challenge.solvePart2());
    }
}
