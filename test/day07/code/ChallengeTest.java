package day07.code;

import org.junit.jupiter.api.*;

import java.io.*;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ChallengeTest {

    final String data = "16,1,2,0,4,2,7,1,2,14";

    final InputStream bais = new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8));
    final Challenge challenge = new Challenge(new Parser().parse(bais));

    @Test
    @Order(10)
    void solvePart1() {
        assertEquals(37, challenge.solvePart1());
    }

    @Test
    @Order(20)
    void solvePart2() {
        assertEquals(168, challenge.solvePart2());
    }
}
