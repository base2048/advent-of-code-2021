package day23.code;

import org.junit.jupiter.api.*;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ChallengeTest {

    final String data = """
            #############
            #...........#
            ###B#C#B#D###
              #A#D#C#A#
              #########
            """;

    final ByteArrayInputStream bais = new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8));
    final Challenge challenge = new Challenge(new Parser().parse(bais));

    @Test
    @Order(10)
    void solvePart1() {
        assertEquals(12521, challenge.solvePart1());
    }

    @Test
    @Order(20)
    void solvePart2() {
        assertEquals(44169, challenge.solvePart2());
    }
}
